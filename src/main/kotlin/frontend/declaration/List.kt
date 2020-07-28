package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import kotlin.math.roundToInt

class TreblaList(override val declaringContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration,
    Callable {
    override val identifier = "List"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            Parameter("type", TypeType),
            Parameter("size", declaringContext.numberType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val size = (argumentValues["size"] as RawStructValue).value.toIR().tryConstexprEvaluate()?.roundToInt()
            ?: compileError("List size must be a compile time constant.")
        val type = (argumentValues["type"] as? Allocatable)
            ?: compileError("List type must be allocatable")
        return ListType(size, type)
    }
}

data class ListType(val size: Int, val containedType: Allocatable) : Type, Allocatable {
    override fun accepts(other: Value): Boolean {
        return other.type.let { it is ListType && it == this }
    }

    override val type = TypeType

    override fun allocateOn(allocator: Allocator, context: Context): Copyable {
        return ListValue(context, this, List(size) { containedType.allocateOn(allocator, context) })
    }
}

class ListValue(val context: Context, override val type: Type, val values: List<Copyable>) : Copyable, MemberAccessor,
    Callable {
    override val parameters by lazy {
        listOf(
            Parameter("index", context.numberType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val index = (parameters.pairedWithAndValidated(arguments).values.first() as RawStructValue).value.toIR()
            .tryConstexprEvaluate()?.roundToInt()
            ?: compileError("List index must be a compile time constant.")
        if (index !in values.indices) compileError("List index out of range.")
        return values[index]
    }

    override fun onBlock(block: Int, offset: RawValue): Copyable {
        return ListValue(context, type, values.map { it.onBlock(block, offset) })
    }

    override fun copyOn(allocator: Allocator, context: ExecutionContext): Copyable {
        return ListValue(context, type, values.map { it.copyOn(allocator, context) })
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return name == "forEach" || name == "forEachIndexed"
    }

    override fun getMember(name: String, accessingContext: Context?): Value {
        return when {
            name == "forEach" -> ListValueForEach(context, this, false)
            name == "forEachIndexed" -> ListValueForEach(context, this, true)
            else -> compileError("List does not have member with name $name.")
        }
    }
}

class ListValueForEach(val context: Context, val listValue: ListValue, val indexed: Boolean) : Callable, Value {
    override val type = FunctionType

    override val parameters by lazy {
        listOf(
            Parameter("start",
                UnionType(listOf(UnitValue, context.numberType)),
                DefaultParameter(ValueExpression(UnitValue), context)),
            Parameter("stop",
                UnionType(listOf(UnitValue, context.numberType)),
                DefaultParameter(ValueExpression(UnitValue), context)),
            Parameter("operation", FunctionType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val args = parameters.pairedWithAndValidated(arguments).byParameterName()
        val operation = args["operation"] as Callable
        val start = (args["start"] as? RawStructValue)?.value?.let {
            it.toIR().tryConstexprEvaluate() ?: compileError("Start index should be a compile time constant.")
        }?.roundToInt() ?: 0
        val stop = (args["stop"] as? RawStructValue)?.value?.let {
            it.toIR().tryConstexprEvaluate() ?: compileError("Start index should be a compile time constant.")
        }?.roundToInt() ?: listValue.values.size
        if (callingContext !is ExecutionContext) compileError("List forEach requires an execution context.")
        var index = start
        listValue.values.subList(start, stop).forEach {
            if (indexed) operation.callWith(
                listOf(
                    ValueArgument(null, RawStructValue(LiteralValue(index.toDouble()), context, context.numberType)),
                    ValueArgument(null, it)
                ),
                callingContext,
            )
            else operation.callWith(listOf(ValueArgument(null, it)), callingContext)
            index++
        }
        return UnitValue
    }
}