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
    val embedded: List<Value> get() = emptyList()

    val commonName: String? get() = null

    fun coerceTo(type: Type): Value? = if (type.accepts(this.type)) this else null

    /**
     * Returns an immutable version of this value (possibly itself) or null if that is not possible.
     * This value is intended for use as type parameters.
     * In addition, for non singletons, the binding context should be dropped.
     */
    fun coerceImmutable(): Value? = null

    fun repr() = toString()
    fun getMember(name: String, accessingContext: Context?): Value? = null
}

interface Allocatable : Type {
    /**
     * Creates a new instance of the allocatable type
     * on the given allocator in the given context.
     */
    fun allocateOn(allocator: Allocator, context: Context): Allocated

    /**
     * The size this allocatable takes.
     */
    val allocationSize: Int
}

/**
 * A value that could be allocated in memory (but not necessarily so in all circumstances).
 * When not actually allocated in memory, some methods may throw compile errors.
 */
interface Allocated : Value {
    override val type: Allocatable

    /**
     * Copies the value onto new allocations on the allocator.
     * This should keep the current binding context in the new value.
     */
    fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated

    /**
     * Copies the value of the other value into this value.
     * This should keep the current binding context.
     */
    fun copyFrom(other: Value, context: ExecutionContext)

    /**
     * Used for shared/data arrays.
     * Reallocates with the particular offset, where possible for shared and data allocated values.
     * Otherwise should throw a compile error.
     * Working assumption is that both shared and data have a size of 32 * entities, so only a single offset is needed.
     * [offset] can then just be a Get call from an already computed 32*entities rather than multiplying each time.
     */
    fun toEntityArrayValue(offset: RawValue): Allocated
}

/**
 * A type with only one value. The default return value for functions that don't return anything else.
 */
object UnitValue : BuiltinType("Unit"), Value {
    override val type = this
}
