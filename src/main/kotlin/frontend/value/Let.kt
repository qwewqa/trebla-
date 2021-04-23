package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.LetDeclarationNode

class LetDeclaration(
    override val node: LetDeclarationNode,
    override val parentContext: Context,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility: Visibility
    override val type = UnitValue

    val typeConstraint = node.type?.applyIn(parentContext) ?: AnyType

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
        }
    }

    override fun applyTo(context: Context): UnitValue {
        if (identifier.all { it == '_' }) {
            compileError("All underscore identifiers are reserved.")
        }
        context.scope.addLazy(
            LetLazyValue {
                node.expression.runWithErrorMessage("Error in let declaration.") {
                    val value = node.expression.parseAndApplyTo(context)
                    value.coerceTo(typeConstraint)
                        ?: compileError("Value of type ${value.type.commonName} does not satisfy explicit type ${typeConstraint.commonName}.")
                }
            },
            identifier, signature, visibility,
        )
        return UnitValue
    }

    inner class LetLazyValue(val initializer: () -> Value) : Lazy<Value> {
        private var initializing = false
        private var initialized = false
        private var _value: Value? = null

        override val value: Value
            get() {
                if (initialized) return _value!!
                if (initializing) compileError("Let value accessed while still initializing.")
                initializing = true
                _value = initializer()
                initializing = false
                initialized = true
                return _value!!
            }

        override fun isInitialized() = initialized
    }
}
