package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.expression.LiteralRawValue
import xyz.qwewqa.trebla.frontend.expression.RawValue
import xyz.qwewqa.trebla.frontend.expression.toLiteralRawValue

sealed class Allocator {
    abstract fun allocate(): Allocation
}

class StandardAllocator(val block: Int, val size: Int, startIndex: Int = 0) : Allocator() {
    var index = startIndex
        private set

    override fun allocate() =
        if (index == size) compileError("Allocator is full.")
        else ConcreteAllocation(block, index++)

    fun allocateContiguous(size: Int) =
        AllocationPointer(block, index).also {
            this.index += size
            if (index >= this.size) compileError("Allocator is full.")
        }
}

data class AllocationPointer(val block: Int, val index: Int)

// size is indeterminate; backend may move around or eliminate some of these
// this will usually end up as part of entity memory, though the door is technically open
// to storing things in other blocks depending on what's available to the callback
class TemporaryAllocator : Allocator() {
    private var counter = 1
    override fun allocate() = TemporaryAllocation(counter++)
}

class DynamicAllocator(private val block: RawValue, private val startIndex: RawValue) : Allocator() {
    private var offset = 0
    override fun allocate() = DynamicAllocation(
        block,
        startIndex,
        LiteralRawValue((offset++).toDouble())
    )
}

sealed class Allocation

/**
 * A specific location in memory known at compile time.
 */
data class ConcreteAllocation(val block: Int, val index: Int) : Allocation() {
    /**
     * Converts this to a dynamic allocation.
     * It is important that the index is converted to the offset in order
     * to properly interact with some behavior from derefing a pointer
     * (in such cases, the offsets are increases compile time constants and the index comes from the pointer).
     */
    fun toDynamicAllocation() =
        DynamicAllocation(block.toLiteralRawValue(), 0.toLiteralRawValue(), index.toLiteralRawValue())
}

/**
 * A location in memory which may be determined at runtime.
 */
data class DynamicAllocation(val block: RawValue, val index: RawValue, val offset: RawValue) : Allocation()

/**
 * Represents some unknown location in memory for a temporary variable.
 *
 * @property id used for uniqueness between temporary allocations
 */
data class TemporaryAllocation(val id: Int) : Allocation()

const val LEVEL_MEMORY_BLOCK = 0
const val LEVEL_DATA_BLOCK = 1
const val LEVEL_OPTION_BLOCK = 2
const val LEVEL_TRANSFORM_BLOCK = 3
const val LEVEL_BACKGROUND_BLOCK = 4
const val LEVEL_UI_BLOCK = 5
const val LEVEL_BUCKET_BLOCK = 6
const val LEVEL_SCORE_BLOCK = 7
const val LEVEL_LIFE_BLOCK = 8
const val ENTITY_INFO_ARRAY_BLOCK = 10
const val ENTITY_DATA_ARRAY_BLOCK = 11
const val ENTITY_SHARED_MEMORY_ARRAY_BLOCK = 12
const val ENTITY_INFO_BLOCK = 20
const val ENTITY_MEMORY_BLOCK = 21
const val ENTITY_DATA_BLOCK = 22
const val ENTITY_INPUT_BLOCK = 23
const val ENTITY_SHARED_MEMORY_BLOCK = 24
const val TEMPORARY_MEMORY_BLOCK = 100
const val TEMPORARY_DATA_BLOCK = 101

const val SHARED_BLOCK_SIZE = 32

const val LEVEL_DATA_OFFSET = 6
