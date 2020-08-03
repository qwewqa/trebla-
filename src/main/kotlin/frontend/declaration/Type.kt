package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ReadOnlyContext
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.TypeNode

/**
 * A type, as used here, is a constraint for a struct member or a function argument or return type.
 * Types exist only at compile time as a constraint.
 */
interface Type : Value {
    fun accepts(other: Value): Boolean
}

abstract class BuiltinType(override val identifier: String) : Type,
    Declaration {
    override val type: Type = TypeType

    override val parentContext: Context? = null

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override fun accepts(other: Value) = other.type == this
}

class UnionType(val types: List<Type>) : Type {
    override val type = TypeType

    override fun accepts(other: Value): Boolean {
        return types.any { it.accepts(other) }
    }
}

object TypeType : BuiltinType("Type") {
    override fun accepts(other: Value): Boolean = other.type == TypeType
}

object AnyType : BuiltinType("Any") {
    override fun accepts(other: Value) = true
}

fun TypeNode.applyIn(context: Context) =
    /*
    The new context ensures there isn't an execution context, which a type shouldn't need.
    TypeNodes are just expressions that return a type,
    but for the purpose of sanity, they should never need an execution context.
    */
    (value.parseAndApplyTo(ReadOnlyContext(context)) as? Type) ?: compileError("Not a valid type.", this)
