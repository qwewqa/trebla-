package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.context.visibilityModifiers
import xyz.qwewqa.sono.frontend.expression.UnitValue
import xyz.qwewqa.sono.frontend.expression.Value
import xyz.qwewqa.sono.frontend.expression.parseAndApplyTo
import xyz.qwewqa.sono.grammar.sono.LetDeclarationNode

class LetDeclaration(
    override val node: LetDeclarationNode,
    override val declaringContext: Context,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = UnitValue

    val typeConstraint = node.type?.applyIn(declaringContext) ?: AnyType

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
        }
    }

    override fun applyTo(context: Context): Value {
        context.scope.addDeferred(identifier, signature, visibility) {
            val value = node.expression.parseAndApplyTo(context)
            if (!typeConstraint.accepts(value)) compileError("Expression does not satisfy explicit type.")
            value
        }
        return UnitValue
    }

}