package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.Declaration
import xyz.qwewqa.trebla.frontend.declaration.FunctionType
import xyz.qwewqa.trebla.frontend.declaration.Signature
import xyz.qwewqa.trebla.frontend.expression.*

object LocalContext : Declaration, Callable {
    override val declaringContext: Context? = null
    override val identifier = "localContext"
    override val type = AnyType
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters: List<Parameter> = emptyList()

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        // just triggers the standard error if any arguments are passed
        parameters.pairedWithAndValidated(arguments)
        return callingContext
    }
}

object WithContext : Declaration, Callable {
    override val declaringContext: Context? = null
    override val identifier = "withContext"
    override val type = AnyType
    override val signature = Signature.Default
    override val visibility = Visibility.PUBLIC

    override val parameters: List<Parameter> = listOf(
        Parameter("context", ContextType),
        Parameter("op", FunctionType),
    )

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val argumentValues = parameters.pairedWithAndValidated(arguments).byParameterName()
        val innerContext = argumentValues["context"] as Context
        val op = argumentValues["op"] as? LambdaExpression ?: compileError("Operation must be a lambda")
        val context =
            if (callingContext is ExecutionContext) CombinedExecutionContext(callingContext, innerContext)
            else CombinedContext(callingContext, innerContext)
        // kinda a hack to do this
        // might be worth considering making a function-like class independent of function/lambda declarations
        return LambdaExpression(op.node, context).callWith(listOf(ValueArgument(null, callingContext)), context)
    }
}

class CombinedContext(private val outer: Context, private val inner: Context) : Context {
    override val scope = CombinedScope(this, outer.scope, inner.scope)
}

class CombinedExecutionContext(private val outer: ExecutionContext, private val inner: Context) : ExecutionContext {
    override val scope = CombinedScope(this, outer.scope, inner.scope)

    override val localAllocator = outer.localAllocator
    override val statements = outer.statements
}

class CombinedScope(context: Context, private val outer: Scope, private val inner: Scope) : Scope(context, inner) {
    override fun find(name: String, signature: Signature): Value? {
        return super.find(name, signature) ?: outer.find(name, signature)
    }
}