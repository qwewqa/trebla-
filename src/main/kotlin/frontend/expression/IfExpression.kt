package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.SonoFunction
import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.grammar.trebla.ExpressionNode
import xyz.qwewqa.trebla.grammar.trebla.IfExpressionNode

class IfExpression(override val node: IfExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = if (node.isConst) applyConstant(context) else applyNormal(context)

    private fun applyNormal(context: Context): UnitValue {
        if (context !is ExecutionContext) compileError("non-const if statement requires an execution context.", node)
        val condition = node.condition.parseAndApplyTo(context).asBooleanStruct(context)
        context.statements += SonoFunction.If.raw(
            condition.raw,
            SimpleExecutionContext(context).also { ctx ->
                node.tbranch.evaluateIn(ctx)
            }.raw(),
            SimpleExecutionContext(context).also { ctx ->
                node.fbranch?.evaluateIn(ctx)
            }.raw()
        )
        return UnitValue
    }

    private fun applyConstant(context: Context): Value {
        val selectedBranch = if (node.condition.parseAndEvaluateConstantBoolean(context)) {
            node.tbranch.value
        } else {
            node.fbranch?.value ?: emptyList()
        }
        return selectedBranch.evaluateInChildOf(context)
    }
}

fun Value.asBooleanStruct(context: Context): RawStructValue {
    if (this !is RawStructValue || this.type != context.booleanType) {
        compileError("Value must be a boolean.", this.node)
    }
    return this
}

fun Value.asNumberStruct(context: Context): RawStructValue {
    if (this !is RawStructValue || this.type != context.numberType) {
        compileError("Value must be a number.", this.node)
    }
    return this
}

fun ExpressionNode.parseAndEvaluateConstantBoolean(context: Context): Boolean {
    val parsed = parseAndApplyTo(SimpleContext(context)).asBooleanStruct(context)
    val result = parsed.raw.toIR().tryConstexprEvaluate()
        ?: compileError("Boolean must be a compile time constant.", this)
    return when (result) {
        1.0 -> true
        0.0 -> false
        else -> compileError("Invalid value for boolean: $result")
    }
}

fun ExpressionNode.parseAndEvaluateConstantNumber(context: Context): Double {
    val parsed = parseAndApplyTo(SimpleContext(context)).asNumberStruct(context)
    return parsed.raw.toIR().tryConstexprEvaluate()
        ?: compileError("Number must be a compile time constant.", this)
}
