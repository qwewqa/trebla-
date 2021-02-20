package xyz.qwewqa.trebla.frontend.value.intrinsics

import xyz.qwewqa.trebla.frontend.value.BuiltinType
import xyz.qwewqa.trebla.frontend.expression.Value

object StringType : BuiltinType("String")

data class StringValue(
    val value: String,
) : Value {
    override val type = StringType
}
