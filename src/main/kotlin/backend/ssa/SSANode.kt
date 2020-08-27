package xyz.qwewqa.trebla.backend.allocate

import xyz.qwewqa.trebla.backend.compile.SonoFunction

sealed class SSANode()

data class SSAValue(val value: Double) : SSANode() {
    override fun toString() = "$value"
}

sealed class SSATemp() : SSANode() {
    abstract val location: SSAReadLocation
}
sealed class SSATempReadAccess() : SSATemp()
sealed class SSATempWriteAccess() : SSATemp()

data class SSATempRead(val id: Int) : SSATempReadAccess() {
    override fun toString() = "\$$id"
    override val location = NormalReadLocation(id)
}

// Small note: using ':=' instead of '<-' because values are immutable

data class SSATempAssign(val id: Int, val rhs: SSANode) : SSATempWriteAccess() {
    override fun toString() = "\$$id := $rhs"
    override val location = NormalReadLocation(id)
}

data class SSAFunctionCall(val variant: SonoFunction, val arguments: List<SSANode>, val phiAssigns: List<Phi>) :
    SSANode() {
    override fun toString() = "$variant${
        arguments.map { it.toString() }.let {
            if (it.joinToString(", ").length > 120)
                "(\n${it.joinToString(",\n").prependIndent()}\n)"
            else
                "(${it.joinToString(", ")})"
        }
    }" + if (phiAssigns.isNotEmpty()) ":(\n${phiAssigns.joinToString(",\n").prependIndent()}\n)" else ""
}

data class SSASeqTempAssign(val id: Int, val size: Int, val offset: SSANode, val rhs: SSANode) : SSATempWriteAccess() {
    override fun toString() = "\$$id[$size|$offset] := $rhs"
    override val location = SeqReadLocation(id, size)
}

data class SSASeqTempRead(val id: Int, val size: Int, val offset: SSANode) : SSATempReadAccess() {
    override fun toString() = "\$$id[$size|$offset]"
    override val location = SeqReadLocation(id, size)
}

sealed class Phi {
    // for the purposes of eliminating dead code, treating multiple equals arguments as one makes no difference
    // so a set suffices
    abstract val locations: Set<SSAReadLocation>
    abstract val targetLocation: SSAReadLocation
}
data class PhiAssign(val id: Int, val values: List<Int?>) : Phi() {
    override fun toString() = "\$$id := \$Phi(${values.joinToString(", ") { "\$${it ?: "_"}" }})"
    override val locations = values.filterNotNull().map { NormalReadLocation(it) }.toSet()
    override val targetLocation = NormalReadLocation(id)
}
data class PhiSeqAssign(val id: Int, val size: Int, val values: List<Int?>) : Phi() {
    override fun toString() = "\$$id[$size|:] := \$Phi(${values.joinToString(", ") { "\$${it ?: "_"}[$:|:]" }})"
    override val locations = values.filterNotNull().map { SeqReadLocation(it, size) }.toSet()
    override val targetLocation = SeqReadLocation(id, size)
}

sealed class SSAReadLocation
data class NormalReadLocation(val id: Int) : SSAReadLocation()
data class SeqReadLocation(val id: Int, val size: Int) : SSAReadLocation()
