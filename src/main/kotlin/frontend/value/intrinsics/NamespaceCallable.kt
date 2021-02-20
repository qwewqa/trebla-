package xyz.qwewqa.trebla.frontend.declaration.intrinsics

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.SimpleContext
import xyz.qwewqa.trebla.frontend.context.Visibility
import xyz.qwewqa.trebla.frontend.declaration.BuiltinType
import xyz.qwewqa.trebla.frontend.declaration.CallableType
import xyz.qwewqa.trebla.frontend.expression.*

class NamespaceCallable(context: Context) :
    SimpleDeclaration(
        context,
        "namespace",
        CallableType
    ),
    Callable by CallableDelegate(
        {
            "init" type LambdaType
        },
        {
            val init = "init".cast<LambdaExpression>()

            if (!init.hasDefaultParameters) {
                compileError("Namespace initializer must be a lambda expression that has default (no) parameters.")
            }

            val namespaceContext = SimpleContext(callingContext)

            init.run {
                statements.forEach {
                    it.parseAndApplyTo(namespaceContext)
                }
            }

            Namespace(namespaceContext)
        }
    )

object NamespaceType : BuiltinType("Namespace")

class Namespace(private val namespace: Context) : Value {
    override val type = NamespaceType
    override fun getMember(name: String, accessingContext: Context?): Value? {
        return namespace.scope.get(name, minVisibility = Visibility.PUBLIC)
    }
}
