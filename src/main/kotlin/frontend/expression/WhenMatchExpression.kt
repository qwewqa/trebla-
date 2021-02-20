package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.backend.ir.toIR
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.EnumStructVariant
import xyz.qwewqa.trebla.frontend.declaration.EnumInstance
import xyz.qwewqa.trebla.frontend.declaration.EnumVariant
import xyz.qwewqa.trebla.grammar.trebla.WhenElseEntryNode
import xyz.qwewqa.trebla.grammar.trebla.WhenMatchExpressionNode
import xyz.qwewqa.trebla.grammar.trebla.WhenMatchVariantNode

class WhenMatchExpression(override val node: WhenMatchExpressionNode) : Expression {
    override fun applyTo(context: Context): Value {
        if (context !is ExecutionContext) compileError("When match statement requires an execution context.", node)
        val matchValue = node.expression.parseAndApplyTo(context)
        if (matchValue !is EnumInstance) {
            compileError("When match expressions require an enum value.")
        }
        val enumType = matchValue.type
        val entries = node.entries.filterIsInstance<WhenMatchVariantNode>()
        val defaultIRNode = node.entries.filterIsInstance<WhenElseEntryNode>().also {
            if (it.size > 1) compileError("when expression should only have one default.")
        }.firstOrNull()?.let {
            val bodyContext = SimpleExecutionContext(context)
            it.body.evaluateIn(bodyContext)
            bodyContext.statements.asIR()
        }
        val entryIRNodes = entries.flatMap { entryNode ->
            // this should not need an execution context
            val variant = entryNode.variant.parseAndApplyTo(SimpleContext(context))
            if (variant !in enumType.variants || variant !is EnumVariant) {
                compileError("Expected a variant of ${enumType.commonName} " +
                        "but instead got a ${variant.type.commonName}.", entryNode)
            }
            val bodyContext = SimpleExecutionContext(context)
            entryNode.destructure?.value?.let { destructureIdentifiers ->
                if (variant !is EnumStructVariant) {
                    compileError("Destructure requires a struct enum variant.")
                }
                (destructureIdentifiers zip variant.destructure(matchValue)).forEach { (identifier, value) ->
                    if (identifier != null) bodyContext.scope.add(value, identifier)
                }
            }
            entryNode.body.evaluateIn(bodyContext)
            listOf(variant.ordinal.toIR(), bodyContext.statements.asIR())
        }
        context.statements +=
            if (defaultIRNode != null) {
                IRFunctionCall(
                    SonoFunction.SwitchWithDefault,
                    listOf(matchValue.ordinal.toIR(context)) + entryIRNodes + defaultIRNode
                )
            } else {
                IRFunctionCall(
                    SonoFunction.Switch,
                    listOf(matchValue.ordinal.toIR(context)) + entryIRNodes
                )
            }

        return UnitValue
    }
}
