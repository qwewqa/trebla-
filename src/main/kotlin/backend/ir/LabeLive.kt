package xyz.qwewqa.trebla.backend.ir

fun IRNode.constructGraph(): IFGraph {
    liveForwards()
    liveBackwards()
    val graph = IFGraph()
    finalizeLive(graph)
    return graph
}

private fun IRNode.finalizeLive(graph: IFGraph): Unit = when (this) {
    is IRValue -> {
        graph += liveForwards intersect liveBackwards
    }
    is IRFunctionCall -> {
        arguments.forEach { it.finalizeLive(graph) }
//        graph += liveForwards intersect liveBackwards
    }
    is IRTempRead -> {
        graph += liveForwards intersect liveBackwards
    }
    is IRTempAssign -> {
//        graph += liveForwards intersect liveBackwards
        rhs.finalizeLive(graph)
    }
    is IRSeqTempRead -> {
//        graph += liveForwards intersect liveBackwards
        offset.finalizeLive(graph)
    }
    is IRSeqTempAssign -> {
//        graph += liveForwards intersect liveBackwards
        offset.finalizeLive(graph)
        rhs.finalizeLive(graph)
    }
}

private fun IRNode.liveForwards(live: Set<TempLocation> = emptySet()): Set<TempLocation> = when (this) {
    is IRValue -> {
        liveForwards += live
        live
    }
    is IRFunctionCall -> when (variant) {
        SonoFunction.If -> {
            var current = live
            current = arguments[0].liveForwards(current)
            current = arguments[1].liveForwards(current) + arguments[2].liveForwards(current)
            liveForwards += current
            current
        }
        SonoFunction.Switch -> {
            var current = live
            val conditions = mutableListOf(arguments[0])
            val branches = mutableListOf<IRNode>()
            arguments.drop(1).forEachIndexed { i, v ->
                if (i % 2 == 0) {
                    conditions += v
                } else {
                    branches += v
                }
            }
            current = conditions.fold(current) { a, v -> v.liveForwards(a) }
            current = branches.map { it.liveForwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            liveForwards += current
            current
        }
        SonoFunction.SwitchWithDefault -> {
            var current = live
            val conditions = mutableListOf(arguments[0])
            val branches = mutableListOf(arguments.last())
            arguments.drop(1).dropLast(1).forEachIndexed { i, v ->
                if (i % 2 == 0) {
                    conditions += v
                } else {
                    branches += v
                }
            }
            current = conditions.fold(current) { a, v -> v.liveForwards(a) }
            current = branches.map { it.liveForwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            liveForwards += current
            current
        }
        SonoFunction.SwitchInteger -> {
            var current = arguments[0].liveForwards(live)
            val branches = arguments.drop(1)
            current = branches.map { it.liveForwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            liveForwards += current
            current
        }
        SonoFunction.SwitchIntegerWithDefault -> {
            var current = arguments[0].liveForwards(live)
            val branches = arguments.drop(1)
            current = branches.map { it.liveForwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            liveForwards += current
            current
        }
        SonoFunction.While -> {
            var current = live
            listOf(arguments[0], arguments[1], arguments[0]).forEach {
                current = it.liveForwards(current)
            }
            liveForwards += current
            current
        }
        else -> {
            var current = live
            arguments.forEach {
                current = it.liveForwards(current)
            }
            liveForwards += current
            current
        }
    }
    is IRTempRead -> {
        val current = live + location
        liveForwards += current
        current
    }
    is IRTempAssign -> {
        val current = rhs.liveForwards(live)
        liveForwards += current
        current + location // only make this live after the assignment, so things don't interfere for copy coalescing
    }
    is IRSeqTempRead -> {
        var current = live + location
        current = offset.liveForwards(current)
        liveForwards += current
        current
    }
    is IRSeqTempAssign -> {
        var current = live + location
        current = offset.liveForwards(current)
        current = rhs.liveForwards(current)
        liveForwards += current
        current
    }
}

private fun IRNode.liveBackwards(live: Set<TempLocation> = emptySet()): Set<TempLocation> = when (this) {
    is IRValue -> {
        liveBackwards += live
        live
    }
    is IRFunctionCall -> when (variant) {
        SonoFunction.If -> {
            var current = live
            current = arguments[1].liveBackwards(current) + arguments[2].liveBackwards(current)
            current = arguments[0].liveBackwards(current)
            liveBackwards += current
            current
        }
        SonoFunction.Switch -> {
            var current = live
            val conditions = mutableListOf(arguments[0])
            val branches = mutableListOf<IRNode>()
            arguments.drop(1).forEachIndexed { i, v ->
                if (i % 2 == 0) {
                    conditions += v
                } else {
                    branches += v
                }
            }
            current = branches.map { it.liveBackwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            current = conditions.fold(current) { a, v -> v.liveBackwards(a) }
            liveBackwards += current
            current
        }
        SonoFunction.SwitchWithDefault -> {
            var current = live
            val conditions = mutableListOf(arguments[0])
            val branches = mutableListOf(arguments.last())
            arguments.drop(1).dropLast(1).forEachIndexed { i, v ->
                if (i % 2 == 0) {
                    conditions += v
                } else {
                    branches += v
                }
            }
            current = branches.map { it.liveBackwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            current = conditions.fold(current) { a, v -> v.liveBackwards(a) }
            liveBackwards += current
            current
        }
        SonoFunction.SwitchInteger -> {
            var current = live
            val branches = arguments.drop(1)
            current = branches.map { it.liveBackwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            current = arguments[0].liveBackwards(live)
            liveBackwards += current
            current
        }
        SonoFunction.SwitchIntegerWithDefault -> {
            var current = live
            val branches = arguments.drop(1)
            current = branches.map { it.liveBackwards(current) }.fold(emptySet(), Set<TempLocation>::plus)
            current = arguments[0].liveBackwards(live)
            liveBackwards += current
            current
        }
        SonoFunction.While -> {
            var current = live
            listOf(arguments[1], arguments[0], arguments[1]).forEach {
                current = it.liveBackwards(current)
            }
            liveForwards += current
            current
        }
        else -> {
            var current = live
            arguments.asReversed().forEach {
                current = it.liveBackwards(current)
            }
            liveBackwards += current
            current
        }
    }
    is IRTempRead -> {
        val current = live + location
        liveBackwards += current
        current
    }
    is IRTempAssign -> {
        var current = live + location
        current = rhs.liveBackwards(current)
        liveBackwards += current
        current
    }
    is IRSeqTempRead -> {
        var current = live + location
        current = offset.liveBackwards(current)
        liveBackwards += current
        current
    }
    is IRSeqTempAssign -> {
        var current = live + location
        current = rhs.liveBackwards(current)
        current = offset.liveBackwards(current)
        liveBackwards += current
        current
    }
}
