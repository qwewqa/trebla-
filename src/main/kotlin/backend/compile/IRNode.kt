package xyz.qwewqa.trebla.backend.compile

/**
 * An IR node.
 * Used for optimization and assignment of temporary memory locations.
 */
sealed class IRNode {
    var number: Int = -1
}

data class IRValue(val value: Double) : IRNode() {
    override fun toString() = "$value"
}

fun Number.toIR() = IRValue(this.toDouble())
fun Boolean.toIR() = IRValue(if (this) 1.0 else 0.0)

data class IRFunctionCall(val variant: SonoFunction, val arguments: List<IRNode>) : IRNode() {
    override fun toString() = "$variant${
        arguments.map { it.toString() }.let {
            if (it.joinToString(", ").length > 120)
                "(\n${it.joinToString(",\n").prependIndent()}\n)"
            else
                "(${it.joinToString(", ")})"
        }
    }"
}

sealed class TempLocation
data class SingleLocation(val id: Int, val version: Int? = null) : TempLocation() {
    override fun toString() = if (version == null) "\$$id" else "\$${id}_$version"
}

data class SeqLocation(val id: Int, val size: Int) : TempLocation() {
    override fun toString() = "\$$id{$size}"
}

sealed class IRTempOperation : IRNode() {
    abstract val location: TempLocation
}

data class IRTempRead(override val location: SingleLocation) : IRTempOperation() {
    constructor(id: Int) : this(SingleLocation(id))

    override fun toString() = "$location"
}

data class IRTempAssign(override val location: SingleLocation, val rhs: IRNode) : IRTempOperation() {
    constructor(id: Int, rhs: IRNode) : this(SingleLocation(id), rhs)

    override fun toString() = "$location <- $rhs"
}

data class IRSeqTempRead(override val location: SeqLocation, val offset: IRNode) : IRTempOperation() {
    constructor(id: Int, size: Int, offset: IRNode) : this(SeqLocation(id, size), offset)

    override fun toString() = "$location[$offset]"
}

data class IRSeqTempAssign(override val location: SeqLocation, val offset: IRNode, val rhs: IRNode) :
    IRTempOperation() {
    constructor(id: Int, size: Int, offset: IRNode, rhs: IRNode) : this(SeqLocation(id, size), offset, rhs)

    override fun toString() = "$location[$offset] <- $rhs"
}
