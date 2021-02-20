package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.value.AnyType
import xyz.qwewqa.trebla.frontend.value.Type
import xyz.qwewqa.trebla.frontend.expression.Value

open class Scope(val parent: Scope?) {
    private val bindings = mutableMapOf<String, MutableMap<Signature, Binding>>()
    private val imported = mutableMapOf<String, MutableMap<Signature, Binding>>()

    private val bindingValues get() = bindings.values.flatMap { it.values }

    private operator fun MutableMap<String, MutableMap<Signature, Binding>>.get(key: NamespacedIdentifier): Binding? {
        return this[key.identifier]?.get(key.signature)
    }

    private operator fun MutableMap<String, MutableMap<Signature, Binding>>.set(
        key: NamespacedIdentifier,
        value: Binding,
    ) {
        this.getOrPut(key.identifier) { mutableMapOf() }[key.signature] = value
    }

    private operator fun MutableMap<String, MutableMap<Signature, Binding>>.contains(key: NamespacedIdentifier): Boolean {
        return key.identifier in this && key.signature in this[key.identifier]!!
    }

    /**
     * Adds the given value to this scope.
     */
    fun add(
        value: Value,
        identifier: String,
        signature: Signature = DefaultSignature,
        visibility: Visibility = Visibility.PUBLIC,
    ) = addLazy(lazyOf(value), identifier, signature, visibility)

    open fun addLazy(
        value: Lazy<Value>,
        identifier: String,
        signature: Signature = DefaultSignature,
        visibility: Visibility = Visibility.PUBLIC,
    ) {
        val namespacedIdentifier = NamespacedIdentifier(identifier, signature)
        if (namespacedIdentifier in bindings) {
            compileError("Symbol with identifier $identifier and signature $signature already exists.")
        }
        bindings[namespacedIdentifier] = Binding(
            identifier = identifier,
            signature = signature,
            visibility = visibility,
            lazyValue = value,
        )
    }

    /**
     * Returns the value with the given identifier and signature directly in this scope,
     * excluding parents or imports, if it exists and has the minimum visibility.
     * Otherwise returns null.
     */
    open fun get(
        identifier: String,
        signature: Signature = DefaultSignature,
        minVisibility: Visibility = Visibility.PRIVATE,
    ): Value? {
        return bindings[NamespacedIdentifier(identifier, signature)]?.let {
            if (it.visibility >= minVisibility) it.lazyValue.value
            else null
        }?.also {
            if (it == AmbiguousImportDummy) {
                compileError("Conflicting imported values with identifier $identifier and signature $signature.")
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
        signature: Signature = DefaultSignature,
        minVisibility: Visibility = Visibility.PRIVATE,
    ): Value? {
        return get(identifier, signature, minVisibility)
            ?: imported[NamespacedIdentifier(identifier, signature)]?.lazyValue?.value?.also {
                if (it == AmbiguousImportDummy) {
                    compileError("Conflicting imported values with identifier $identifier and signature $signature.")
                }
            }
            ?: parent?.find(identifier, signature, minVisibility)
    }

    /**
     * Returns a map of signatures to values with values nearest to this scope taking precedence.
     * A value may be an [AmbiguousImportDummy] which should be handled accordingly.
     */
    open fun findAll(
        identifier: String,
        minVisibility: Visibility,
    ): Map<Signature, Value> {
        val ownResults =
            (imported.getOrPut(identifier) { mutableMapOf() } + bindings.getOrPut(identifier) { mutableMapOf() })
                .mapValues { (_, binding) ->
                    binding.lazyValue.value
                }
        return if (parent != null) ownResults + parent.findAll(identifier, minVisibility) else ownResults
    }

    /**
     * Imports symbols from the [other] scope.
     * Imported symbols are shadowed by symbols declared locally.
     * Imported symbols in the other scope are not imported.
     */
    open fun import(other: Scope, minVisibility: Visibility = Visibility.PUBLIC) {
        other.bindingValues.forEach { binding ->
            if (binding.visibility >= minVisibility) {
                val namespacedIdentifier = binding.namespacedIdentifier
                imported[namespacedIdentifier] =
                    if (namespacedIdentifier in imported && imported[namespacedIdentifier] != binding) {
                        binding.copy(lazyValue = AmbiguousImportDummy)
                    } else {
                        binding
                    }
            }
        }
    }

    /**
     * Merges the internal and public members of the [other] scope into this scope.
     */
    open fun mergeIn(other: Scope) {
        other.bindingValues.forEach { binding ->
            if (binding.visibility >= Visibility.INTERNAL) {
                val namespacedIdentifier = binding.namespacedIdentifier
                if (namespacedIdentifier in bindings && bindings[namespacedIdentifier] != binding) {
                    compileError("Duplicate value with identifier ${binding.identifier} and signature ${binding.signature}.")
                }
                bindings[namespacedIdentifier] = binding
            }
        }
    }
}

data class NamespacedIdentifier(val identifier: String, val signature: Signature)

data class Binding(
    val identifier: String,
    val signature: Signature,
    val visibility: Visibility,
    val lazyValue: Lazy<Value>,
) {
    val namespacedIdentifier get() = NamespacedIdentifier(identifier, signature)
}

/**
 * When two imported values have the same identifier and signature, the binding will take this object as its value
 * rather than immediately erroring.
 * Instead, an error only happens if a binding with this value is accessed.
 */
object AmbiguousImportDummy : Value, Lazy<Value> {
    override val type = AnyType

    override val value = this
    override fun isInitialized() = true
}

interface Signature : Type

val DefaultSignature = object : Signature {
    override val commonName = "DefaultSignature"
    override val parentTypes = listOf<Type>()
}

class ReadOnlyScope(parent: Scope? = null) : Scope(parent) {
    override fun addLazy(value: Lazy<Value>, identifier: String, signature: Signature, visibility: Visibility) {
        compileError("Scope is read only.")
    }

    override fun import(other: Scope, minVisibility: Visibility) {
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
