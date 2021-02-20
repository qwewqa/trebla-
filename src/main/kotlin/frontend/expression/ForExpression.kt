package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.BooleanType
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.InnerExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.fromRaw
import xyz.qwewqa.trebla.grammar.trebla.ForExpressionNode

class ForExpression(override val node: ForExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("For statement not allowed at location.", node)
        val innerContext = InnerExecutionContext(context)
        node.initializer?.parseAndApplyTo(innerContext)
        val conditionContext = SimpleExecutionContext(innerContext)
        val condition = node.condition?.parseAndApplyTo(conditionContext)
            ?: BooleanType.fromRaw(LiteralRawValue(1.0))
        val afterthought = node.afterthought?.parse(innerContext)
        if (condition !is PrimitiveInstance || condition.type != BooleanType)
            compileError("For statement condition must be a boolean.", node.condition)
        val body = SimpleExecutionContext(innerContext).also { ctx ->
            node.body.value.forEach { it.parseAndApplyTo(ctx) }
            afterthought?.applyTo(ctx)
        }
        innerContext.statements += SonoFunction.While.calledWith(
            SonoFunction.Execute.calledWith(
                conditionContext.statements.asIR(),
                condition.value.toIR(conditionContext)
            ),
            body.statements.asIR(),
        )
        return UnitValue
    }
}

