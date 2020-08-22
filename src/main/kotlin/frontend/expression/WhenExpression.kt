package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.context.createSimpleChild
import xyz.qwewqa.trebla.grammar.trebla.BlockNode
import xyz.qwewqa.trebla.grammar.trebla.WhenEntryNode
import xyz.qwewqa.trebla.grammar.trebla.WhenExpressionNode

class WhenExpression(override val node: WhenExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = if (node.isConst) applyConstant(context) else applyNormal(context)

    private fun applyNormal(context: Context): UnitValue {
        if (context !is ExecutionContext) compileError("non-const when statement requires an execution context.", node)
        val (conditionals, default) = getEntries()
        val elseContext = SimpleExecutionContext(context)
            .apply { default?.body?.value?.forEach { it.parseAndApplyTo(this) } }
            .toIR()
        context.statements += IRRawValue(
            conditionals.foldRight(elseContext) { entry, prev ->
                val condition = entry.condition!!.parseAndApplyTo(context).asBooleanStruct(context)
                IRFunction.If.calledWith(
                    condition.raw.toIR(),
                    SimpleExecutionContext(context)
                        .apply { entry.body.value.forEach { it.parseAndApplyTo(this) } }
                        .toIR(),
                    prev,
                )
            }
        )
        return UnitValue
    }

    private fun applyConstant(context: Context): Value {
        val (conditionals, default) = getEntries()
        conditionals.forEach { entry ->
            when (entry.condition!!.parseAndApplyTo(context).asBooleanStruct(context).raw.toIR().tryConstexprEvaluate()) {
                null -> compileError("Condition in const when must be a compile time constant.", entry.condition)
                0.0 -> {}
                1.0 -> {
                    val whenContext = context.createSimpleChild()
                    return entry.body.value
                        .asSequence()
                        .map { it.parseAndApplyTo(whenContext) }
                        .lastOrNull() ?: UnitValue
                }
            }
        }
        if (default != null) {
            val whenContext = context.createSimpleChild()
            return default.body.value
                .asSequence()
                .map { it.parseAndApplyTo(whenContext) }
                .lastOrNull() ?: UnitValue
        }
        compileError("Const when expression has no default and no conditions were satisfied.")
    }

    private fun getEntries(): Pair<List<WhenEntryNode>, WhenEntryNode?> {
        val entries = node.entries.groupBy { it.condition != null }
        val conditionals = entries[true] ?: emptyList()
        val default = entries[false]?.also {
            if (it.size > 1) compileError("when expression should only have one default.")
        }?.firstOrNull()
        return conditionals to default
    }
}