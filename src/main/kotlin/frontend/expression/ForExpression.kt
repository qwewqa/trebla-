package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.ForExpressionNode
import xyz.qwewqa.trebla.grammar.trebla.WhileExpressionNode

class ForExpression(override val node: ForExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("For statement not allowed at location.", node)
        node.initializer?.parseAndApplyTo(context)
        val condition = node.condition?.parseAndApplyTo(context) ?: RawStructValue(LiteralValue(1.0), context, context.booleanType)
        val afterthought = node.afterthought?.parse(context)
        if (condition !is RawStructValue || condition.type != context.booleanType)
            compileError("For statement condition must be a boolean.", node.condition)
        val body = SimpleExecutionContext(context).also { ctx ->
            node.body.value.forEach { it.parseAndApplyTo(ctx) }
            afterthought?.applyTo(ctx)
        }
        context.statements += WhileStatement(condition, body)
        return UnitValue
    }
}