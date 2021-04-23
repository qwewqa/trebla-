package xyz.qwewqa.trebla.frontend.value.intrinsics

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.NumberType
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.value.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.frontend.fromRaw

class EntityPointerCallableType(context: Context) :
    BuiltinType("EntityPointer"),
    Subscriptable by SubscriptableDelegate(
        {
            "script" type ScriptType
        },
        {
            SpecificEntityPointerType(context, "script".cast())
        }
    )

class SpecificEntityPointerType(val context: Context, val script: ScriptDeclaration) :
    Callable,
    Allocatable {

    override val commonName = "EntityPointer"

    private val callableDelegate = CallableDelegate(
        {
            "index" type NumberType
        },
        {
            EntityPointerValue(
                NumberType
                    .fromRaw(BuiltinCallRawValue(
                        SonoFunction.Multiply,
                        listOf(
                            "index".cast<PrimitiveInstance>().value,
                            SHARED_BLOCK_SIZE.toLiteralRawValue()
                        )
                    )),
                this@SpecificEntityPointerType,
                callingContext
            )
        },
    )

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        callableDelegate.callWith(arguments, callingContext)

    override val allocationSize = 1
    override val parentTypes: List<Type> = listOf(context.getFullyQualified("std", "EntityPointer") as Type)

    override fun allocateOn(allocator: Allocator, context: Context): Allocated = EntityPointerValue(
        NumberType.fromRaw(AllocatedRawValue(allocator.allocate())),
        this,
        context
    )

    override fun fromFlat(values: List<RawValue>): Allocated = EntityPointerValue(
        NumberType.fromRaw(values[0]),
        this,
        context
    )

    override fun equals(other: Any?) = other is SpecificEntityPointerType && other.script == script
    override fun hashCode() = script.hashCode()
}


class EntityPointerValue(
    val offset: PrimitiveInstance, // this is the offset, not index (offset = index * 32 currently)
    override val type: SpecificEntityPointerType,
    val context: Context?,
) : Allocated {
    val script = type.script

    override fun getMember(name: String, accessingContext: Context?): Value? {
        if (accessingContext == null) compileError("Requires a context.")
        return script.scope.get(name)?.let {
            // This could indeed leak some values in an entity like functions (via a let declaration or within a struct)
            // Trying to use such a value would lead to undefined behavior
            if (it is Allocated) it.toEntityArrayValue(offset.value)
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

    override fun toEntityArrayValue(offset: RawValue): Allocated = EntityPointerValue(
        this.offset.toEntityArrayValue(offset),
        type,
        context
    )

    override fun flat(): List<RawValue> = listOf(offset.value)
}
