package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.BooleanType
import xyz.qwewqa.trebla.frontend.NumberType
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.fromRaw
import xyz.qwewqa.trebla.grammar.trebla.BooleanLiteralNode
import xyz.qwewqa.trebla.grammar.trebla.NumberLiteralNode

class NumberLiteral(override val node: NumberLiteralNode) : Expression {
    override fun applyTo(context: Context): Value = NumberType.fromRaw(
        LiteralRawValue(node.value),
    )
}

class BooleanLiteral(override val node: BooleanLiteralNode) : Expression {
    override fun applyTo(context: Context): Value = BooleanType.fromRaw(
        LiteralRawValue(if (node.value) 1.0 else 0.0),
    )
}
