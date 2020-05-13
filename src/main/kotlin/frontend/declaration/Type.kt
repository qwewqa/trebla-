package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.ReadOnlyContext
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.expression.Value
import xyz.qwewqa.sono.frontend.expression.parseAndApplyTo
import xyz.qwewqa.sono.grammar.sono.TypeNode

/**
 * A type, as used here, is a constraint for a struct member or a function argument or return type.
 * Types exist only at compile time as a constraint.
 */
interface Type {
    fun accepts(other: Value): Boolean
}

abstract class BuiltinType(override val identifier: String) : Type,
    Declaration {
    override val type: Type = TypeType

    override val declaringContext: Context? = null

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override fun accepts(other: Value) = other.type == this
}

object TypeType : BuiltinType("Type") {
    override fun accepts(other: Value): Boolean = other is TypeType
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
