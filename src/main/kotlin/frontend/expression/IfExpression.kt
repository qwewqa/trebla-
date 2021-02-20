package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.BooleanType
import xyz.qwewqa.trebla.frontend.NumberType
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.grammar.trebla.ExpressionNode
import xyz.qwewqa.trebla.grammar.trebla.IfExpressionNode

class IfExpression(override val node: IfExpressionNode) : Expression {
    override fun applyTo(context: Context): Value = if (node.isConst) applyConstant(context) else applyNormal(context)

    private fun applyNormal(context: Context): UnitValue {
        if (context !is ExecutionContext) compileError("non-const if statement requires an execution context.", node)
        val condition = node.condition.parseAndApplyTo(context).asBooleanPrimitiveInstance()
        context.statements += SonoFunction.If.calledWith(
            condition.value.toIR(context),
            SimpleExecutionContext(context).also { ctx ->
                node.tbranch.evaluateIn(ctx)
            }.statements.asIR(),
            SimpleExecutionContext(context).also { ctx ->
                node.fbranch?.evaluateIn(ctx)
            }.statements.asIR()
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

fun Value.asBooleanPrimitiveInstance(): PrimitiveInstance {
    if (this !is PrimitiveInstance || this.type != BooleanType) {
        compileError("Value must be a Boolean. Instead got ${type.commonName}.")
    }
    return this
}

fun Value.asNumberPrimitiveInstance(): PrimitiveInstance {
    if (this !is PrimitiveInstance || this.type != NumberType) {
        compileError("Value must be a Number. Instead got ${type.commonName}.")
    }
    return this
}

fun ExpressionNode.parseAndEvaluateConstantBoolean(context: Context): Boolean {
    val parsed = parseAndApplyTo(SimpleContext(context)).asBooleanPrimitiveInstance()
    val result = parsed.value.tryConstexprEvaluate()
        ?: compileError("Boolean must be a compile time constant.", this)
    return when (result) {
        1.0 -> true
        0.0 -> false
        else -> compileError("Invalid value for boolean: $result")
    }
}

fun ExpressionNode.parseAndEvaluateConstantNumber(context: Context): Double {
    val parsed = parseAndApplyTo(SimpleContext(context)).asNumberPrimitiveInstance()
    return parsed.value.tryConstexprEvaluate()
        ?: compileError("Number must be a compile time constant.", this)
}
