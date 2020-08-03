package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.expression.Value

open class Scope(val parent: Scope?) {
    private val values = mutableMapOf<String, MutableMap<Signature, ValueMetadata>>()
    private val imported = mutableMapOf<String, MutableMap<Signature, ValueMetadata>>()

    /**
     * Adds the given value to this scope.
     */
    open fun add(
        value: Lazy<Value>,
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
    ) {
        val bySignature = values.getOrPut(identifier) { mutableMapOf() }
        if (bySignature[signature]?.lazyValue != null) {
            compileError("Symbol with identifier $identifier and signature $signature already exists.")
        }
        bySignature[signature] = ValueMetadata(value, visibility)
    }

    /**
     * Returns the value with the given identifier and signature directly in this scope,
     * excluding parents or imports, if it exists and has the minimum visibility.
     * Otherwise returns null.
     */
    open fun get(
        identifier: String,
        signature: Signature = Signature.Default,
        minVisibility: Visibility = Visibility.PRIVATE,
    ): Value? {
        return values[identifier]?.get(signature)?.let {
            if (it.visibility >= minVisibility) it.lazyValue.value
            else null
        }
    }

    /**
     * Searches for a value with the given identifier, signature, and minimum visibility
     * in this scope, then imports, then repeats this operator for parents until a value is found,
     * or returns null.
     */
    open fun find(
        identifier: String,
        signature: Signature = Signature.Default,
        minVisibility: Visibility = Visibility.PRIVATE,
    ): Value? {
        return get(identifier, signature, minVisibility)
            ?: imported.getOrPut(identifier) { mutableMapOf() }[signature]?.lazyValue?.value
            ?: parent?.find(identifier, signature, minVisibility)
    }

    /**
     * Imports symbols from the [other] scope.
     * Imported symbols are shadowed by symbols declared locally.
     * Imported symbols in the other scope are not imported.
     */
    open fun import(other: Scope) {
        other.values.forEach { (identifier, bySignature) ->
            bySignature.forEach { (signature, valueMetadata) ->
                imported.getOrPut(identifier) { mutableMapOf() }.let { bySignature ->
                    if (bySignature[signature]?.let { it.lazyValue != valueMetadata.lazyValue } == true) {
                        compileError("Duplicate import with identifier $identifier and signature $signature.")
                    } else {
                        bySignature[signature] = valueMetadata
                    }
                }
            }
        }
    }

    /**
     * Merges the internal and public members of the [other] scope into
     * this scope.
     */
    open fun mergeIn(other: Scope) {
        other.values.forEach { (identifier, bySignature) ->
            bySignature.forEach { (signature, valueMetadata) ->
                values.getOrPut(identifier) { mutableMapOf() }.let { bySignature ->
                    if (bySignature[signature]?.lazyValue?.let { it != valueMetadata.lazyValue } == true) {
                        compileError("Ambiguous merge with identifier $identifier and signature $signature.")
                    }
                    if (valueMetadata.visibility >= Visibility.INTERNAL) bySignature[signature] = valueMetadata
                }
            }
        }
    }

    /**
     * Checks the lazy values in this scope and finalizes them as it's
     * desirable to detect errors even if they exist in unused values.
     */
    fun finalize() {
        values.values.forEach { bySignature -> bySignature.forEach { (_, v) -> v.lazyValue.value.finalize() } }
    }

    data class ValueMetadata(val lazyValue: Lazy<Value>, val visibility: Visibility)
}

/*
Not that pretty. Might want to find a better way of doing this at some point.
 */
fun Scope.getFullyQualifiedOrNull(name: List<String>): Entity? {
    if (name.isEmpty()) error("Declaration name is empty")
    parent?.let { return parent.getFullyQualifiedOrNull(name) }
    val base = get(name[0]) ?: return null
    return name
        .drop(1)
        .fold(base as Entity) { a, v ->
            (a as? Context)?.scope?.get(v) ?: (a as? MemberAccessor)?.getMember(v, null) ?: return null
        }
}

fun Scope.getFullyQualified(name: List<String>) = getFullyQualifiedOrNull(name)
    ?: compileError("${name.joinToString(".")} does not exist")

fun Scope.getFullyQualified(vararg name: String) = getFullyQualified(name.toList())

sealed class Signature {
    object Default : Signature() {
        override fun toString() = "Default"
    }

    object Archetype : Signature() {
        override fun toString() = "Archetype"
    }

    data class TypedReceiver(val receiverType: Type) : Signature() {
        override fun toString() = "Receiver ($receiverType)"
    }
}

class ReadOnlyScope(parent: Scope? = null) : Scope(parent) {
    override fun add(value: Lazy<Value>, identifier: String, signature: Signature, visibility: Visibility) {
        compileError("Scope is read only.")
    }

    override fun import(other: Scope) {
        compileError("Scope is read only.")
    }

    override fun mergeIn(other: Scope) {
        compileError("Scope is read only.")
    }
}

/**
 * A scope where add immediately tries to resolve the lazy value.
 */
open class EagerScope(parent: Scope?) : Scope(parent) {
    override fun add(value: Lazy<Value>, identifier: String, signature: Signature, visibility: Visibility) {
        value.value
        super.add(value, identifier, signature, visibility)
    }
}

enum class Visibility {
    PRIVATE, INTERNAL, PUBLIC
}

val visibilityModifiers = mapOf(
    "private" to Visibility.PRIVATE,
    "internal" to Visibility.INTERNAL,
    "public" to Visibility.PUBLIC
)
