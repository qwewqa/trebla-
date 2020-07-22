package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.context.getFullyQualified
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.IfExpressionNode

class IfExpression(override val node: IfExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("If statement not allowed at location.", node)
        val condition = node.expression.parseAndApplyTo(context)
        if (condition !is RawStructValue || condition.type != context.scope.getFullyQualified("std", "Boolean"))
            compileError("if statement condition must be a boolean.", node.expression)
        when (condition.value.toIR().tryConstexprEvaluate()) {
            null -> { // Condition unknown at compile time
                context.statements += IfElseStatement(
                    condition.value,
                    SimpleExecutionContext(context).apply {
                        node.tbranch.value.forEach { it.parseAndApplyTo(this) }
                    },
                    SimpleExecutionContext(context).apply {
                        node.fbranch?.value?.forEach { it.parseAndApplyTo(this) }
                    }
                )
            }
            0.0 -> { // False at compile time
                node.fbranch?.let { fbranch ->
                    fbranch.value.forEach {
                        it.parseAndApplyTo(context)
                    }
                }
            }
            else -> { // True at compile time
                node.tbranch.value.forEach {
                    it.parseAndApplyTo(context)
                }
            }
        }
        return UnitValue
    }
}

class IfElseStatement(val condition: RawValue, val tbranch: ExecutionContext, val fbranch: ExecutionContext) :
    Statement {
    override fun toIR() = FunctionIRNodeVariant.If.calledWith(
        condition.toIR(),
        FunctionIRNodeVariant.Execute.calledWith(tbranch.statements.map { it.toIR() }),
        FunctionIRNodeVariant.Execute.calledWith(fbranch.statements.map { it.toIR() })
    )
}