package xyz.qwewqa.trebla.backend.ssa

fun SSANode.propagateCopies() = propagateCopies(
    @Suppress("UNCHECKED_CAST")
    getPropagatableCopies(
        getCoalesceMapping(),
        getAssigns().filterValues { it is SSATempReadAccess } as Map<SSAReadLocation, SSATempReadAccess>,
    )
)

private fun SSANode.propagateCopies(
    copyAssigns: Map<SSAReadLocation, SSATempReadAccess>,
): SSANode = when (this) {
    is SSAValue -> this
    is SSATempRead -> copyAssigns[location]?.propagateCopies(copyAssigns) ?: this
    is SSATempAssign -> this
    is SSAFunctionCall -> SSAFunctionCall(
        variant,
        arguments.map { it.propagateCopies(copyAssigns) },
        phiAssigns
    )
    is SSASeqTempAssign -> this
    is SSASeqTempRead -> copyAssigns[location]?.propagateCopies(copyAssigns) ?: this
}

private fun getPropagatableCopies(
    coalesceMappings: Map<SSAReadLocation, SSAReadLocation>,
    trivialAssigns: Map<SSAReadLocation, SSATempReadAccess>,
): Map<SSAReadLocation, SSATempReadAccess> {
    val coalesceTerminals = coalesceMappings.values.toSet()
    return trivialAssigns.filterValues { it.location in coalesceTerminals || it.location !in coalesceMappings }
}
