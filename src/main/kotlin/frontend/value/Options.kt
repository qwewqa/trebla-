package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.*
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.AllocatedRawValue
import xyz.qwewqa.trebla.frontend.expression.Value

class OptionsAccessor(override val parentContext: Context) : Declaration {
    override val identifier = "options"
    override val type = AnyType
    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC

    private val options = parentContext.globalContext!!.configuration.projectConfig.options.mapIndexed { i, option ->
        val name = option["name"]
        val type = option["type"]
        if (name == null) compileError("Option exists without a name.")
        if (name !is String) compileError("Option $name invalid or does not exist.")
        if (type == null) compileError("Option $name has no type specified.")
        if (type !is String) compileError("Option $name has invalid type '$type'.")
        name to OptionData(
            i,
            when (type) {
                "slider" -> OptionType.SLIDER
                "toggle" -> OptionType.TOGGLE
                else -> compileError("Unknown option type '$type'.")
            }
        )
    }.toMap()

    override fun getMember(name: String, accessingContext: Context?): Value? {
        val option = options[name] ?: run {
            // someOption -> SOME_OPTION
            val convertedName = name.fold(StringBuilder(name.length)) { acc, c ->
                if (c in 'A'..'Z') (if (acc.isNotEmpty()) acc.append('_') else acc).append(c + ('a' - 'A'))
                else acc.append(c)
            }.toString().uppercase()
            options["#$convertedName"]
        } ?: return null
        return option.optionType.type.fromRaw(
            AllocatedRawValue(ConcreteAllocation(2, option.index))
        )
    }
}

private data class OptionData(val index: Int, val optionType: OptionType)
private enum class OptionType(val type: PrimitiveType) {
    SLIDER(NumberType),
    TOGGLE(BooleanType)
}
