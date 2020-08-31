package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.IRNode

fun IRNode.applySSAOptimizations(): IRNode {
    var last: SSANode? = null
    var current = this.toSSA(SSAContext())
    while (current != last) {
        last = current
        current = current
            .propagateConstants()
            .dropIneffectual()
            .pruneSimple()
            .pruneDeadAssigns()
            .simplifyExpressions()
            .simplifyFlow()
    }
    return current.toIR()
}
