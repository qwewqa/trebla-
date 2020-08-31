package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.SonoFunction

fun SSANode.dropIneffectual(): SSANode = if (this.isDroppable()) {
    SSAValue(0.0)
} else {
    when (this) {
        is SSAValue -> this
        is SSAFunctionCall ->
            if (this.isImpure()) this else copy(arguments = arguments.map { it.dropIneffectual() })
        is SSATempRead -> this
        is SSATempAssign -> copy(rhs = rhs.dropIneffectual())
        is SSASeqTempRead -> copy(offset = offset.dropIneffectual())
        is SSASeqTempAssign -> copy(offset = offset.dropIneffectual(), rhs = rhs.dropIneffectual())
    }
}

fun SSANode.pruneSimple(): SSANode = when (this) {
    is SSAValue -> this
    is SSAFunctionCall -> when (variant) {
        SonoFunction.Execute ->
            copy(
                arguments = arguments
                    .map { it.pruneSimple() }
                    .let { args ->
                        if (args.size <= 1) {
                            args
                        } else {
                            args.dropLast(1).filter { it !is SSAValue && it !is SSATempRead } + args.last()
                        }
                    }
                    .flattenExecute()
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
