// Generated from SonoParser.g4 by ANTLR 4.8

    package xyz.qwewqa.sono.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SonoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SonoParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SonoParser#sonoFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSonoFile(SonoParser.SonoFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#packageHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageHeader(SonoParser.PackageHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(SonoParser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#importHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportHeader(SonoParser.ImportHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#topLevelObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopLevelObject(SonoParser.TopLevelObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(SonoParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#functionReceiver}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionReceiver(SonoParser.FunctionReceiverContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#functionValueParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionValueParameters(SonoParser.FunctionValueParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#functionValueParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionValueParameter(SonoParser.FunctionValueParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(SonoParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyDeclaration(SonoParser.PropertyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(SonoParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#structFields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructFields(SonoParser.StructFieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#structField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructField(SonoParser.StructFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptDeclaration(SonoParser.ScriptDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#scriptBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptBody(SonoParser.ScriptBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptMemberDeclaration(SonoParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDeclaration(SonoParser.ArchetypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#archetypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeName(SonoParser.ArchetypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDefaults(SonoParser.ArchetypeDefaultsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#archetypeDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDefault(SonoParser.ArchetypeDefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallbackDeclaration(SonoParser.CallbackDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#callbackOrder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallbackOrder(SonoParser.CallbackOrderContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#letDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetDeclaration(SonoParser.LetDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#initBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitBlock(SonoParser.InitBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(SonoParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SonoParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#parenthesizedType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedType(SonoParser.ParenthesizedTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#simpleUserType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleUserType(SonoParser.SimpleUserTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SonoParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SonoParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(SonoParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(SonoParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#disjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjunction(SonoParser.DisjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(SonoParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#equalityComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityComparison(SonoParser.EqualityComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(SonoParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfixFunctionCall(SonoParser.InfixFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(SonoParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(SonoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#exponentiationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExponentiationExpression(SonoParser.ExponentiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryExpression(SonoParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryExpression(SonoParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#atomicExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicExpression(SonoParser.AtomicExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(SonoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#callSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallSuffix(SonoParser.CallSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#valueArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArguments(SonoParser.ValueArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#valueArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArgument(SonoParser.ValueArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(SonoParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(SonoParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#loopExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopExpression(SonoParser.LoopExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#whileExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExpression(SonoParser.WhileExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#controlStructureBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStructureBody(SonoParser.ControlStructureBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#modifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifierList(SonoParser.ModifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(SonoParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SonoParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleIdentifier(SonoParser.SimpleIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#literalConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralConstant(SonoParser.LiteralConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#additiveOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOperator(SonoParser.AdditiveOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOperation(SonoParser.MultiplicativeOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryOperation(SonoParser.PrefixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryOperation(SonoParser.PostfixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessOperator(SonoParser.MemberAccessOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(SonoParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#equalityOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityOperation(SonoParser.EqualityOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#comparisonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperator(SonoParser.ComparisonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#semi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemi(SonoParser.SemiContext ctx);
	/**
	 * Visit a parse tree produced by {@link SonoParser#anysemi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnysemi(SonoParser.AnysemiContext ctx);
}