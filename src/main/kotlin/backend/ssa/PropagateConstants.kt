package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.compile.IRFunctionCall
import xyz.qwewqa.trebla.backend.compile.IRNode
import xyz.qwewqa.trebla.backend.compile.IRValue
import xyz.qwewqa.trebla.backend.compile.SingleLocation
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate

fun SSANode.propagateConstants(constants: Map<SingleLocation, Double> = buildConstantMap()): SSANode = when (this) {
    is SSAValue -> this
    is SSAFunctionCall -> copy(arguments = arguments.map { it.propagateConstants(constants) })
    is SSATempRead -> constants[location]?.let { SSAValue(it) } ?: this
    is SSATempAssign -> copy(rhs = rhs.propagateConstants(constants))
    is SSASeqTempRead -> copy(offset = offset.propagateConstants(constants))
    is SSASeqTempAssign -> copy(offset = offset.propagateConstants(constants), rhs = rhs.propagateConstants(constants))
}

fun SSANode.simplifyExpressions(): SSANode {
    val simplified =  when (this) {
        is SSAValue -> this
        is SSAFunctionCall -> copy(arguments = arguments.map { it.simplifyExpressions() })
        is SSATempRead -> this
        is SSATempAssign -> copy(rhs = rhs.simplifyExpressions())
        is SSASeqTempRead -> copy(offset = offset.simplifyExpressions())
        is SSASeqTempAssign -> copy(offset = offset.simplifyExpressions(), rhs = rhs.simplifyExpressions())
    }
    return simplified.toConstantIR(emptyMap())?.tryConstexprEvaluate()?.let { SSAValue(it) } ?: simplified
}

private fun SSANode.buildConstantMap(constants: MutableMap<SingleLocation, Double> = mutableMapOf()): MutableMap<SingleLocation, Double> {
    when (this) {
        is SSAValue -> Unit
        is SSAFunctionCall -> {
            inPhi.forEach { phi ->
                phi.arguments.filterNotNull().map { constants[it] }.toSet().singleOrNull()?.let {
                    constants[phi.location] = it
                }
            }
            arguments.forEach { it.buildConstantMap(constants) }
            outPhi.forEach { phi ->
                phi.arguments.filterNotNull().map { constants[it] }.toSet().singleOrNull()?.let {
                    constants[phi.location] = it
                }
            }
        }
        is SSATempRead -> Unit
        is SSATempAssign -> {
            rhs.buildConstantMap(constants)
            rhs.toConstantIR(constants)?.tryConstexprEvaluate()?.let {
                constants[location] = it
            }
        }
        is SSASeqTempRead -> {
            offset.buildConstantMap(constants)
        }
        is SSASeqTempAssign -> {
            offset.buildConstantMap(constants)
            rhs.buildConstantMap(constants)
        }
    }
    return constants
}

private fun SSANode.toConstantIR(constants: Map<SingleLocation, Double>): IRNode? {
    return when (this) {
        is SSAValue -> IRValue(value)
        is SSAFunctionCall -> IRFunctionCall(variant, arguments.map { it.toConstantIR(constants) ?: return null })
        is SSATempRead -> constants[location]?.let { IRValue(it) }
        is SSATempAssign -> null
        is SSASeqTempRead -> null
        is SSASeqTempAssign -> null
    }
}
