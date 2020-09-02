package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class Pointer(context: Context) :
    SimpleDeclaration(
        context,
        "Pointer",
        TypeType
    ),
    Subscriptable by SubscriptableDSL(
        context,
        {
            "type" type TypeType
        },
        {
            SpecificPointerType(callingContext, "type".cast())
        }
    ),
    Type

class SpecificPointerType(context: Context, val insideType: Type) :
    Callable,
    Allocatable {
    private val callableDelegate = CallableDSL(
        context,
        {
            "block" type NumberType
            "index" type NumberType
        },
        {
            PointerValue(
                context,
                this@SpecificPointerType, // this not exposed in delegation through the by keyword
                "block".cast(),
                "index".cast(),
            )
        }
    )

    override val parameters: List<Parameter>?
        get() = callableDelegate.parameters

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        callableDelegate.callWith(arguments, callingContext)

    override val type = TypeType
    override val bindingContext = context
    override val allocationSize = 2
    override val bindingHierarchy = listOf(listOf(bindingContext.scope.getFullyQualified("std", "Pointer") as Type))

    override fun accepts(other: Type): Boolean {
        return super.accepts(other) || (other is SpecificPointerType && insideType.accepts(other.insideType))
    }

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        return PointerValue(
            context,
            this,
            RawStructValue(AllocatedRawValue(allocator.allocate()), context, context.numberType),
            RawStructValue(AllocatedRawValue(allocator.allocate()), context, context.numberType),
        )
    }

    override fun equals(other: Any?) = other is SpecificPointerType && other.insideType == insideType
    override fun hashCode() = insideType.hashCode()
}

class PointerValue(
    override val bindingContext: Context,
    override val type: SpecificPointerType,
    val block: RawStructValue,
    val index: RawStructValue,
) : Allocated,
    MemberAccessor,
    Dereferenceable,
    Subscriptable by SubscriptableDSL(
        bindingContext,
        {
            "index" type NumberType
        },
        {
            val insideType = type.insideType
            if (insideType !is SpecificListType) compileError("Only pointers to lists are subscriptable")
            val listContainedType = insideType.containedType
            val elementSize = (listContainedType as? Allocatable)?.allocationSize
                ?: compileError("Only pointers to lists containing an allocatable type are subscriptable")
            val listIndex = "index".cast<RawStructValue>()
            PointerValue(
                bindingContext,
                SpecificPointerType(bindingContext, listContainedType),
                block,
                SonoFunction.Add.raw(
                    index.raw,
                    SonoFunction.Multiply.raw(
                        listIndex.raw,
                        elementSize.toLiteralRawValue()
                    )
                ).toNumberStruct(bindingContext)
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
    override fun offsetReallocate(offset: RawValue): Allocated = PointerValue(
        bindingContext,
        type,
        this.block.offsetReallocate(offset),
        this.index.offsetReallocate(offset),
    )

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated = PointerValue(
        bindingContext,
        type,
        block.copyTo(allocator, context),
        index.copyTo(allocator, context),
    )

    override fun deref(context: Context): Value {
        if (type.insideType !is Allocatable) compileError("Cannot dereference pointer to non-allocatable type")
        return type.insideType.allocateOn(DynamicAllocator(block.raw, index.raw), context)
    }
}

interface Dereferenceable {
    fun deref(context: Context): Value
}

class Deref(context: Context) :
    SimpleDeclaration(
        context,
        "deref",
        CallableType
    ),
    Callable by CallableDSL(
        context,
        {
            "pointer" type AnyType
        },
        {
            val value = ("pointer".cast<Value>() as? PointerValue) ?: compileError("Not a pointer.")
            value.deref(callingContext)
        }
    )
