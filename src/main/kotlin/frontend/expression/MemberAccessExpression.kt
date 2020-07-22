package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.MemberAccessor
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.MemberAccessNode
import xyz.qwewqa.trebla.grammar.trebla.UnaryFunctionNode

class MemberAccessExpression(override val node: UnaryFunctionNode) : Expression {
    val name = (node.op as? MemberAccessNode)?.identifier?.value ?: error("Not a member access.")

    override fun applyTo(context: Context): Value {
        val lhs = node.value.parseAndApplyTo(context)
        if (lhs !is MemberAccessor) compileError("Member access not valid for this object.", node)
        return runWithErrorMessage("Error in member access expression.") {
            lhs.getMember(name, context)
        }
    }
}