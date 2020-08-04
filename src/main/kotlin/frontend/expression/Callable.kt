package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.declaration.AnyType
import xyz.qwewqa.trebla.frontend.declaration.Type
import xyz.qwewqa.trebla.frontend.declaration.accepts
import xyz.qwewqa.trebla.frontend.declaration.applyIn
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.*

interface Callable {
    val isOperator: Boolean get() = false
    val isInfix: Boolean get() = false
    val parameters: List<Parameter>? get() = null
    fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value
}

/**
 * A value argument is passed into a function call.
 */
data class ValueArgument(val name: String?, val value: Value, val isTailLambda: Boolean = false)

class CallExpression(override val node: UnaryFunctionNode) : Expression {
    private val arguments = (node.op as? FunctionCallNode)?.arguments ?: error("Not a call expression.")
    private val argumentNodes = arguments.arguments
    private val lambdaNode = arguments.tailLambda

    override fun applyTo(context: Context): Value {
        val valueArguments = argumentNodes.applyAllIn(context)
        val lambda = lambdaNode?.parseAndApplyTo(context)?.let { ValueArgument(null, it, true) }
        val arguments = if (lambda != null) valueArguments + lambda else valueArguments

        val callable = node.value.parseAndApplyTo(context)
        if (callable !is Callable) compileError("Not a callable.", node)
        return runWithErrorMessage("Error in call expression.") {
            callable.callWith(arguments, context)
        }
    }
}

fun ValueArgumentNode.applyIn(context: Context) =
    ValueArgument(this.name?.value, this.expression.parseAndApplyTo(context))

fun List<ValueArgumentNode>.applyAllIn(context: Context) = map { it.applyIn(context) }

/**
 * A parameter which is often, but not always, derived from a [ParameterNode].
 */
class Parameter(val name: String, val type: Type, val default: DefaultParameter? = null, override val node: TreblaNode? = null) : Entity {
    constructor(node: ParameterNode, context: Context) : this(
        node.identifier.value,
        node.type?.applyIn(context) ?: AnyType,
        node.default?.let { DefaultParameter(it.parse(context), context) },
        node
    )
}

data class DefaultParameter(val expression: Expression, val context: Context) {
    fun get() = expression.applyTo(context)
}

fun List<ParameterNode>.parse(context: Context) = map {
    Parameter(
        it,
        context
    )
}

fun List<Parameter>.pairedWithAndValidated(arguments: List<ValueArgument>) =
    pairArguments(arguments).also { it.validateTyping() }

fun List<Parameter>.pairArguments(arguments: List<ValueArgument>): Map<Parameter, Value> {
    if (arguments.size > this.size) compileError("Too many arguments.")

    val finalArgument = arguments.lastOrNull()

    val valueArguments: List<ValueArgument>
    val tailArgument: List<Pair<Parameter, ValueArgument>>
    if (finalArgument?.isTailLambda == true) {
        val tailParam = this.lastOrNull() ?: compileError("A lambda argument was supplied, but no parameters exist.")
        tailArgument = listOf(tailParam to finalArgument)
        valueArguments = arguments.dropLast(1)
    } else {
        tailArgument = emptyList()
        valueArguments = arguments
    }

    // This can still contain named arguments. However, they must match the place in their order.
    // That is, the call would remain equivalent if the name was removed.
    // This is merely a convenience for reading the function call.
    val orderedArguments = valueArguments.dropLastWhile { it.name != null }

    val namedArguments = valueArguments.takeLastWhile { it.name != null }
    val namedParameters = this.associateBy { it.name }
    val ordered = this.zip(orderedArguments)
    ordered.forEach { (param, arg) ->
        if (arg.name != null && param.name != arg.name)
            compileError("Named parameter ${arg.name} is out of order and does not come at the end.")
    }
    val named = namedArguments.map {
        val param = namedParameters[it.name]
            ?: compileError("No parameter with name ${it.name} exists or has already been supplied as an ordered argument.")
        param to it
    }
    val usedParams: Set<Parameter> = ordered.map { (param, _) -> param }.toSet() + named.map { (param, _) -> param } + tailArgument.map { (param, _) -> param }
    val defaults = this.filter { it !in usedParams }.associateWith {
        (it.default?.get() ?: compileError("Parameter ${it.name} was not provided an argument and has no default."))
    }
    return (ordered + named + tailArgument).also {
        if (ordered.map { (param, _) -> param }.toSet().intersect(named.map { (param, _) -> param }.toSet()).isNotEmpty())
            compileError("Duplicate named and ordered parameters.")
    }.associate { (param, arg) -> param to arg.value } + defaults
}

fun Map<Parameter, Value>.byParameterName() = entries.associate { (param, value) -> param.name to value }

/*
Want some more specific error reporting at some point. This can do for now.
 */
fun Map<Parameter, Value>.validateTyping() = forEach { (param, arg) ->
    if (!param.type.accepts(arg)) compileError("Argument does not satisfy type constraint.")
}