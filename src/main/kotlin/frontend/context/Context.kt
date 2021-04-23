package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.backend.compile.CallbackName
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.expression.BlockStatement
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.expression.parseAndApplyTo
import xyz.qwewqa.trebla.frontend.value.BuiltinType
import xyz.qwewqa.trebla.frontend.value.Type
import xyz.qwewqa.trebla.grammar.trebla.BlockNode
import xyz.qwewqa.trebla.grammar.trebla.StatementNode

/**
 * A context contains declarations, which are stored in its scope.
 */
abstract class Context(
    val parentContext: Context?,
) : Value {
    open val scope: Scope = Scope(parentContext?.scope)
    open val callback: CallbackName? = parentContext?.callback
    open val globalContext: GlobalContext by lazy { parentContext?.globalContext ?: error("Expected global context.") }

    open val levelAllocator: StandardAllocator? = parentContext?.levelAllocator
    open val leveldataAllocator: StandardAllocator? = parentContext?.leveldataAllocator
    open val tempAllocator: StandardAllocator? = parentContext?.tempAllocator
    open val memoryAllocator: Allocator? = parentContext?.memoryAllocator
    open val dataAllocator: Allocator? = parentContext?.dataAllocator
    open val sharedAllocator: Allocator? = parentContext?.sharedAllocator

    private val depth: Int = parentContext?.depth?.plus(1) ?: 0

    override val type: Type get() = ContextType

    override fun getMember(name: String, accessingContext: Context?) = scope.find(name)

    val bindingContext: Context? get() = parentContext

    init {
        if (depth >= CONTEXT_DEPTH_LIMIT) {
            compileError("Maximum depth reached.")
        }
    }
}

const val CONTEXT_DEPTH_LIMIT = 100

fun Context.getFullyQualified(name: List<String>) =
    globalContext.getPackage(name.dropLast(1))?.getMember(name.last(), null)
        ?: compileError("${name.joinToString(".")} does not exist")

fun Context.getFullyQualified(vararg name: String) = getFullyQualified(name.toList())

object ContextType : BuiltinType("Context")

/**
 * A context under which a block of expressions may be evaluated.
 */
abstract class ExecutionContext(parentContext: Context) : Context(parentContext) {
    abstract val localAllocator: TemporaryAllocator
    abstract val statements: BlockStatement
}

open class SimpleContext(parentContext: Context) : Context(parentContext)

open class SimpleExecutionContext(parentContext: ExecutionContext) : ExecutionContext(parentContext) {
    override val scope = EagerScope(parentContext.scope)
    override val localAllocator = parentContext.localAllocator
    override val statements = BlockStatement()
}

class AlternateScopeContext(parentContext: Context, scopeContext: Context) : SimpleContext(parentContext) {
    override val scope = Scope(scopeContext.scope)
    // Note: Metadata also should reflect both parents eventually
}

class AlternateScopeExecutionContext(parentContext: ExecutionContext, scopeContext: Context) :
    SimpleExecutionContext(parentContext) {
    override val scope = EagerScope(scopeContext.scope)
}

fun Context.createAndAddChild() = when (this) {
    is ExecutionContext -> SimpleExecutionContext(this).also { this.statements += it.statements }
    else -> SimpleContext(this)
}

fun Context.createAndAddChild(scopeContext: Context) = when (this) {
    is ExecutionContext -> AlternateScopeExecutionContext(this, scopeContext).also { this.statements += it.statements }
    else -> AlternateScopeContext(this, scopeContext)
}

fun Context.createChild() = when (this) {
    is ExecutionContext -> SimpleExecutionContext(this)
    else -> SimpleContext(this)
}

class InnerExecutionContext(parentContext: ExecutionContext) : SimpleExecutionContext(parentContext) {
    override val scope = EagerScope(parentContext.scope)
    override val statements = parentContext.statements
}

class ReadOnlyContext(parentContext: Context) : Context(parentContext) {
    override val scope = ReadOnlyScope(parentContext.scope)
}

fun List<StatementNode>.evaluateIn(context: Context): Value =
    this
        .asSequence()
        .map { it.parseAndApplyTo(context) }
        .lastOrNull() ?: UnitValue

fun List<StatementNode>.evaluateInChildOf(context: Context): Value = evaluateIn(context.createAndAddChild())

fun BlockNode.evaluateIn(context: Context) = value.evaluateIn(context)
fun BlockNode.evaluateInChildOf(context: Context): Value = evaluateIn(context.createAndAddChild())
