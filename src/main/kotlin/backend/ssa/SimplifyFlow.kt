package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.SonoFunction

fun SSANode.simplifyFlow(): SSANode = when (this) {
    is SSAFunctionCall -> when (variant) {
        SonoFunction.If -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(arguments[2])
                else -> simplifyTo(arguments[1])
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.And -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(SSAValue(0.0))
                else -> simplifyTo(arguments[1])
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.Or -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(arguments[1])
                else -> simplifyTo(SSAValue(1.0))
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.While -> (arguments[0] as? SSAValue)?.value?.let {
            when (it) {
                0.0 -> simplifyTo(SSAValue(0.0))
                else -> null // Infinite loop. Allowing it, but not very useful with no way to break out
            }
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.Switch -> (arguments[0] as? SSAValue)?.value?.let {
            val (even, odd) = arguments.drop(1).withIndex().partition { (i, _) -> i % 2 == 0 }
            val branches = even.zip(odd).associate { p -> (p.first.value as? SSAValue)?.value to p.second.value }
            branches[it]?.let { b -> simplifyTo(b) }
            // if all branch conditions are known but none are satisfied, switch does not do anything
                ?: if (null !in branches) simplifyTo(SSAValue(0.0)) else null
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.SwitchInteger -> (arguments[0] as? SSAValue)?.value?.let {
            val branches = arguments.drop(1).withIndex().associate { (i, v) -> (i + 1).toDouble() to v }
            branches[it]?.let { b -> simplifyTo(b) }
                ?: simplifyTo(SSAValue(0.0))
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.SwitchWithDefault -> (arguments[0] as? SSAValue)?.value?.let {
            val (even, odd) = arguments.drop(1).dropLast(1).withIndex().partition { (i, _) -> i % 2 == 0 }
            val branches = even.zip(odd).associate { p -> (p.first.value as? SSAValue)?.value to p.second.value }
            branches[it]?.let { b -> simplifyTo(b) }
            // if all branch conditions are known but none are satisfied, switch does not do anything
                ?: if (null !in branches) simplifyTo(arguments.last()) else null
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        SonoFunction.SwitchIntegerWithDefault -> (arguments[0] as? SSAValue)?.value?.let {
            val branches = arguments.drop(1).dropLast(1).withIndex().associate { (i, v) -> (i + 1).toDouble() to v }
            branches[it]?.let { b -> simplifyTo(b) }
                ?: simplifyTo(arguments.last())
        } ?: copy(arguments = arguments.map { it.simplifyFlow() })
        else -> copy(arguments = arguments.map { it.simplifyFlow() })
    }
    is SSAValue -> this
    is SSATempRead -> this
    is SSATempAssign -> copy(rhs = rhs.simplifyFlow())
    is SSASeqTempRead -> copy(offset = offset.simplifyFlow())
    is SSASeqTempAssign -> copy(offset = offset.simplifyFlow(), rhs = rhs.simplifyFlow())
}

private fun SSAFunctionCall.simplifyTo(node: SSANode) = SSAFunctionCall(
    SonoFunction.Execute,
    listOf(node),
    inPhi,
    outPhi,
)
