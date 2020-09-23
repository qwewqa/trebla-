package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class EntityPointer(context: Context) :
    SimpleDeclaration(
        context,
        "EntityPointer",
        TypeType
    ),
    Subscriptable by SubscriptableDelegate(
        context,
        {
            "script" type ScriptType
        },
        {
            SpecificEntityPointerType(context, "script".cast())
        }
    ),
    Type

class SpecificEntityPointerType(context: Context, val script: ScriptDeclaration) :
    Callable,
    Allocatable {
    val callableDelegate = CallableDelegate(
        context,
        {
            "index" type NumberType
        },
        {
            EntityPointerValue(
                RawStructValue(
                    BuiltinCallRawValue(
                        SonoFunction.Multiply,
                        listOf(
                            "index".cast<RawStructValue>().raw,
                            SHARED_BLOCK_SIZE.toLiteralRawValue()
                        )
                    ),
                    context.numberType
                ),
                this@SpecificEntityPointerType,
                callingContext
            )
        },
    )

    override val parameters: List<Parameter>?
        get() = callableDelegate.parameters

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        callableDelegate.callWith(arguments, callingContext)

    override val type = TypeType
    val bindingContext = context
    override val allocationSize = 1
    override val bindingHierarchy = listOf(listOf(bindingContext.getFullyQualified("std", "EntityPointer") as Type))

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        return EntityPointerValue(
            RawStructValue(AllocatedRawValue(allocator.allocate()), context.numberType),
            this,
            context
        )
    }

    override fun equals(other: Any?) = other is SpecificEntityPointerType && other.script == script
    override fun hashCode() = script.hashCode()
}


class EntityPointerValue(
    val offset: RawStructValue, // this is the offset, not index (offset = index * 32 currently)
    override val type: SpecificEntityPointerType,
    val context: Context?,
) : MemberAccessor,
    Allocated {
    val script = type.script

    override fun getMember(name: String, accessingContext: Context?): Value? {
        if (accessingContext == null) compileError("Requires a context.")
        return (script.dataProperties[name] ?: script.sharedProperties[name] ?: script.sharedLetDeclarations[name])?.let {
            // This could indeed leak some values in an entity like functions (via a let declaration or within a struct)
            // Trying to use such a value would lead to undefined behavior
            if (it is Allocated) it.offsetReallocate(offset.raw)
            else it
        }
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated = EntityPointerValue(
        offset.copyTo(allocator, context),
        type,
        this.context
    )

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is EntityPointerValue || other.type != type) {
            compileError("Incompatible assignment.")
        }
        offset.copyFrom(other.offset, context)
    }

    override fun offsetReallocate(offset: RawValue): Allocated = EntityPointerValue(
        this.offset.offsetReallocate(offset),
        type,
        context
    )
}
