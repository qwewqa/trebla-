package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.Scope
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.value.AnyType
import xyz.qwewqa.trebla.frontend.value.ReceiverType
import xyz.qwewqa.trebla.frontend.value.Type
import xyz.qwewqa.trebla.frontend.value.matchBest
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.MemberAccessNode
import xyz.qwewqa.trebla.grammar.trebla.UnaryFunctionNode

class MemberAccessExpression(override val node: UnaryFunctionNode, op: MemberAccessNode) : Expression {
    val name = op.identifier.value

    override fun applyTo(context: Context): Value {
        val lhs = node.value.parseAndApplyTo(context)
        return node.runWithErrorMessage("Error in member access expression.") {
            lhs.resolveMember(name, context)
                ?: compileError("No member with name $name found for a value with type ${lhs.type.commonName}.")
        }
    }
}

/**
 * Resolves a member of this value in with the given name in the given context.
 *
 * Order is as follows:
 * 1. Direct members
 * 2. (Only if this value is a type) a value in accessing context that receives this value as a type (unbound)
 * 3. The value in accessing context that receives the type of this value (bound)
 * 4. The value in the the scope of the type of this value that receives the type of this value (bound; public only)
 */
fun Value.resolveMember(name: String, context: Context?): Value? =
    getMember(name, context)
        ?: context?.let { (this as? Type)?.resolveBinding(name, context.scope, Visibility.PRIVATE) }
        ?: context?.let { type.resolveBinding(name, context.scope, Visibility.PRIVATE)?.tryBind(this, context) }
        ?: type.bindingScope?.let { typeScope ->
            context?.let { type.resolveBinding(name, typeScope, Visibility.PUBLIC)?.tryBind(this, context) }
        }
        ?: embedded.mapNotNull { it.resolveMember(name, context) }.toSet().let {
            if (it.size > 1) compileError("Ambiguous member due to embeds.") else it.singleOrNull()
        }

fun Value.resolveDirectBindingMember(name: String, context: Context?) =
    context?.let { type.resolveBinding(name, it.scope, Visibility.PRIVATE)?.tryBind(this, context) }
        ?: type.bindingScope?.let { scope ->
            context?.let { type.resolveBinding(name, scope, Visibility.PUBLIC)?.tryBind(this, it) }
        }

fun Type.resolveBinding(name: String, scope: Scope, minVisibility: Visibility): Value? {
    val candidates = scope.findAll(name, minVisibility)
    return candidates[ReceiverType(this).matchBest(candidates.keys).getOrElse { return null }]!!
}

private fun Value.tryBind(toValue: Value, context: Context) =
    (this as? Bindable)?.boundTo(toValue, context) ?: this
