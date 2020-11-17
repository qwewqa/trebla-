package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.AllocatedRawValue
import xyz.qwewqa.trebla.frontend.expression.Value

class OptionsAccessor(override val parentContext: Context) : Declaration {
    override val identifier = "options"
    override val type = AnyType
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    private val options = parentContext.globalContext.configuration.projectConfig.options.mapIndexed { i, option ->
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
            }.toString().toUpperCase()
            options["#$convertedName"]
        } ?: return null
        return RawStructValue(
            AllocatedRawValue(ConcreteAllocation(2, option.index)),
            parentContext.getFullyQualified("std", option.type.structName) as StructDeclaration
        )
    }
}

private data class OptionData(val index: Int, val type: OptionType)
private enum class OptionType(val structName: String) {
    SLIDER("Number"),
    TOGGLE("Boolean")
}
