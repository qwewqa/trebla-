package xyz.qwewqa.sono.frontend.context

import xyz.qwewqa.sono.frontend.compileError

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

sealed class Allocation

/**
 * Represents a specific location in memory.
 */
data class ConcreteAllocation(val block: Int, val index: Int) : Allocation()

/**
 * Represents some unknown location in memory for a temporary variable.
 *
 * @property id used for uniqueness between temporary allocations
 */
data class TemporaryAllocation(val id: Int) : Allocation()
