package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.context.evaluateInChildOf
import xyz.qwewqa.trebla.grammar.trebla.WhenConditionalEntryNode
import xyz.qwewqa.trebla.grammar.trebla.WhenElseEntryNode
import xyz.qwewqa.trebla.grammar.trebla.WhenExpressionNode

class WhenExpression(override val node: WhenExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = if (node.isConst) applyConstant(context) else applyNormal(context)

    private fun applyNormal(context: Context): UnitValue {
        if (context !is ExecutionContext) compileError("non-const when statement requires an execution context.", node)
        val (conditionals, default) = getEntries()
        val elseContext = SimpleExecutionContext(context)
            .apply { default?.body?.value?.forEach { it.parseAndApplyTo(this) } }
            .statements
            .asIR()
        context.statements +=
            conditionals.foldRight(elseContext) { entry, prev ->
                val conditionContext = SimpleExecutionContext(context)
                val condition = entry.condition.parseAndApplyTo(conditionContext).asBooleanStruct(context)
                SonoFunction.If.calledWith(
                    SonoFunction.Execute.calledWith(
                        conditionContext.statements.asIR(),
                        condition.raw.toIR(context),
                    ),
                    SimpleExecutionContext(context)
                        .apply { entry.body.value.forEach { it.parseAndApplyTo(this) } }
                        .statements
                        .asIR(),
                    prev,
                )
            }

        return UnitValue
    }

    private fun applyConstant(context: Context): Value {
        val (conditionals, default) = getEntries()
        conditionals.forEach { entry ->
            if (entry.condition.parseAndEvaluateConstantBoolean(context)) {
                return entry.body.evaluateInChildOf(context)
            }
        }
        if (default != null) return default.body.evaluateInChildOf(context)
        compileError("Const when expression has no default and no conditions were satisfied.")
    }

    private fun getEntries(): Pair<List<WhenConditionalEntryNode>, WhenElseEntryNode?> {
        val conditionals = node.entries.filterIsInstance<WhenConditionalEntryNode>()
        val default = node.entries.filterIsInstance<WhenElseEntryNode>().also {
            if (it.size > 1) compileError("when expression should only have one default.")
        }.firstOrNull()
        return conditionals to default
    }
}
