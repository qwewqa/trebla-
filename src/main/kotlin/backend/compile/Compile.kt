package xyz.qwewqa.trebla.backend.compile

import xyz.qwewqa.trebla.backend.BackendConfig
import xyz.qwewqa.trebla.backend.ir.*
import xyz.qwewqa.trebla.backend.ssa.*

fun compileEngine(
    scripts: List<IRScript>,
    archetypes: List<Archetype>,
    options: List<Map<String, Any>>,
    buckets: List<Map<String, Any>>,
    config: BackendConfig,
): EngineData {
    val callbackNodesByCallback = mutableMapOf<IRCallback, CompiledNode>()
    val callbackNodes = scripts.flatMap { sc ->
        sc.callbacks.map { cb ->
            compileEntryNode(cb.node, sc.freeIndexes, config).also { callbackNodesByCallback[cb] = it }
        }
    }
    val nodeData = combineCallbackNodes(callbackNodes)
    val compiledScripts = scripts.sortedBy { it.index }.also { s ->
        val indexes = s.map { it.index }
        if (indexes.isNotEmpty() && (indexes.toSet().size != indexes.size || indexes.first() != 0 || indexes.last() != indexes.size - 1))
            backendError("Script indexes not consecutive and starting at 0.")
    }.map { sc ->
        CompiledScript(
            sc.callbacks.map { cb ->
                CompiledCallback(cb.name.compiledName,
                    cb.order,
                    nodeData.indexes.getValue(callbackNodesByCallback.getValue(cb)))
            },
            sc.arguments
        )
    }
    archetypes.forEach { if (it.script !in scripts.indices) backendError("Archetype ${it.name} script index ${it.script} is out of range.") }
    return EngineData(compiledScripts, archetypes, nodeData.orderedNodes, options, buckets)
}

fun compileEntryNode(entryNode: IRNode, freeIndexes: List<Int>, config: BackendConfig) =
    entryNode
        .applySSAOptimizations()
        .allocate(freeIndexes)
        .pruneSimple()
        .toCompiledNode()

class CompileBackendError(message: String) : IllegalStateException(message)

fun backendError(message: String): Nothing = throw CompileBackendError(message)
