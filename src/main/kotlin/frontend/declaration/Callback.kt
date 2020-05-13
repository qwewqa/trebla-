package xyz.qwewqa.sono.frontend.declaration

import xyz.qwewqa.sono.backend.compile.CallbackName
import xyz.qwewqa.sono.backend.compile.FunctionIRNode
import xyz.qwewqa.sono.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.sono.backend.compile.ValueIRNode
import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.ExecutionContext
import xyz.qwewqa.sono.frontend.context.Scope
import xyz.qwewqa.sono.frontend.context.TemporaryAllocator
import xyz.qwewqa.sono.frontend.context.Visibility
import xyz.qwewqa.sono.frontend.expression.Statement
import xyz.qwewqa.sono.frontend.expression.Value
import xyz.qwewqa.sono.frontend.expression.parseAndApplyTo
import xyz.qwewqa.sono.frontend.expression.tryConstexprEval
import xyz.qwewqa.sono.grammar.sono.CallbackDeclarationNode
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
    override val scope = Scope(script.scope)
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