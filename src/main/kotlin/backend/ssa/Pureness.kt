package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.SonoFunction.*
import xyz.qwewqa.trebla.backend.compile.SonoFunction.Set

val impureFunctions = setOf(
    Set,
    Get,
    SetShifted,
    GetShifted,
    Draw,
    Play,
    PlayScheduled,
    Spawn,
    SpawnParticleEffect,
    MoveParticleEffect,
    DestroyParticleEffect,
    Random,
    RandomInteger,
)

/**
 * Similar to impure functions, but excludes things like random or get,
 * which aren't technically pure, but if they're all that exist, the code has not effect anyways
 * unless it ends up as the argument of a function which does have an effect
 */
val undroppableFunctions = setOf(
    Set,
    SetShifted,
    Draw,
    Play,
    PlayScheduled,
    Spawn,
    SpawnParticleEffect,
    MoveParticleEffect,
    DestroyParticleEffect,
)

val orderedImpureFunctions = setOf(
    Set,
    Get,
    SetShifted,
    GetShifted,
)

fun SSANode.isImpure(): Boolean = when (this) {
    is SSAFunctionCall -> variant in impureFunctions || arguments.any(SSANode::isImpure)
    is SSAValue -> false
    is SSATempRead -> true
    is SSASeqTempRead -> true
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
fun SSANode.isPure() = !isImpure()

fun SSANode.isUndroppable(): Boolean = when (this) {
    is SSAFunctionCall -> variant in undroppableFunctions || arguments.any(SSANode::isUndroppable)
    is SSAValue -> false
    is SSATempRead -> false
    is SSASeqTempRead -> false
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
fun SSANode.isDroppable() = !isUndroppable()


/*
The order sono function arguments are evaluated is not documented,
so it's best not to depend upon it.
As such, things which are order dependent should be detected, and an error thrown when encountered in these cases.
 */
fun SSANode.isOrderedImpure(): Boolean = when (this) {
    is SSAFunctionCall -> variant in orderedImpureFunctions || arguments.any(SSANode::isOrderedImpure)
    is SSAValue -> false
    is SSATempRead -> true
    is SSASeqTempRead -> true
    is SSATempAssign -> true
    is SSASeqTempAssign -> true
}
