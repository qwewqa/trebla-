package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRNode
import xyz.qwewqa.trebla.backend.compile.SonoFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.WhileExpressionNode

class WhileExpression(override val node: WhileExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("While statement not allowed at location.", node)
        val conditionContext = SimpleExecutionContext(context)
        val condition = node.condition.parseAndApplyTo(conditionContext)
        if (condition !is RawStructValue || condition.type != context.booleanType)
            compileError("while statement condition must be a boolean.", node.condition)
        val body = SimpleExecutionContext(context).also { ctx ->
            node.body.value.forEach { it.parseAndApplyTo(ctx) }
        }
        context.statements += WhileStatement(
            SonoFunction.Execute.calledWith(
                conditionContext.toIR(),
                condition.raw.toIR(),
            ),
            body,
        )
        return UnitValue
    }
}

class WhileStatement(val condition: IRNode, val body: SimpleExecutionContext) : Statement {
    override fun toIR() = SonoFunction.While.calledWith(
        condition,
        body.toIR(),
    )
}
