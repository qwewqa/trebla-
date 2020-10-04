package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.backend.ir.*
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue

/**
 * Either a location in memory, or a literal. Should generally appear within a raw struct except
 * a builtin call, which may appear as a statement some of the time.
 * The distinction between this and the [Statement] interface is that this goes inside raw structs,
 * while statements go inside execution contexts from other sources with a bit of overlap for builtin calls.
 */
sealed class RawValue {
    /**
     * Returns the IR representation of this value when read from.
     */
    abstract fun toIR(): IRNode
}

class AllocatedRawValue(val allocation: Allocation) : RawValue() {
    override fun toIR() = when (allocation) {
        is ConcreteAllocation -> SonoFunction.Get.calledWith(
            allocation.block.toIR(),
            allocation.index.toIR()
        )
        is TemporaryAllocation -> IRTempRead(allocation.id)
        is DynamicAllocation -> SonoFunction.GetShifted.calledWith(
            allocation.block.toIR(),
            allocation.index.toIR(),
            allocation.offset.toIR(),
        )
    }
}

data class LiteralRawValue(val value: Double) : RawValue() {
    override fun toIR(): IRNode {
        return value.toIR()
    }
}

fun Number.toLiteralRawValue() = LiteralRawValue(this.toDouble())
fun Boolean.toLiteralRawValue() = LiteralRawValue(if (this) 1.0 else 0.0)

class BuiltinCallRawValue(val function: SonoFunction, val arguments: List<RawValue>) : RawValue(), Statement {
    override fun toIR(): IRNode {
        // Doing some simplification here might help with performance but this isn't tested.
        // It's really so initial IR is a bit easier to read when debugging.
        return IRFunctionCall(function, arguments.map { it.toIR() }).let { func ->
            func.tryConstexprEvaluate()?.let { IRValue(it) } ?: func
        }
    }
}

fun SonoFunction.raw(vararg args: RawValue) = BuiltinCallRawValue(this, args.toList())

/**
 * Wraps an IRNode directly as raw value.
 */
class IRRawValue(val value: IRNode) : RawValue(), Statement {
    override fun toIR(): IRNode {
        return value
    }
}

fun Statement.raw() = IRRawValue(toIR())

class AllocatedValueAssignment(val lhs: AllocatedRawValue, val rhs: RawValue) : Statement {
    override fun toIR() = when (val alloc = lhs.allocation) {
        is ConcreteAllocation -> SonoFunction.Set.calledWith(
            alloc.block.toIR(),
            alloc.index.toIR(),
            rhs.toIR()
        )
        is TemporaryAllocation -> IRTempAssign(alloc.id, rhs.toIR())
        is DynamicAllocation -> SonoFunction.SetShifted.calledWith(
            alloc.block.toIR(),
            alloc.index.toIR(),
            alloc.offset.toIR(),
            rhs.toIR()
        )
    }
}

fun RawValue.toNumberStruct(context: Context) = RawStructValue(
    this,
    context.numberType
)

fun RawValue.toBooleanStruct(context: Context) = RawStructValue(
    this,
    context.booleanType
)

fun RawValue.copyFrom(other: RawValue, context: ExecutionContext) {
    when (this) {
        is AllocatedRawValue -> context.statements += AllocatedValueAssignment(this, other)
        else -> {
            val thisValue = this.toIR().tryConstexprEvaluate()
            val otherValue = other.toIR().tryConstexprEvaluate()
            if (thisValue == null || thisValue != otherValue) compileError("Value is not mutable.")
        }
    }
}

fun RawValue.copyTo(allocator: Allocator, context: ExecutionContext): AllocatedRawValue {
    val newValue = AllocatedRawValue(allocator.allocate())
    context.statements += AllocatedValueAssignment(newValue, this)
    return newValue
}

fun RawValue.toEntityArrayValue(offset: RawValue): RawValue {
    return when (this) {
        is AllocatedRawValue -> when (allocation) {
            is ConcreteAllocation ->
                AllocatedRawValue(
                    DynamicAllocation(
                        when (allocation.block) {
                            ENTITY_SHARED_MEMORY_BLOCK -> ENTITY_SHARED_MEMORY_ARRAY_BLOCK
                            ENTITY_DATA_BLOCK -> ENTITY_DATA_ARRAY_BLOCK
                            else -> compileError("Only data and shared allocated structs can be reallocated.")
                        }.toLiteralRawValue(),
                        offset,
                        this.allocation.index.toLiteralRawValue()
                    )
                )
            else -> compileError("Reallocation not available for dynamic or temporary allocated raw structs.")
        }
        is LiteralRawValue -> this
        else -> compileError("Reallocation only available for allocated or constant raw structs.")
    }
}

fun RawValue.coerceImmutable() = toIR().tryConstexprEvaluate()?.toLiteralRawValue()
