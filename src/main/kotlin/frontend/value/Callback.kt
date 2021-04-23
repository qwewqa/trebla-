package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.IRNode
import xyz.qwewqa.trebla.backend.ir.IRValue
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.PrimitiveInstance
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.grammar.trebla.CallbackDeclarationNode
import kotlin.math.roundToInt

class CallbackDeclaration(
    override val node: CallbackDeclarationNode,
    override val parentContext: ScriptContext,
) : Declaration {
    override val identifier = node.identifier.value
    override val signature = DefaultSignature
    override val visibility = Visibility.PUBLIC
    override val type = AnyType

    val name = callbacksByName[identifier]
        ?: compileError("Unknown callback $identifier.", node.identifier)

    fun getCallback(): Callback {
        val callback = if (name == CallbackName.ShouldSpawn) {
            NonExecutionCallback(parentContext, node.order?.let {
                it.tryConstexprEval(parentContext) ?: compileError("Invalid callback order expression.")
            }?.roundToInt() ?: 0, name)
        } else {
            ExecutionCallback(parentContext, node.order?.let {
                it.tryConstexprEval(parentContext) ?: compileError("Invalid callback order expression.")
            }?.roundToInt() ?: 0, name)
        }
        node.body.value.let { body ->
            body.dropLast(1).forEach { it.parseAndApplyTo(callback.context) }
            body.lastOrNull()?.let { callback.returnValue = it.parseAndApplyTo(callback.context) }
        }
        return callback
    }
}

interface Callback {
    val context: Context
    val order: Int
    val name: CallbackName

    var returnValue: Value?

    fun toIR(): IRNode
}

class ExecutionCallback(
    val parentContext: ScriptContext,
    override val order: Int,
    override val name: CallbackName,
) : Callback {
    override val context: ExecutionContext = CallbackExecutionContext(parentContext, name)

    override var returnValue: Value? = null

    override fun toIR(): IRFunctionCall {
        val returnIRValue = (returnValue as? PrimitiveInstance)?.value?.toIR(context) ?: IRValue(0.0)
        return SonoFunction.Execute.calledWith(listOf(context.statements.asIR()) + listOf(returnIRValue))
    }
}

class NonExecutionCallback(
    val parentContext: ScriptContext,
    override val order: Int,
    override val name: CallbackName,
) : Callback {
    override val context: Context = CallbackContext(parentContext, name)

    override var returnValue: Value? = null

    override fun toIR(): IRNode {
        return (returnValue as? PrimitiveInstance)?.value?.toIR(context) ?: IRValue(0.0)
    }
}

private open class CallbackContext(parentContext: Context, override val callback: CallbackName) : Context(parentContext)

private class CallbackExecutionContext(parentContext: Context, override val callback: CallbackName) :
    ExecutionContext(parentContext) {
    override val localAllocator = TemporaryAllocator()
    override val statements = BlockStatement()
}

val callbacksByName = mapOf(
    "initialize" to CallbackName.Initialize,
    "shouldSpawn" to CallbackName.ShouldSpawn,
    "updateSequential" to CallbackName.UpdateSequential,
    "touch" to CallbackName.Touch,
    "updateParallel" to CallbackName.UpdateParallel,
    "preprocess" to CallbackName.Preprocess,
    "spawnOrder" to CallbackName.SpawnOrder,
)
