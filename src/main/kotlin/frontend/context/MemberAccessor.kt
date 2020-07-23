package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.expression.Value

/**
 * Represents a declaration which the member access operator (dot) will work on.
 */
interface MemberAccessor : Value {
    fun getMember(name: String, accessingContext: Context?): Value
    fun hasMember(name: String, accessingContext: Context?): Boolean
}