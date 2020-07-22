package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.grammar.trebla.SimpleIdentifierNode

class SimpleIdentifierExpression(override val node: SimpleIdentifierNode) : Expression {
    override fun applyTo(context: Context): Value {
        return context.scope.find(node.value) ?: compileError("Unknown symbol ${node.value}.", node)
    }
}