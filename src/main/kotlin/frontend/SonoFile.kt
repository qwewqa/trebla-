package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.grammar.trebla.*

val defaultPackage = listOf("engine")

class TreblaFile(override val node: TreblaFileNode, val globalContext: GlobalContext) : Context, Entity,
    GlobalAllocatorContext, DeferrableContext {
    private val pkg = globalContext.getPackage(node.packageHeader?.identifier?.value ?: defaultPackage)
    override val levelAllocator = globalContext.levelAllocator
    override val tempAllocator = globalContext.tempAllocator

    /*
    A file has its own set of imports and private declarations that are local to the file.
    Non-private declarations are added to the package scope in addition to the local file scope.
     */
    override val scope = Scope(this, pkg.scope)

    val scripts = mutableListOf<ScriptDeclaration>()
    val archetypes = mutableListOf<ArchetypeDeclaration>()

    private val deferredDeclarations = mutableListOf<Declaration>()

    private val deferredChecks = mutableListOf<() -> Unit>()

    override fun addDeferredCheck(block: () -> Unit) {
        deferredChecks += block
    }

    /**
     * The first part of the file loading process.
     * Processes each top level statement but only applies it if it is declared early.
     * Actions here are self contained, and any dependence on anything external to the file
     * in any statement is lazy.
     */
    fun loadInitial() {
        importStd()
        node.topLevelObjects
            .asSequence()
            .map {
                when (it) {
                    is ArchetypeDeclarationNode -> ArchetypeDeclaration(it, this)
                    is ScriptDeclarationNode -> ScriptDeclaration(it, this)
                    is FunctionDeclarationNode -> FunctionDeclaration(it, this)
                    is StructDeclarationNode -> StructDeclaration(it, this)
                    is PropertyDeclarationNode -> PropertyDeclaration(it, this)
                    is LetDeclarationNode -> LetDeclaration(it, this)
                    else -> error("Unknown top level object.") // should not happen
                }.also { declr ->
                    when (declr) {
                        is ScriptDeclaration -> scripts += declr
                        is ArchetypeDeclaration -> archetypes += declr
                    }
                }
            }
            .forEach { expr ->
                if (!expr.loadEarly) deferredDeclarations += expr
                else expr.applyTo(this)
            }
        updatePackage()
    }

    /**
     * The middle part of the file loading process.
     * Import statements are processed, but non-wildcard imports that do not exist are allowed at this point,
     * because those might refer to deferred statements.
     * Deferred statements are applied here, which might depend on non-deferred statements.
     */
    fun loadNormal() {
        importStd()
        importLenient()
        deferredDeclarations.forEach { it.applyTo(this) }
        updatePackage()
    }

    /**
     * The final part of the file loading process.
     * Imports are reloaded and the scripts and callbacks in this file are ready to be processed.
     */
    fun loadFinal() {
        importStd()
        importStrict()
        updatePackage()
        deferredChecks.forEach { it() }
    }

    private fun updatePackage() {
        pkg.scope.import(this.scope, minVisibility = Visibility.INTERNAL, native = true)
    }

    private fun importWildcard(identifier: List<String>) = scope.import(globalContext.getPackage(identifier).scope)

    private fun importSingle(identifier: List<String>): Boolean {
        val pkg = identifier.dropLast(1)
        val name = identifier.last()
        return if (pkg.isEmpty()) {
            scope.import(globalContext.scope, filterName = name)
        } else {
            scope.import(globalContext.getPackage(pkg).scope, filterName = name)
        }
    }

    private fun importStd() {
        importWildcard(listOf("std"))
    }

    /**
     * At the stage this is used, all symbols should be loaded,
     * so non-wildcard imports that don't do anything should error.
     * Wildcard imports are free to do nothing as a package is free to have no public members.
     */
    private fun importStrict() {
        node.imports.imports.forEach { importStatement ->
            importStatement.runWithErrorMessage("Failed to import ${importStatement.identifier.value.joinToString(".")}.") {
                val identifier = importStatement.identifier.value
                if (importStatement.isWildcard) importWildcard(identifier)
                else importSingle(identifier).also { success -> if (!success) compileError("Symbol does not exist or is not public.") }
            }
        }
    }

    /**
     * Allow non-wildcard import statements that fail to do anything initially,
     * because not every symbol may be loaded yet.
     */
    private fun importLenient() {
        node.imports.imports.forEach { importStatement ->
            importStatement.runWithErrorMessage("Failed to import ${importStatement.identifier.value.joinToString(".")}.") {
                val identifier = importStatement.identifier.value
                if (importStatement.isWildcard) importWildcard(identifier) else importSingle(identifier)
            }
        }
    }
}