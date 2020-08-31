package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.backend.ir.TempLocation

fun SSANode.pruneDeadAssigns(uses: Map<TempLocation, Int> = countUses()): SSANode = when (this) {
    is SSAValue -> this
    is SSAFunctionCall -> {
        val newOutPhi = outPhi.filter { uses.getOrDefault(it.location, 0) != 0 }
        val newArguments = arguments.map { it.pruneDeadAssigns(uses) }
        val newInPhi = inPhi.filter { uses.getOrDefault(it.location, 0) != 0 }
        this.copy(
            arguments = newArguments,
            inPhi = newInPhi,
            outPhi = newOutPhi
        )
    }
    is SSATempRead -> this
    is SSATempAssign -> if (uses.getOrDefault(location, 0) == 0) {
        rhs.pruneDeadAssigns(uses)
    } else {
        copy(rhs = rhs.pruneDeadAssigns(uses))
    }
    is SSASeqTempRead -> copy(offset = offset.pruneDeadAssigns(uses))
    is SSASeqTempAssign -> if (uses.getOrDefault(location, 0) == 0) {
        SSAFunctionCall(
            SonoFunction.Execute,
            listOf(offset.pruneDeadAssigns(uses), rhs.pruneDeadAssigns(uses)),
            emptyList(),
            emptyList(),
        )
    } else {
        copy(offset = offset.pruneDeadAssigns(uses), rhs = rhs.pruneDeadAssigns(uses))
    }
}

fun SSANode.countUses(): Map<TempLocation, Int> = when (this) {
    is SSAValue -> emptyMap()
    is SSAFunctionCall -> (arguments.map { it.countUses() } +
            inPhi.flatMap { it.arguments }.filterNotNull().associateWith { 1 } +
            outPhi.flatMap { it.arguments }.filterNotNull().associateWith { 1 })
        .fold(hashMapOf()) { a, v -> v.forEach { (id, c) -> a.merge(id, c, Int::plus) }; a }
    is SSATempRead -> mapOf(location to 1)
    is SSATempAssign -> rhs.countUses()
    is SSASeqTempRead -> offset.countUses().toMutableMap().also { it[location] = (it[location] ?: 0) + 1 }
    is SSASeqTempAssign -> listOf(offset.countUses(), rhs.countUses())
        .fold(hashMapOf()) { a, v -> v.forEach { (id, c) -> a.merge(id, c, Int::plus) }; a }
}
