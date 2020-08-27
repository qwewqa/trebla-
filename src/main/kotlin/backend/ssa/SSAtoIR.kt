package xyz.qwewqa.trebla.backend.allocate

import xyz.qwewqa.trebla.backend.compile.*

private fun SSANode.collectPhi(): List<Phi> = when (this) {
    is SSAFunctionCall -> {
        phiAssigns + arguments.asReversed().flatMap { it.collectPhi() }
    }
    else -> emptyList()
}

fun SSANode.getCoalesceMapping(): Map<SSAReadLocation, SSAReadLocation> =
    mutableMapOf<SSAReadLocation, SSAReadLocation>().also {
        collectPhi().forEach { phi ->
            phi.locations.forEach { loc ->
                it[loc] = it[phi.targetLocation] ?: phi.targetLocation
            }
        }
    }

fun SSANode.toIR(coalesceMapping: Map<SSAReadLocation, SSAReadLocation> = getCoalesceMapping()): IRNode =
    when (this) {
        is SSAValue -> IRValue(value)
        is SSATempRead -> IRTempRead(((coalesceMapping[location] as NormalReadLocation?)?.id ?: id) / SSA_RENUMBER_COEF)
        is SSASeqTempRead -> (coalesceMapping[location] as SeqReadLocation?)?.let {
            IRSeqTempRead(
                it.id / SSA_RENUMBER_COEF,
                it.size,
                offset.toIR(coalesceMapping),
            )
        } ?: IRSeqTempRead(
            id / SSA_RENUMBER_COEF,
            size,
            offset.toIR(coalesceMapping),
        )
        is SSATempAssign -> IRTempAssign(
            ((coalesceMapping[location] as NormalReadLocation?)?.id ?: id) / SSA_RENUMBER_COEF,
            rhs.toIR(coalesceMapping),
        )
        is SSASeqTempAssign -> (coalesceMapping[location] as SeqReadLocation?)?.let {
            IRSeqTempAssign(
                it.id / SSA_RENUMBER_COEF,
                it.size,
                offset.toIR(coalesceMapping),
                rhs.toIR(coalesceMapping),
            )
        } ?: IRSeqTempAssign(
            id / SSA_RENUMBER_COEF,
            size,
            offset.toIR(coalesceMapping),
            rhs.toIR(coalesceMapping),
        )
        is SSAFunctionCall -> IRFunctionCall(
            variant,
            arguments.map {
                it.toIR(coalesceMapping)
            },
        )
    }
