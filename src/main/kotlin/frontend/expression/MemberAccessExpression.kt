package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.Dereferenceable
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.MemberAccessNode
import xyz.qwewqa.trebla.grammar.trebla.UnaryFunctionNode

class MemberAccessExpression(override val node: UnaryFunctionNode, op: MemberAccessNode) : Expression {
    val name = op.identifier.value

    override fun applyTo(context: Context): Value {
        val lhs = node.value.parseAndApplyTo(context)
        return runWithErrorMessage("Error in member access expression.") {
            lhs.resolveMember(name, context)
                ?: if (lhs is Dereferenceable) {
                    compileError("No member with name $name found. Dereference may be missing.")
                } else {
                    compileError("No member with name $name found.")
                }
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
fun Value.resolveMember(name: String, context: Context?) =
    (this as? MemberAccessor)?.getMember(name, context)
        ?: context?.let { (this as? Type)?.resolveBinding(name, context.scope, Visibility.PRIVATE) }
        ?: context?.let { type.resolveBinding(name, context.scope, Visibility.PRIVATE)?.tryBind(this, context) }
        ?: type.bindingScope?.let { typeScope ->
            context?.let { type.resolveBinding(name, typeScope, Visibility.PUBLIC)?.tryBind(this, context) }
        }

fun Value.resolveNonTypeMember(name: String, context: Context?) =
    (this as? MemberAccessor)?.getMember(name, context)
        ?: context?.let { type.resolveBinding(name, it.scope, Visibility.PRIVATE)?.tryBind(this, context) }
        ?: type.bindingScope?.let { scope ->
            context?.let { type.resolveBinding(name, scope, Visibility.PUBLIC)?.tryBind(this, it) }
        }

fun Type.resolveBinding(name: String, scope: Scope, minVisibility: Visibility): Value? =
    scope.find(name, Signature.Receiver(this), minVisibility)
        ?: this.bindingHierarchy.asSequence()
            .mapNotNull { layer ->
                layer
                    .map { type -> type.resolveBinding(name, scope, minVisibility) }
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
        ?: scope.find(name, Signature.Receiver(AnyType), minVisibility)

private fun Value.tryBind(toValue: Value, context: Context) =
    (this as? Bindable)?.boundTo(toValue, context) ?: this
