package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class Pointer(context: Context) :
    BuiltinType("Pointer"),
    Subscriptable by SubscriptableDelegate(
        context,
        {
            "type" type TypeType
        },
        {
            SpecificPointerType(context, "type".cast())
        }
    )

class SpecificPointerType(val context: Context, val insideType: Type) :
    Callable,
    Allocatable {
    private val callableDelegate = CallableDelegate(
        context,
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

    override val parameters: List<Parameter>?
        get() = callableDelegate.parameters

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        callableDelegate.callWith(arguments, callingContext)

    override val type = TypeType
    override val allocationSize = 2
    override val bindingHierarchy = listOf(listOf(context.getFullyQualified("std", "Pointer") as Type))

    override fun accepts(other: Type): Boolean =
        super.accepts(other) || (other is SpecificPointerType && insideType.accepts(other.insideType))

    override fun allocateOn(allocator: Allocator, context: Context): Allocated = PointerValue(
        this,
        RawStructValue(AllocatedRawValue(allocator.allocate()), context.numberType),
        RawStructValue(AllocatedRawValue(allocator.allocate()), context.numberType),
    )

    override fun equals(other: Any?) = other is SpecificPointerType && other.insideType == insideType
    override fun hashCode() = insideType.hashCode()

    override fun fromFlat(values: List<RawValue>): Allocated = PointerValue(
        this,
        RawStructValue(values[0], context.numberType),
        RawStructValue(values[1], context.numberType),
    )
}

class PointerValue(
    override val type: SpecificPointerType,
    val block: RawStructValue,
    val index: RawStructValue,
) : Allocated,
    Dereferenceable,
    Subscriptable by SubscriptableDelegate(
        type.context,
        {
            "index" type NumberType
        },
        {
            val insideType = type.insideType
            if (insideType !is SizedListType) compileError("Only pointers to lists are subscriptable")
            val listContainedType = insideType.containedType
            val elementSize = (listContainedType as? Allocatable)?.allocationSize
                ?: compileError("Only pointers to lists containing an allocatable type are subscriptable")
            val listIndex = "index".cast<RawStructValue>()
            PointerValue(
                SpecificPointerType(type.context, listContainedType),
                block,
                SonoFunction.Add.raw(
                    index.raw,
                    SonoFunction.Multiply.raw(
                        listIndex.raw,
                        elementSize.toLiteralRawValue()
                    )
                ).toNumberStruct(type.context)
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
        return type.insideType.allocateOn(DynamicAllocator(block.raw, index.raw), context)
    }

    override fun flat() = listOf(block.raw, index.raw)
}

fun Type.pointerTo(block: RawValue, index: RawValue, context: Context) =
    PointerValue(
        SpecificPointerType(context, this),
        block.toNumberStruct(context),
        index.toNumberStruct(context),
    )

class PointerTo(context: Context) :
    SimpleDeclaration(
        context,
        "pointerTo",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
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

class Deref(context: Context) :
    SimpleDeclaration(
        context,
        "deref",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            "pointer" type AnyType
        },
        {
            val value = ("pointer".cast<Value>() as? PointerValue) ?: compileError("Not a pointer.")
            value.deref(callingContext)
        }
    )
