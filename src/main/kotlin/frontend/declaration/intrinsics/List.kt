package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*
import kotlin.math.roundToInt

class TreblaList(override val parentContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration,
    Callable {
    override val identifier = "List"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            Parameter("type", TypeType),
            Parameter("size", parentContext.numberType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val size = (argumentValues["size"] as RawStructValue).raw.toIR().tryConstexprEvaluate()?.roundToInt()
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

    override fun allocateOn(allocator: Allocator, context: Context?): Mutable {
        if (context == null) compileError("Requires a context.")
        return ListValue(context, this, List(size) { containedType.allocateOn(allocator, context) })
    }
}

class ListValue(val parentContext: Context, override val type: Type, val values: List<Mutable>) : Mutable, MemberAccessor,
    Callable {
    override val parameters by lazy {
        listOf(
            Parameter("index", parentContext.numberType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val index = (parameters.pairedWithAndValidated(arguments).values.first() as RawStructValue).raw.toIR()
            .tryConstexprEvaluate()?.roundToInt()
            ?: compileError("List index must be a compile time constant.")
        if (index !in values.indices) compileError("List index out of range.")
        return values[index]
    }

    override fun reallocate(allocator: Allocator, context: Context?): Mutable {
        return ListValue(parentContext, type, values.map { it.reallocate(allocator, context) })
    }

    override fun copyOn(allocator: Allocator, context: ExecutionContext): Mutable {
        return ListValue(context, type, values.map { it.copyOn(allocator, context) })
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        TODO("Not yet implemented")
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return name in members.keys
    }

    override fun getMember(name: String, accessingContext: Context?): Value {
        return members[name] ?: compileError("List does not have member with name $name.")
    }

    private val members: Map<String, Value> by lazy {
        mapOf(
            "forEach" to ListValueForEach(parentContext, this, false),
            "forEachIndexed" to ListValueForEach(parentContext, this, true),
            "get" to ListGet(parentContext, this)
        )
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

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val args = parameters.pairedWithAndValidated(arguments).byParameterName()
        val operation = args["operation"] as Callable
        val start = (args["start"] as? RawStructValue)?.raw?.let {
            it.toIR().tryConstexprEvaluate() ?: compileError("Start index should be a compile time constant.")
        }?.roundToInt() ?: 0
        val stop = (args["stop"] as? RawStructValue)?.raw?.let {
            it.toIR().tryConstexprEvaluate() ?: compileError("Start index should be a compile time constant.")
        }?.roundToInt() ?: listValue.values.size
        if (callingContext !is ExecutionContext) compileError("List forEach requires an execution context.")
        var index = start
        listValue.values.subList(start, stop).forEach {
            if (indexed) operation.callWith(
                listOf(
                    ValueArgument(null, RawStructValue(LiteralRawValue(index.toDouble()), context, context.numberType)),
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

class ListGet(val context: Context, val listValue: ListValue) : Callable, Value {
    override val type = FunctionType

    override val parameters by lazy {
        listOf(
            Parameter(
                "index",
                UnionType(listOf(UnitValue, context.numberType)),
                DefaultParameter(ValueExpression(UnitValue), context)
            )
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val index = parameters.pairedWithAndValidated(arguments).byParameterName()["index"] as RawStructValue
        if (callingContext !is ExecutionContext) compileError("Dynamic list get requires an execution context.")
        val types = listValue.values.map { it.type }.toSet()
        if (types.isEmpty()) compileError("List is empty.")
        if (types.size > 1) compileError("Dynamic list get is only available for homogenous lists.")
        val type = types.first()
        if (type !is Allocatable) compileError("Dynamic list get is only available for lists with allocatable types.")
        val returns = type.allocateOn(callingContext.localAllocator, callingContext)
        callingContext.statements += BuiltinCallRawValue(
            BuiltinFunctionVariant.SwitchIntegerWithDefault,
            listValue.values.map { value ->
                SimpleExecutionContext(callingContext).also {
                    returns.copyFrom(value, it)
                }.toIR()
            }.let {
                /*
                Switch is one indexed but lists are zero indexed, so we can put the first value as the final, default value
                rather than subtracting one.
                Out of range access is undefined regardless.
                */
                listOf(index.raw.toIR()) + it.drop(1) + it.first()
            }
        )
        return returns
    }
}