package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.compile.IRFunction
import xyz.qwewqa.trebla.backend.compile.IRFunctionVariant
import xyz.qwewqa.trebla.backend.compile.IRValue
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.Statement
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.frontend.expression.tryConstexprEval
import xyz.qwewqa.trebla.grammar.trebla.CallbackDeclarationNode
import kotlin.math.roundToInt

class CallbackDeclaration(
    override val node: CallbackDeclarationNode,
    override val parentContext: ScriptDeclaration,
) :
    Declaration {
    override val identifier = node.identifier.value
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC
    override val type = AnyType

    val name = callbacksByName[identifier]
        ?: if (identifier == "initialize") compileError(
            "Initialize callback is not supported use init blocks instead.",
            node.identifier
        )
        else compileError("Unknown callback $identifier.", node.identifier)

    fun getCallback(): Callback {
        val callback = Callback(parentContext, node.order?.let {
            it.tryConstexprEval(parentContext) ?: compileError("Invalid callback order expression.")
        }?.roundToInt() ?: 0, name, parentContext)
        node.body.value.let { body ->
            body.dropLast(1).forEach { it.parseAndApplyTo(callback) }
            body.lastOrNull()?.let { callback.returnValue = it.parseAndApplyTo(callback) }
        }
        return callback
    }
}

class Callback(
    override val parentContext: ScriptContext,
    val order: Int,
    val name: CallbackName,
    script: ScriptDeclaration,
) : ExecutionContext {
    override val scope = EagerScope(script.scope)
    override val configuration = parentContext.configuration
    override val localAllocator = TemporaryAllocator()
    override val statements = mutableListOf<Statement>()

    var returnValue: Value? = null

    fun toIR(): IRFunction {
        val returnIRValue = (returnValue as? RawStructValue)?.raw?.toIR() ?: IRValue(0.0)
        return IRFunctionVariant.Execute.calledWith(statements.map { it.toIR() } + listOf(returnIRValue))
    }
}

val callbacksByName = mapOf(
    "shouldSpawn" to CallbackName.ShouldSpawn,
    "updateSequential" to CallbackName.UpdateSequential,
    "touch" to CallbackName.Touch,
    "updateParallel" to CallbackName.UpdateParallel
)