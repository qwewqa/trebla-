package xyz.qwewqa.trebla.backend.ir

fun AllocatedIRNode.pruneSimple(): AllocatedIRNode = when (this) {
    is AllocatedIRValue -> this
    is AllocatedIRFunctionCall -> when (variant) {
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
                when (it.arguments.size) {
                    0 -> AllocatedIRValue(0.0)
                    1 -> it.arguments[0]
                    else -> it
                }
            }
        else -> copy(arguments = arguments.map { it.pruneSimple() })
    }
}

private fun List<AllocatedIRNode>.flattenExecute() = flatMap {
    if (it is AllocatedIRFunctionCall && it.variant == SonoFunction.Execute) {
        it.arguments
    } else {
        listOf(it)
    }
}
