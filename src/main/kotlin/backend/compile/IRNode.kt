package xyz.qwewqa.trebla.backend.compile

/**
 * An IR node.
 * Used for optimization and assignment of temporary memory locations.
 */
sealed class IRNode

data class ValueIRNode(val value: Double) : IRNode() {
    override fun toString() = "$value"
}

fun Number.toValueIRNode() = ValueIRNode(this.toDouble())
fun Boolean.toValueIRNode() = ValueIRNode(if (this) 1.0 else 0.0)

data class ReadTempIRNode(val id: Int) : IRNode() {
    override fun toString() = "$$id"
}

data class AssignTempIRNode(val id: Int, val rhs: IRNode) : IRNode() {
    override fun toString() = "$$id <- $rhs"
}

data class FunctionIRNode(val variant: FunctionIRNodeVariant, var arguments: List<IRNode>) : IRNode() {
    override fun toString() = "$variant${
        if (arguments.joinToString(", ").length > 120)
            "(\n${arguments.joinToString(",\n").prependIndent()}\n)"
        else
            "(${arguments.joinToString(", ")})"
    }"
}