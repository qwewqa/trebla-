package xyz.qwewqa.trebla.backend.compile

import xyz.qwewqa.trebla.backend.BackendConfig
import xyz.qwewqa.trebla.backend.ssa.*
import xyz.qwewqa.trebla.frontend.context.TEMPORARY_MEMORY_BLOCK

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
            SonoFunction.Get.calledWith(
                TEMPORARY_MEMORY_BLOCK.toIR(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toIR()
            )
        }
        is IRTempAssign -> {
            SonoFunction.Set.calledWith(
                TEMPORARY_MEMORY_BLOCK.toIR(),
                mapping.getOrPut(id) {
                    remainingIndexes.removeFirstOrNull() ?: backendError("Temporary Allocations Full")
                }.toIR(),
                rhs.processTemporaryAllocations(config, remainingIndexes, mapping)
            )
        }
        is IRFunctionCall -> {
            copy(
                arguments = arguments.map { it.processTemporaryAllocations(config, remainingIndexes, mapping) }
            )
        }
        else -> TODO()
    }
}
