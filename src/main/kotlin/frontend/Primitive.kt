package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.frontend.context.Allocator
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.expression.*

object PrimitiveBaseType : BuiltinType("Primitive")

class PrimitiveType(identifier: String) : BuiltinType(identifier), Allocatable, Callable {
    override val parentTypes = listOf(PrimitiveBaseType)

    override val allocationSize = 1

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value =
        this.fromRaw((arguments.singleOrNull()?.value as? PrimitiveInstance)?.value
            ?: compileError("Invalid arguments."))

    override fun allocateOn(allocator: Allocator, context: Context): Allocated {
        return PrimitiveInstance(allocator.allocateValue(), this)
    }

    override fun fromFlat(values: List<RawValue>): Allocated {
        return PrimitiveInstance(values[0], this)
    }
}

fun PrimitiveType.fromRaw(value: RawValue) = PrimitiveInstance(value, this)

val NumberType = PrimitiveType("Number")
val BooleanType = PrimitiveType("Boolean")
val UnknownPrimitiveType = PrimitiveType("UnknownPrimitive")

class PrimitiveInstance(val value: RawValue, override val type: PrimitiveType) : Allocated {
    override fun coerceImmutable(): Value? {
        return PrimitiveInstance(value.coerceImmutable() ?: return null, type)
    }

    override fun copyTo(allocator: Allocator, context: ExecutionContext): PrimitiveInstance {
        return PrimitiveInstance(value.copyTo(allocator, context), type)
    }

    override fun copyFrom(other: Value, context: ExecutionContext) {
        if (other !is PrimitiveInstance || type != other.type) compileError("Incompatible assigment.")
        value.copyFrom(other.value, context)
    }

    override fun toEntityArrayValue(offset: RawValue): PrimitiveInstance {
        return PrimitiveInstance(value.toEntityArrayValue(offset), type)
    }

    override fun flat(): List<RawValue> {
        return listOf(value)
    }
}

