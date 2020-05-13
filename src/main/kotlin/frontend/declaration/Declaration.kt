package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.expression.Expression
import xyz.qwewqa.sono.frontend.expression.Value
import xyz.qwewqa.sono.grammar.sono.ModifierListNode
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Declarations alter the environment and will add things to the scope.
 * A declaration does not necessarily add itself to the scope, though this is the default.
 * If referenced back by a value as its [sourceExpression], it can provide some additional metadata for error reporting and such.
 */
interface Declaration : Value, Expression {
    /**
     * The identifier or name of this declaration.
     */
    val identifier: String

    /**
     * The signature of this declaration.
     * Declarations with the same identifier can exist in the same scope if they have different signatures.
     */
    val signature: Signature

    /**
     * The context containing this declaration.
     */
    val declaringContext: Context?

    /**
     * The visibility modifier of this declaration.
     */
    val visibility: Visibility

    /**
     * Things like structs should be loaded earlier since they don't depend on other declarations,
     * but other declarations depend on them.
     */
    val loadEarly: Boolean get() = false

    /*
    By default, a declaration adds itself, but not all declarations may do so, like property declarations.
     */
    override fun applyTo(context: Context): Value {
        context.scope.add(this)
        return this
    }
}

sealed class Signature {
    object Default : Signature()
    object Archetype : Signature()
    data class TypedReceiver(val receiverType: Type) : Signature()
}

@OptIn(ExperimentalContracts::class)
inline fun ModifierListNode.parse(block: ModifierListParseContext.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    ModifierListParseContext(this).apply {
        block()
        finalize()
    }
}

class ModifierListParseContext(private val node: ModifierListNode) {
    private val remaining = node.value.toMutableSet().also {
        if (it.size != node.value.size) compileError(
            "Duplicate modifiers.",
            node
        )
    }

    /**
     * Finds and removes the first key in [modifiers] from the set, and returns the corresponding value
     * that the key corresponds to. Returns null if no match found. Throws a compile error if multiple matches are found.
     */
    fun <T> selectFromMap(modifiers: Map<String, T>): T? {
        var matchKey: String? = null
        var matchValue: T? = null
        modifiers.forEach { (k, v) ->
            if (k in remaining) {
                if (matchValue != null) compileError(
                    "Multiple modifiers in set $remaining found.",
                    node
                )
                matchValue = v
                matchKey = k
            }
        }
        if (matchKey != null) remaining.remove(matchKey!!)
        return matchValue
    }

    /**
     * If a modifier with the given [name] exists, removes it and returns true, otherwise returns false.
     */
    fun selectSingle(name: String) = remaining.remove(name)

    /**
     * Error if there are remaining, unused modifiers.
     */
    fun finalize() {
        if (remaining.isNotEmpty()) compileError(
            "Extraneous modifier(s) $remaining",
            node
        )
    }
}