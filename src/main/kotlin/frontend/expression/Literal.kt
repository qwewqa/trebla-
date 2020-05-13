package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.declaration.RawStructValue
import xyz.qwewqa.sono.frontend.declaration.StructDeclaration
import xyz.qwewqa.sono.grammar.sono.BooleanLiteralNode
import xyz.qwewqa.sono.grammar.sono.NumberLiteralNode

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