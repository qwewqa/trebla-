package xyz.qwewqa.trebla.backend.compile

import xyz.qwewqa.trebla.backend.ir.AllocatedIRFunctionCall
import xyz.qwewqa.trebla.backend.ir.AllocatedIRNode
import xyz.qwewqa.trebla.backend.ir.AllocatedIRValue
import xyz.qwewqa.trebla.backend.ir.SonoFunction

sealed class CompiledNode {
    abstract fun finalize(indexes: Map<CompiledNode, Int>): Map<String, Any>
}

data class CompiledValueNode(val value: Double) : CompiledNode() {
    override fun toString() = "$value"
    override fun finalize(indexes: Map<CompiledNode, Int>) = mapOf("value" to value)
}

data class CompiledFunctionNode(val name: String, val args: List<CompiledNode>) : CompiledNode() {
    override fun toString() = "$name(${args.joinToString(", ")})"
    override fun finalize(indexes: Map<CompiledNode, Int>) =
        mapOf("func" to name, "args" to args.map { indexes[it] ?: backendError("Invalid indexes") })
}

@OptIn(ExperimentalStdlibApi::class)
fun combineCallbackNodes(entryNodes: List<CompiledNode>): CompiledNodeData {
    val indexes = mutableMapOf<CompiledNode, Int>()
    val nodes = mutableListOf<CompiledNode>()
    val queue = ArrayDeque(entryNodes.sortedBy { it.hashCode() })
    for (node in queue) {
        if (node in indexes) continue
        indexes[node] = nodes.size
        nodes += node
        if (node is CompiledFunctionNode) queue += node.args
    }
    return CompiledNodeData(nodes.map { it.finalize(indexes) }, indexes)
}

data class CompiledNodeData(val orderedNodes: List<Map<String, Any>>, val indexes: Map<CompiledNode, Int>)

fun AllocatedIRNode.toCompiledNode(): CompiledNode = when (this) {
    is AllocatedIRValue -> CompiledValueNode(value)
    is AllocatedIRFunctionCall -> when (variant) {
        SonoFunction.Execute -> {
            if (arguments.isEmpty()) CompiledValueNode(0.0)
            else CompiledFunctionNode(variant.name, arguments.map { it.toCompiledNode() })
        }
        else -> CompiledFunctionNode(variant.name, arguments.map { it.toCompiledNode() })
    }
}
