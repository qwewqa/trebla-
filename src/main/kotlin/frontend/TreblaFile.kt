package xyz.qwewqa.trebla.frontend

import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.value.ArchetypeDeclaration
import xyz.qwewqa.trebla.frontend.value.Declaration
import xyz.qwewqa.trebla.frontend.value.Package
import xyz.qwewqa.trebla.frontend.value.ScriptDeclaration
import xyz.qwewqa.trebla.grammar.trebla.TreblaFileNode

val defaultPackage = listOf("engine")

class TreblaFile(val node: TreblaFileNode, val globalContext: GlobalContext) {
    private val pkg = globalContext.getOrCreatePackage(node.packageHeader?.identifier?.value ?: defaultPackage)
    val context: Context = FileContext(globalContext, pkg)

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
                it.parse(context).also { declaration ->
                    when (declaration) {
                        is ScriptDeclaration -> scripts += declaration
                        is ArchetypeDeclaration -> archetypes += declaration
                    }
                }
            }
            .forEach { expression ->
                if (!expression.loadFirstPass) deferredDeclarations += expression
                else expression.applyTo(this.context)
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
        deferredDeclarations.forEach { it.applyTo(this.context) }
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
            pkg.context.scope.mergeIn(context.scope)
        } catch (e: CompileError) {
            throw CompileError("Package contains duplicate declarations with the same identifier", cause = e)
        }
    }

    private fun importWildcard(identifier: List<String>) {
        val pkg = globalContext.getPackage(identifier)
            ?: compileError("No package with name ${identifier.joinToString(".")} exists")
        context.scope.import(
            pkg.context.scope,
            if (this.pkg.includedBy(pkg)) Visibility.INTERNAL else Visibility.PUBLIC
        )
    }

    private fun importPackage(identifier: List<String>) {
        val pkg = globalContext.getPackage(identifier)
            ?: compileError("No package with name ${identifier.joinToString(".")} exists")
        context.scope.add(pkg, identifier.last())
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

private class FileContext(parentContext: GlobalContext, pkg: Package) : Context(parentContext) {
    override val scope = Scope(pkg.context.scope)
}
