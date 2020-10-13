@file:Suppress("UNCHECKED_CAST")

package xyz.qwewqa.trebla.grammar.trebla

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import xyz.qwewqa.trebla.grammar.generated.TreblaParser
import xyz.qwewqa.trebla.grammar.generated.TreblaParserBaseVisitor
import java.util.*

class TreblaFileVisitor(private val filename: String) : TreblaParserBaseVisitor<TreblaNode>() {
    override fun visitTreblaFile(ctx: TreblaParser.TreblaFileContext): TreblaFileNode {
        return TreblaFileNode(
            ctx, filename,
            ctx.packageHeader()?.visit() as PackageHeaderNode?,
            ctx.importList().visit() as ImportListNode,
            ctx.topLevelObject().visit() as List<TopLevelObjectNode>
        )
    }

    override fun visitPackageHeader(ctx: TreblaParser.PackageHeaderContext): TreblaNode {
        return PackageHeaderNode(ctx, filename, ctx.identifier().visit() as IdentifierNode)
    }

    override fun visitImportList(ctx: TreblaParser.ImportListContext): TreblaNode {
        return ImportListNode(ctx, filename, ctx.importHeader().visit() as List<ImportHeaderNode>)
    }

    override fun visitImportHeader(ctx: TreblaParser.ImportHeaderContext): TreblaNode {
        return ImportHeaderNode(ctx, filename, ctx.identifier().visit() as IdentifierNode)
    }

    override fun visitFunctionValueParameters(ctx: TreblaParser.FunctionValueParametersContext): TreblaNode {
        return ParametersNode(
            ctx, filename,
            ctx.parameter().visit() as List<ParameterNode>
        )
    }

    override fun visitFunctionBody(ctx: TreblaParser.FunctionBodyContext): TreblaNode {
        return if (ctx.block().exist) {
            ctx.block().visit()
        } else {
            BlockNode(ctx, filename, listOf(ctx.expression().visit() as ExpressionNode))
        }
    }

