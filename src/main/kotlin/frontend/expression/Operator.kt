package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.compile.FunctionIRNode
import xyz.qwewqa.trebla.backend.compile.FunctionIRNodeVariant
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.MemberAccessor
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.BuiltinFunctionVariant
import xyz.qwewqa.trebla.frontend.declaration.RawStructValue
import xyz.qwewqa.trebla.frontend.runWithErrorMessage
import xyz.qwewqa.trebla.grammar.trebla.InfixFunctionNode
import xyz.qwewqa.trebla.grammar.trebla.PostfixUnaryFunctionNode
import xyz.qwewqa.trebla.grammar.trebla.PrefixUnaryFunctionNode
import xyz.qwewqa.trebla.grammar.trebla.UnaryFunctionNode

class UnaryFunctionExpression(override val node: UnaryFunctionNode) : Expression {
    private val functionName = when (val op = node.op) {
        is PrefixUnaryFunctionNode -> prefixOperatorNames.getValue(op.value)
        is PostfixUnaryFunctionNode -> postfixOperatorNames.getValue(op.value)
        else -> error("Not a unary function node.")
    }

    override fun applyTo(context: Context): Value {
        val lhs = node.value.parseAndApplyTo(context)
        if (lhs !is MemberAccessor) compileError("Unary function not valid.", node)
        val func = lhs.getMember(functionName, context)
        if (func !is Callable) compileError("Unary function does not exist.", node)
        return runWithErrorMessage("Error in unary function call") {
            func.callWith(emptyList(), context) // no arguments; receiver should already be bound
        }
    }
}

class InfixFunctionExpression(override val node: InfixFunctionNode) : Expression {
    private val functionName = infixOperatorNames[node.op] ?: node.op
    private val isOperator = node.op in infixOperatorNames

    override fun applyTo(context: Context): Value = runWithErrorMessage("Error in infix expression.") {
        when (functionName) {
            "||" -> return doShortCircuitingBoolean(BuiltinFunctionVariant.Or, context)
            "&&" -> return doShortCircuitingBoolean(BuiltinFunctionVariant.And, context)
        }
        val lhs = node.lhs.parseAndApplyTo(context)
        val rhs = node.rhs.parseAndApplyTo(context)
        if (functionName == "=") {
            doAssignment(lhs, rhs, context)
            return UnitValue
        }
        if (lhs !is MemberAccessor) compileError("Operator '${node.op}' not applicable.")
        val func = lhs.getMember(functionName, context)
        if (func !is Callable) compileError("Operator '${node.op}' ($functionName) not defined by a function.")
        if (isOperator && !func.isOperator) compileError("Operator '${node.op}' ($functionName) has a function but is not marked as operator.")
        if (!isOperator && !func.isInfix) compileError("$functionName is a function but is not marked as infix.")
        return func.callWith(listOf(ValueArgument(null, rhs)), context)
    }

    private fun doAssignment(lhs: Value, rhs: Value, context: Context) {
        if (lhs !is Mutable) compileError("Invalid assignment.")
        if (context !is ExecutionContext) compileError("Invalid location for assignment.")
        lhs.copyFrom(rhs, context)
    }

    private fun doShortCircuitingBoolean(
        operation: BuiltinFunctionVariant,
        context: Context,
    ): Value {
        val booleanType = context.booleanType
        val lhsValue = node.lhs.parseAndApplyTo(context)
        if (lhsValue !is RawStructValue || lhsValue.type != context.booleanType)
            compileError("Short circuiting operators can only be applied to booleans.")
        if (context !is ExecutionContext)
            compileError("Short circuiting operators can only be used in execution contexts. Use the infix `and` or `or` instead.")

        // We need to make a new block to place on the right side of the builtin short circuiting function
        // such that it only executes if the first part evaluated to a relevant value.
        val rhsBlock = SimpleExecutionContext(context)

        val rhsValue = node.rhs.parseAndApplyTo(rhsBlock)
        if (rhsValue !is RawStructValue || rhsValue.type != context.booleanType)
            compileError("Short circuiting operators can only be applied to booleans.")
        val resultValue = BuiltinCallRawValue(operation, listOf(
            lhsValue.raw.toIR(),
            FunctionIRNode(FunctionIRNodeVariant.Execute, listOf(
                rhsBlock.toIR(),
                rhsValue.raw.toIR()
            ))
        ))

        // Since the right hand side might have done something (play, draw, etc.), the resulting value
        // must be copied into a temporary variable.
        // Otherwise, the action would be rerun each time the value was accessed (or not at all if never accessed).
        // In simpler cases, it's up to the backend to optimize (at least once implemented).
        return RawStructValue(resultValue, context, booleanType).copyOn(context.localAllocator, context)
    }
}

val infixOperatorNames = mapOf(
    "+" to "plus",
    "-" to "minus",
    "*" to "times",
    "/" to "div",
    "%" to "mod",
    "**" to "pow",
    "+=" to "plusAssign",
    "-=" to "minusAssign",
    "*=" to "timesAssign",
    "/=" to "divAssign",
    "%=" to "modAssign",
    "**=" to "powAssign",
    "==" to "equals",
    "!=" to "notEquals",
    ">" to "greater",
    "<" to "less",
    ">=" to "greaterOr",
    "<=" to "lessOr",
)

val prefixOperatorNames = mapOf(
    "+" to "unaryPlus",
    "-" to "unaryMinus",
    "!" to "not",
    "++" to "preIncrement",
    "--" to "preDecrement"
)

val postfixOperatorNames = mapOf(
    "++" to "postIncrement",
    "--" to "postDecrement"
)