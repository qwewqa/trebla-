package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.TypeNode

interface Type : Value {
    fun accepts(other: Type): Boolean {
        return this == other || other.bindingHierarchy.flatten().any { accepts(it) }
    }

    override fun coerceImmutable() = this

    /**
     * Receiver functions are resolved in order of the hierarchy.
     * The list contains each layer, in order.
     * Implicitly, the type itself comes first, and the Any type comes last.
     * Receiver functions are resolved from the bottom to top layer.
     * Ambiguity in the same layer will result in an error.
     */
    val bindingHierarchy: List<Collection<Type>> get() = emptyList()

    val bindingScope: Scope? get() = null
}

fun Type.accepts(other: Value) = accepts(other.type)

open class BuiltinType(override val identifier: String) : Type, Declaration {
    override val type: Type = TypeType
    override val parentContext: Context? = null

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC
}

object AnyType : BuiltinType("Any") {
    override fun accepts(other: Type) = true
}

object TypeType : BuiltinType("Type")

/**
 * A union type, intended as a type constraint for functions where either of
 * a list of types is acceptable.
 */
class UnionType(val types: List<Type>, override val bindingScope: Scope? = null) : Type {
    override val type = TypeType
    override val bindingHierarchy = listOf(listOf(AnyType))

    override fun accepts(other: Type): Boolean {
        return types.any { it.accepts(other) } || super.accepts(other)
    }
}

fun TypeNode.applyIn(context: Context) =
    /*
    The new context ensures there isn't an execution context, which a type shouldn't need.
    TypeNodes are just expressions that return a type,
    but for the purpose of sanity, they should never need an execution context.
    */
    (value.parseAndApplyTo(ReadOnlyContext(context)) as? Type) ?: compileError("Not a valid type.", this)
