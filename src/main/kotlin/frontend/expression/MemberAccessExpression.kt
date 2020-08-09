package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.MemberAccessor
import xyz.qwewqa.trebla.frontend.context.Signature
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.MemberAccessNode
import xyz.qwewqa.trebla.grammar.trebla.UnaryFunctionNode

class MemberAccessExpression(override val node: UnaryFunctionNode) : Expression {
    val name = (node.op as? MemberAccessNode)?.identifier?.value ?: error("Not a member access.")

    override fun applyTo(context: Context): Value {
        val lhs = node.value.parseAndApplyTo(context)
        return runWithErrorMessage("Error in member access expression.") {
            lhs.resolveMember(name, context)
                ?: compileError("No member with name $name found.")
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
 * 4. The value in the the binding context of this value that receives the type of this value (bound)
 */
fun Value.resolveMember(name: String, context: Context?) =
    (this as? MemberAccessor)?.getMember(name, context)
        ?: context?.let { (this as? Type)?.resolveBinding(name, it) }
        ?: context?.let { type.resolveBinding(name, it)?.tryBind(this, context) }
        ?: this.bindingContext?.let { bindingContext ->
            context?.let { type.resolveBinding(name, bindingContext)?.tryBind(this, it) }
        }

fun Type.resolveBinding(name: String, context: Context): Value? =
    context.scope.find(name, Signature.Receiver(this))
        ?: this.bindingHierarchy.asSequence()
            .mapNotNull { layer ->
                layer
                    .map { type -> type.resolveBinding(name, context) }
                    .toSet()
                    .let {
                        when (it.size) {
                            0 -> null
                            1 -> it.first()
                            else -> compileError("Ambiguous bind.")
                        }
                    }
            }
            .firstOrNull()
        ?: context.scope.find(name, Signature.Receiver(AnyType))

private fun Value.tryBind(toValue: Value, context: Context) =
    (this as? Bindable)?.boundTo(toValue, context) ?: this