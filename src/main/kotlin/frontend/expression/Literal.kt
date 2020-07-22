package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.declaration.StructDeclaration
import xyz.qwewqa.trebla.grammar.trebla.BooleanLiteralNode
import xyz.qwewqa.trebla.grammar.trebla.NumberLiteralNode

class NumberLiteral(override val node: NumberLiteralNode) : Expression {
    override fun applyTo(context: Context): Value = RawStructValue(
        LiteralValue(node.value),
        context,
        context.scope.getFullyQualified(listOf("std", "Number")) as StructDeclaration
    )
}

class BooleanLiteral(override val node: BooleanLiteralNode) : Expression {
    override fun applyTo(context: Context): Value = RawStructValue(
        LiteralValue(if (node.value) 1.0 else 0.0),
        context,
        context.scope.getFullyQualified(listOf("std", "Number")) as StructDeclaration
    )
}