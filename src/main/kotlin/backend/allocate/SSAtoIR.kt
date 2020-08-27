package xyz.qwewqa.trebla.backend.allocate

import xyz.qwewqa.trebla.backend.compile.*

private fun SSANode.collectPhi(): List<Phi> = when (this) {
    is SSAFunctionCall -> {
        phiAssigns + arguments.asReversed().flatMap { it.collectPhi() }
    }
    else -> emptyList()
}

// assumes list is in reverse order (bottom to top) as obtained from collectPhi
private fun List<Phi>.getCoalesceMapping() =
    mutableMapOf<SSAReadLocation, SSAReadLocation>().also {
        this.forEach { phi ->
            phi.locations.forEach { loc ->
                it[loc] = it[phi.targetLocation] ?: phi.targetLocation
            }
        }
    }

fun SSANode.toIR(coalesceMapping: Map<SSAReadLocation, SSAReadLocation> = collectPhi().getCoalesceMapping()): IRNode =
    when (this) {
        is SSAValue -> IRValue(value)
        is SSATempRead -> IRTempRead((coalesceMapping[location] as NormalReadLocation?)?.id ?: id)
        is SSASeqTempRead -> (coalesceMapping[location] as SeqReadLocation?)?.let {
            IRSeqTempRead(
                it.id,
                it.size,
                offset.toIR(coalesceMapping),
            )
        } ?: IRSeqTempRead(
            id,
            size,
            offset.toIR(coalesceMapping),
        )
        is SSATempAssign -> IRTempAssign((coalesceMapping[location] as NormalReadLocation?)?.id ?: id,
            rhs.toIR(coalesceMapping))
        is SSASeqTempAssign -> (coalesceMapping[location] as SeqReadLocation?)?.let {
            IRSeqTempAssign(
                it.id,
                it.size,
                offset.toIR(coalesceMapping),
                rhs.toIR(coalesceMapping),
            )
        } ?: IRSeqTempAssign(
            id,
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
