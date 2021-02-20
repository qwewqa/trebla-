package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.NumberType
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.frontend.fromRaw

class PointerCallableType(context: Context) :
    BuiltinType("Pointer"),
    Subscriptable by SubscriptableDelegate(
        {
            "type" type TypeType
        },
        {
            SpecificPointerType(context, "type".cast())
        }
    ),
    ParameterizableType {
    override val variances = listOf(TypeVariance.Covariant)
}

class SpecificPointerType(val context: Context, val insideType: Type) :
    Callable,
    Allocatable,
    ParameterizedType {
    private val callableDelegate = CallableDelegate(
        {
            "block" type NumberType
            "index" type NumberType
        },
        {
            PointerValue(
                this@SpecificPointerType,
                "block".cast(),
                "index".cast(),
            )
        }
    )

    override val commonName = "Pointer"

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        callableDelegate.callWith(arguments, callingContext)

    override val allocationSize = 2
    override val baseType = context.getFullyQualified("std", "Pointer") as ParameterizableType

    override val typeParameters = listOf(insideType)

    override fun allocateOn(allocator: Allocator, context: Context): Allocated = PointerValue(
        this,
        NumberType.fromRaw(AllocatedRawValue(allocator.allocate())),
        NumberType.fromRaw(AllocatedRawValue(allocator.allocate())),
    )

    override fun equals(other: Any?) = other is SpecificPointerType && other.insideType == insideType
    override fun hashCode() = insideType.hashCode()

    override fun fromFlat(values: List<RawValue>): Allocated = PointerValue(
        this,
        NumberType.fromRaw(values[0]),
        NumberType.fromRaw(values[1]),
    )
}

class PointerValue(
    override val type: SpecificPointerType,
    val block: PrimitiveInstance,
    val index: PrimitiveInstance,
) : Allocated,
    Dereferenceable,
    Subscriptable by SubscriptableDelegate(
        {
            "index" type NumberType
        },
        {
            val insideType = type.insideType
            if (insideType !is SizedListType) compileError("Only pointers to lists are subscriptable")
            val listContainedType = insideType.containedType
            val elementSize = (listContainedType as? Allocatable)?.allocationSize
                ?: compileError("Only pointers to lists containing an allocatable type are subscriptable")
            val listIndex = "index".cast<PrimitiveInstance>()
            PointerValue(
                SpecificPointerType(type.context, listContainedType),
                block,
                NumberType.fromRaw(SonoFunction.Add.raw(
                    index.value,
                    SonoFunction.Multiply.raw(
                        listIndex.value,
                        elementSize.toLiteralRawValue()
                    )
                ))
            )
        }
    ) {
    override fun getMember(name: String, accessingContext: Context?): Value? = when (name) {
        "block" -> block
        "index" -> index
        else -> null
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is PointerValue || other.type != type) {
            compileError("Incompatible assignment. Dereference may be missing.")
        }
        block.copyFrom(other.block, context)
        index.copyFrom(other.index, context)
    }

    // In the case of local pointers, accessing this won't exactly give a meaningful result
    // (Entity Memory is different if you access the pointer from another entity)
    // Global pointers (to shared/data) are still meaningful, so this is allowed.
    override fun toEntityArrayValue(offset: RawValue): Allocated = PointerValue(
        type,
        this.block.toEntityArrayValue(offset),
        this.index.toEntityArrayValue(offset),
    )

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated = PointerValue(
        type,
        block.copyTo(allocator, context),
        index.copyTo(allocator, context),
    )

    override fun deref(context: Context): Value {
        if (type.insideType !is Allocatable) compileError("Cannot dereference pointer to non-allocatable type")
        return type.insideType.allocateOn(DynamicAllocator(block.value, index.value), context)
    }

    override fun flat() = listOf(block.value, index.value)
}

fun Type.pointerTo(block: RawValue, index: RawValue, context: Context) =
    PointerValue(
        SpecificPointerType(context, this),
        NumberType.fromRaw(block),
        NumberType.fromRaw(index),
    )

class PointerToCallable(context: Context) :
    SimpleDeclaration(
        context,
        "pointerTo",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "value" type AnyType
        },
        {
            val value = "value".cast<Value>()
            if (value !is Allocated) compileError("Pointers may only be created to allocated values.")
            value.pointer(context) ?: compileError("Pointers may only be created to allocated and contiguous values.")
        }
    )

interface Dereferenceable {
    fun deref(context: Context): Value
}

class DerefCallable(context: Context) :
    SimpleDeclaration(
        context,
        "deref",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "pointer" type AnyType
        },
        {
            val value = ("pointer".cast<Value>() as? PointerValue) ?: compileError("Not a pointer.")
            value.deref(callingContext)
        }
    )
