package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.constexpr.ConstexprEvaluationException
import xyz.qwewqa.trebla.backend.constexpr.constexprEvaluate
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.project.ProjectConfiguration

class OptionsAccessor(override val declaringContext: Context, projectConfiguration: CompilerConfiguration) : Declaration, MemberAccessor {
    override val identifier = "options"
    override val type = AnyType
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    private val options = projectConfiguration.projectConfig.options.mapIndexed { i, option ->
        val name = option["name"]
        val type = option["type"]
        if (name !is String) compileError("Option name invalid or does not exist.")
        if (type !is String) compileError("Invalid option type '$type'.")
        name to OptionData(
            i,
            when (type) {
                "slider" -> OptionType.SLIDER
                "toggle" -> OptionType.TOGGLE
                else -> compileError("Unknown option type '$type'.")
            }
        )
    }.toMap()

    override fun getMember(name: String, accessingContext: Context?): Value {
        val option = options[name] ?: compileError("No option with name '$name'.")
        return RawStructValue(
            AllocatedValue(ConcreteAllocation(2, option.index)),
            accessingContext,
            declaringContext.scope.getFullyQualified("std", option.type.structName) as StructDeclaration
        )
    }

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return name in options.keys
    }
}

private data class OptionData(val index: Int, val type: OptionType)
private enum class OptionType(val structName: String) {
    SLIDER("Number"),
    TOGGLE("Boolean")
}