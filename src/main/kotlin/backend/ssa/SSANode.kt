package xyz.qwewqa.trebla.backend.ssa

import xyz.qwewqa.trebla.backend.ir.SeqLocation
import xyz.qwewqa.trebla.backend.ir.SingleLocation
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.backend.ir.TempLocation

sealed class SSANode

data class SSAValue(val value: Double) : SSANode() {
    override fun toString() = "$value"
}

data class SSAFunctionCall(
    val variant: SonoFunction,
    val arguments: List<SSANode>,
    val inPhi: List<Phi>,
    val outPhi: List<Phi>,
) : SSANode() {
    override fun toString() =
        if (inPhi.isNotEmpty()) {
            "(\n${inPhi.joinToString(",\n").prependIndent()}\n)::"
        } else {
            ""
        } + "$variant${
            arguments.map { it.toString() }.let {
                if (it.joinToString(", ").length > 120)
                    "(\n${it.joinToString(",\n").prependIndent()}\n)"
                else
                    "(${it.joinToString(", ")})"
            }
        }" + if (outPhi.isNotEmpty()) {
            "::(\n${outPhi.joinToString(",\n").prependIndent()}\n)"
        } else {
            ""
        }

    init {
        assert(inPhi.isEmpty() || variant == SonoFunction.While || variant == SonoFunction.Execute) {
            "Unexpected in phi for variant $variant"
        }
        assert(outPhi.isEmpty() || variant in controlFlowFunctions) {
            "Unexpect out phi for variant $variant"
        }
    }
}

sealed class SSATempOperation : SSANode() {
    abstract val location: TempLocation
}

data class SSATempRead(override val location: SingleLocation) : SSATempOperation() {
    constructor(id: Int) : this(SingleLocation(id))

    override fun toString() = "$location"
}

data class SSATempAssign(override val location: SingleLocation, val rhs: SSANode) : SSATempOperation() {
    constructor(id: Int, rhs: SSANode) : this(SingleLocation(id), rhs)

    override fun toString() = "$location := $rhs"
}

data class SSASeqTempRead(override val location: SeqLocation, val offset: SSANode) : SSATempOperation() {
    constructor(id: Int, size: Int, offset: SSANode) : this(SeqLocation(id, size), offset)

    override fun toString() = "$location[$offset]"
}

data class SSASeqTempAssign(override val location: SeqLocation, val offset: SSANode, val rhs: SSANode) :
    SSATempOperation() {
    constructor(id: Int, size: Int, offset: SSANode, rhs: SSANode) : this(SeqLocation(id, size), offset, rhs)

    override fun toString() = "$location[$offset] := $rhs"
}

data class Phi(val location: SingleLocation, val arguments: List<SingleLocation?>) {
    override fun toString() = "$location := \$Phi(${arguments.joinToString(", ") { it?.toString() ?: "\$_" }})"
}

val controlFlowFunctions = setOf(
    // during optimization, phi nodes might still get moved to executes
    // such as after control flow simplification
    SonoFunction.Execute,
    SonoFunction.If,
    SonoFunction.Switch,
    SonoFunction.SwitchInteger,
    SonoFunction.SwitchWithDefault,
    SonoFunction.SwitchIntegerWithDefault,
    SonoFunction.And,
    SonoFunction.Or,
    SonoFunction.While,
)
