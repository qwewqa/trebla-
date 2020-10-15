package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.SingleLocation
import xyz.qwewqa.trebla.backend.ir.SonoFunction

fun SSANode.simplifyFlow() = simplifyFlowUncleaned().cleanPhi()

private fun SSANode.simplifyFlowUncleaned(): SSANode = when (this) {
    is SSAFunctionCall -> when (variant) {
        SonoFunction.If -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(arguments[2])
                else -> simplifyTo(arguments[1])
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.And -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(SSAValue(0.0))
                else -> simplifyTo(arguments[1])
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.Or -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(arguments[1])
                else -> simplifyTo(SSAValue(1.0))
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.While -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(SSAValue(0.0))
                else -> null // Infinite loop. Allowing it, but not very useful with no way to break out
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.Switch -> (arguments[0] as? SSAValue)?.value?.let {
            val (even, odd) = arguments.drop(1).withIndex().partition { (i, _) -> i % 2 == 0 }
            val branches = even.zip(odd).associate { p -> (p.first.value as? SSAValue)?.value to p.second.value }
            branches[it]?.let { b -> simplifyTo(b) }
            // if all branch conditions are known but none are satisfied, switch does not do anything
                ?: if (null !in branches) simplifyTo(SSAValue(0.0)) else null
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.SwitchInteger -> (arguments[0] as? SSAValue)?.value?.let {
            val branches = arguments.drop(1).withIndex().associate { (i, v) -> (i + 1).toDouble() to v }
            branches[it]?.let { b -> simplifyTo(b) }
                ?: simplifyTo(SSAValue(0.0))
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.SwitchWithDefault -> (arguments[0] as? SSAValue)?.value?.let {
            val (even, odd) = arguments.drop(1).dropLast(1).withIndex().partition { (i, _) -> i % 2 == 0 }
            val branches = even.zip(odd).associate { p -> (p.first.value as? SSAValue)?.value to p.second.value }
            branches[it]?.let { b -> simplifyTo(b) }
                ?: if (null !in branches) simplifyTo(arguments.last()) else null
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        SonoFunction.SwitchIntegerWithDefault -> (arguments[0] as? SSAValue)?.value?.let {
            val branches = arguments.drop(1).dropLast(1).withIndex().associate { (i, v) -> (i + 1).toDouble() to v }
            branches[it]?.let { b -> simplifyTo(b) }
                ?: simplifyTo(arguments.last())
        } ?: copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
        else -> copy(arguments = arguments.map { it.simplifyFlowUncleaned() })
    }
    is SSAValue -> this
    is SSATempRead -> this
    is SSATempAssign -> copy(rhs = rhs.simplifyFlowUncleaned())
    is SSASeqTempRead -> copy(offset = offset.simplifyFlowUncleaned())
    is SSASeqTempAssign -> copy(offset = offset.simplifyFlowUncleaned(), rhs = rhs.simplifyFlowUncleaned())
}

private fun SSAFunctionCall.simplifyTo(node: SSANode) = SSAFunctionCall(
    SonoFunction.Execute,
    listOf(node),
    inPhi,
    outPhi,
)

private fun SSANode.cleanPhi(assigned: Set<SingleLocation> = getAssignedSet()): SSANode = when (this) {
    is SSAValue -> this
    is SSAFunctionCall -> copy(
        arguments = arguments.map { it.cleanPhi(assigned) },
        inPhi = inPhi.map { phi -> phi.copy(arguments = phi.arguments.map { if (it in assigned) it else null }) },
        outPhi = outPhi.map { phi -> phi.copy(arguments = phi.arguments.map { if (it in assigned) it else null }) }
    )
    is SSATempRead -> this
    is SSATempAssign -> copy(rhs = rhs.cleanPhi(assigned))
    is SSASeqTempRead -> copy(offset = offset.cleanPhi(assigned))
    is SSASeqTempAssign -> copy(offset = offset.cleanPhi(assigned), rhs = rhs.cleanPhi(assigned))
}

/**
 * Returns the set of assigned locations (single only) within the node.
 */
private fun SSANode.getAssignedSet(): Set<SingleLocation> = when (this) {
    is SSAValue -> emptySet()
    is SSAFunctionCall -> arguments
        .map { it.getAssignedSet() }
        .fold(emptySet<SingleLocation>()) { a, v -> a + v } + inPhi.map { it.location } + outPhi.map { it.location }
    is SSATempRead -> emptySet()
    is SSATempAssign -> setOf(location) + rhs.getAssignedSet()
    is SSASeqTempRead -> offset.getAssignedSet()
    is SSASeqTempAssign -> offset.getAssignedSet() + rhs.getAssignedSet()
}
