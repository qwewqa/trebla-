package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.ArchetypeDeclaration
import xyz.qwewqa.trebla.frontend.declaration.Declaration
import xyz.qwewqa.trebla.frontend.declaration.ScriptDeclaration
import xyz.qwewqa.trebla.grammar.trebla.TreblaFileNode

val defaultPackage = listOf("engine")

class TreblaFile(override val node: TreblaFileNode, override val parentContext: GlobalContext) : Context,
    GlobalAllocatorContext {
    override val globalContext: GlobalContext = parentContext.globalContext
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)
    private val pkg = parentContext.getOrCreatePackage(node.packageHeader?.identifier?.value ?: defaultPackage)
    override val levelAllocator = parentContext.levelAllocator
    override val leveldataAllocator = parentContext.leveldataAllocator
    override val tempAllocator = parentContext.tempAllocator

    /*
    A file has its own set of imports and private declarations that are local to the file.
    Non-private declarations are added to the package scope in addition to the local file scope.
     */
    override val scope = Scope(pkg.scope)

    val scripts = mutableListOf<ScriptDeclaration>()
    val archetypes = mutableListOf<ArchetypeDeclaration>()

    private val deferredDeclarations = mutableListOf<Declaration>()

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
                it.parse(this).also { declr ->
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
        loadImports()
        deferredDeclarations.forEach { it.applyTo(this) }
        updatePackage()
    }

    /**
     * The final part of the file loading process.
     * Imports are reloaded and the scripts and callbacks in this file are ready to be processed.
     */
    fun loadFinal() {
        importStd()
        loadImports()
        updatePackage()
    }

    private fun updatePackage() {
        try {
            pkg.scope.mergeIn(this.scope)
        } catch (e: CompileError) {
            throw CompileError("Package contains duplicate declarations with the same identifier", cause = e)
        }
    }

    private fun importWildcard(identifier: List<String>) {
        val pkg = parentContext.getPackage(identifier)
            ?: compileError("No package with name ${identifier.joinToString(".")} exists")
        scope.import(
            pkg.scope,
            if (this.pkg.isInternalTo(pkg)) Visibility.INTERNAL else Visibility.PUBLIC
        )
    }

    private fun importPackage(identifier: List<String>) {
        val pkg = parentContext.getPackage(identifier)
            ?: compileError("No package with name ${identifier.joinToString(".")} exists")
        scope.add(pkg, identifier.last())
    }

    private fun importStd() {
        importWildcard(listOf("std"))
    }

    private fun loadImports() {
        node.imports.imports.forEach { importStatement ->
            importStatement.runWithErrorMessage("Failed to import ${importStatement.identifier.value.joinToString(".")}.") {
                if (importStatement.isWildcard) {
                    importWildcard(importStatement.identifier.value)
                } else {
                    importPackage(importStatement.identifier.value)
                }
            }
        }
    }
}
