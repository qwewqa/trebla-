package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.backend.compile.FunctionIRNode
import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.backend.compile.ValueIRNode
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.Scope
import xyz.qwewqa.trebla.frontend.context.TemporaryAllocator
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.expression.Statement
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.frontend.expression.tryConstexprEval
import xyz.qwewqa.trebla.grammar.trebla.CallbackDeclarationNode
import kotlin.math.roundToInt

class CallbackDeclaration(
    override val node: CallbackDeclarationNode,
    override val declaringContext: ScriptDeclaration,
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
        val callback = Callback(node.order?.let {
            it.tryConstexprEval(declaringContext) ?: compileError("Invalid callback order expression.")
        }?.roundToInt() ?: 0, name, declaringContext)
        node.body.value.let { body ->
            body.dropLast(1).forEach { it.parseAndApplyTo(callback) }
            body.lastOrNull()?.let { callback.returnValue = it.parseAndApplyTo(callback) }
        }
        return callback
    }
}

class Callback(val order: Int, val name: CallbackName, script: ScriptDeclaration) : ExecutionContext {
    override val scope = Scope(this, script.scope)
    override val localAllocator = TemporaryAllocator()
    override val statements = mutableListOf<Statement>()

    var returnValue: Value? = null

    fun toIR(): FunctionIRNode {
        val returnIRValue = (returnValue as? RawStructValue)?.value?.toIR() ?: ValueIRNode(0.0)
        return FunctionIRNodeVariant.Execute.calledWith(statements.map { it.toIR() } + listOf(returnIRValue))
    }
}

val callbacksByName = mapOf(
    "shouldSpawn" to CallbackName.ShouldSpawn,
    "updateSequential" to CallbackName.UpdateSequential,
    "touch" to CallbackName.Touch,
    "updateParallel" to CallbackName.UpdateParallel
)