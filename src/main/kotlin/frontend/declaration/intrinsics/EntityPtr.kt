package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ENTITY_DATA_ARRAY
import xyz.qwewqa.trebla.frontend.context.ENTITY_SHARED_MEMORY_ARRAY
import xyz.qwewqa.trebla.frontend.context.MemberAccessor
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.declaration.ScriptDeclaration
import xyz.qwewqa.trebla.frontend.declaration.ScriptType
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.toLiteralRawValue

class EntityPtr(parentContext: Context, val projectConfiguration: CompilerConfiguration) :
    IntrinsicCallable by IntrinsicCallableDSL(
        parentContext,
        "entityOffsetPtr",
        {
            "index" type NumberType
            "script" type ScriptType
        },
        { callingContext ->
            EntityPointer("index".cast(), "script".cast(), callingContext)
        },
    )

class EntityPointer(val index: RawStructValue, val script: ScriptDeclaration, override val bindingContext: Context?) :
    MemberAccessor {
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
