package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.expression.LiteralRawValue
import xyz.qwewqa.trebla.frontend.expression.RawValue

sealed class Allocator {
    abstract fun allocate(): Allocation
}

class StandardAllocator(val block: Int, val size: Int) : Allocator() {
    var index = 0
        private set

    override fun allocate() =
        if (index == size) compileError("Allocator is full.")
        else ConcreteAllocation(block, index++)
}

// size is indeterminate; backend may move around or eliminate some of these
// this will usually end up as part of entity memory, though the door is technically open
// to storing things in other blocks depending on what's available to the callback
class TemporaryAllocator : Allocator() {
    private var counter = 0
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
data class ConcreteAllocation(val block: Int, val index: Int) : Allocation()

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
const val ENTITY_INFO_ARRAY = 10
const val ENTITY_DATA_ARRAY = 11
const val ENTITY_SHARED_MEMORY_ARRAY = 12
const val ENTITY_INFO = 20
const val ENTITY_MEMORY = 21
const val ENTITY_DATA = 22
const val ENTITY_INPUT = 23
const val ENTITY_SHARED_MEMORY = 24
const val TEMPORARY_MEMORY = 100
const val TEMPORARY_DATA = 101
