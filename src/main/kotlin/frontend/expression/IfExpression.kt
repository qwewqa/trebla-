package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.IfExpressionNode

class IfExpression(override val node: IfExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("if statement not allowed at location.", node)
        val condition = node.condition.parseAndApplyTo(context)
        if (condition !is RawStructValue || condition.type != context.booleanType)
            compileError("if statement condition must be a boolean.", node.condition)
        when (condition.raw.toIR().tryConstexprEvaluate()) {
            null -> {
                // As a side note here, once if expressions are implemented,
                // const if expressions should be able to have immutable/incompatible values in each branch
                if (node.isConst) {
                    compileError("if statement is marked const, but condition is not a compile time constant.")
                }
                context.statements += IfElseStatement(
                    condition.raw,
                    SimpleExecutionContext(context).also { ctx ->
                        node.tbranch.value.forEach { it.parseAndApplyTo(ctx) }
                    },
                    SimpleExecutionContext(context).also { ctx ->
                        node.fbranch?.value?.forEach { it.parseAndApplyTo(ctx) }
                    }
                )
            }
            0.0 -> {
                node.fbranch?.let { fbranch ->
                    fbranch.value.forEach {
                        it.parseAndApplyTo(context)
                    }
                }
            }
            else -> {
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
    override fun toIR() = IRFunction.If.calledWith(
        condition.toIR(),
        IRFunction.Execute.calledWith(tbranch.statements.map { it.toIR() }),
        IRFunction.Execute.calledWith(fbranch.statements.map { it.toIR() })
    )
}
