package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.expression.*

class LocalContext(context: Context) :
    SimpleDeclaration(
        context,
        "localContext",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {},
        {
            callingContext
        }
    )

class WithContext(context: Context) :
    SimpleDeclaration(
        context,
        "withContext",
        CallableType
    ),
    Callable by CallableDelegate(
        context,
        {
            "context" type ContextType
            "block" type LambdaType
        },
        {
            val innerContext = "context".cast<Context>()
            val op = "op".cast<LambdaExpression>()
            val newContext =
                when (callingContext) {
                    is ExecutionContext -> CombinedExecutionContext(callingContext, innerContext)
                    else -> CombinedContext(callingContext, innerContext)
                }
            // kinda a hack to do this
            // might be worth considering making a function-like class independent of function/lambda declarations
            LambdaExpression(op.node, newContext).callWith(listOf(ValueArgument(null, callingContext)), newContext)
        }
    )

class CombinedContext(outer: Context, inner: Context) : Context {
    override val parentContext = outer
    override val globalContext: GlobalContext = outer.globalContext
    override val scope = CombinedScope(outer.scope, inner.scope)
}

class CombinedExecutionContext(outer: ExecutionContext, inner: Context) : ExecutionContext {
    override val parentContext = outer
    override val globalContext: GlobalContext = outer.globalContext
    override val scope = CombinedScope(outer.scope, inner.scope)

    override val localAllocator = outer.localAllocator
    override val statements = outer.statements
}

class CombinedScope(private val outer: Scope, inner: Scope) : EagerScope(inner) {
    override fun find(
        identifier: String,
        signature: Signature,
        minVisibility: Visibility,
    ): Value? {
        return super.find(identifier, signature, minVisibility) ?: outer.find(identifier, signature, minVisibility)
    }
}
