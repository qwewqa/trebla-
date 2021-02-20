package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.ir.IRFunctionCall
import xyz.qwewqa.trebla.backend.ir.SonoFunction
import xyz.qwewqa.trebla.frontend.*
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ExecutionContext
import xyz.qwewqa.trebla.frontend.context.SimpleExecutionContext
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.Dereferenceable
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
        if (lhs is Dereferenceable && functionName == "deref") {
            return lhs.deref(context)
        }
        val func = lhs.resolveMember(functionName, context)
        if (func !is Callable) compileError("Unary function is not implemented.", node)
        if (!func.isOperator) compileError("Unary function is not marked operator.", node)
        return node.runWithErrorMessage("Error in unary function call") {
            func.callWith(emptyList(), context) // no arguments; receiver should already be bound
        }
    }
}

class InfixFunctionExpression(override val node: InfixFunctionNode) : Expression {
    private val functionName = infixOperatorNames[node.op] ?: node.op
    private val isOperator = node.op in infixOperatorNames

    override fun applyTo(context: Context): Value = node.runWithErrorMessage("Error in infix expression.") {
        when (functionName) {
            "||" -> return doShortCircuitingBoolean(ShortCircuitOperation.Or, context)
            "&&" -> return doShortCircuitingBoolean(ShortCircuitOperation.And, context)
        }
        val lhs = node.lhs.parseAndApplyTo(context)
        val rhs = node.rhs.parseAndApplyTo(context)
        return when (functionName) {
            "=" -> {
                doAssignment(lhs, rhs, context)
                UnitValue
            }
            "===" -> {
                BooleanType.fromRaw((rhs === lhs).let { if (it) 1.0 else 0.0 }.toLiteralRawValue())
            }
            "!==" -> {
                BooleanType.fromRaw((rhs !== lhs).let { if (it) 1.0 else 0.0 }.toLiteralRawValue())
            }
            else -> {
                val func = lhs.resolveDirectBindingMember(functionName, context)
                if (func !is Callable) compileError("Operator '${node.op}' ($functionName) not defined by a function.")
                if (isOperator && !func.isOperator) compileError("Operator '${node.op}' ($functionName) has a function but is not marked as operator.")
                if (!isOperator && !func.isInfix) compileError("$functionName is a function but is not marked as infix.")
                func.callWith(listOf(ValueArgument(null, rhs)), context)
            }
        }
    }

    private fun doAssignment(lhs: Value, rhs: Value, context: Context) {
        if (lhs !is Allocated) compileError("Invalid assignment. Left hand side is not allocated.")
        if (context !is ExecutionContext) compileError("Invalid location for assignment.")
        lhs.copyFrom(rhs, context)
    }

    private fun doShortCircuitingBoolean(
        operation: ShortCircuitOperation,
        context: Context,
    ): Value {
        val booleanType = BooleanType
        val lhsValue = node.lhs.parseAndApplyTo(context)
        if (lhsValue !is PrimitiveInstance || lhsValue.type != BooleanType) {
            compileError("Short circuiting operators can only be applied to booleans. Instead got ${lhsValue.type.commonName}.")
        }
        if (context !is ExecutionContext) {
            return doNonExecutionBoolean(operation, context)
        }

        // We need to make a new block to place on the right side of the builtin short circuiting function
        // such that it only executes if the first part evaluated to a relevant value.
        val rhsBlock = SimpleExecutionContext(context)

        val rhsValue = node.rhs.parseAndApplyTo(rhsBlock)
        if (rhsValue !is PrimitiveInstance || rhsValue.type != BooleanType)
            compileError("Short circuiting operators can only be applied to booleans. Instead got ${rhsValue.type.commonName}.")
        val resultValue = IRFunctionCall(operation.function, listOf(
            lhsValue.value.toIR(context),
            IRFunctionCall(SonoFunction.Execute, listOf(
                rhsBlock.statements.asIR(),
                rhsValue.value.toIR(context)
            ))
        ))

        // Since the right hand side might have done something (play, draw, etc.), the resulting value
        // must be copied into a temporary variable.
        // Otherwise, the action would be rerun each time the value was accessed (or not at all if never accessed).
        // In simpler cases, it's up to the backend to optimize (at least once implemented).
        return booleanType.fromRaw(IRRawValue(resultValue)).copyTo(context.localAllocator, context)
    }

    private fun doNonExecutionBoolean(
        operation: ShortCircuitOperation,
        context: Context,
    ): Value {
        val lhs = node.lhs.parseAndApplyTo(context).asBooleanPrimitiveInstance()
        val rhs = node.rhs.parseAndApplyTo(context).asBooleanPrimitiveInstance()
        return BooleanType.fromRaw(BuiltinCallRawValue(operation.function, listOf(lhs.value, rhs.value)))
    }

    enum class ShortCircuitOperation(val function: SonoFunction) {
        And(SonoFunction.And),
        Or(SonoFunction.Or),
    }
}

val infixOperatorNames = mapOf(
    "+" to "plus",
    "-" to "minus",
    "*" to "times",
    "/" to "div",
    "%" to "mod",
    "^" to "pow",
    "+=" to "plusAssign",
    "-=" to "minusAssign",
    "*=" to "timesAssign",
    "/=" to "divAssign",
    "%=" to "modAssign",
    "^=" to "powAssign",
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
    "--" to "preDecrement",
    "*" to "deref"
)

val postfixOperatorNames = mapOf(
    "++" to "postIncrement",
    "--" to "postDecrement"
)
