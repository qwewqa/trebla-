package xyz.qwewqa.trebla.backend.ir

import xyz.qwewqa.trebla.backend.compile.backendError

fun IRNode.allocate(indexes: List<Int>) = applyAllocation(getAllocationMapping(indexes))

fun IRNode.applyAllocation(mapping: Map<TempLocation, List<Int>>): AIRNode = when (this) {
    is IRValue -> AIRValue(value)
    is IRFunctionCall -> AIRFunctionCall(variant, arguments.map { it.applyAllocation(mapping) })
    is IRTempRead -> AIRFunctionCall(
        SonoFunction.Get,
        listOf(
            AIRValue(21.0),
            AIRValue(mapping.getValue(location).single().toDouble()),
        ),
    )
    is IRTempAssign -> if (rhs is IRTempRead && mapping.getValue(rhs.location) == mapping.getValue(location)) {
        AIRValue(0.0)
    } else {
        AIRFunctionCall(
            SonoFunction.Set,
            listOf(
                AIRValue(21.0),
                AIRValue(mapping.getValue(location).single().toDouble()),
                rhs.applyAllocation(mapping),
            ),
        )
    }
    is IRSeqTempRead -> AIRFunctionCall(
        SonoFunction.GetShifted,
        listOf(
            AIRValue(21.0),
            AIRValue(mapping.getValue(location).single().toDouble()),
            offset.applyAllocation(mapping),
        ),
    )
    is IRSeqTempAssign -> AIRFunctionCall(
        SonoFunction.SetShifted,
        listOf(
            AIRValue(21.0),
            AIRValue(mapping.getValue(location).single().toDouble()),
            offset.applyAllocation(mapping),
            rhs.applyAllocation(mapping),
        ),
    )
}

fun IRNode.getAllocationMapping(indexes: List<Int>): Map<TempLocation, List<Int>> {
    val graph = constructGraph()
    val copies = getCopyRelations()
    graph.coalesce(copies)
    val (seq, single) = graph.nodes.partition { it.locations.singleOrNull() is SeqLocation }
    seq.forEach { it.color(indexes) }
    single.forEach { it.color(indexes) }
    return graph.nodes.flatMap { node -> node.locations.map { it to node.colors } }.toMap()
}

private fun IFGraph.IFNode.color(indexes: List<Int>) {
    (locations.singleOrNull() as? SeqLocation)?.let { loc ->
        val occupied = edges.flatMap { it.colors }.toSet()
        val free = indexes.filter { it !in occupied }
        if (free.isEmpty()) backendError("Allocation failed.")
        val ranges = mutableListOf(mutableListOf(free.first()))
        free.drop(1).forEach {
            if (it - ranges.last().last() == 1) {
                ranges.last() += it
            } else {
                ranges += mutableListOf(it)
            }
        }
        val range = ranges.filter { it.size >= loc.size }.minByOrNull { it.size } ?: backendError("Allocation failed.")
        colors = range.take(loc.size)
    } ?: run {
        val occupied = edges.flatMap { it.colors }.toSet()
        val free = indexes.firstOrNull { it !in occupied } ?: backendError("Allocation failed.")
        colors = listOf(free)
    }
}
