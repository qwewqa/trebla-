package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.CompileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.grammar.trebla.TryExpressionNode

class TryExpression(override val node: TryExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = when (context) {
        is ExecutionContext -> applyToExecutionContext(context)
        else -> applyToContext(context)
    }

    private fun applyToContext(context: Context): Value {
        return try {
            val tryContext = SimpleContext(context)
            node.tryBranch.value
                .asSequence()
                .map { it.parseAndApplyTo(tryContext) }
                .lastOrNull() ?: UnitValue
        } catch (e: CompileError) {
            val exceptContext = SimpleContext(context)
            node.exceptBranch.value
                .asSequence()
                .map { it.parseAndApplyTo(exceptContext) }
                .lastOrNull() ?: UnitValue
        }
    }

    private fun applyToExecutionContext(context: ExecutionContext): Value {
        return try {
            val tryContext = SimpleExecutionContext(context)
            node.tryBranch.value
                .asSequence()
                .map { it.parseAndApplyTo(tryContext) }
                .lastOrNull()
                .also { context.statements += tryContext } ?: UnitValue
        } catch (e: CompileError) {
            val exceptContext = SimpleExecutionContext(context)
            node.exceptBranch.value
                .asSequence()
                .map { it.parseAndApplyTo(exceptContext) }
                .lastOrNull()
                .also { context.statements += exceptContext } ?: UnitValue
        }
    }
}
