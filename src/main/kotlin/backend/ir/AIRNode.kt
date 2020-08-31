package xyz.qwewqa.trebla.backend.ir

/**
 * Allocated IR
 */
sealed class AIRNode {
    val liveForwards = mutableSetOf<TempLocation>()
    val liveBackwards = mutableSetOf<TempLocation>()
}

data class AIRValue(val value: Double) : AIRNode() {
    override fun toString() = "$value"
}

data class AIRFunctionCall(val variant: SonoFunction, val arguments: List<AIRNode>) : AIRNode() {
    override fun toString() = when {
        variant == SonoFunction.Get && arguments.all { it is AIRValue } ->
            when ((arguments[0] as AIRValue).value) {
                21.0 -> "\$${(arguments[1] as AIRValue).value.toInt()}"
                else -> "@(${
                    (arguments[0] as AIRValue).value.toInt()
                }:${
                    (arguments[1] as AIRValue).value.toInt()
                })"
            }
        variant == SonoFunction.Set && arguments.take(2).all { it is AIRValue } ->
            when ((arguments[0] as AIRValue).value) {
                21.0 -> "\$${(arguments[1] as AIRValue).value.toInt()} <- ${arguments[2]}"
                else -> "@(${
                    (arguments[0] as AIRValue).value.toInt()
                }:${
                    (arguments[1] as AIRValue).value.toInt()
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
