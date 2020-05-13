package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.grammar.sono.SimpleIdentifierNode

class SimpleIdentifierExpression(override val node: SimpleIdentifierNode) : Expression {
    override fun applyTo(context: Context): Value {
        return context.scope.find(node.value) ?: compileError("Unknown symbol ${node.value}.", node)
    }
}