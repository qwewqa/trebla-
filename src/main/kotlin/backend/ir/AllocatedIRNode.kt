package xyz.qwewqa.trebla.backend.ir

/**
 * Allocated IR
 */
sealed class AllocatedIRNode {
    val liveForwards = mutableSetOf<TempLocation>()
    val liveBackwards = mutableSetOf<TempLocation>()
}

data class AllocatedIRValue(val value: Double) : AllocatedIRNode() {
    override fun toString() = "$value"
}

data class AllocatedIRFunctionCall(val variant: SonoFunction, val arguments: List<AllocatedIRNode>) :
    AllocatedIRNode() {
    override fun toString() = when {
        variant == SonoFunction.Get && arguments.all { it is AllocatedIRValue } ->
            when ((arguments[0] as AllocatedIRValue).value) {
                21.0 -> "\$${(arguments[1] as AllocatedIRValue).value.toInt()}"
                else -> "@(${
                    (arguments[0] as AllocatedIRValue).value.toInt()
                }:${
                    (arguments[1] as AllocatedIRValue).value.toInt()
                })"
            }
        variant == SonoFunction.Set && arguments.take(2).all { it is AllocatedIRValue } ->
            when ((arguments[0] as AllocatedIRValue).value) {
                21.0 -> "\$${(arguments[1] as AllocatedIRValue).value.toInt()} <- ${arguments[2]}"
                else -> "@(${
                    (arguments[0] as AllocatedIRValue).value.toInt()
                }:${
                    (arguments[1] as AllocatedIRValue).value.toInt()
                }) <- ${arguments[2]}"
            }
        else -> "$variant${
            arguments.map { it.toString() }.let {
                if (it.joinToString(", ").length > 120)
                    "(\n${it.joinToString(",\n").prependIndent()}\n)"
                else
                    "(${it.joinToString(", ")})"
            }
        }"
    }
}
