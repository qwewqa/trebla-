package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.MemberAccessor
import xyz.qwewqa.sono.frontend.runWithErrorMessage
import xyz.qwewqa.sono.grammar.sono.MemberAccessNode
import xyz.qwewqa.sono.grammar.sono.UnaryFunctionNode

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