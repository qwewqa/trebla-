// Generated from SonoParser.g4 by ANTLR 4.8

    package xyz.qwewqa.sono.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SonoParser}.
 */
public interface SonoParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SonoParser#sonoFile}.
	 * @param ctx the parse tree
	 */
	void enterSonoFile(SonoParser.SonoFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#sonoFile}.
	 * @param ctx the parse tree
	 */
	void exitSonoFile(SonoParser.SonoFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void enterPackageHeader(SonoParser.PackageHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#packageHeader}.
	 * @param ctx the parse tree
	 */
	void exitPackageHeader(SonoParser.PackageHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#importList}.
	 * @param ctx the parse tree
	 */
	void enterImportList(SonoParser.ImportListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#importList}.
	 * @param ctx the parse tree
	 */
	void exitImportList(SonoParser.ImportListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void enterImportHeader(SonoParser.ImportHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#importHeader}.
	 * @param ctx the parse tree
	 */
	void exitImportHeader(SonoParser.ImportHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelObject(SonoParser.TopLevelObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#topLevelObject}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelObject(SonoParser.TopLevelObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(SonoParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(SonoParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#functionReceiver}.
	 * @param ctx the parse tree
	 */
	void enterFunctionReceiver(SonoParser.FunctionReceiverContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#functionReceiver}.
	 * @param ctx the parse tree
	 */
	void exitFunctionReceiver(SonoParser.FunctionReceiverContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameters(SonoParser.FunctionValueParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#functionValueParameters}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameters(SonoParser.FunctionValueParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#functionValueParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValueParameter(SonoParser.FunctionValueParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#functionValueParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValueParameter(SonoParser.FunctionValueParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(SonoParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(SonoParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPropertyDeclaration(SonoParser.PropertyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#propertyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPropertyDeclaration(SonoParser.PropertyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(SonoParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(SonoParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#structFields}.
	 * @param ctx the parse tree
	 */
	void enterStructFields(SonoParser.StructFieldsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#structFields}.
	 * @param ctx the parse tree
	 */
	void exitStructFields(SonoParser.StructFieldsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#structField}.
	 * @param ctx the parse tree
	 */
	void enterStructField(SonoParser.StructFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#structField}.
	 * @param ctx the parse tree
	 */
	void exitStructField(SonoParser.StructFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterScriptDeclaration(SonoParser.ScriptDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#scriptDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitScriptDeclaration(SonoParser.ScriptDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#scriptBody}.
	 * @param ctx the parse tree
	 */
	void enterScriptBody(SonoParser.ScriptBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#scriptBody}.
	 * @param ctx the parse tree
	 */
	void exitScriptBody(SonoParser.ScriptBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterScriptMemberDeclaration(SonoParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#scriptMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitScriptMemberDeclaration(SonoParser.ScriptMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDeclaration(SonoParser.ArchetypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#archetypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDeclaration(SonoParser.ArchetypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeName(SonoParser.ArchetypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeName(SonoParser.ArchetypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDefaults(SonoParser.ArchetypeDefaultsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#archetypeDefaults}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDefaults(SonoParser.ArchetypeDefaultsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#archetypeDefault}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeDefault(SonoParser.ArchetypeDefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#archetypeDefault}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeDefault(SonoParser.ArchetypeDefaultContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterCallbackDeclaration(SonoParser.CallbackDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#callbackDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitCallbackDeclaration(SonoParser.CallbackDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#callbackOrder}.
	 * @param ctx the parse tree
	 */
	void enterCallbackOrder(SonoParser.CallbackOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#callbackOrder}.
	 * @param ctx the parse tree
	 */
	void exitCallbackOrder(SonoParser.CallbackOrderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#letDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLetDeclaration(SonoParser.LetDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#letDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLetDeclaration(SonoParser.LetDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#initBlock}.
	 * @param ctx the parse tree
	 */
	void enterInitBlock(SonoParser.InitBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#initBlock}.
	 * @param ctx the parse tree
	 */
	void exitInitBlock(SonoParser.InitBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(SonoParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(SonoParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SonoParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SonoParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#parenthesizedType}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedType(SonoParser.ParenthesizedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#parenthesizedType}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedType(SonoParser.ParenthesizedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleUserType(SonoParser.SimpleUserTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#simpleUserType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleUserType(SonoParser.SimpleUserTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SonoParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SonoParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SonoParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SonoParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(SonoParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(SonoParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SonoParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SonoParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(SonoParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(SonoParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(SonoParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(SonoParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#equalityComparison}.
	 * @param ctx the parse tree
	 */
	void enterEqualityComparison(SonoParser.EqualityComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#equalityComparison}.
	 * @param ctx the parse tree
	 */
	void exitEqualityComparison(SonoParser.EqualityComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(SonoParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(SonoParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterInfixFunctionCall(SonoParser.InfixFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#infixFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitInfixFunctionCall(SonoParser.InfixFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(SonoParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(SonoParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(SonoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(SonoParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#exponentiationExpression}.
	 * @param ctx the parse tree
	 */
	void enterExponentiationExpression(SonoParser.ExponentiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#exponentiationExpression}.
	 * @param ctx the parse tree
	 */
	void exitExponentiationExpression(SonoParser.ExponentiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpression(SonoParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#prefixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpression(SonoParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryExpression(SonoParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#postfixUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryExpression(SonoParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomicExpression(SonoParser.AtomicExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#atomicExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomicExpression(SonoParser.AtomicExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(SonoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(SonoParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void enterCallSuffix(SonoParser.CallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void exitCallSuffix(SonoParser.CallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void enterValueArguments(SonoParser.ValueArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#valueArguments}.
	 * @param ctx the parse tree
	 */
	void exitValueArguments(SonoParser.ValueArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void enterValueArgument(SonoParser.ValueArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#valueArgument}.
	 * @param ctx the parse tree
	 */
	void exitValueArgument(SonoParser.ValueArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(SonoParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(SonoParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void enterIfExpression(SonoParser.IfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#ifExpression}.
	 * @param ctx the parse tree
	 */
	void exitIfExpression(SonoParser.IfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void enterLoopExpression(SonoParser.LoopExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void exitLoopExpression(SonoParser.LoopExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void enterWhileExpression(SonoParser.WhileExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#whileExpression}.
	 * @param ctx the parse tree
	 */
	void exitWhileExpression(SonoParser.WhileExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void enterControlStructureBody(SonoParser.ControlStructureBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#controlStructureBody}.
	 * @param ctx the parse tree
	 */
	void exitControlStructureBody(SonoParser.ControlStructureBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#modifierList}.
	 * @param ctx the parse tree
	 */
	void enterModifierList(SonoParser.ModifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#modifierList}.
	 * @param ctx the parse tree
	 */
	void exitModifierList(SonoParser.ModifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(SonoParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(SonoParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SonoParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SonoParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleIdentifier(SonoParser.SimpleIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#simpleIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleIdentifier(SonoParser.SimpleIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void enterLiteralConstant(SonoParser.LiteralConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#literalConstant}.
	 * @param ctx the parse tree
	 */
	void exitLiteralConstant(SonoParser.LiteralConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveOperator(SonoParser.AdditiveOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#additiveOperator}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveOperator(SonoParser.AdditiveOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeOperation(SonoParser.MultiplicativeOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#multiplicativeOperation}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeOperation(SonoParser.MultiplicativeOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryOperation(SonoParser.PrefixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#prefixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryOperation(SonoParser.PrefixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryOperation(SonoParser.PostfixUnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#postfixUnaryOperation}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryOperation(SonoParser.PostfixUnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessOperator(SonoParser.MemberAccessOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#memberAccessOperator}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessOperator(SonoParser.MemberAccessOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(SonoParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(SonoParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#equalityOperation}.
	 * @param ctx the parse tree
	 */
	void enterEqualityOperation(SonoParser.EqualityOperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#equalityOperation}.
	 * @param ctx the parse tree
	 */
	void exitEqualityOperation(SonoParser.EqualityOperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(SonoParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(SonoParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#semi}.
	 * @param ctx the parse tree
	 */
	void enterSemi(SonoParser.SemiContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#semi}.
	 * @param ctx the parse tree
	 */
	void exitSemi(SonoParser.SemiContext ctx);
	/**
	 * Enter a parse tree produced by {@link SonoParser#anysemi}.
	 * @param ctx the parse tree
	 */
	void enterAnysemi(SonoParser.AnysemiContext ctx);
	/**
	 * Exit a parse tree produced by {@link SonoParser#anysemi}.
	 * @param ctx the parse tree
	 */
	void exitAnysemi(SonoParser.AnysemiContext ctx);
}