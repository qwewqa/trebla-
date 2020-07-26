package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.*
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.context.Allocation
import xyz.qwewqa.trebla.frontend.context.ConcreteAllocation
import xyz.qwewqa.trebla.frontend.context.TemporaryAllocation
import xyz.qwewqa.trebla.frontend.declaration.BuiltinFunctionVariant

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

class AllocatedValue(val allocation: Allocation) : RawValue() {
    override fun toIR() = when (allocation) {
        is ConcreteAllocation -> FunctionIRNodeVariant.Get.calledWith(
            allocation.block.toValueIRNode(),
            allocation.index.toValueIRNode()
        )
        is TemporaryAllocation -> ReadTempIRNode(allocation.id)
    }
}

class DynamicAllocatedValue(val block: IRNode, val offset: IRNode, val index: IRNode) : RawValue() {
    override fun toIR() = FunctionIRNodeVariant.GetShifted.calledWith(
        offset,
        block,
        index,
    )
}

class LiteralValue(val value: Double) : RawValue() {
    override fun toIR(): IRNode {
        return value.toValueIRNode()
    }
}

class BuiltinCallValue(val function: BuiltinFunctionVariant, val arguments: List<IRNode>) : RawValue(), Statement {
    override fun toIR(): IRNode {
        // Doing some simplification here might help with performance but this isn't tested.
        // It's really so initial IR is a bit easier to read when debugging.
        return FunctionIRNode(function.ir, arguments).let { func ->
            func.tryConstexprEvaluate()?.let { ValueIRNode(it) } ?: func
        }
    }
}

class AllocatedValueAssignment(val lhs: AllocatedValue, val rhs: RawValue) : Statement {
    override fun toIR() = when (val alloc = lhs.allocation) {
        is ConcreteAllocation -> FunctionIRNodeVariant.Set.calledWith(
            alloc.block.toValueIRNode(),
            alloc.index.toValueIRNode(),
            rhs.toIR()
        )
        is TemporaryAllocation -> AssignTempIRNode(alloc.id, rhs.toIR())
    }
}

class DynamicAllocatedValueAssignment(val lhs: DynamicAllocatedValue, val rhs: RawValue) : Statement {
    override fun toIR() = FunctionIRNodeVariant.SetShifted.calledWith(
        lhs.offset,
        lhs.block,
        lhs.index,
        rhs.toIR()
    )
}