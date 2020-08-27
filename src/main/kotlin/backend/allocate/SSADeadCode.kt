package xyz.qwewqa.trebla.backend.allocate

fun SSANode.collectPhi(): List<Phi> = when (this) {
    is SSAFunctionCall -> {
        phiAssigns + arguments.asReversed().flatMap { it.collectPhi() }
    }
    else -> emptyList()
}

fun SSANode.getReads(): Map<SSAReadLocation, Int> = when (this) {
    is SSAFunctionCall -> arguments.asSequence().map { it.getReads() }
        .fold(mutableMapOf<SSAReadLocation, Int>()) { a, v ->
            v.forEach { (loc, count) -> a[loc] = (a[loc] ?: 0) + count }
            a
        }.also { reads ->
            // each value can appear in only one phi function
            // (though maybe multiple times within that one, but we count that as one collective read)
            phiAssigns.flatMap { it.locations }.forEach { reads[it] = (reads[it] ?: 0) + 1 }
        }
    is SSATempRead -> mapOf(this.location to 1)
    is SSASeqTempRead -> mapOf(this.location to 1)
    is SSATempAssign -> rhs.getReads()
    is SSASeqTempAssign -> rhs.getReads()
    is SSAValue -> emptyMap()
}

/**
 * Returns a map of locations to the rhs value of assignments, where applicable.
 * Phi assigns are not included.
 */
fun SSANode.getAssigns(): Map<SSAReadLocation, SSANode> = when (this) {
    is SSAFunctionCall -> arguments.asSequence().map { it.getAssigns() }.fold(emptyMap()) { a, v -> a + v }
    is SSATempAssign -> mapOf(location to rhs) + rhs.getAssigns()
    is SSASeqTempAssign -> mapOf(location to rhs) + offset.getAssigns() + rhs.getAssigns()
    else -> emptyMap()
}

fun SSANode.pruneUnusedAssigns() = pruneUnusedAssigns(getReads().toMutableMap())

private fun SSANode.pruneUnusedAssigns(reads: MutableMap<SSAReadLocation, Int>): SSANode = when (this) {
    is SSAValue -> this
    is SSATempRead -> this
    is SSASeqTempRead -> this
    is SSATempAssign ->
        if (reads.getOrPut(location) { 0 } == 0) {
            if (rhs.isDroppable()) {
                rhs.drop(reads)
                SSAValue(0.0)
            } else {
                rhs.pruneUnusedAssigns(reads)
            }
        }
        else {
            SSATempAssign(id, rhs.pruneUnusedAssigns(reads))
        }
    is SSASeqTempAssign ->
        if (reads.getOrPut(location) { 0 } == 0) {
            if (rhs.isDroppable()) {
                rhs.drop(reads)
                SSAValue(0.0)
            } else {
                rhs.pruneUnusedAssigns(reads)
            }
        }
        else {
            SSASeqTempAssign(
                id,
                size,
                offset.pruneUnusedAssigns(reads),
                rhs.pruneUnusedAssigns(reads),
            )
        }
    is SSAFunctionCall -> {
        /*
        Due to the constrains of Sonolus nodes (no free GOTOs)
        One pass backwards should be sufficient (if that assumption is wrong, this part probably should be changed)
         */
        val phi = phiAssigns.groupBy { reads.getOrPut(it.targetLocation) { 0 } == 0 }
        val unusedPhi = phi[true] ?: emptyList()
        val usedPhi = phi[false] ?: emptyList()
        unusedPhi.forEach { p ->
            p.locations.forEach {
                reads[it] = reads.getValue(it) - 1
            }
        }
        SSAFunctionCall(
            variant,
            arguments.asReversed().map { it.pruneUnusedAssigns(reads) }.asReversed(),
            usedPhi
        )
    }
}

private fun SSANode.drop(reads: MutableMap<SSAReadLocation, Int>) {
    getReads().forEach { (k, v) -> reads[k] = reads.getValue(k) - v }
}

fun SSANode.inlineAssigns() = inlineAssigns(getReads().toMutableMap(), getAssigns())
private fun SSANode.inlineAssigns(
    reads: MutableMap<SSAReadLocation, Int>,
    assigns: Map<SSAReadLocation, SSANode>,
): SSANode = when(this) {
    is SSATempRead ->
        assigns[location]?. let {
            if (reads.getOrPut(location) { 0 } == 1) {
                reads[location] = -1
                it.inlineAssigns(reads, assigns)
            } else {
                this
            }
        } ?: this
    is SSATempAssign ->
        if (reads.getOrPut(location) { 0 } == -1) {
            SSAValue(0.0)
        } else {
            this
        }
    is SSAFunctionCall -> SSAFunctionCall(
        variant,
        arguments.asReversed().map { it.inlineAssigns(reads, assigns) }.asReversed(),
        phiAssigns,
    )
    else -> this
}
