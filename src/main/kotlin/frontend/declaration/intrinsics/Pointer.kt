package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.declaration.TypeType
import xyz.qwewqa.trebla.frontend.expression.*

open class PointerValue(
    override val bindingContext: Context,
    val insideType: Allocatable,
    val block: RawStructValue,
    val index: RawStructValue,
) : Mutable, MemberAccessor {
    override val type = PointerType(insideType)

    override fun getMember(name: String, accessingContext: Context?): Value? = when (name) {
        "block" -> this.block
        "index" -> this.index
        else -> null
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is PointerValue || other.insideType != insideType) {
            compileError("Incompatible assignment.")
        }
        block.copyFrom(other.block, context)
        index.copyFrom(other.index, context)
    }

    override fun offsetReallocate(block: RawValue, index: RawValue): Mutable = PointerValue(
        bindingContext,
        insideType,
        this.block.offsetReallocate(block, index),
        this.index.offsetReallocate(block, index),
    )

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Mutable = PointerValue(
        bindingContext,
        insideType,
        block.copyTo(allocator, context),
        index.copyTo(allocator, context),
    )

    fun deref(context: Context) = insideType.allocateOn(DynamicAllocator(block.raw, index.raw), context)
}

/**
 * An immutable pointer, resulting from a property initialized with a box.
 * Just gives a slightly more helpful error message.
 */
class ImmutablePointer(bindingContext: Context, insideType: Allocatable, block: RawStructValue, index: RawStructValue) :
    PointerValue(bindingContext, insideType, block, index) {
    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Pointer is immutable.")
    }
}

class DerefFunction(parentContext: Context, config: CompilerConfiguration) : IntrinsicCallable by IntrinsicCallableDSL(
    parentContext,
    "deref",
    {
        "pointer" type AnyType
    },
    {
        if (callingContext == null) compileError("Deref requires a context.")
        val value = ("pointer".cast<Value>() as? PointerValue) ?: compileError("Not a pointer.")
        value.deref(callingContext)
    }
)

class BoxFunction(parentContext: Context, config: CompilerConfiguration) : IntrinsicCallable by IntrinsicCallableDSL(
    parentContext,
    "box",
    {
        "value" type AnyType
    },
    {
        if (callingContext == null) compileError("Box requires a context.")
        val value = "value".cast<Value>()
        if (value !is Mutable) compileError("Only mutable values can be boxed.")
        TransientBoxValue(callingContext, value)
    }
)

data class PointerType(val insideType: Allocatable) : Allocatable {
    override val type = TypeType
    override val bindingContext: Context? = null
    override val allocationSize = 2

    override fun allocateOn(allocator: Allocator, context: Context): Mutable {
        return PointerValue(
            context,
            insideType,
            RawStructValue(AllocatedRawValue(allocator.allocate()), context, context.numberType),
            RawStructValue(AllocatedRawValue(allocator.allocate()), context, context.numberType),
        )
    }
}

/**
 * A box is a special value, which when used as a property initializer,
 * allocates itself on the respective block and returns an immutable pointer (location known at compile time),
 * which the property takes as its value.
 */
class TransientBoxValue(override val bindingContext: Context, val inside: Mutable) : Mutable {
    override val type = TransientBoxType

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Mutable {
        val ptr = when (allocator) {
            is StandardAllocator -> allocator.allocateContiguous(inside.type.allocationSize)
            else -> compileError("Invalid location or property type for box.")
        }
        val dyn = DynamicAllocator(ptr.block.toLiteralRawValue(), ptr.index.toLiteralRawValue())
        val new = inside.copyTo(dyn, context)
        return ImmutablePointer(
            bindingContext,
            new.type,
            RawStructValue(ptr.block.toLiteralRawValue(), bindingContext, context.numberType),
            RawStructValue(ptr.index.toLiteralRawValue(), bindingContext, context.numberType),
        )
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Assignment to a transient box is not allowed.")
    }

    override fun offsetReallocate(block: RawValue, index: RawValue): Mutable {
        // The only place this should be called is as the result of a property
        // But after copying (copyOn), this should have turned into a normal box
        error("Unexpected call.")
    }
}

object TransientBoxType : BuiltinType("TransientBox"), Allocatable {
    override val allocationSize: Int
        get() = compileError("A transient box is not sized.")

    override fun allocateOn(allocator: Allocator, context: Context): Mutable {
        compileError("A transient box cannot be allocated.")
    }
}