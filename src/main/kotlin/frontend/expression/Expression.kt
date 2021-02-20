package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ReadOnlyContext
import xyz.qwewqa.trebla.grammar.trebla.ExpressionNode
import xyz.qwewqa.trebla.grammar.trebla.StatementNode
import xyz.qwewqa.trebla.grammar.trebla.TreblaNode

/**
 * Expressions are evaluated to compute their value.
 */
interface Expression {
    val node: TreblaNode? get() = null

    /**
     * Applies the effects of this frontend.expression to the provided context, such as adding a declaration to the scope
     * or adding a statement.
     * Returns a value which is valid after the frontend.expression is applied.
     */
    fun applyTo(context: Context): Value
}

/**
 * An frontend.expression that returns a constant [value].
 */
class ValueExpression(val value: Value) : Expression {
    override fun applyTo(context: Context): Value {
        return value
    }
}

fun ExpressionNode.tryConstexprEval(context: Context) =
    (parseAndApplyTo(ReadOnlyContext(context)) as? PrimitiveInstance)?.value?.tryConstexprEvaluate()

fun StatementNode.parseAndApplyTo(context: Context) = parse(context).applyTo(context)

