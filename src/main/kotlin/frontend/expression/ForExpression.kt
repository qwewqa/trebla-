package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.InnerExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.ForExpressionNode

class ForExpression(override val node: ForExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("For statement not allowed at location.", node)
        val innerContext = InnerExecutionContext(context)
        node.initializer?.parseAndApplyTo(innerContext)
        val condition = node.condition?.parseAndApplyTo(innerContext) ?: RawStructValue(LiteralValue(1.0), innerContext, innerContext.booleanType)
        val afterthought = node.afterthought?.parse(innerContext)
        if (condition !is RawStructValue || condition.type != innerContext.booleanType)
            compileError("For statement condition must be a boolean.", node.condition)
        val body = SimpleExecutionContext(innerContext).also { ctx ->
            node.body.value.forEach { it.parseAndApplyTo(ctx) }
            afterthought?.applyTo(ctx)
        }
        innerContext.statements += WhileStatement(condition, body)
        return UnitValue
    }
}