package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.SonoFunction

fun SSANode.pruneIneffectual(): SSANode = if (this.isDroppable()) {
    SSAValue(0.0)
} else {
    when (this) {
        is SSAValue -> this
        is SSAFunctionCall ->
            if (this.isImpure()) this else copy(arguments = arguments.map { it.pruneIneffectual() })
        is SSATempRead -> this
        is SSATempAssign -> copy(rhs = rhs.pruneIneffectual())
        is SSASeqTempRead -> copy(offset = offset.pruneIneffectual())
        is SSASeqTempAssign -> copy(offset = offset.pruneIneffectual(), rhs = rhs.pruneIneffectual())
    }
}

fun SSANode.pruneSimple(): SSANode = when (this) {
    is SSAValue -> this
    is SSAFunctionCall -> when (variant) {
        SonoFunction.Execute ->
            copy(
                arguments = arguments
                    .flattenExecute()
                    .map { it.pruneSimple() }
                    .let { args ->
                        if (args.size <= 1) {
                            args
                        } else {
                            args.dropLast(1).filter { it.isUndroppable() } + args.last()
                        }
                    }
            ).let {
                when {
                    it.arguments.isEmpty() -> SSAValue(0.0)
                    it.arguments.size == 1 && it.inPhi.isEmpty() && it.outPhi.isEmpty() -> it.arguments[0]
                    else -> it
                }
            }
        else -> copy(arguments = arguments.map { it.pruneSimple() })
    }
    is SSATempRead -> this
    is SSATempAssign -> copy(rhs = rhs.pruneSimple())
    is SSASeqTempRead -> copy(offset = offset.pruneSimple())
    is SSASeqTempAssign -> copy(offset = offset.pruneSimple(), rhs = rhs.pruneSimple())
}

private fun List<SSANode>.flattenExecute() = flatMap {
    if (it is SSAFunctionCall && it.variant == SonoFunction.Execute && it.inPhi.isEmpty() && it.outPhi.isEmpty()) {
        it.arguments
    } else {
        listOf(it)
    }
}
