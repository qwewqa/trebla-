package xyz.qwewqa.sono.frontend.context

import xyz.qwewqa.sono.frontend.expression.Value

/**
 * Represents a declaration which the member access operator (dot) will work on.
 */
interface MemberAccessor : Value {
    fun getMember(name: String, accessingContext: Context?): Value
}