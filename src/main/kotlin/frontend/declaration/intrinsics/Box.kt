package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*

class Box(context: Context) :
    SimpleDeclaration(
        context,
        "Box",
        TypeType
    ),
    Subscriptable by SubscriptableDSL(
        context,
        {
            "type" type TypeType
        },
        {
            val type =
                ("type".cast<Type>() as? Allocatable) ?: compileError("Only box of allocatable types are allowed.")
            SpecificBoxType(callingContext, type)
        }
    ),
    Type

class BoxValue(
    override val bindingContext: Context,
    override val type: SpecificBoxType,
    val block: Int,
    val index: Int,
) : MemberAccessor, Dereferenceable, Allocated {
    private val blockStruct = RawStructValue(block.toLiteralRawValue(), bindingContext, bindingContext.numberType)
    private val indexStruct = RawStructValue(index.toLiteralRawValue(), bindingContext, bindingContext.numberType)

    override fun getMember(name: String, accessingContext: Context?): Value? = when (name) {
        "block" -> blockStruct
        "index" -> indexStruct
        else -> null
    }

    override fun deref(context: Context): Value =
        type.insideType.allocateOn(DynamicAllocator(blockStruct.raw, indexStruct.raw), context)

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated {
        compileError("Box cannot be copied.")
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Box cannot be mutated.")
    }

    override fun offsetReallocate(block: RawValue, index: RawValue): Allocated {
        compileError("Box cannot be reallocated.")
    }
}

class SpecificBoxType(context: Context, val insideType: Allocatable) : Allocatable {
    override val type = TypeType
    override val bindingContext = context
    override val allocationSize = insideType.allocationSize
    override val bindingHierarchy = listOf(listOf(bindingContext.scope.getFullyQualified("std", "Box") as Type))

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        val ptr = when (allocator) {
            is StandardAllocator -> allocator.allocateContiguous(insideType.allocationSize)
            else -> compileError("Invalid location or property type for box.")
        }
        return BoxValue(
            context,
            this,
            ptr.block,
            ptr.index,
        )
    }

    override fun equals(other: Any?) = other is SpecificBoxType && other.insideType == insideType
    override fun hashCode() = insideType.hashCode()
}

class ResolveBoxPointer(context: Context) :
    SimpleDeclaration(
        context,
        "resolveBoxPointer",
        CallableType
    ),
    Callable by CallableDSL(
        context,
        {
            "box" type (context.scope.getFullyQualified("std", "Box") as Type)
            "entityIndex" type NumberType
        },
        {
            val box: BoxValue = "box".cast()
            val entityIndex: RawStructValue = "entityIndex".cast()
            val newBlock = when (box.block) {
                ENTITY_DATA -> ENTITY_DATA_ARRAY
                ENTITY_SHARED_MEMORY -> ENTITY_SHARED_MEMORY_ARRAY
                else -> compileError("Box pointer only exists for data and shared memory.")
            }
            val coef = 32 // same for both arrays, currently
            PointerValue(
                callingContext,
                SpecificPointerType(callingContext, box.type.insideType),
                RawStructValue(newBlock.toLiteralRawValue(), callingContext, context.numberType),
                RawStructValue(
                    BuiltinCallRawValue(
                        IRFunction.Add,
                        listOf(
                            BuiltinCallRawValue(
                                IRFunction.Multiply,
                                listOf(
                                    entityIndex.raw,
                                    coef.toLiteralRawValue()
                                )
                            ),
                            box.index.toLiteralRawValue()
                        )
                    ),
                    callingContext,
                    context.numberType
                )
            )
        }
    )

class ResolveLocalBoxPointer(context: Context) :
    SimpleDeclaration(
        context,
        "resolveLocalBoxPointer",
        CallableType
    ),
    Callable by CallableDSL(
        context,
        {
            "box" type (context.scope.getFullyQualified("std", "Box") as Type)
        },
        {
            val box: BoxValue = "box".cast()
            PointerValue(
                callingContext,
                SpecificPointerType(callingContext, box.type.insideType),
                RawStructValue(box.block.toLiteralRawValue(), callingContext, context.numberType),
                RawStructValue(box.index.toLiteralRawValue(), callingContext, context.numberType),
            )
        }
    )

/**
 * A transient box is a special value, which when used as a property initializer,
 * allocates itself on the respective block and returns a box (like a pointer with location known at compile time),
 * which the property takes as its value.
 */
class TransientBoxValue(override val bindingContext: Context, val inside: Allocated) : Allocated {
    override val type = TransientBoxType

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated {
        val ptr = when (allocator) {
            is StandardAllocator -> allocator.allocateContiguous(inside.type.allocationSize)
            else -> compileError("Invalid location or property type for box.")
        }
        val dyn = DynamicAllocator(ptr.block.toLiteralRawValue(), ptr.index.toLiteralRawValue())
        val new = inside.copyTo(dyn, context)
        return BoxValue(
            bindingContext,
            SpecificBoxType(context, new.type),
            ptr.block,
            ptr.index,
        )
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        compileError("Assignment to a transient box is not allowed.")
    }

    override fun offsetReallocate(block: RawValue, index: RawValue): Allocated {
        // The only place this should be called is as the result of a property
        // But after copying (copyOn), this should have turned into a normal box
        error("Unexpected call.")
    }
}

object TransientBoxType : BuiltinType("TransientBox"), Allocatable {
    override val allocationSize: Int
        get() = compileError("A transient box is not sized.")

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        compileError("A transient box cannot be allocated.")
    }
}
