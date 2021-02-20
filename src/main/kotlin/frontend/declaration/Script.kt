package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.IRValue
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.backend.ir.toIR
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.*
import kotlin.properties.Delegates

class ScriptDeclaration(override val node: ScriptDeclarationNode, override val parentContext: Context) :
    Declaration, ScriptContext, Callable {
    override val globalContext: GlobalContext = parentContext.globalContext
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC
    override val type = ScriptType
    override val bindingContext = parentContext
    override val scope = Scope(parentContext.scope)
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)
    override val memoryAllocator = StandardAllocator(21, 64)
    override val dataAllocator = StandardAllocator(22, 32)
    override val sharedAllocator = StandardAllocator(24, 32)

    var index by Delegates.notNull<Int>()

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

    private val initializeCallback = ExecutionCallback(this, 0, CallbackName.Initialize, this)
    private val initializationContext = ScriptInitializationContext(this, initializeCallback)

    val propertyDeclarations by lazy {
        node.body.value
            .filterIsInstance<PropertyDeclarationNode>()
            .map { it.parse(initializationContext) }
            .groupBy { it.variant }
    }

    val spawnProperties by lazy {
        (propertyDeclarations[PropertyVariant.SPAWN] ?: emptyList())
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val dataProperties by lazy {
        (propertyDeclarations[PropertyVariant.DATA] ?: emptyList())
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val sharedProperties by lazy {
        (propertyDeclarations[PropertyVariant.SHARED] ?: emptyList())
            .let { exprs ->
                exprs.forEach { it.applyTo(initializationContext) }
                exprs.associate { it.identifier to initializationContext.scope.get(it.identifier) }
            }
    }

    val sharedLetDeclarations by lazy {
        node.body.value
            .filterIsInstance<LetDeclarationNode>()
            .map { it.parse(initializationContext) }
            .filter { it.isShared }
            .let { exprs ->
                exprs.forEach { it.applyTo(this) }
                exprs.associate { it.identifier to this.scope.get(it.identifier) }
            }
    }

    fun process(): ScriptData {
        node.body.value.filterIsInstance<DeclarationNode>().forEach {
            val parsed = it.parse(this)
            if (parsed.loadFirstPass) parsed.applyTo(this)
        }

        // make sure lazy properties have fired
        spawnProperties
        dataProperties
        sharedProperties
        sharedLetDeclarations

        val callbackDeclarationNodes = mutableListOf<CallbackDeclarationNode>()
        val propertyDeclarationsByNode =
            (propertyDeclarations[PropertyVariant.NORMAL] ?: emptyList()).associateBy { it.node }
        node.body.value.forEach { memberNode ->
            when (memberNode) {
                is PropertyDeclarationNode -> propertyDeclarationsByNode[memberNode]?.applyTo(initializationContext)
                is LetDeclarationNode -> {
                    memberNode.parse(initializationContext).let {
                        // shared let declarations (accessible from other scripts via an entity pointer)
                        // cannot access normal (local) properties
                        // but unshared ones can, so they are initialized in order in this case
                        if (!it.isShared) it.applyTo(initializationContext)
                    }
                }
                is InitBlockNode -> memberNode.body.value.forEach { it.parseAndApplyTo(initializeCallback) }
                is CallbackDeclarationNode -> callbackDeclarationNodes += memberNode
                is StatementNode -> {
                    val parsed = memberNode.parse(this)
                    if (parsed !is Declaration || !parsed.loadFirstPass) parsed.applyTo(this)
                }
                else -> error("Unsupported script member.") // should not happen
            }
        }

        val processedCallbacks = listOf(initializeCallback) + callbackDeclarationNodes.map { cbNode ->
            CallbackDeclaration(cbNode, this).run {
                applyTo(this@ScriptDeclaration)
                getCallback()
            }
        }

        return ScriptData(
            identifier,
            index,
            processedCallbacks,
            dataProperties.mapValues
            { (_, v) -> ((((v as PrimitiveInstance).value as AllocatedRawValue).allocation) as ConcreteAllocation).index },
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
        callingContext.statements += IRFunctionCall(
            SonoFunction.Spawn,
            listOf(index.toIR()) + cells.map { it?.toIR(callingContext) ?: 0.0.toIR() }
                .dropLastWhile { it is IRValue && it.value == 0.0 }
        )
        return UnitValue
    }

    fun set(prop: Value?, value: Value?, cells: MutableList<RawValue?>) {
        when {
            value is PrimitiveInstance && prop is PrimitiveInstance -> setPrimitive(prop, value, cells)
            value is StructInstance && prop is StructInstance -> setStruct(prop, value, cells)
            else -> compileError("Incompatible types")
        }
    }

    private fun setPrimitive(prop: PrimitiveInstance, value: PrimitiveInstance, cells: MutableList<RawValue?>) {
        cells[((prop.value as AllocatedRawValue).allocation as ConcreteAllocation).index] = value.value
    }

    private fun setStruct(prop: StructInstance, value: StructInstance, cells: MutableList<RawValue?>) {
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
class ScriptInitializationContext(script: ScriptDeclaration, initializeCallback: ExecutionCallback) :
    ExecutionContext, ScriptContext by script {
    override val scope = script.scope // Necessary for some reason. Delegate alone won't work.
    override val statements = initializeCallback.statements
    override val localAllocator = initializeCallback.localAllocator
    override val contextMetadata = ContextMetadata(script.contextMetadata, CallbackName.Initialize)
}
