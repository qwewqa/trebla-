package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.compile.ValueIRNode
import xyz.qwewqa.trebla.frontend.TreblaFile
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.*

class ScriptDeclaration(override val node: ScriptDeclarationNode, override val declaringContext: TreblaFile) :
    Declaration, ScriptContext, Callable {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC
    override val type = ScriptType
    override val scope = Scope(this, declaringContext.scope)
    override val memoryAllocator = StandardAllocator(21, 64)
    override val dataAllocator = StandardAllocator(22, 32)
    override val sharedAllocator = StandardAllocator(24, 32)

    var index = declaringContext.globalContext.scriptIndex++

    private val initializeCallback = Callback(0, CallbackName.Initialize, this)
    private val initializationContext = ScriptInitializationContext(this, initializeCallback)

    val propertyDeclarations by lazy {
        node.body.value
            .filterIsInstance<PropertyDeclarationNode>()
            .map { it.parse(initializationContext) }
            .filterIsInstance<PropertyDeclaration>()
    }

    val spawnProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.SPAWN }
            .associate { it.identifier to it.applyTo(initializationContext) }
    }

    val dataProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.DATA }
            .associate { it.identifier to it.applyTo(initializationContext) }
    }

    val sharedProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.SHARED }
            .associate { it.identifier to it.applyTo(initializationContext) }
    }

    fun finalize(): ScriptData {
        // make sure lazy properties have fired
        spawnProperties
        dataProperties
        sharedProperties

        val callbacks = mutableListOf(initializeCallback)
        propertyDeclarations.forEach {
            if (it.variant != PropertyVariant.SPAWN && it.variant != PropertyVariant.DATA && it.variant != PropertyVariant.SHARED) it.applyTo(
                initializationContext
            )
        }
        node.body.value.forEach { memberNode ->
            when (memberNode) {
                is PropertyDeclarationNode -> {
                }
                is CallbackDeclarationNode -> CallbackDeclaration(memberNode, this).let {
                    it.applyTo(initializationContext)
                    callbacks += it.getCallback()
                }
                is InitBlockNode -> memberNode.body.value.forEach { it.parseAndApplyTo(initializeCallback) }
                is StatementNode -> memberNode.parseAndApplyTo(initializationContext)
                else -> error("Unsupported script member.") // should not happen
            }
        }
        return ScriptData(
            identifier,
            index,
            callbacks,
            dataProperties.mapValues { (_, v) -> ((((v as RawStructValue).value as AllocatedValue).allocation) as ConcreteAllocation).index },
            (memoryAllocator.index until memoryAllocator.size).toList()
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val cells = MutableList<RawValue?>(64) { null }
        arguments.forEach { (name, value) ->
            if (name == null) compileError("Unnamed arguments to script spawns are not allowed.")
            val prop = spawnProperties[name] ?: compileError("Unknown property $name")
            set(prop, value, cells)
        }
        cells.dropLastWhile { it == null }
        if (callingContext !is ExecutionContext) compileError("Script spawns require an execution context.")
        callingContext.statements += BuiltinCallValue(
            BuiltinFunctionVariant.Spawn,
            listOf(LiteralValue(index.toDouble()).toIR()) + cells.map { it ?: LiteralValue(0.0) }
                .map { it.toIR() }
                .dropLastWhile { it is ValueIRNode && it.value == 0.0 }
        )
        return UnitValue
    }

    fun set(prop: Value?, value: Value?, cells: MutableList<RawValue?>) {
        when {
            value is RawStructValue && prop is RawStructValue -> setRaw(prop, value, cells)
            value is NormalStructValue && prop is NormalStructValue -> setNormal(prop, value, cells)
            else -> compileError("Incompatible types")
        }
    }

    fun setRaw(prop: RawStructValue, value: RawStructValue, cells: MutableList<RawValue?>) {
        cells[((prop.value as AllocatedValue).allocation as ConcreteAllocation).index] = value.value
    }

    fun setNormal(prop: NormalStructValue, value: NormalStructValue, cells: MutableList<RawValue?>) {
        prop.fields.forEach { (name, field) ->
            set(field, value.fields[name], cells)
        }
    }
}

object ScriptType : BuiltinType("Script")

interface ScriptContext : Context {
    val memoryAllocator: Allocator
    val dataAllocator: Allocator
    val sharedAllocator: Allocator
}

data class ScriptData(
    val name: String,
    val index: Int,
    val callbacks: List<Callback>,
    val arguments: Map<String, Int>,
    val freeIndexes: List<Int>,
)

/**
 * Statements directly in a script should updated the scope of the script. However,
 * they should add statements to the initialize callback, so this combines the two.
 */
class ScriptInitializationContext(script: ScriptDeclaration, initializeCallback: Callback) : ExecutionContext,
    ScriptContext by script {
    override val statements = initializeCallback.statements
    override val localAllocator = initializeCallback.localAllocator
}