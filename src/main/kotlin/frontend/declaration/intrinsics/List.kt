package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.expression.*
import kotlin.math.roundToInt

class ListBaseType(context: Context) :
    BuiltinType("List"),
    Subscriptable by SubscriptableDelegate(
        context,
        {
            "type" type TypeType
        },
        {
            val type = "type".cast<Type>()
            UnsizedListType(type, context)
        },
    ),
    ParameterizableType {
    override val variances = listOf(TypeVariance.Covariant)
}

class ListOfCallable(context: Context) :
    SimpleDeclaration(
        context,
        "listOf",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            unmanaged
        },
        {
            if (arguments.any { it.name != null }) compileError("listOf requires only unnamed arguments.")
            val values = arguments.map { it.value }
            val type = values.map { it.type }.toSet().singleOrNull() ?: if (values.isEmpty()) NothingType else AnyType
            ListValue(context, SizedListType(arguments.size, UnsizedListType(type, context), context), values)
        },
    )

data class UnsizedListType(val containedType: Type, val context: Context) : ParameterizedType, ParameterizableType,
    Subscriptable {
    override val type = TypeType
    override val baseType = context.getFullyQualified("std", "List") as ParameterizableType
    override val typeParameters = listOf(containedType)

    // The size of the list is compared via an equality check
    override val variances = listOf(TypeVariance.Equality)

    override val commonName = "List"

    private val subscriptDelegate = SubscriptableDelegate(
        context,
        {
            "size" type NumberType
        },
        {

            val size = "size".cast<RawStructValue>().raw.tryConstexprEvaluate()?.roundToInt()
                ?: compileError("List size must be a compile time constant.")
            SizedListType(size, this@UnsizedListType, context)
        }
    )

    override fun subscriptWith(arguments: List<ValueArgument>, callingContext: Context) =
        subscriptDelegate.subscriptWith(arguments, callingContext)

    override fun getMember(name: String, accessingContext: Context?) = when (name) {
        "containedType" -> containedType
        else -> null
    }
}

data class SizedListType(val size: Int, override val baseType: UnsizedListType, val context: Context) : Type,
    Allocatable, ParameterizedType {
    override val type = TypeType
    override val typeParameters = listOf(size)
    val containedType = baseType.containedType

    override val commonName = "List"

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        if (containedType !is Allocatable) {
            compileError("List allocation is only possible for lists with allocatable types.")
        }
        return ListValue(context, this, List(size) { containedType.allocateOn(allocator, context) })
    }

    override val allocationSize by lazy {
        if (containedType !is Allocatable) {
            compileError("List not sized because the contained type is not sized.")
        }
        size * containedType.allocationSize
    }

    override fun equals(other: Any?): Boolean =
        other is SizedListType && size == other.size && containedType == other.containedType

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + containedType.hashCode()
        return result
    }

    override fun getMember(name: String, accessingContext: Context?): Value? {
        return when (name) {
            "containedType" -> containedType
            "size" -> size.toStruct(context)
            else -> null
        }
    }

    override fun fromFlat(values: List<RawValue>): Allocated {
        if (containedType !is Allocatable) {
            // probably shouldn't happen since there'd be no way to even get a flat version
            compileError("Unexpected list construction from flat.")
        }
        val chunks = values.chunked(containedType.allocationSize)
        return ListValue(context, this, List(size) { containedType.fromFlat(chunks[it]) })
    }
}

class ListValue(val parentContext: Context, override val type: SizedListType, val values: List<Value>) : Allocated {
    override fun toEntityArrayValue(offset: RawValue): Allocated =
        ListValue(
            parentContext,
            type,
            values.map {
                (it as? Allocated)?.toEntityArrayValue(offset)
                    ?: compileError("Reallocation not possible for lists containing non-allocated values")
            }
        )

    override fun copyTo(allocator: Allocator, context: ExecutionContext): Allocated =
        ListValue(
            context,
            type,
            values.map {
                (it as? Allocated)?.copyTo(allocator, context)
                    ?: compileError("Copying not possible for lists containing non-allocated values")
            }
        )

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other is ListValue && other.type == type) {
            values.zip(other.values).forEach { (v, o) ->
                if (v !is Allocated || o !is Allocated) compileError("Assignment not possible for non-allocated values.")
                v.copyFrom(o, context)
            }
        } else {
            compileError("Incompatible assignment.")
        }
    }

    override fun flat() = values.flatMap {
        (it as? Allocated)?.flat() ?: compileError("List has non allocated members and cannot be flattened.")
    }
}
