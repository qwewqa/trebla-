package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.IRNode
import xyz.qwewqa.trebla.backend.ir.IRValue
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.CallbackDeclarationNode
import kotlin.math.roundToInt

class CallbackDeclaration(
    override val node: CallbackDeclarationNode,
    override val parentContext: ScriptDeclaration,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC
    override val type = AnyType

    val name = callbacksByName[identifier]
        ?: if (identifier == "initialize") compileError(
            "Initialize callback is not supported use init blocks instead.",
            node.identifier
        )
        else compileError("Unknown callback $identifier.", node.identifier)

    fun getCallback(): Callback {
        val callback = if (name == CallbackName.ShouldSpawn) {
            NonExecutionCallback(parentContext, node.order?.let {
                it.tryConstexprEval(parentContext) ?: compileError("Invalid callback order expression.")
            }?.roundToInt() ?: 0, name, parentContext)
        } else {
            ExecutionCallback(parentContext, node.order?.let {
                it.tryConstexprEval(parentContext) ?: compileError("Invalid callback order expression.")
            }?.roundToInt() ?: 0, name, parentContext)
        }
        node.body.value.let { body ->
            body.dropLast(1).forEach { it.parseAndApplyTo(callback) }
            body.lastOrNull()?.let { callback.returnValue = it.parseAndApplyTo(callback) }
        }
        return callback
    }
}

interface Callback : Context {
    val order: Int
    val name: CallbackName

    var returnValue: Value?

    fun toIR(): IRNode
}

class ExecutionCallback(
    override val parentContext: ScriptContext,
    override val order: Int,
    override val name: CallbackName,
    script: ScriptDeclaration,
) : Callback, ExecutionContext {
    override val scope = EagerScope(script.scope)
    override val globalContext: GlobalContext = parentContext.globalContext
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata, name)
    override val localAllocator = TemporaryAllocator()
    override val statements = BlockStatement()

    override var returnValue: Value? = null

    override fun toIR(): IRFunctionCall {
        val returnIRValue = (returnValue as? RawStructValue)?.raw?.toIR(this) ?: IRValue(0.0)
        return SonoFunction.Execute.calledWith(listOf(statements.asIR()) + listOf(returnIRValue))
    }
}

class NonExecutionCallback(
    override val parentContext: ScriptContext,
    override val order: Int,
    override val name: CallbackName,
    script: ScriptDeclaration,
) : Callback {
    override val scope = EagerScope(script.scope)
    override val globalContext: GlobalContext = parentContext.globalContext
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata, name)

    override var returnValue: Value? = null

    override fun toIR(): IRNode {
        return (returnValue as? RawStructValue)?.raw?.toIR(this) ?: IRValue(0.0)
    }
}

val callbacksByName = mapOf(
    "shouldSpawn" to CallbackName.ShouldSpawn,
    "updateSequential" to CallbackName.UpdateSequential,
    "touch" to CallbackName.Touch,
    "updateParallel" to CallbackName.UpdateParallel,
    "preprocess" to CallbackName.Preprocess,
    "spawnOrder" to CallbackName.SpawnOrder,
)
