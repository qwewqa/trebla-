package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.backend.compile.FunctionIRNode
import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.backend.compile.IRNode
import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.expression.Statement
import xyz.qwewqa.trebla.frontend.expression.Value

/**
 * A context contains declarations, which are stored in its scope.
 */
interface Context : MemberAccessor {
    val parentContext: Context?
    val source: Entity? get() = null

    override val type: Type get() = ContextType

    override fun hasMember(name: String, accessingContext: Context?): Boolean {
        return scope.find(name) != null
    }

    override fun getMember(name: String, accessingContext: Context?): Value {
        return scope.find(name) ?: compileError("Context has no member with name $name/")
    }

    val scope: Scope
}

object ContextType : BuiltinType("Context")

/**
 * A context under which a block of expressions may be evaluated.
 */
interface ExecutionContext : Context {
    override val scope: EagerScope
    val localAllocator: TemporaryAllocator
    val statements: MutableList<Statement>
}

class SimpleContext(override val parentContext: Context?) : Context {
    override val scope = Scope(parentContext?.scope)
}

class SimpleExecutionContext(override val parentContext: ExecutionContext) : ExecutionContext, Statement {
    override val scope = EagerScope(parentContext.scope)
    override val localAllocator = parentContext.localAllocator
    override val statements = mutableListOf<Statement>()

    override fun toIR(): IRNode {
        return FunctionIRNode(FunctionIRNodeVariant.Execute, statements.map { it.toIR() })
    }
}

class InnerExecutionContext(override val parentContext: ExecutionContext) : ExecutionContext {
    override val scope = EagerScope(parentContext.scope)
    override val localAllocator = parentContext.localAllocator
    override val statements = parentContext.statements
}

class ReadOnlyContext(override val parentContext: Context) : Context {
    override val scope = ReadOnlyScope(parentContext.scope)
}

class EmptyContext : Context {
    override val parentContext: Context? = null
    override val scope = ReadOnlyScope()
}