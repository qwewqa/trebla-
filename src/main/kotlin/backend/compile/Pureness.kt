package xyz.qwewqa.trebla.backend.compile

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

val orderedImpureFunctions = setOf(
    Set,
    SetShifted,
)

fun IRNode.isImpure(): Boolean = when (this) {
    is IRFunctionCall -> variant in impureFunctions || arguments.any(IRNode::isImpure)
    is IRValue -> false
    is IRTempRead -> true
    is IRSeqTempRead -> true
    is IRTempAssign -> true
    is IRSeqTempAssign -> true
}

fun IRNode.isPure() = !isImpure()

/*
The order sono function arguments are evaluated is not documented,
so it's best not to depend upon it.
As such, things which are order dependent should be detected, and an error thrown when encountered in these cases.
 */
fun IRNode.isOrderedImpure(): Boolean = when (this) {
    is IRFunctionCall -> variant in orderedImpureFunctions || arguments.any(IRNode::isOrderedImpure)
    is IRValue -> false
    is IRTempRead -> false
    is IRSeqTempRead -> false
    is IRTempAssign -> true
    is IRSeqTempAssign -> true
}
