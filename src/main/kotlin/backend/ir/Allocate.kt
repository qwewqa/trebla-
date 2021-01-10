package xyz.qwewqa.trebla.backend.ir

import xyz.qwewqa.trebla.backend.compile.backendError

fun IRNode.allocate(indexes: List<Int>) = applyAllocation(getAllocationMapping(indexes))

fun IRNode.applyAllocation(mapping: Map<TempLocation, List<Int>>): AllocatedIRNode = when (this) {
    is IRValue -> AllocatedIRValue(value)
    is IRFunctionCall -> AllocatedIRFunctionCall(variant, arguments.map { it.applyAllocation(mapping) })
    is IRTempRead -> AllocatedIRFunctionCall(
        SonoFunction.Get,
        listOf(
            AllocatedIRValue(21.0),
            AllocatedIRValue(mapping.getValue(location).single().toDouble()),
        ),
    )
    is IRTempAssign -> if (rhs is IRTempRead && mapping.getValue(rhs.location) == mapping.getValue(location)) {
        AllocatedIRValue(0.0)
    } else {
        AllocatedIRFunctionCall(
            SonoFunction.Set,
            listOf(
                AllocatedIRValue(21.0),
                AllocatedIRValue(mapping.getValue(location).single().toDouble()),
                rhs.applyAllocation(mapping),
            ),
        )
    }
    is IRSeqTempRead -> AllocatedIRFunctionCall(
        SonoFunction.GetShifted,
        listOf(
            AllocatedIRValue(21.0),
            AllocatedIRValue(mapping.getValue(location).single().toDouble()),
            offset.applyAllocation(mapping),
        ),
    )
    is IRSeqTempAssign -> AllocatedIRFunctionCall(
        SonoFunction.SetShifted,
        listOf(
            AllocatedIRValue(21.0),
            AllocatedIRValue(mapping.getValue(location).single().toDouble()),
            offset.applyAllocation(mapping),
            rhs.applyAllocation(mapping),
        ),
    )
}

fun IRNode.getAllocationMapping(indexes: List<Int>): Map<TempLocation, List<Int>> {
    val graph = constructIFGraph()
    val copies = getCopyRelations()
    graph.coalesce(copies)
    val (seq, single) = graph.nodes.partition { it.locations.singleOrNull() is SeqLocation }
    seq.forEach { it.color(indexes) }
    single.forEach { it.color(indexes) }
    return graph.nodes
        .sortedBy { it.locations.hashCode() } // for determinism between runs
        .flatMap { node -> node.locations.map { it to node.colors } }
        .toMap()
}

private fun IFGraph.IFNode.color(indexes: List<Int>) {
    val seqLocation = locations.singleOrNull() as? SeqLocation
    if (seqLocation == null) {
        val occupied = edges.flatMap { it.colors }.toSet()
        val free = indexes.firstOrNull { it !in occupied } ?: backendError("Allocation failed.")
        this.colors = listOf(free)
    } else {
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
        val range = ranges.filter { it.size >= seqLocation.size }.minByOrNull { it.size }
            ?: backendError("Allocation failed.")
        this.colors = range.take(seqLocation.size)
    }
}
