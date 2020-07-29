package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.compile.toValueIRNode
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class EntityPtr(override val declaringContext: Context, val projectConfiguration: CompilerConfiguration) : Declaration,
    Callable {
    override val identifier = "entityPtr"
    override val type = AnyType

    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters by lazy {
        listOf(
            Parameter("index", declaringContext.numberType),
            Parameter("script", ScriptType),
        )
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val index = argumentValues["index"] as RawStructValue
        val script = argumentValues["script"] as ScriptDeclaration
        return EntityPointer(index, script)
    }
}

class EntityPointer(val index: RawStructValue, val script: ScriptDeclaration) : MemberAccessor {
    override val type = AnyType

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return name in script.dataProperties || name in script.sharedProperties
    }

    override fun getMember(name: String, accessingContext: Context?): Value {
        return when {
            name in script.dataProperties -> {
                script.dataProperties.getValue(name).onBlock(
                    ENTITY_DATA_ARRAY,
                    BuiltinCallValue(BuiltinFunctionVariant.Multiply, listOf(32.toValueIRNode(), index.value.toIR())),
                )
            }
            name in script.sharedProperties -> {
                script.sharedProperties.getValue(name).onBlock(
                    ENTITY_SHARED_MEMORY_ARRAY,
                    BuiltinCallValue(BuiltinFunctionVariant.Multiply, listOf(32.toValueIRNode(), index.value.toIR())),
                )
            }
            else -> compileError("Unknown script member $name.")
        }
    }
}
