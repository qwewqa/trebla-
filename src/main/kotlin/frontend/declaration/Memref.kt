package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.constexpr.ConstexprEvaluationException
import xyz.qwewqa.trebla.backend.constexpr.constexprEvaluate
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.ConcreteAllocation
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.context.getFullyQualified
import xyz.qwewqa.trebla.frontend.expression.*

class Memref(override val declaringContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration, Callable {
    override val identifier = "memref"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            Parameter("block", declaringContext.scope.getFullyQualified("std", "Number") as StructDeclaration),
            Parameter("index", declaringContext.scope.getFullyQualified("std", "Number") as StructDeclaration)
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val blockArg = argumentValues.getValue("block")
        val indexArg = argumentValues.getValue("index")
        if (blockArg !is RawStructValue || indexArg !is RawStructValue) compileError("memref arguments should be raw values.")
        val block: Double
        val index: Double
        try {
            block = blockArg.value.toIR().constexprEvaluate()
            index = indexArg.value.toIR().constexprEvaluate()
        } catch (e: ConstexprEvaluationException) {
            compileError("Invalid memref arguments.")
        }
        if (block.toInt().toDouble() != block || index.toInt().toDouble() != index)
            compileError("memref arguments must be integers.")
        return RawStructValue(
            AllocatedValue(ConcreteAllocation(block.toInt(), index.toInt())),
            callingContext,
            declaringContext.scope.getFullyQualified("std", "Raw") as StructDeclaration
        )
    }
}