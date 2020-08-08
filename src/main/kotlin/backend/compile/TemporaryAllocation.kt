package xyz.qwewqa.trebla.backend.compile

import xyz.qwewqa.trebla.backend.BackendConfig

const val TEMPORARY_MEMORY_BLOCK = 21

fun IRNode.processTemporaryAllocations(
    freeIndexes: List<Int>,
    config: BackendConfig,
) = this.processTemporaryAllocations(config, freeIndexes.toMutableList(), mutableMapOf())

@OptIn(ExperimentalStdlibApi::class)
private fun IRNode.processTemporaryAllocations(
    config: BackendConfig,
    remainingIndexes: MutableList<Int>,
    mapping: MutableMap<Int, Int>,
): IRNode {
    return when (this) {
        is IRValue -> this
        is IRTempRead -> {
            IRFunctionVariant.Get.calledWith(
                TEMPORARY_MEMORY_BLOCK.toIR(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toIR()
            )
        }
        is IRTempAssign -> {
            IRFunctionVariant.Set.calledWith(
                TEMPORARY_MEMORY_BLOCK.toIR(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toIR(),
                rhs.processTemporaryAllocations(config, remainingIndexes, mapping)
            )
        }
        is IRFunction -> {
            copy(
                arguments = arguments.map { it.processTemporaryAllocations(config, remainingIndexes, mapping) }
            )
        }
    }
}