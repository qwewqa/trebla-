package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.expression.Value

object MapProxyType : BuiltinType("MapProxy")

class MapProxyInstance(val values: Map<String, Value>): Value {
    constructor(vararg values: Pair<String, Value>): this(values.toMap())

    override val type = MapProxyType

    override fun getMember(name: String, accessingContext: Context?) = values[name]
}
