package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.*

fun SSANode.toIR(mapping: Map<SingleLocation, SingleLocation> = getCoalesceMapping()): IRNode = when (this) {
    is SSAValue -> IRValue(value)
    is SSAFunctionCall -> IRFunctionCall(variant, arguments.map { it.toIR(mapping) })
    is SSATempRead -> IRTempRead(mapping[location] ?: location)
    is SSATempAssign -> IRTempAssign(mapping[location] ?: location, rhs.toIR(mapping))
    is SSASeqTempRead -> IRSeqTempRead(location, offset.toIR(mapping))
    is SSASeqTempAssign -> IRSeqTempAssign(location, offset.toIR(mapping), rhs.toIR(mapping))
}

private fun SSANode.collectPhi(): List<Phi> = when (this) {
    is SSAFunctionCall -> {
        outPhi + arguments.asReversed().flatMap { it.collectPhi() } + inPhi
    }
    else -> emptyList()
}

private fun SSANode.getCoalesceMapping(): Map<SingleLocation, SingleLocation> =
    mutableMapOf<SingleLocation, SingleLocation>().also {
        collectPhi().forEach { phi ->
            phi.arguments.filterNotNull().forEach { loc ->
                // In the case of while loops, phi could reference each other circularly
                // To prevent this, only add a mapping if it doesn't exist yet
                it.getOrPut(loc) { it[phi.location] ?: phi.location }
            }
        }
    }
