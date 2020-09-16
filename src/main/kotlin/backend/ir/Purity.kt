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
)

val orderedImpureFunctions = setOf(
    Set,
    Get,
    SetShifted,
    GetShifted,
)

fun AIRNode.isImpure(): Boolean = when (this) {
    is AIRFunctionCall -> variant in impureFunctions || arguments.any { it.isImpure() }
    is AIRValue -> false
}
fun AIRNode.isPure() = !isImpure()

fun AIRNode.isUndroppable(): Boolean = when (this) {
    is AIRFunctionCall -> variant in undroppableFunctions || arguments.any { it.isUndroppable() }
    is AIRValue -> false
}
fun AIRNode.isDroppable() = !isUndroppable()

fun AIRNode.isOrderedImpure(): Boolean = when (this) {
    is AIRFunctionCall -> variant in orderedImpureFunctions || arguments.any { it.isOrderedImpure() }
    is AIRValue -> false
}
