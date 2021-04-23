// Generated from TreblaParser.g4 by ANTLR 4.8

    package xyz.qwewqa.trebla.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TreblaParser}.
 */
public interface TreblaParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TreblaParser#treblaFile}.
	 * @param ctx the parse tree
	 */
	void enterTreblaFile(TreblaParser.TreblaFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#treblaFile}.
	 * @param ctx the parse tree
	 */
	void exitTreblaFile(TreblaParser.TreblaFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void enterPackageHeader(TreblaParser.PackageHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void exitPackageHeader(TreblaParser.PackageHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(TreblaParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(TreblaParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void enterImportHeader(TreblaParser.ImportHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void exitImportHeader(TreblaParser.ImportHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelObject(TreblaParser.TopLevelObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelObject(TreblaParser.TopLevelObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(TreblaParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(TreblaParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#functionReceiver}.
	 * @param ctx the parse tree
	 */
	void enterFunctionReceiver(TreblaParser.FunctionReceiverContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#functionReceiver}.
	 * @param ctx the parse tree
	 */
	void exitFunctionReceiver(TreblaParser.FunctionReceiverContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameters(TreblaParser.FunctionValueParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameters(TreblaParser.FunctionValueParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(TreblaParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(TreblaParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDeclaration(TreblaParser.PropertyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDeclaration(TreblaParser.PropertyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(TreblaParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(TreblaParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#structTypeParameters}.
	 * @param ctx the parse tree
	 */
	void enterStructTypeParameters(TreblaParser.StructTypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#structTypeParameters}.
	 * @param ctx the parse tree
	 */
	void exitStructTypeParameters(TreblaParser.StructTypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#structFields}.
	 * @param ctx the parse tree
	 */
	void enterStructFields(TreblaParser.StructFieldsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#structFields}.
	 * @param ctx the parse tree
	 */
	void exitStructFields(TreblaParser.StructFieldsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#structField}.
	 * @param ctx the parse tree
	 */
	void enterStructField(TreblaParser.StructFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#structField}.
	 * @param ctx the parse tree
	 */
	void exitStructField(TreblaParser.StructFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(TreblaParser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(TreblaParser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#enumVariants}.
	 * @param ctx the parse tree
	 */
	void enterEnumVariants(TreblaParser.EnumVariantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#enumVariants}.
	 * @param ctx the parse tree
	 */
	void exitEnumVariants(TreblaParser.EnumVariantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#enumVariant}.
	 * @param ctx the parse tree
	 */
	void enterEnumVariant(TreblaParser.EnumVariantContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#enumVariant}.
	 * @param ctx the parse tree
	 */
	void exitEnumVariant(TreblaParser.EnumVariantContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#enumVariantDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumVariantDefinition(TreblaParser.EnumVariantDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#enumVariantDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumVariantDefinition(TreblaParser.EnumVariantDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterScriptDeclaration(TreblaParser.ScriptDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitScriptDeclaration(TreblaParser.ScriptDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#scriptBody}.
	 * @param ctx the parse tree
	 */
	void enterScriptBody(TreblaParser.ScriptBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#scriptBody}.
	 * @param ctx the parse tree
	 */
	void exitScriptBody(TreblaParser.ScriptBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterScriptMemberDeclaration(TreblaParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitScriptMemberDeclaration(TreblaParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDeclaration(TreblaParser.ArchetypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDeclaration(TreblaParser.ArchetypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeName(TreblaParser.ArchetypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeName(TreblaParser.ArchetypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDefaults(TreblaParser.ArchetypeDefaultsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDefaults(TreblaParser.ArchetypeDefaultsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#archetypeDefault}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDefault(TreblaParser.ArchetypeDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#archetypeDefault}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDefault(TreblaParser.ArchetypeDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterCallbackDeclaration(TreblaParser.CallbackDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitCallbackDeclaration(TreblaParser.CallbackDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#callbackOrder}.
	 * @param ctx the parse tree
	 */
	void enterCallbackOrder(TreblaParser.CallbackOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#callbackOrder}.
	 * @param ctx the parse tree
	 */
	void exitCallbackOrder(TreblaParser.CallbackOrderContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#letDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLetDeclaration(TreblaParser.LetDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#letDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLetDeclaration(TreblaParser.LetDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(TreblaParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(TreblaParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(TreblaParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(TreblaParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(TreblaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(TreblaParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TreblaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TreblaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TreblaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TreblaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(TreblaParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(TreblaParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(TreblaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(TreblaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpression(TreblaParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpression(TreblaParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryExpression(TreblaParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryExpression(TreblaParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomicExpression(TreblaParser.AtomicExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomicExpression(TreblaParser.AtomicExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(TreblaParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(TreblaParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void enterCallSuffix(TreblaParser.CallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void exitCallSuffix(TreblaParser.CallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#subscriptingSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptingSuffix(TreblaParser.SubscriptingSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#subscriptingSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptingSuffix(TreblaParser.SubscriptingSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#lambda}.
	 * @param ctx the parse tree
	 */
	void enterLambda(TreblaParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#lambda}.
	 * @param ctx the parse tree
	 */
	void exitLambda(TreblaParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void enterValueArguments(TreblaParser.ValueArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void exitValueArguments(TreblaParser.ValueArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void enterValueArgument(TreblaParser.ValueArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void exitValueArgument(TreblaParser.ValueArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(TreblaParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(TreblaParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(TreblaParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(TreblaParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhenExpression(TreblaParser.WhenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhenExpression(TreblaParser.WhenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenMatchExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhenMatchExpression(TreblaParser.WhenMatchExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenMatchExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhenMatchExpression(TreblaParser.WhenMatchExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenEntry(TreblaParser.WhenEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenEntry(TreblaParser.WhenEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenMatchEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenMatchEntry(TreblaParser.WhenMatchEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenMatchEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenMatchEntry(TreblaParser.WhenMatchEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenConditionalEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenConditionalEntry(TreblaParser.WhenConditionalEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenConditionalEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenConditionalEntry(TreblaParser.WhenConditionalEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenElseEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenElseEntry(TreblaParser.WhenElseEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenElseEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenElseEntry(TreblaParser.WhenElseEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whenMatchVariantEntry}.
	 * @param ctx the parse tree
	 */
	void enterWhenMatchVariantEntry(TreblaParser.WhenMatchVariantEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whenMatchVariantEntry}.
	 * @param ctx the parse tree
	 */
	void exitWhenMatchVariantEntry(TreblaParser.WhenMatchVariantEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#destructuringTuple}.
	 * @param ctx the parse tree
	 */
	void enterDestructuringTuple(TreblaParser.DestructuringTupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#destructuringTuple}.
	 * @param ctx the parse tree
	 */
	void exitDestructuringTuple(TreblaParser.DestructuringTupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTryExpression(TreblaParser.TryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#tryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTryExpression(TreblaParser.TryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void enterLoopExpression(TreblaParser.LoopExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void exitLoopExpression(TreblaParser.LoopExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhileExpression(TreblaParser.WhileExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhileExpression(TreblaParser.WhileExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void enterForExpression(TreblaParser.ForExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void exitForExpression(TreblaParser.ForExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#forInitializer}.
	 * @param ctx the parse tree
	 */
	void enterForInitializer(TreblaParser.ForInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#forInitializer}.
	 * @param ctx the parse tree
	 */
	void exitForInitializer(TreblaParser.ForInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void enterForCondition(TreblaParser.ForConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void exitForCondition(TreblaParser.ForConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#forAfterthought}.
	 * @param ctx the parse tree
	 */
	void enterForAfterthought(TreblaParser.ForAfterthoughtContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#forAfterthought}.
	 * @param ctx the parse tree
	 */
	void exitForAfterthought(TreblaParser.ForAfterthoughtContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void enterControlStructureBody(TreblaParser.ControlStructureBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void exitControlStructureBody(TreblaParser.ControlStructureBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#modifierList}.
	 * @param ctx the parse tree
	 */
	void enterModifierList(TreblaParser.ModifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#modifierList}.
	 * @param ctx the parse tree
	 */
	void exitModifierList(TreblaParser.ModifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(TreblaParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(TreblaParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(TreblaParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(TreblaParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleIdentifier(TreblaParser.SimpleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleIdentifier(TreblaParser.SimpleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstant(TreblaParser.LiteralConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstant(TreblaParser.LiteralConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLineStringLiteral(TreblaParser.LineStringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#lineStringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLineStringLiteral(TreblaParser.LineStringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryOperation(TreblaParser.PrefixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryOperation(TreblaParser.PrefixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryOperation(TreblaParser.PostfixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryOperation(TreblaParser.PostfixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessOperator(TreblaParser.MemberAccessOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessOperator(TreblaParser.MemberAccessOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(TreblaParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(TreblaParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#semi}.
	 * @param ctx the parse tree
	 */
	void enterSemi(TreblaParser.SemiContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#semi}.
	 * @param ctx the parse tree
	 */
	void exitSemi(TreblaParser.SemiContext ctx);
	/**
	 * Enter a parse tree produced by {@link TreblaParser#anysemi}.
	 * @param ctx the parse tree
	 */
	void enterAnysemi(TreblaParser.AnysemiContext ctx);
	/**
	 * Exit a parse tree produced by {@link TreblaParser#anysemi}.
	 * @param ctx the parse tree
	 */
	void exitAnysemi(TreblaParser.AnysemiContext ctx);
}