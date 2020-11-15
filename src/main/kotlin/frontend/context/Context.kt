package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.IRNode
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.expression.Statement
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.grammar.trebla.BlockNode
import xyz.qwewqa.trebla.grammar.trebla.StatementNode

/**
 * A context contains declarations, which are stored in its scope.
 */
interface Context : Value {
    val parentContext: Context?
    val source: Entity? get() = null

    val globalContext: GlobalContext

    val scope: Scope

    val contextMetadata: ContextMetadata

    override val type: Type get() = ContextType

    override fun getMember(name: String, accessingContext: Context?) = scope.find(name)

    val bindingContext: Context? get() = parentContext
}

const val CONTEXT_DEPTH_LIMIT = 100

class ContextMetadata(val parent: ContextMetadata?) {
    val depth: Int = parent?.depth?.plus(1) ?: 0

    init {
        if (depth >= CONTEXT_DEPTH_LIMIT) {
            compileError("Maximum depth reached.")
        }
    }
}

fun Context.getFullyQualified(name: List<String>) =
    globalContext.getPackage(name.dropLast(1))?.getMember(name.last(), null)
        ?: compileError("${name.joinToString(".")} does not exist")

fun Context.getFullyQualified(vararg name: String) = getFullyQualified(name.toList())

object ContextType : BuiltinType("Context")

/**
 * A context under which a block of expressions may be evaluated.
 */
interface ExecutionContext : Context {
    val localAllocator: TemporaryAllocator
    val statements: MutableList<Statement>
}

class SimpleContext(override val parentContext: Context) : Context {
    override val globalContext: GlobalContext = parentContext.globalContext
    override val scope = Scope(parentContext.scope)
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)
}

class SimpleExecutionContext(override val parentContext: ExecutionContext) : ExecutionContext, Statement {
    override val scope = EagerScope(parentContext.scope)
    override val globalContext: GlobalContext = parentContext.globalContext
    override val localAllocator = parentContext.localAllocator
    override val statements = mutableListOf<Statement>()
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)

    override fun toIR(): IRNode {
        return IRFunctionCall(SonoFunction.Execute, statements.map { it.toIR() })
    }
}

fun Context.createAndAddChild() = when (this) {
    is ExecutionContext -> SimpleExecutionContext(this).also { this.statements += it }
    else -> SimpleContext(this)
}

fun Context.createChild() = when (this) {
    is ExecutionContext -> SimpleExecutionContext(this)
    else -> SimpleContext(this)
}

class InnerExecutionContext(override val parentContext: ExecutionContext) : ExecutionContext {
    override val scope = EagerScope(parentContext.scope)
    override val globalContext: GlobalContext = parentContext.globalContext
    override val localAllocator = parentContext.localAllocator
    override val statements = parentContext.statements
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)
}

class ReadOnlyContext(override val parentContext: Context) : Context {
    override val scope = ReadOnlyScope(parentContext.scope)
    override val globalContext: GlobalContext = parentContext.globalContext
    override val contextMetadata = ContextMetadata(parentContext.contextMetadata)
}

fun List<StatementNode>.evaluateIn(context: Context): Value =
    this
        .asSequence()
        .map { it.parseAndApplyTo(context) }
        .lastOrNull() ?: UnitValue

fun List<StatementNode>.evaluateInChildOf(context: Context): Value = evaluateIn(context.createAndAddChild())

fun BlockNode.evaluateIn(context: Context) = value.evaluateIn(context)
fun BlockNode.evaluateInChildOf(context: Context): Value = evaluateIn(context.createAndAddChild())
