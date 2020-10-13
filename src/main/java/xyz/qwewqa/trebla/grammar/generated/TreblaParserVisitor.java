// Generated from TreblaParser.g4 by ANTLR 4.8

    package xyz.qwewqa.trebla.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TreblaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TreblaParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TreblaParser#treblaFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTreblaFile(TreblaParser.TreblaFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#packageHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageHeader(TreblaParser.PackageHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#importList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportList(TreblaParser.ImportListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#importHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportHeader(TreblaParser.ImportHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#topLevelObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopLevelObject(TreblaParser.TopLevelObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(TreblaParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#functionReceiver}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionReceiver(TreblaParser.FunctionReceiverContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#functionValueParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionValueParameters(TreblaParser.FunctionValueParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(TreblaParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyDeclaration(TreblaParser.PropertyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(TreblaParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#structTypeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructTypeParameters(TreblaParser.StructTypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#structFields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructFields(TreblaParser.StructFieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#structField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructField(TreblaParser.StructFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(TreblaParser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#enumVariants}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumVariants(TreblaParser.EnumVariantsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#enumVariant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumVariant(TreblaParser.EnumVariantContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#enumVariantDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumVariantDefinition(TreblaParser.EnumVariantDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptDeclaration(TreblaParser.ScriptDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#scriptBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptBody(TreblaParser.ScriptBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptMemberDeclaration(TreblaParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDeclaration(TreblaParser.ArchetypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#archetypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeName(TreblaParser.ArchetypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDefaults(TreblaParser.ArchetypeDefaultsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#archetypeDefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeDefault(TreblaParser.ArchetypeDefaultContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallbackDeclaration(TreblaParser.CallbackDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#callbackOrder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallbackOrder(TreblaParser.CallbackOrderContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#letDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetDeclaration(TreblaParser.LetDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#initBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitBlock(TreblaParser.InitBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(TreblaParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(TreblaParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#parenthesizedType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedType(TreblaParser.ParenthesizedTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#simpleUserType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleUserType(TreblaParser.SimpleUserTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TreblaParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(TreblaParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(TreblaParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(TreblaParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#disjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjunction(TreblaParser.DisjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(TreblaParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#equalityComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityComparison(TreblaParser.EqualityComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(TreblaParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfixFunctionCall(TreblaParser.InfixFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(TreblaParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(TreblaParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#exponentiationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExponentiationExpression(TreblaParser.ExponentiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryExpression(TreblaParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryExpression(TreblaParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#atomicExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomicExpression(TreblaParser.AtomicExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(TreblaParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#callSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallSuffix(TreblaParser.CallSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#subscriptingSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptingSuffix(TreblaParser.SubscriptingSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#lambda}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambda(TreblaParser.LambdaContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#valueArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArguments(TreblaParser.ValueArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#valueArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueArgument(TreblaParser.ValueArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(TreblaParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#ifExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpression(TreblaParser.IfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whenExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenExpression(TreblaParser.WhenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whenEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenEntry(TreblaParser.WhenEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whenElseEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenElseEntry(TreblaParser.WhenElseEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whenMatchExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenMatchExpression(TreblaParser.WhenMatchExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whenMatchEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhenMatchEntry(TreblaParser.WhenMatchEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#destructuringTuple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDestructuringTuple(TreblaParser.DestructuringTupleContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#tryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryExpression(TreblaParser.TryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#loopExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopExpression(TreblaParser.LoopExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#whileExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExpression(TreblaParser.WhileExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#forExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForExpression(TreblaParser.ForExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#forInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInitializer(TreblaParser.ForInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#forCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCondition(TreblaParser.ForConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#forAfterthought}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForAfterthought(TreblaParser.ForAfterthoughtContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#controlStructureBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStructureBody(TreblaParser.ControlStructureBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#modifierList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifierList(TreblaParser.ModifierListContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(TreblaParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(TreblaParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleIdentifier(TreblaParser.SimpleIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#literalConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralConstant(TreblaParser.LiteralConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineStringLiteral(TreblaParser.LineStringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#additiveOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveOperator(TreblaParser.AdditiveOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeOperation(TreblaParser.MultiplicativeOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryOperation(TreblaParser.PrefixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixUnaryOperation(TreblaParser.PostfixUnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessOperator(TreblaParser.MemberAccessOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(TreblaParser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#equalityOperation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityOperation(TreblaParser.EqualityOperationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#comparisonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperator(TreblaParser.ComparisonOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#semi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemi(TreblaParser.SemiContext ctx);
	/**
	 * Visit a parse tree produced by {@link TreblaParser#anysemi}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnysemi(TreblaParser.AnysemiContext ctx);
}