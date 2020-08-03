package xyz.qwewqa.trebla.frontend.declaration

import xyz.qwewqa.trebla.backend.compile.FunctionIRNode
import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.backend.compile.IRNode
import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.*
import xyz.qwewqa.trebla.frontend.expression.*
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.FunctionDeclarationNode

class FunctionDeclaration(
    override val node: FunctionDeclarationNode,
    override val parentContext: Context,
) : Declaration, Callable {
    override val type = FunctionType
    override val identifier = node.identifier.value
    override val visibility: Visibility

    val valueParameters by lazy {
        node.parameters.values.map {
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
            if (it.type !is StructDeclaration) compileError("Method receiver must be a struct.")
            if ((it.type as? StructDeclaration)?.fieldNames?.contains(identifier) == true)
                compileError("Method has same name as member of receiver struct.", node.identifier)
        }
    }

    override val parameters by lazy {
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
        receiverParameter?.let { Signature.TypedReceiver(it.type) } ?: Signature.Default
    }

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?): Value {
        val statements = node.body.value
        val pairedArguments = parameters.pairedWithAndValidated(arguments)

        if (callingContext == null) compileError("Function call requires a context.")
        if (callingContext !is ExecutionContext) {
            // we allow single or zero expressions even in non-execution contexts
            val returnValue = when (statements.size) {
                0 -> UnitValue
                1 -> statements.first().parseAndApplyTo(FunctionSimpleContext(callingContext, parentContext, pairedArguments))
                else -> compileError("Invalid location for multi-statement function call.")
            }
            if (returnType == UnitValue) compileError("Invalid location for call to this function.")
            return returnValue.also {
                if (!returnType.accepts(it))
                    compileError("Function final statement type incompatible with specified return type.")
            }
        }
        val functionContext = FunctionExecutionContext(
            callingContext,
            parentContext,
            pairedArguments
        )

        statements.dropLast(1).forEach {
            it.parseAndApplyTo(functionContext)
        }
        val returnValue = statements.lastOrNull()?.parseAndApplyTo(functionContext) ?: UnitValue

        if (functionContext.statements.isNotEmpty()) callingContext.statements += functionContext

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

object FunctionType : BuiltinType("Function")

class BoundFunction(
    val receiver: Value,
    val function: FunctionDeclaration,
) : Callable, Entity,
    Value {
    override val type = FunctionType
    override val isOperator = function.isOperator
    override val isInfix = function.isInfix

    override fun callWith(arguments: List<ValueArgument>, callingContext: Context?) =
        function.callWith(
            listOf(
                ValueArgument(
                    null,
                    receiver
                )
            ) + arguments, callingContext
        )
}

fun FunctionDeclaration.boundTo(receiver: Value) =
    BoundFunction(receiver, this)

class FunctionScope(
    parent: Scope,
    argumentValues: Map<Parameter, Value>
) : EagerScope(parent) {
    init {
        argumentValues.forEach { (param, value) -> add(value, param.name) }
    }
}

class FunctionExecutionContext(
    callingContext: ExecutionContext,
    declarationContext: Context,
    argumentValues: Map<Parameter, Value>
) : ExecutionContext,
    Statement {
    override val parentContext = callingContext

    // the parent of the scope comes from the context where the function was declared, not where it was called
    override val scope = FunctionScope(declarationContext.scope, argumentValues)

    override val localAllocator = callingContext.localAllocator
    override val statements = mutableListOf<Statement>()

    override fun toIR(): IRNode {
        return FunctionIRNode(FunctionIRNodeVariant.Execute, statements.map { it.toIR() })
    }
}

class FunctionSimpleContext(
    override val parentContext: Context,
    declarationContext: Context,
    argumentValues: Map<Parameter, Value>
) : Context {
    // the parent of the scope comes from the context where the function was declared, not where it was called
    override val scope = FunctionScope(declarationContext.scope, argumentValues)
}