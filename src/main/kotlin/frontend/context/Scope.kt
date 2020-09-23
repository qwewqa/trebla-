package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.resolveMember

open class Scope(val parent: Scope?) {
    private val values = mutableMapOf<String, MutableMap<Signature, ValueMetadata>>()
    private val imported = mutableMapOf<String, MutableMap<Signature, ValueMetadata>>()

    /**
     * Adds the given value to this scope.
     */
    fun add(
        value: Value,
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
    ) = addLazy(lazyOf(value), identifier, signature, visibility)

    open fun addLazy(
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
        }?.also {
            if (it == AmbiguousImportMarker) {
                compileError("Multiple imported symbols with the identifier $identifier and same signature exist.")
            }
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
    open fun import(other: Scope, minVisibility: Visibility = Visibility.PUBLIC) {
        other.values.forEach { (identifier, bySignature) ->
            bySignature.forEach { (signature, valueMetadata) ->
                if (valueMetadata.visibility >= minVisibility) {
                    imported.getOrPut(identifier) { mutableMapOf() }.let { bySignature ->
                        if (bySignature[signature]?.let { it.lazyValue != valueMetadata.lazyValue } == true) {
                            bySignature[signature] = ValueMetadata(AmbiguousImportMarker, Visibility.PRIVATE)
                        } else {
                            bySignature[signature] = valueMetadata
                        }
                    }
                }
            }
        }
    }

    /**
     * Merges the internal and public members of the [other] scope into this scope.
     */
    open fun mergeIn(other: Scope) {
        other.values.forEach { (identifier, bySignature) ->
            bySignature.forEach { (signature, valueMetadata) ->
                values.getOrPut(identifier) { mutableMapOf() }.let { bySignature ->
                    if (bySignature[signature]?.lazyValue?.let { it != valueMetadata.lazyValue } == true) {
                        compileError("Duplicate declaration with identifier $identifier and signature $signature.")
                    }
                    if (valueMetadata.visibility >= Visibility.INTERNAL) bySignature[signature] = valueMetadata
                }
            }
        }
    }

    data class ValueMetadata(val lazyValue: Lazy<Value>, val visibility: Visibility)
}

private object AmbiguousImportMarker : Value, Lazy<Value> {
    override val type = AnyType
    val bindingContext: Nothing? = null

    override val value = this
    override fun isInitialized() = true
}

sealed class Signature {
    object Default : Signature() {
        override fun toString() = "Default"
    }

    object Archetype : Signature() {
        override fun toString() = "Archetype"
    }

    data class Receiver(val receiverType: Type) : Signature() {
        override fun toString() = "Receiver ($receiverType)"
    }
}

class ReadOnlyScope(parent: Scope? = null) : Scope(parent) {
    override fun addLazy(value: Lazy<Value>, identifier: String, signature: Signature, visibility: Visibility) {
        compileError("Scope is read only.")
    }

    override fun import(other: Scope, minVisibility: Visibility) {
        compileError("Scope is read only.")
    }

    override fun mergeIn(other: Scope) {
        compileError("Scope is read only.")
    }
}

/**
 * A scope where addLazy immediately tries to resolve the lazy value.
 */
open class EagerScope(parent: Scope?) : Scope(parent) {
    override fun addLazy(value: Lazy<Value>, identifier: String, signature: Signature, visibility: Visibility) {
        value.value
        super.addLazy(value, identifier, signature, visibility)
    }
}

enum class Visibility {
    /**
     * Accessible only in the declaring file.
     */
    PRIVATE,

    /**
     * Accessible only in the package and child packages.
     * Not importable.
     */
    INTERNAL,

    /**
     * Accessible anywhere and importable.
     */
    PUBLIC,
}

val visibilityModifiers = mapOf(
    "private" to Visibility.PRIVATE,
    "internal" to Visibility.INTERNAL,
    "public" to Visibility.PUBLIC
)
