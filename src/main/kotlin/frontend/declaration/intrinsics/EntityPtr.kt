package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class EntityPtr(override val parentContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration,
    Callable {
    override val identifier = "entityOffsetPtr"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            // The index of the start of the entity, i.e. entity index * 32 for some blocks
            // Done this way to save a bit of performance
            Parameter("index", parentContext.numberType),
            Parameter("script", ScriptType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val index = argumentValues["index"] as RawStructValue
        val script = argumentValues["script"] as ScriptDeclaration
        return EntityPointer(index, script, callingContext)
    }
}

class EntityPointer(val index: RawStructValue, val script: ScriptDeclaration, override val bindingContext: Context?) : MemberAccessor {
    override val type = AnyType

    override fun getMember(name: String, accessingContext: Context?): Value? {
        return when {
            name in script.dataProperties -> {
                script.dataProperties.getValue(name).offsetReallocate(
                    ENTITY_DATA_ARRAY.toLiteralRawValue(),
                    index.raw,
                )
            }
            name in script.sharedProperties -> {
                script.sharedProperties.getValue(name).offsetReallocate(
                    ENTITY_SHARED_MEMORY_ARRAY.toLiteralRawValue(),
                    index.raw,
                )
            }
            else -> null
        }
    }
}
