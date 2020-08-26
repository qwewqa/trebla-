package xyz.qwewqa.trebla.backend.allocate

import xyz.qwewqa.trebla.backend.compile.SonoFunction

sealed class SSANode()

data class SSAValue(val value: Double) : SSANode() {
    override fun toString() = "$value"
}

sealed class SSATemp() : SSANode()
sealed class SSATempReadAccess() : SSATemp()

data class SSATempRead(val id: Int) : SSATempReadAccess() {
    override fun toString() = "\$$id"
}

// Small note: using ':=' instead of '<-' because values are immutable

data class SSATempAssign(val id: Int, val rhs: SSANode) : SSATemp() {
    override fun toString() = "\$$id := $rhs"
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

data class SSASeqTempAssign(val id: Int, val size: Int, val offset: SSANode, val rhs: SSANode) : SSATemp() {
    override fun toString() = "\$$id[$size|$offset] := $rhs"
}

data class SSASeqTempRead(val id: Int, val size: Int, val offset: SSANode) : SSATempReadAccess() {
    override fun toString() = "\$$id[$size|$offset]"
}

sealed class Phi
data class PhiAssign(val id: Int, val values: List<Int?>) : Phi() {
    override fun toString() = "\$$id := \$Phi(${values.joinToString(", ") { "\$$it" }})"
}
data class PhiSeqAssign(val id: Int, val size: Int, val values: List<Int?>) : Phi() {
    override fun toString() = "\$$id[$size|:] := \$Phi(${values.joinToString(", ") { "\$${it ?: "_"}[$:|:]" }})"
}
