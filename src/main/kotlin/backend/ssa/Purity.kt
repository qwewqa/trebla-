package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.impureFunctions
import xyz.qwewqa.trebla.backend.ir.orderedImpureFunctions
import xyz.qwewqa.trebla.backend.ir.undroppableFunctions

fun SSANode.isImpure(): Boolean = when (this) {
    is SSAFunctionCall -> variant in impureFunctions || arguments.any { it.isImpure() }
    is SSAValue -> false
    is SSATempRead -> true
    is SSASeqTempRead -> true
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
fun SSANode.isPure() = !isImpure()

fun SSANode.isUndroppable(): Boolean = when (this) {
    is SSAFunctionCall -> variant in undroppableFunctions || arguments.any { it.isUndroppable() }
    is SSAValue -> false
    is SSATempRead -> false
    is SSASeqTempRead -> false
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
fun SSANode.isDroppable() = !isUndroppable()

fun SSANode.isOrderedImpure(): Boolean = when (this) {
    is SSAFunctionCall -> variant in orderedImpureFunctions || arguments.any { it.isOrderedImpure() }
    is SSAValue -> false
    is SSATempRead -> true
    is SSASeqTempRead -> true
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
