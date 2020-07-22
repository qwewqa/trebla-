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
        is ValueIRNode -> this
        is ReadTempIRNode -> {
            FunctionIRNodeVariant.Get.calledWith(
                TEMPORARY_MEMORY_BLOCK.toValueIRNode(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toValueIRNode()
            )
        }
        is AssignTempIRNode -> {
            FunctionIRNodeVariant.Set.calledWith(
                TEMPORARY_MEMORY_BLOCK.toValueIRNode(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toValueIRNode(),
                rhs.processTemporaryAllocations(config, remainingIndexes, mapping)
            )
        }
        is FunctionIRNode -> {
            copy(
                arguments = arguments.map { it.processTemporaryAllocations(config, remainingIndexes, mapping) }
            )
        }
    }
}