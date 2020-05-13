package xyz.qwewqa.sono.frontend.context

import xyz.qwewqa.sono.frontend.CompileError
import xyz.qwewqa.sono.frontend.Entity
import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.declaration.AnyType
import xyz.qwewqa.sono.frontend.declaration.Declaration
import xyz.qwewqa.sono.frontend.declaration.Signature
import xyz.qwewqa.sono.frontend.expression.Value

open class Scope(
    private val parent: Scope?,
) {
    private val symbols = SymbolTable()

    open fun add(declaration: Declaration) = symbols.add(declaration)
    open fun add(
        value: Value,
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
        native: Boolean = true,
    ) = symbols.add(value, identifier, signature, visibility, native)

    open fun addDeferred(
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
        native: Boolean = true,
        value: () -> Value,
    ) = symbols.addDeferred(identifier, signature, visibility, native, value)

    open fun get(name: String, signature: Signature = Signature.Default) = symbols.get(name, signature)
    open fun getNative(name: String, signature: Signature = Signature.Default) = symbols.getNative(name, signature)
    open fun find(name: String, signature: Signature = Signature.Default): Value? =
        get(name, signature) ?: parent?.find(name, signature)

    fun import(
        other: Scope,
        minVisibility: Visibility = Visibility.PUBLIC,
        filterName: String? = null,
        native: Boolean = false,
    ) = symbols.import(other.symbols, minVisibility, filterName, native)

    open fun getFullyQualified(name: List<String>) = getFullyQualifiedOrNull(name)
        ?: compileError("${name.joinToString(".")} does not exist")

    open fun getFullyQualifiedOrNull(name: List<String>): Entity? {
        if (name.isEmpty()) error("Declaration name is empty")
        parent?.let { return parent.getFullyQualifiedOrNull(name) }
        val base = get(name[0]) ?: return null
        return name
            .drop(1)
            .fold(base as Entity) { a, v ->
                (a as? Context)?.scope?.get(v) ?: (a as? MemberAccessor)?.getMember(v, null) ?: return null
            }
    }
}

class ReadOnlyScope(parent: Scope? = null) : Scope(parent) {
    override fun add(declaration: Declaration) {
        compileError("Scope is read only.")
    }

    override fun add(value: Value, identifier: String, signature: Signature, visibility: Visibility, native: Boolean) {
        compileError("Scope is read only.")
    }
}

fun Scope.getFullyQualified(vararg name: String) = getFullyQualified(name.toList())

/**
 * A table for organizing and finding symbols and corresponding signatures and declarations
 */
class SymbolTable {
    /**
     * A map of symbols to a map of signatures to declarations.
     * Always a superset of [nativeSymbols].
     */
    private val symbols = mutableMapOf<String, MutableMap<Signature, Symbol>>()

    /**
     * Same as [symbols] but only contains symbols that were specified as native
     * when added through [add] (default: true).
     * Currently should just exclude imported symbols.
     */
    private val nativeSymbols = mutableMapOf<String, MutableMap<Signature, Symbol>>()

    fun get(name: String, signature: Signature = Signature.Default) = symbols[name]?.get(signature)?.value?.let {
        if (it is DeferredWrapper) it.get()
        else it
    }

    fun getNative(name: String, signature: Signature = Signature.Default) = nativeSymbols[name]?.get(signature)?.value

    /**
     * Add a declaration or throws an [IllegalStateException] if
     * a declaration with an identical identifier and signature exists already in the context.
     * The symbol is native by default and included in the output of [getNative] unless [native] is specified as false.
     */
    fun add(declaration: Declaration, native: Boolean = true) {
        add(declaration, declaration.identifier, declaration.signature, declaration.visibility, native)
    }

    fun add(
        value: Value,
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
        native: Boolean = true,
    ) {
        symbols.getOrPut(identifier) { mutableMapOf() }.let {
            if (signature in it && it[signature]!!.value != value) throw CompileError(
                "Symbol of identifier $identifier with the same signature already exists within this scope",
                value.node,
                CompileError("Already declared", it[signature]!!.value.node)
            )
            it[signature] = Symbol(visibility, value)
        }
        if (native) nativeSymbols.getOrPut(identifier) { mutableMapOf() }[signature] =
            Symbol(visibility, value)
    }

    fun addDeferred(
        identifier: String,
        signature: Signature = Signature.Default,
        visibility: Visibility = Visibility.PUBLIC,
        native: Boolean = true,
        value: () -> Value,
    ) = DeferredWrapper(value).let { add(it, identifier, signature, visibility, native) }

    /**
     * Imports symbols which are native in another symbol table with visibility at least [minVisibility], which is
     * public only by default.
     * All symbols are considered if [filterName] is null, otherwise only imports those with that identifier.
     * Returns true if the import succeeded in importing at least one value (even if the value already exists in this table)
     */
    fun import(
        other: SymbolTable,
        minVisibility: Visibility = Visibility.PUBLIC,
        filterName: String? = null,
        native: Boolean = false,
    ): Boolean {
        var found = false
        other.nativeSymbols.forEach { (identifier, entry) ->
            entry.forEach { (signature, symbol) ->
                if (symbol.visibility >= minVisibility && (filterName == null || identifier == filterName)) {
                    found = true
                    add(
                        symbol.value,
                        identifier,
                        signature,
                        symbol.visibility,
                        native
                    )
                }
            }
        }
        return found
    }

    private class DeferredWrapper(private val expr: () -> Value) : Value {
        override val type = AnyType
        private lateinit var value: Value

        fun get(): Value {
            if (!::value.isInitialized) {
                value = expr()
            }
            return value
        }
    }
}

data class Symbol(val visibility: Visibility, val value: Value)

enum class Visibility {
    PRIVATE, INTERNAL, PUBLIC
}

val visibilityModifiers = mapOf(
    "private" to Visibility.PRIVATE,
    "internal" to Visibility.INTERNAL,
    "public" to Visibility.PUBLIC
)
