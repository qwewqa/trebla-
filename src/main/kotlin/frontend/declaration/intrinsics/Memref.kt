package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.constexpr.ConstexprEvaluationException
import xyz.qwewqa.trebla.backend.constexpr.constexprEvaluate
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class Memref(override val parentContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration, Callable {
    override val identifier = "memref"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            Parameter("block", parentContext.numberType),
            Parameter("index", parentContext.numberType)
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val blockArg = argumentValues.getValue("block")
        val indexArg = argumentValues.getValue("index")
        if (blockArg !is RawStructValue || indexArg !is RawStructValue) compileError("memref arguments should be raw values.")
        val block: Double
        val index: Double
        try {
            block = blockArg.raw.toIR().constexprEvaluate()
            index = indexArg.raw.toIR().constexprEvaluate()
        } catch (e: ConstexprEvaluationException) {
            compileError("Invalid memref arguments.")
        }
        if (block.toInt().toDouble() != block || index.toInt().toDouble() != index)
            compileError("memref arguments must be integers.")
        return RawStructValue(
            AllocatedRawValue(ConcreteAllocation(block.toInt(), index.toInt())),
            callingContext,
            parentContext.scope.getFullyQualified("std", "Raw") as StructDeclaration
        )
    }
}