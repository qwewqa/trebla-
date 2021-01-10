package xyz.qwewqa.trebla.backend.ir

import xyz.qwewqa.trebla.backend.ir.SonoFunction.*

val impureFunctions = setOf(
    Set,
    Get,
    SetShifted,
    GetShifted,
    Draw,
    DrawCurvedL,
    DrawCurvedR,
    DrawCurvedLR,
    DrawCurvedB,
    DrawCurvedT,
    DrawCurvedBT,
    Play,
    PlayScheduled,
    Spawn,
    SpawnParticleEffect,
    MoveParticleEffect,
    DestroyParticleEffect,
    Random,
    RandomInteger,
    DebugPause,
    DebugLog,
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
    DrawCurvedL,
    DrawCurvedR,
    DrawCurvedLR,
    DrawCurvedB,
    DrawCurvedT,
    DrawCurvedBT,
    Play,
    PlayScheduled,
    Spawn,
    SpawnParticleEffect,
    MoveParticleEffect,
    DestroyParticleEffect,
    DebugPause,
    DebugLog,
)

val orderedImpureFunctions = setOf(
    Set,
    Get,
    SetShifted,
    GetShifted,
)

fun AllocatedIRNode.isImpure(): Boolean = when (this) {
    is AllocatedIRFunctionCall -> variant in impureFunctions || arguments.any { it.isImpure() }
    is AllocatedIRValue -> false
}
fun AllocatedIRNode.isPure() = !isImpure()

fun AllocatedIRNode.isUndroppable(): Boolean = when (this) {
    is AllocatedIRFunctionCall -> variant in undroppableFunctions || arguments.any { it.isUndroppable() }
    is AllocatedIRValue -> false
}
fun AllocatedIRNode.isDroppable() = !isUndroppable()

fun AllocatedIRNode.isOrderedImpure(): Boolean = when (this) {
    is AllocatedIRFunctionCall -> variant in orderedImpureFunctions || arguments.any { it.isOrderedImpure() }
    is AllocatedIRValue -> false
}
