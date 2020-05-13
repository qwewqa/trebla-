package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.frontend.Entity
import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.declaration.AnyType
import xyz.qwewqa.sono.frontend.declaration.Type
import xyz.qwewqa.sono.frontend.declaration.applyIn
import xyz.qwewqa.sono.frontend.runWithErrorMessage
import xyz.qwewqa.sono.grammar.sono.*

interface Callable {
    val isOperator: Boolean get() = false
    val isInfix: Boolean get() = false
    val parameters: List<Parameter>? get() = null
    fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value
}

/**
 * A value argument is passed into a function call
 */
data class ValueArgument(val name: String?, val value: Value)

class CallExpression(override val node: UnaryFunctionNode) : Expression {
    private val argumentNodes = (node.op as? FunctionCallNode)?.arguments?.arguments ?: error("Not a call expression.")

    override fun applyTo(context: Context): Value {
        val valueArguments = argumentNodes.applyAllIn(context)
        val callable = node.value.parseAndApplyTo(context)
        if (callable !is Callable) compileError("Not a callable.", node)
        return runWithErrorMessage("Error in call expression.") {
            callable.callWith(valueArguments, context)
        }
    }
}

fun ValueArgumentNode.applyIn(context: Context) =
    ValueArgument(this.name?.value, this.expression.parseAndApplyTo(context))

fun List<ValueArgumentNode>.applyAllIn(context: Context) = map { it.applyIn(context) }

/**
 * A parameter which is often, but not always, derived from a [ParameterNode].
 */
class Parameter(val name: String, val type: Type, override val node: SonoNode? = null) : Entity {
    constructor(node: ParameterNode, context: Context) : this(
        node.identifier.value,
        node.type?.applyIn(context) ?: AnyType,
        node
    )
}

fun List<ParameterNode>.parse(context: Context) = map {
    Parameter(
        it,
        context
    )
}

fun List<Parameter>.pairedWithAndValidated(arguments: List<ValueArgument>) =
    pairArguments(arguments).also { it.validateTyping() }

fun List<Parameter>.pairArguments(arguments: List<ValueArgument>): List<Pair<Parameter, Value>> {
    // This can still contain named arguments. However, they must match the place in their order.
    // That is, the call would remain equivalent if the name was removed.
    // This is merely a convenience for reading the function call.
    val orderedArguments = arguments.dropLastWhile { it.name != null }

    val namedArguments = arguments.takeLastWhile { it.name != null }
    val namedParameters = this.associateBy { it.name }
    val pairedOrderedArguments = this.zip(orderedArguments)
    pairedOrderedArguments.forEach { (param, arg) ->
        if (arg.name != null && param.name != arg.name)
            compileError("Named parameter ${arg.name} is out of order and does not come at the end.")
    }
    val pairedNamedParameters = namedArguments.map {
        val param = namedParameters[it.name]
            ?: compileError("No parameter with name ${it.name} exists or has already been supplied as an ordered argument.")
        param to it
    }
    val allPairedParameters = pairedOrderedArguments + pairedNamedParameters
    if (allPairedParameters.size != this.size) {
        compileError("Wrong number of parameters supplied.")
    }
    return allPairedParameters.map { (param, arg) -> param to arg.value }
}

fun List<Pair<Parameter, Value>>.associateByParameterName() = associate { (param, value) -> param.name to value }

/*
Want some more specific error reporting at some point. This can do for now.
 */
fun List<Pair<Parameter, Value>>.validateTyping() = forEach { (param, arg) ->
    if (!param.type.accepts(arg)) compileError("Argument does not satisfy type constraint.")
}