package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.value.BuiltinType
import xyz.qwewqa.trebla.frontend.value.Type
import xyz.qwewqa.trebla.frontend.value.includes

/**
 * Represents a value that results from an frontend.expression.
 * In short, a value encapsulates data on how to do something, from manipulating memory in the case of struct values,
 * to any declaration like function declarations.
 */
interface Value {
    val type: Type
    val embedded: List<Value> get() = emptyList()

    val commonName: String? get() = null

    fun coerceTo(type: Type): Value? = if (type includes this.type) this else null

    /**
     * Returns an immutable version of this value (possibly itself) or null if that is not possible.
     * This value is intended for use as type parameters.
     * In addition, for non singletons, the binding context should be dropped.
     */
    fun coerceImmutable(): Value? = null

    fun repr() = toString()
    fun getMember(name: String, accessingContext: Context?): Value? = null
}

/**
 * A type that may have a representation in memory.
 * The methods in this interface are expected to throw exceptions in some cases (such as List[Any]),
 * though it may make sense to eventually tighten restrictions in implementors
 * and provide alternatives in relevant cases in the future.
 */
interface Allocatable : Type {
    /**
     * The size this allocatable takes.
     */
    val allocationSize: Int

    /**
     * Creates a new instance of the allocatable type
     * on the given allocator in the given context.
     */
    fun allocateOn(allocator: Allocator, context: Context): Allocated

    /**
     * Convert from a flat representation to a normal instance of this type. See [Allocated.flat].
     */
    fun fromFlat(values: List<RawValue>): Allocated
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

    /**
     * A flat representation of the data in this value. See [Allocatable.fromFlat].
     */
    fun flat(): List<RawValue>

    /**
     * Returns a pair with block first and index second.
     */
    fun allocationLocation(): Pair<RawValue, RawValue>? {
        val flat = flat()
        if (!flat.all { it is AllocatedRawValue }) return null
        val allocations = flat
            .filterIsInstance<AllocatedRawValue>()
            .map { it.allocation }
            .map { if (it is ConcreteAllocation) it.toDynamicAllocation() else it }
            .also { if (it.any { alloc -> alloc !is DynamicAllocation }) return null }
            .filterIsInstance<DynamicAllocation>()
        val blocks = allocations.map { it.block }
        val indexes = allocations.map { it.index }
        val offsets = allocations.map { it.offset }
        val offsetValues = offsets.map { it.tryConstexprEvaluate() ?: return null }
        if (!offsetValues.zipWithNext().all { (a, b) -> b - a == 1.0 }) return null
        val block = blocks.toSet().singleOrNull() ?: return null
        /** see [ConcreteAllocation] for information */
        val index = indexes.toSet().singleOrNull() ?: return null
        return block to BuiltinCallRawValue(SonoFunction.Add, listOf(index, offsets.first()))
    }
}

/**
 * A type with only one value. The default return value for functions that don't return anything else.
 */
object UnitValue : BuiltinType("Unit")
