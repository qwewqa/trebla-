package xyz.qwewqa.trebla.frontend.value

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
    Declaration, Callable {
    val context = ScriptContext(parentContext)
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC
    override val type = ScriptType

    var index by Delegates.notNull<Int>()

    val propertyDeclarations by lazy {
        node.body.value
        .filterIsInstance<PropertyDeclarationNode>()
        .map { it.parse(context) }
        .groupBy { it.variant }
        .mapValues { (_, group) -> group.associate { it.identifier to it.applyTo(context) } }
    }

    val spawnProperties by lazy { propertyDeclarations[PropertyVariant.SPAWN] ?: emptyMap() }
    val dataProperties by lazy { propertyDeclarations[PropertyVariant.DATA] ?: emptyMap() }

    fun process(): ScriptData {
        node.body.value.filterIsInstance<DeclarationNode>().forEach {
            val parsed = it.parse(context)
            if (parsed.loadFirstPass) parsed.applyTo(context)
        }

        // Ensure this lazy property fires
        propertyDeclarations

        val callbackDeclarationNodes = mutableListOf<CallbackDeclarationNode>()

        node.body.value.forEach { memberNode ->
            when (memberNode) {
                is PropertyDeclarationNode -> {}
                is CallbackDeclarationNode -> callbackDeclarationNodes += memberNode
                is StatementNode -> {
                    val parsed = memberNode.parse(context)
                    if (parsed !is Declaration || !parsed.loadFirstPass) parsed.applyTo(context)
                }
                else -> error("Unsupported script member.") // should not happen
            }
        }

        val processedCallbacks = callbackDeclarationNodes.map { cbNode ->
            CallbackDeclaration(cbNode, context).run {
                applyTo(context)
                getCallback()
            }
        }

        return ScriptData(
            identifier,
            index,
            processedCallbacks,
            dataProperties
                .filterValues { v -> v is PrimitiveInstance }
                .mapValues { (_, v) ->
                    ((((v as PrimitiveInstance).value as AllocatedRawValue).allocation) as ConcreteAllocation).index
                },
            (context.memoryAllocator.index until context.memoryAllocator.size).toList()
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

class ScriptContext(parentContext: Context) : Context(parentContext) {
    override val memoryAllocator = StandardAllocator(21, 64)
    override val dataAllocator = StandardAllocator(22, 32)
    override val sharedAllocator = StandardAllocator(24, 32)
}

data class ScriptData(
    val name: String,
    val index: Int,
    val callbacks: List<Callback>,
    val arguments: Map<String, Int>,
    val freeIndexes: List<Int>,
)
