package xyz.qwewqa.trebla.backend.ir

import xyz.qwewqa.trebla.backend.ssa.*

fun AIRNode.pruneSimple(): AIRNode = when (this) {
    is AIRValue -> this
    is AIRFunctionCall -> when (variant) {
        SonoFunction.Execute ->
            copy(
                arguments = arguments
                    .map { it.pruneSimple() }
                    .let { args ->
                        if (args.size <= 1) {
                            args
                        } else {
                            args.dropLast(1).filter { it !is AIRValue } + args.last()
                        }
                    }
                    .flattenExecute()
            ).let {
                when (it.arguments.size) {
                    0 -> AIRValue(0.0)
                    1 -> it.arguments[0]
                    else -> it
                }
            }
        else -> copy(arguments = arguments.map { it.pruneSimple() })
    }
}

private fun List<AIRNode>.flattenExecute() = flatMap {
    if (it is AIRFunctionCall && it.variant == SonoFunction.Execute) {
        it.arguments
    } else {
        listOf(it)
    }
}
