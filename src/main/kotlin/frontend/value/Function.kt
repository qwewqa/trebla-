package xyz.qwewqa.trebla.frontend.value

import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.FunctionDeclarationNode

class FunctionDeclaration(
    override val node: FunctionDeclarationNode,
    override val parentContext: Context,
) : Declaration, Callable, Bindable {
    override val type = CallableType
    override val identifier = node.identifier.value
    override val visibility: Visibility

    val valueParameters by lazy {
        node.parameters.value.map {
            Parameter(
                it,
                parentContext
            )
        }
    }

    val receiverParameter by lazy {
        node.receiver?.let {
            it.runWithErrorMessage("Receiver parameter type does not exist or is not a struct.") {
                Parameter(
                    name = it.identifier?.value ?: "this",
                    type = it.type.applyIn(parentContext),
                    default = null,
                    node = it
                )
            }
        }?.also {
            if ((it.type as? StructDeclaration)?.fieldNames?.contains(identifier) == true)
                compileError("Method has same name as member of receiver struct.", node.identifier)
        }
    }

    val parameters by lazy {
        receiverParameter?.let { listOf(it) + valueParameters } ?: valueParameters
    }

    /*
    Unit is the default return type if none is specified and the function has a block body.
    In an assigment body, the return type is Any.
    This is a constraint, due to how functions are inline and always using Any will not break anything,
    but may make code less clear to read and does not allow the compiler to detect if an unexpected type is returned.
    A function that has a unit return type always returns unit, regardless of the final statement.
    It is roughly equivalent to a void return type in some languages.
     */
    val returnType by lazy {
        node.type?.applyIn(parentContext) ?: if (node.assigmentBody) AnyType else UnitValue
    }
    val returnsUnit by lazy {
        returnType is UnitValue
    }

    override val isOperator: Boolean
    override val isInfix: Boolean

    override val signature by lazy {
        receiverParameter?.let { ReceiverType(it.type) } ?: DefaultSignature
    }

    override fun boundTo(value: Value, context: Context): Value {
        if (receiverParameter == null) compileError("Not a binding function.")
        return BoundFunction(context, value, this)
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context): Value {
        val statements = node.body.value
        val pairedArguments = parameters.pairedWithAndValidated(arguments)

        val functionContext = callingContext.createAndAddChild(parentContext)
        pairedArguments.forEach { (param, value) -> functionContext.scope.add(value, param.name) }

        statements.dropLast(1).forEach {
            it.parseAndApplyTo(functionContext)
        }
        val returnValue = statements.lastOrNull()?.parseAndApplyTo(functionContext) ?: UnitValue

        return if (returnsUnit) UnitValue else returnValue.also {
            if (!returnType.accepts(it))
                compileError("Function final statement type incompatible with specified return type.")
        }
    }

    init {
        node.modifiers.parse {
            visibility = selectFromMap(visibilityModifiers) ?: Visibility.PUBLIC
            isOperator = selectSingle("operator")
            isInfix = selectSingle("infix")
        }
    }
}

object CallableType : BuiltinType("Callable")

class BoundFunction(
    val context: Context?,
    val receiver: Value,
    val function: FunctionDeclaration,
) : Callable, Value {
    override val type = CallableType
    override val isOperator = function.isOperator
    override val isInfix = function.isInfix

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context) =
        function.callWith(
            listOf(
                ValueArgument(
                    null,
                    receiver
                )
            ) + arguments, callingContext
        )
}
