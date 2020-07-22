package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.context.Allocator
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.declaration.Type

/**
 * Represents a value that results from an frontend.expression.
 * In short, a value encapsulates data on how to do something, from manipulating memory in the case of struct values,
 * to any declaration like function declarations.
 */
interface Value : Entity {
    val type: Type
}

interface Allocatable : Value {
    fun allocateOn(allocator: Allocator, context: Context): Value
}

interface Copyable : Value {
    /**
     * Copies the value onto new allocations on the allocator.
     */
    fun copyOn(allocator: Allocator, context: ExecutionContext): Copyable
}

interface Mutable : Value {
    fun copyFrom(other: Value, context: ExecutionContext)
}

/**
 * A type with only one value. The default return value for functions that don't return anything else.
 */
object UnitValue : BuiltinType("Unit"), Value {
    override val type = this
}