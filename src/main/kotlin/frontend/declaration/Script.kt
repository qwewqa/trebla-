package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.*

class ScriptDeclaration(override val node: ScriptDeclarationNode, override val parentContext: Context) :
    Declaration, ScriptContext, Callable {
    override val configuration = parentContext.configuration
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC
    override val type = ScriptType
    override val bindingContext = parentContext
    override val scope = EagerScope(parentContext.scope)
    override val memoryAllocator = StandardAllocator(21, 64)
    override val dataAllocator = StandardAllocator(22, 32)
    override val sharedAllocator = StandardAllocator(24, 32)

    var index = parentContext.configuration.sharedState.scriptIndex.getAndIncrement()

    /*
    There are 3 contexts here:
    - initializationContext: scope is script, executes in initialize callback
    - initializeCallback: scope is initialize callback, executes in initialize callback
    - this (script): scope is script, no execution

    Properties can use initializationContext because the update the scope of the script,
    but won't leak temporary variables since they copy.
    Other declarations (that is, let declarations), however, must not leak temporary variables,
    and thus cannot have an execution context.
     */

    private val initializeCallback = Callback(this, 0, CallbackName.Initialize, this)
    private val initializationContext = ScriptInitializationContext(this, initializeCallback)

    val propertyDeclarations by lazy {
        node.body.value
            .filterIsInstance<PropertyDeclarationNode>()
            .map { it.parse(initializationContext) }
    }

    val spawnProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.SPAWN }
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val dataProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.DATA }
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val sharedProperties by lazy {
        propertyDeclarations
            .filter { it.variant == PropertyVariant.SHARED }
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val letDeclarations by lazy {
        node.body.value
            .filterIsInstance<LetDeclarationNode>()
            .map { it.parse(initializationContext) }
            .let { exprs ->
                exprs.forEach { it.applyTo(this) }
                exprs.associate { it.identifier to this.scope.get(it.identifier) }
            }
    }

    fun process(): ScriptData {
        // make sure lazy properties have fired
        spawnProperties
        dataProperties
        sharedProperties
        letDeclarations

        val callbacks = mutableListOf(initializeCallback)
        val propertyDeclarationsByNode = propertyDeclarations.associateBy { it.node }
        node.body.value.forEach { memberNode ->
            when (memberNode) {
                is PropertyDeclarationNode -> {
                    propertyDeclarationsByNode[memberNode]?.let {
                        if (it.variant != PropertyVariant.SPAWN && it.variant != PropertyVariant.DATA && it.variant != PropertyVariant.SHARED) it.applyTo(
                            initializationContext
                        )
                    }
                }
                is LetDeclarationNode -> {} // Already dealt with in the letDeclarations property
                is InitBlockNode -> memberNode.body.value.forEach { it.parseAndApplyTo(initializeCallback) }
                is CallbackDeclarationNode -> CallbackDeclaration(memberNode, this).let {
                    it.applyTo(this)
                    callbacks += it.getCallback()
                }
                is StatementNode -> memberNode.parseAndApplyTo(this)
                else -> error("Unsupported script member.") // should not happen
            }
        }
        return ScriptData(
            identifier,
            index,
            callbacks,
            dataProperties.mapValues { (_, v) -> ((((v as RawStructValue).raw as AllocatedRawValue).allocation) as ConcreteAllocation).index },
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
        callingContext.statements += BuiltinCallRawValue(
            IRFunction.Spawn,
            listOf(LiteralRawValue(index.toDouble())) + cells.map { it ?: LiteralRawValue(0.0) }
                .dropLastWhile { it is LiteralRawValue && it.value == 0.0 }
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

    private fun setRaw(prop: RawStructValue, value: RawStructValue, cells: MutableList<RawValue?>) {
        cells[((prop.raw as AllocatedRawValue).allocation as ConcreteAllocation).index] = value.raw
    }

    private fun setNormal(prop: NormalStructValue, value: NormalStructValue, cells: MutableList<RawValue?>) {
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
 * Statements directly in a script should update the scope of the script. However,
 * they should add statements to the initialize callback, so this combines the two.
 */
class ScriptInitializationContext(script: ScriptDeclaration, initializeCallback: Callback) :
    ExecutionContext, ScriptContext by script {
    override val scope = script.scope // Necessary for some reason. Delegate alone won't work.
    override val statements = initializeCallback.statements
    override val localAllocator = initializeCallback.localAllocator
}
