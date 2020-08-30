package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.IRNode

fun IRNode.applySSAOptimizations(): IRNode {
    var last: IRNode? = null
    var current = this
    while (current != last) {
        last = current
        current = current
            .toSSA(SSAContext())
            .propagateConstants()
            .dropIneffectual()
            .pruneSimple()
            .pruneDeadAssigns()
            .simplifyExpressions()
            .simplifyFlow()
            .toIR()
    }
    return current
}
