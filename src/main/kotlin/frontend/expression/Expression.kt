package xyz.qwewqa.trebla.frontend.expression

import xyz.qwewqa.trebla.backend.constexpr.tryConstexprEvaluate
import xyz.qwewqa.trebla.frontend.Entity
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.ReadOnlyContext
import xyz.qwewqa.trebla.frontend.context.getFullyQualified
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.grammar.trebla.*

/**
 * Expressions are evaluated to compute their value.
 */
interface Expression : Entity {
    /**
     * Applies the effects of this frontend.expression to the provided context, such as adding a declaration to the scope
     * or adding a statement.
     * Returns a value which is valid after the frontend.expression is applied.
     */
    fun applyTo(context: Context): Value
}

/**
 * An frontend.expression that returns a constant [value].
 */
class ValueExpression(val value: Value) :
    Expression {
    override fun applyTo(context: Context): Value {
        return value
    }
}

fun ExpressionNode.tryConstexprEval(context: Context) =
    (parseAndApplyTo(ReadOnlyContext(context)) as? RawStructValue)?.value?.toIR()?.tryConstexprEvaluate()

fun StatementNode.parseAndApplyTo(context: Context) = parse(context).applyTo(context)

fun StatementNode.parse(context: Context): Expression = when (this) {
    is NumberLiteralNode -> literalNumber(this.value, context)
    is BooleanLiteralNode -> literalBoolean(this.value, context)
    is SimpleIdentifierNode -> SimpleIdentifierExpression(this)
    is IfExpressionNode -> IfExpression(this)
    is WhileExpressionNode -> WhileExpression(this)
    is ForExpressionNode -> ForExpression(this)
    is UnaryFunctionNode -> when (this.op) {
        is MemberAccessNode -> MemberAccessExpression(this)
        is PrefixUnaryFunctionNode -> UnaryFunctionExpression(this)
        is PostfixUnaryFunctionNode -> UnaryFunctionExpression(this)
        is FunctionCallNode -> CallExpression(this)
    }
    is InfixFunctionNode -> InfixFunctionExpression(this)
    is FunctionDeclarationNode -> FunctionDeclaration(this, context)
    is StructDeclarationNode -> StructDeclaration(this, context)
    is PropertyDeclarationNode -> PropertyDeclaration(this, context)
    is LetDeclarationNode -> LetDeclaration(this, context)
    is LambdaNode -> LambdaExpression(this, context)
    // Should not actually happen, but no sealed interfaces, so this has to be here
    else -> throw UnsupportedOperationException("Unknown statement or expression.")
}

fun literalNumber(value: Double, context: Context) =
    ValueExpression(
        RawStructValue(
            LiteralValue(value),
            context,
            (context.scope.getFullyQualified("std", "Number") as StructDeclaration)
        )
    )

fun literalBoolean(value: Boolean, context: Context) =
    ValueExpression(
        RawStructValue(
            LiteralValue(if (value) 1.0 else 0.0),
            context,
            (context.scope.getFullyQualified("std", "Boolean") as StructDeclaration)
        )
    )

val Context.booleanType get() = scope.getFullyQualified("std", "Boolean") as StructDeclaration
val Context.numberType get() = scope.getFullyQualified("std", "Number") as StructDeclaration
val Context.rawType get() = scope.getFullyQualified("std", "Raw") as StructDeclaration