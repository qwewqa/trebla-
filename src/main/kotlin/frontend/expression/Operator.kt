package xyz.qwewqa.sono.frontend.expression

import xyz.qwewqa.sono.frontend.compileError
import xyz.qwewqa.sono.frontend.context.Context
import xyz.qwewqa.sono.frontend.context.ExecutionContext
import xyz.qwewqa.sono.frontend.context.MemberAccessor
import xyz.qwewqa.sono.frontend.runWithErrorMessage
import xyz.qwewqa.sono.grammar.sono.InfixFunctionNode
import xyz.qwewqa.sono.grammar.sono.PostfixUnaryFunctionNode
import xyz.qwewqa.sono.grammar.sono.PrefixUnaryFunctionNode
import xyz.qwewqa.sono.grammar.sono.UnaryFunctionNode

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
        val lhs = node.lhs.parseAndApplyTo(context)
        val rhs = node.rhs.parseAndApplyTo(context)
        if (functionName == "=") {
            if (lhs !is Mutable) compileError("Invalid assignment.")
            if (context !is ExecutionContext) compileError("Invalid location for assignment.")
            lhs.copyFrom(rhs, context)
            return UnitValue
        }
        if (lhs !is MemberAccessor) compileError("Operator '${node.op}' not applicable.")
        val func = lhs.getMember(functionName, context)
        if (func !is Callable) compileError("Operator '${node.op}' ($functionName) not defined by a function.")
        if (isOperator && !func.isOperator) compileError("Operator '${node.op}' ($functionName) has a function but is not marked as operator.")
        if (!isOperator && !func.isInfix) compileError("$functionName is a function but is not marked as infix.")
        return func.callWith(listOf(ValueArgument(null, rhs)), context)
    }
}


// TODO: Maybe implement short circuiting operators
// || and && should, in the end, not be overloadable operators and instead be boolean specific operators.
// For now, they will be implemented the same way as other operators but
// that would have to change to support short circuiting.
// `and` and `or` likely would remain as infix functions if those are supported.
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
    "||" to "or",
    "&&" to "and",
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