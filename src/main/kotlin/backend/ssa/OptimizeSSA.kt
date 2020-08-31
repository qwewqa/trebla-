package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.IRNode

fun IRNode.applySSAOptimizations(): IRNode {
    var last: IRNode? = null
    var current = this
    while (current != last) {
        last = current
        current = current.toSSA(SSAContext()).applyOptimizationRound().toIR()
    }
    return current
}

fun SSANode.applyOptimizationRound(): SSANode {
    var last: SSANode? = null
    var current = this
    while (current != last) {
        last = current
        current = current
            .propagateConstants()
            .pruneIneffectual()
            .pruneSimple()
            .pruneDeadAssigns()
            .simplifyExpressions()
            .simplifyFlow()
    }
    return current
}
