package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.context.visibilityModifiers
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.LetDeclarationNode

class LetDeclaration(
    override val node: LetDeclarationNode,
    override val parentContext: Context,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility: Visibility
    override val type = UnitValue

    val isShared: Boolean

    val typeConstraint = node.type?.applyIn(parentContext) ?: AnyType

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            isShared = selectSingle("shared")
        }
        if (isShared && parentContext !is ScriptContext) {
            compileError("let declaration shared modifier only allowed directly in scripts.")
        }
    }

    override fun applyTo(context: Context): UnitValue {
        context.scope.add(
            lazy {
                val value = node.expression.parseAndApplyTo(context)
                value.coerceTo(typeConstraint) ?: compileError("Expression does not satisfy explicit type.")
            },
            identifier, signature, visibility,
        )
        return UnitValue
    }

}
