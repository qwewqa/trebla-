package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.context.createSimpleChild
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.IfExpressionNode

class IfExpression(override val node: IfExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = if (node.isConst) applyConstant(context) else applyNormal(context)

    private fun applyNormal(context: Context): UnitValue {
        if (context !is ExecutionContext) compileError("non-const if statement requires an execution context.", node)
        val condition = node.condition.parseAndApplyTo(context).asBooleanStruct(context)
        context.statements += IRFunction.If.raw(
            condition.raw,
            SimpleExecutionContext(context).also { ctx ->
                node.tbranch.value.forEach { it.parseAndApplyTo(ctx) }
            }.raw(),
            SimpleExecutionContext(context).also { ctx ->
                node.fbranch?.value?.forEach { it.parseAndApplyTo(ctx) }
            }.raw()
        )
        return UnitValue
    }

    private fun applyConstant(context: Context): Value {
        val condition = node.condition.parseAndApplyTo(context).asBooleanStruct(context)
        val ifContext = context.createSimpleChild()
        val selectedBranch = when (condition.raw.toIR().tryConstexprEvaluate()) {
            null -> compileError("if statement is marked const, but condition is not a compile time constant.")
            0.0 -> node.fbranch?.value ?: emptyList()
            else -> node.tbranch.value
        }
        return selectedBranch
            .asSequence()
            .map { it.parseAndApplyTo((ifContext)) }
            .lastOrNull() ?: UnitValue
    }
}

fun Value.asBooleanStruct(context: Context): RawStructValue {
    if (this !is RawStructValue || this.type != context.booleanType) {
        compileError("if statement condition must be a boolean.", this.node)
    }
    return this
}