    override fun visitPropertyDeclaration(ctx: TreblaParser.PropertyDeclarationContext): TreblaNode {
        return PropertyDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            when {
                // val existed at some point; possible that it might come back eventually
                ctx.VAR().exist -> "var"
                else -> error("Should not happen.")
            },
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.expression()?.visit() as ExpressionNode?,
            ctx.type()?.visit() as TypeNode?
        )
    }

    override fun visitLetDeclaration(ctx: TreblaParser.LetDeclarationContext): TreblaNode {
        return LetDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.expression().visit() as ExpressionNode,
            ctx.type()?.visit() as TypeNode?
        )
    }

    override fun visitStructDeclaration(ctx: TreblaParser.StructDeclarationContext): TreblaNode {
        return StructDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.structTypeParameters().visit() as ParametersNode,
            ctx.structFields().structField().visit() as List<StructFieldNode>,
        )
    }

    override fun visitStructField(ctx: TreblaParser.StructFieldContext): TreblaNode {
        return StructFieldNode(ctx, filename, ctx.parameter().visit() as ParameterNode, ctx.EMBED().exist)
    }

    override fun visitStructTypeParameters(ctx: TreblaParser.StructTypeParametersContext): TreblaNode {
        return ParametersNode(ctx, filename, ctx.parameter().visit() as List<ParameterNode>)
    }

    override fun visitEnumDeclaration(ctx: TreblaParser.EnumDeclarationContext): TreblaNode {
        return EnumDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.enumVariants().enumVariant().visit() as List<EnumVariantNode>,
        )
    }

    override fun visitEnumVariant(ctx: TreblaParser.EnumVariantContext): TreblaNode {
        return EnumVariantNode(
            ctx,
            filename,
            ctx.enumVariantDefinition().visit() as EnumVariantDefinitionNode,
            ctx.IntegerLiteral()?.text?.filter { it != '_' }?.toInt()
        )
    }

    override fun visitEnumVariantDefinition(ctx: TreblaParser.EnumVariantDefinitionContext): TreblaNode {
        return if (ctx.structFields().LPAREN().exist) {
            EnumStructVariantNode(
                ctx, filename,
                ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
                ctx.structFields().structField().visit() as List<StructFieldNode>,
            )
        } else {
            EnumUnitVariantNode(
                ctx, filename,
                ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            )
        }
    }

    override fun visitScriptDeclaration(ctx: TreblaParser.ScriptDeclarationContext): TreblaNode {
        return ScriptDeclarationNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.scriptBody().visit() as ScriptBodyNode
        )
    }

    override fun visitScriptBody(ctx: TreblaParser.ScriptBodyContext): TreblaNode {
        return ScriptBodyNode(ctx, filename, ctx.scriptMemberDeclaration().map { it.visit() as ScriptMemberNode })
    }

    override fun visitFunctionDeclaration(ctx: TreblaParser.FunctionDeclarationContext): TreblaNode {
        return FunctionDeclarationNode(
            ctx,
            filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.functionReceiver()?.visit() as FunctionReceiverNode?,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.functionValueParameters().visit() as ParametersNode,
            ctx.type()?.visit() as TypeNode?,
            ctx.functionBody().visit() as BlockNode,
            ctx.functionBody().ASSIGNMENT().exist
        )
    }

    override fun visitFunctionReceiver(ctx: TreblaParser.FunctionReceiverContext): TreblaNode {
        return FunctionReceiverNode(
            ctx,
            filename,
            ctx.simpleIdentifier()?.visit() as SimpleIdentifierNode?,
            ctx.type().visit() as TypeNode
        )
    }

    override fun visitArchetypeDeclaration(ctx: TreblaParser.ArchetypeDeclarationContext): TreblaNode {
        return ArchetypeDeclarationNode(
            ctx, filename,
            ctx.archetypeName().visit() as SimpleIdentifierNode,
            ctx.NOTE().exist,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.archetypeDefaults().visit() as ArchetypeDefaultsNode
        )
    }

    override fun visitArchetypeName(ctx: TreblaParser.ArchetypeNameContext): TreblaNode {
        return ctx.simpleIdentifier().visit()
    }

    override fun visitArchetypeDefaults(ctx: TreblaParser.ArchetypeDefaultsContext): TreblaNode {
        return ArchetypeDefaultsNode(ctx, filename, ctx.archetypeDefault().visit() as List<ArchetypeDefaultNode>)
    }

    override fun visitArchetypeDefault(ctx: TreblaParser.ArchetypeDefaultContext): TreblaNode {
        return ArchetypeDefaultNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.expression().visit() as ExpressionNode
        )
    }

    override fun visitCallbackDeclaration(ctx: TreblaParser.CallbackDeclarationContext): TreblaNode {
        return CallbackDeclarationNode(
            ctx, filename,
            ctx.callbackOrder()?.visit() as ExpressionNode?,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.functionBody().visit() as BlockNode
        )
    }

    override fun visitInitBlock(ctx: TreblaParser.InitBlockContext): TreblaNode {
        return InitBlockNode(ctx, filename, ctx.block().visit() as BlockNode)
    }

    override fun visitBlock(ctx: TreblaParser.BlockContext): TreblaNode {
        return BlockNode(ctx, filename, ctx.statement().visit() as List<StatementNode>)
    }

    override fun visitStatement(ctx: TreblaParser.StatementContext): TreblaNode {
        return when {
            ctx.expression().exist -> ctx.expression().visit()
            else -> ctx.declaration().visit()
        }
    }

    override fun visitType(ctx: TreblaParser.TypeContext): TreblaNode {
        return TypeNode(ctx, filename, ctx.prefixUnaryExpression().visit() as ExpressionNode)
    }

    override fun visitSimpleUserType(ctx: TreblaParser.SimpleUserTypeContext): TreblaNode {
        return SimpleUserTypeNode(ctx, filename, ctx.simpleIdentifier().visit() as SimpleIdentifierNode)
    }

    override fun visitModifierList(ctx: TreblaParser.ModifierListContext): TreblaNode {
        return ModifierListNode(ctx, filename, ctx.modifier().visit().map { (it as ModifierNode).value })
    }

    override fun visitModifier(ctx: TreblaParser.ModifierContext): TreblaNode {
        return ModifierNode(ctx, filename, ctx.text)
    }

    override fun visitParameter(ctx: TreblaParser.ParameterContext): TreblaNode {
        return ParameterNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.type()?.visit() as TypeNode?,
            ctx.expression()?.visit() as ExpressionNode?
        )
    }

    override fun visitSimpleIdentifier(ctx: TreblaParser.SimpleIdentifierContext): TreblaNode {
        return SimpleIdentifierNode(ctx, filename, ctx.text.filter { it != '`' }) // identifiers can be wrapped in ticks
    }

    override fun visitIdentifier(ctx: TreblaParser.IdentifierContext): TreblaNode {
        return IdentifierNode(ctx, filename, ctx.simpleIdentifier().visit().map { (it as SimpleIdentifierNode).value })
    }

    override fun visitLiteralConstant(ctx: TreblaParser.LiteralConstantContext): TreblaNode {
        return when {
            ctx.BooleanLiteral().exist -> BooleanLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toBoolean())
            ctx.IntegerLiteral().exist -> NumberLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toDouble())
            ctx.FloatLiteral().exist -> NumberLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toDouble())
            else -> ctx.error()
        }
    }

    override fun visitLineStringLiteral(ctx: TreblaParser.LineStringLiteralContext): TreblaNode {
        return StringLiteralNode(
            ctx, filename,
            ctx.children.drop(1).dropLast(1).map {
                if (it.text[0] == '\\') StringEscape(ctx, filename, it.text[1])
                else StringText(ctx, filename, it.text)
            }
        )
    }

    override fun visitExpression(ctx: TreblaParser.ExpressionContext): TreblaNode {
        return visitGenericInfixFunction(ctx, true)
    }

    override fun visitDisjunction(ctx: TreblaParser.DisjunctionContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitConjunction(ctx: TreblaParser.ConjunctionContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitEqualityComparison(ctx: TreblaParser.EqualityComparisonContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitComparison(ctx: TreblaParser.ComparisonContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitInfixFunctionCall(ctx: TreblaParser.InfixFunctionCallContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitAdditiveExpression(ctx: TreblaParser.AdditiveExpressionContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitMultiplicativeExpression(ctx: TreblaParser.MultiplicativeExpressionContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitExponentiationExpression(ctx: TreblaParser.ExponentiationExpressionContext): TreblaNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitPrefixUnaryExpression(ctx: TreblaParser.PrefixUnaryExpressionContext): TreblaNode {
        val children = ArrayDeque(ctx.children)
        var expr = children.removeLast().visit() as ExpressionNode
        while (children.isNotEmpty()) {
            expr = UnaryFunctionNode(
                ctx,
                filename,
                expr,
                PrefixUnaryFunctionNode(ctx, filename, children.removeLast().text)
            )
        }
        return expr
    }

    override fun visitPostfixUnaryExpression(ctx: TreblaParser.PostfixUnaryExpressionContext): TreblaNode {
        val children = ArrayDeque(ctx.children)
        var expr = children.removeFirst().visit() as ExpressionNode
        while (children.isNotEmpty()) {
            expr = UnaryFunctionNode(
                ctx, filename,
                expr,
                (children.removeFirst() as TreblaParser.PostfixUnaryOperationContext).let {
                    when {
                        it.callSuffix().exist -> it.callSuffix().visit() as FunctionCallNode
                        it.subscriptingSuffix().exist -> it.subscriptingSuffix().visit() as SubscriptNode
                        it.simpleIdentifier().exist -> MemberAccessNode(ctx,
                            filename,
                            it.simpleIdentifier().visit() as SimpleIdentifierNode)
                        else -> PostfixUnaryFunctionNode(ctx, filename, it.text)
                    }
                }
            )
        }
        return expr
    }

    override fun visitSubscriptingSuffix(ctx: TreblaParser.SubscriptingSuffixContext): TreblaNode {
        return SubscriptNode(ctx, filename, ctx.valueArgument().visit() as List<ValueArgumentNode>)
    }

    override fun visitIfExpression(ctx: TreblaParser.IfExpressionContext): TreblaNode {
        val body = ctx.controlStructureBody()
        return IfExpressionNode(
            ctx, filename,
            ctx.CONST().exist,
            ctx.expression().visit() as ExpressionNode,
            body[0].visit() as BlockNode,
            (if (body.size == 2) body[1].visit() else null) as BlockNode?
        )
    }

    override fun visitWhenExpression(ctx: TreblaParser.WhenExpressionContext): TreblaNode {
        return WhenExpressionNode(
            ctx, filename,
            ctx.CONST().exist,
            ctx.whenEntry().visit() as List<WhenEntryNode>,
        )
    }

    override fun visitWhenMatchExpression(ctx: TreblaParser.WhenMatchExpressionContext): TreblaNode {
        return WhenMatchExpressionNode(
            ctx, filename,
            ctx.expression().visit() as ExpressionNode,
            ctx.whenMatchEntry().visit() as List<WhenMatchEntryNode>,
        )
    }

    override fun visitWhenEntry(ctx: TreblaParser.WhenEntryContext): TreblaNode {
        return when {
            ctx.whenConditionalEntry().exist -> ctx.whenConditionalEntry()!!.visit()
            ctx.whenElseEntry().exist -> ctx.whenElseEntry()!!.visit()
            else -> error("Unexpected when entry.")
        }
    }

    override fun visitWhenMatchEntry(ctx: TreblaParser.WhenMatchEntryContext): TreblaNode {
        return when {
            ctx.whenMatchVariantEntry().exist -> ctx.whenMatchVariantEntry()!!.visit()
            ctx.whenElseEntry().exist -> ctx.whenElseEntry()!!.visit()
            else -> error("Unexpected when entry.")
        }
    }

    override fun visitWhenConditionalEntry(ctx: TreblaParser.WhenConditionalEntryContext): TreblaNode {
        return WhenConditionalEntryNode(
            ctx, filename,
            ctx.expression().visit() as ExpressionNode,
            ctx.controlStructureBody().visit() as BlockNode
        )
    }

    override fun visitWhenMatchVariantEntry(ctx: TreblaParser.WhenMatchVariantEntryContext): TreblaNode {
        return WhenMatchVariantNode(
            ctx, filename,
            ctx.expression().visit() as ExpressionNode,
            ctx.destructuringTuple()?.visit() as DestructuringTupleNode?,
            ctx.controlStructureBody().visit() as BlockNode,
        )
    }

    override fun visitWhenElseEntry(ctx: TreblaParser.WhenElseEntryContext): TreblaNode {
        return WhenElseEntryNode(
            ctx, filename,
            ctx.controlStructureBody().visit() as BlockNode
        )
    }

    override fun visitTryExpression(ctx: TreblaParser.TryExpressionContext): TreblaNode {
        val body = ctx.controlStructureBody()
        return TryExpressionNode(
            ctx, filename,
            body[0].visit() as BlockNode,
            body[1].visit() as BlockNode
        )
    }

    override fun visitWhileExpression(ctx: TreblaParser.WhileExpressionContext): TreblaNode {
        return WhileExpressionNode(
            ctx, filename,
            ctx.expression().visit() as ExpressionNode,
            ctx.controlStructureBody().visit() as BlockNode,
        )
    }

    override fun visitForExpression(ctx: TreblaParser.ForExpressionContext): TreblaNode {
        return ForExpressionNode(
            ctx, filename,
            ctx.forInitializer().visit() as StatementNode?,
            ctx.forCondition().visit() as ExpressionNode?,
            ctx.forAfterthought().visit() as ExpressionNode?,
            ctx.controlStructureBody().visit() as BlockNode
        )
    }

    override fun visitControlStructureBody(ctx: TreblaParser.ControlStructureBodyContext): TreblaNode {
        return if (ctx.expression().exist) {
            BlockNode(ctx,
                filename,
                listOf(ctx.expression().visit() as StatementNode))
        } else ctx.block().visit()
    }

    override fun visitCallSuffix(ctx: TreblaParser.CallSuffixContext): TreblaNode {
        return FunctionCallNode(ctx, filename, ctx.valueArguments().visit() as ValueArgumentsNode)
    }

    override fun visitValueArguments(ctx: TreblaParser.ValueArgumentsContext): TreblaNode {
        return ValueArgumentsNode(
            ctx, filename,
            ctx.valueArgument().visit() as List<ValueArgumentNode>,
            ctx.lambda()?.visit() as LambdaNode?,
        )
    }

    override fun visitLambda(ctx: TreblaParser.LambdaContext): TreblaNode {
        return LambdaNode(
            ctx,
            filename,
            ctx.functionValueParameters()?.visit() as ParametersNode?,
            ctx.statement().visit() as List<StatementNode>
        )
    }

    override fun visitValueArgument(ctx: TreblaParser.ValueArgumentContext): TreblaNode {
        return ValueArgumentNode(
            ctx, filename,
            ctx.simpleIdentifier()?.visit() as SimpleIdentifierNode?,
            ctx.expression().visit() as ExpressionNode
        )
    }

    override fun visitDestructuringTuple(ctx: TreblaParser.DestructuringTupleContext): TreblaNode {
        return DestructuringTupleNode(ctx,
            filename,
            ctx.simpleIdentifier().visit().map { (it as SimpleIdentifierNode).value })
    }

    private fun visitGenericInfixFunction(ctx: ParserRuleContext, rightAssociative: Boolean = false): TreblaNode {
        // Filter stuff like NL
        val children = ArrayDeque(ctx.children.filter { it !is TerminalNode || it.text.isNotBlank() })
        return if (rightAssociative) {
            var expr = children.removeLast().visit() as ExpressionNode
            while (children.isNotEmpty()) {
                val op = children.removeLast().text
                val lhs = children.removeLast().visit() as ExpressionNode
                expr = InfixFunctionNode(ctx, filename, lhs, expr, op)
            }
            expr
        } else {
            var expr = children.removeFirst().visit() as ExpressionNode
            while (children.isNotEmpty()) {
                val op = children.removeFirst().text
                val rhs = children.removeFirst().visit() as ExpressionNode
                expr = InfixFunctionNode(ctx, filename, expr, rhs, op)
            }
            expr
        }
    }

    override fun aggregateResult(aggregate: TreblaNode?, nextResult: TreblaNode?): TreblaNode? {
        return nextResult ?: aggregate
    }

    // should only actually happen if the grammar is updated without updating this file
    private fun ParserRuleContext.error(): Nothing = error("Parse error $text")

    private fun ParseTree.visit() = visit(this)
    private fun List<ParseTree>.visit() = this.map { it.visit() }
    private inline val ParseTree?.exist get() = this != null
}
