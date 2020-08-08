package xyz.qwewqa.trebla.backend.compile

/**
 * An IR node.
 * Used for optimization and assignment of temporary memory locations.
 */
sealed class IRNode

data class IRValue(val value: Double) : IRNode() {
    override fun toString() = "$value"
}

fun Number.toIR() = IRValue(this.toDouble())
fun Boolean.toIR() = IRValue(if (this) 1.0 else 0.0)

data class IRTempRead(val id: Int) : IRNode() {
    override fun toString() = "$$id"
}

data class IRTempAssign(val id: Int, val rhs: IRNode) : IRNode() {
    override fun toString() = "$$id <- $rhs"
}

data class IRFunction(val variant: IRFunctionVariant, var arguments: List<IRNode>) : IRNode() {
    override fun toString() = "$variant${
        arguments.map { it.toString() }.let {
            if (it.joinToString(", ").length > 120)
                "(\n${it.joinToString(",\n").prependIndent()}\n)"
            else
                "(${it.joinToString(", ")})"
        }
    }"
}