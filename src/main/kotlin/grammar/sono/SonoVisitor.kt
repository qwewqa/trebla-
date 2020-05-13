@file:Suppress("UNCHECKED_CAST")

package xyz.qwewqa.sono.grammar.sono

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
import xyz.qwewqa.sono.grammar.generated.SonoParser
import xyz.qwewqa.sono.grammar.generated.SonoParserBaseVisitor
import java.util.*

class SonoFileVisitor(private val filename: String) : SonoParserBaseVisitor<SonoNode>() {
    override fun visitSonoFile(ctx: SonoParser.SonoFileContext): SonoFileNode {
        return SonoFileNode(
            ctx, filename,
            ctx.packageHeader()?.visit() as PackageHeaderNode?,
            ctx.importList().visit() as ImportListNode,
            ctx.topLevelObject().visit() as List<TopLevelObjectNode>
        )
    }

    override fun visitPackageHeader(ctx: SonoParser.PackageHeaderContext): SonoNode {
        return PackageHeaderNode(ctx, filename, ctx.identifier().visit() as IdentifierNode)
    }

    override fun visitImportList(ctx: SonoParser.ImportListContext): SonoNode {
        return ImportListNode(ctx, filename, ctx.importHeader().visit() as List<ImportHeaderNode>)
    }

    override fun visitImportHeader(ctx: SonoParser.ImportHeaderContext): SonoNode {
        return ImportHeaderNode(ctx, filename, ctx.identifier().visit() as IdentifierNode, ctx.MULT().exist)
    }

    override fun visitFunctionValueParameters(ctx: SonoParser.FunctionValueParametersContext): SonoNode {
        return FunctionValueParametersNode(
            ctx, filename,
            ctx.functionValueParameter().visit() as List<FunctionValueParameterNode>
        )
    }

    override fun visitFunctionValueParameter(ctx: SonoParser.FunctionValueParameterContext): SonoNode {
        return FunctionValueParameterNode(
            ctx, filename,
            ctx.parameter().visit() as ParameterNode,
            ctx.expression()?.visit() as ExpressionNode?
        )
    }

    override fun visitFunctionBody(ctx: SonoParser.FunctionBodyContext): SonoNode {
        return if (ctx.block().exist) {
            ctx.block().visit()
        } else {
            BlockNode(ctx, filename, listOf(ctx.expression().visit() as ExpressionNode))
        }
    }

    override fun visitPropertyDeclaration(ctx: SonoParser.PropertyDeclarationContext): SonoNode {
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

    override fun visitLetDeclaration(ctx: SonoParser.LetDeclarationContext): SonoNode {
        return LetDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.expression().visit() as ExpressionNode,
            ctx.type()?.visit() as TypeNode?
        )
    }

    override fun visitStructDeclaration(ctx: SonoParser.StructDeclarationContext): SonoNode {
        return StructDeclarationNode(
            ctx, filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.structFields().visit() as StructFieldsNode
        )
    }

    override fun visitStructFields(ctx: SonoParser.StructFieldsContext): SonoNode {
        return StructFieldsNode(ctx, filename, ctx.structField().visit() as List<ParameterNode>)
    }

    override fun visitScriptDeclaration(ctx: SonoParser.ScriptDeclarationContext): SonoNode {
        return ScriptDeclarationNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.scriptBody().visit() as ScriptBodyNode
        )
    }

    override fun visitScriptBody(ctx: SonoParser.ScriptBodyContext): SonoNode {
        return ScriptBodyNode(ctx, filename, ctx.scriptMemberDeclaration().map { it.visit() as ScriptMemberNode })
    }

    override fun visitFunctionDeclaration(ctx: SonoParser.FunctionDeclarationContext): SonoNode {
        return FunctionDeclarationNode(
            ctx,
            filename,
            ctx.modifierList().visit() as ModifierListNode,
            ctx.functionReceiver()?.visit() as FunctionReceiverNode?,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.functionValueParameters().visit() as FunctionValueParametersNode,
            ctx.type()?.visit() as TypeNode?,
            ctx.functionBody().visit() as BlockNode,
            ctx.functionBody().ASSIGNMENT().exist
        )
    }

    override fun visitFunctionReceiver(ctx: SonoParser.FunctionReceiverContext): SonoNode {
        return FunctionReceiverNode(
            ctx,
            filename,
            ctx.simpleIdentifier()?.visit() as SimpleIdentifierNode?,
            ctx.type().visit() as TypeNode
        )
    }

    override fun visitArchetypeDeclaration(ctx: SonoParser.ArchetypeDeclarationContext): SonoNode {
        return ArchetypeDeclarationNode(
            ctx, filename,
            ctx.archetypeName().visit() as SimpleIdentifierNode,
            ctx.NOTE().exist,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.archetypeDefaults().visit() as ArchetypeDefaultsNode
        )
    }

    override fun visitArchetypeName(ctx: SonoParser.ArchetypeNameContext): SonoNode {
        return ctx.simpleIdentifier().visit()
    }

    override fun visitArchetypeDefaults(ctx: SonoParser.ArchetypeDefaultsContext): SonoNode {
        return ArchetypeDefaultsNode(ctx, filename, ctx.archetypeDefault().visit() as List<ArchetypeDefaultNode>)
    }

    override fun visitArchetypeDefault(ctx: SonoParser.ArchetypeDefaultContext): SonoNode {
        return ArchetypeDefaultNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.expression().visit() as ExpressionNode
        )
    }

    override fun visitCallbackDeclaration(ctx: SonoParser.CallbackDeclarationContext): SonoNode {
        return CallbackDeclarationNode(
            ctx, filename,
            ctx.callbackOrder()?.visit() as ExpressionNode?,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.functionBody().visit() as BlockNode
        )
    }

    override fun visitInitBlock(ctx: SonoParser.InitBlockContext): SonoNode {
        return InitBlockNode(ctx, filename, ctx.block().visit() as BlockNode)
    }

    override fun visitBlock(ctx: SonoParser.BlockContext): SonoNode {
        return BlockNode(ctx, filename, ctx.statement().visit() as List<StatementNode>)
    }

    override fun visitStatement(ctx: SonoParser.StatementContext): SonoNode {
        return when {
            ctx.expression().exist -> ctx.expression().visit()
            else -> ctx.declaration().visit()
        }
    }

    override fun visitType(ctx: SonoParser.TypeContext): SonoNode {
        return TypeNode(ctx, filename, ctx.expression().visit() as ExpressionNode)
    }

    override fun visitSimpleUserType(ctx: SonoParser.SimpleUserTypeContext): SonoNode {
        return SimpleUserTypeNode(ctx, filename, ctx.simpleIdentifier().visit() as SimpleIdentifierNode)
    }

    override fun visitModifierList(ctx: SonoParser.ModifierListContext): SonoNode {
        return ModifierListNode(ctx, filename, ctx.modifier().visit().map { (it as ModifierNode).value })
    }

    override fun visitModifier(ctx: SonoParser.ModifierContext): SonoNode {
        return ModifierNode(ctx, filename, ctx.text)
    }

    override fun visitParameter(ctx: SonoParser.ParameterContext): SonoNode {
        return ParameterNode(
            ctx, filename,
            ctx.simpleIdentifier().visit() as SimpleIdentifierNode,
            ctx.type()?.visit() as TypeNode?
        )
    }

    override fun visitSimpleIdentifier(ctx: SonoParser.SimpleIdentifierContext): SonoNode {
        return SimpleIdentifierNode(ctx, filename, ctx.text.filter { it != '`' }) // identifiers can be wrapped in ticks
    }

    override fun visitIdentifier(ctx: SonoParser.IdentifierContext): SonoNode {
        return IdentifierNode(ctx, filename, ctx.simpleIdentifier().visit().map { (it as SimpleIdentifierNode).value })
    }

    override fun visitLiteralConstant(ctx: SonoParser.LiteralConstantContext): SonoNode {
        return when {
            ctx.BooleanLiteral().exist -> BooleanLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toBoolean())
            ctx.IntegerLiteral().exist -> NumberLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toDouble())
            ctx.FloatLiteral().exist -> NumberLiteralNode(ctx, filename, ctx.text!!.filter { it != '_' }.toDouble())
            else -> ctx.error()
        }
    }

    override fun visitExpression(ctx: SonoParser.ExpressionContext): SonoNode {
        return visitGenericInfixFunction(ctx, true)
    }

    override fun visitDisjunction(ctx: SonoParser.DisjunctionContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitConjunction(ctx: SonoParser.ConjunctionContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitEqualityComparison(ctx: SonoParser.EqualityComparisonContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitComparison(ctx: SonoParser.ComparisonContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitInfixFunctionCall(ctx: SonoParser.InfixFunctionCallContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitAdditiveExpression(ctx: SonoParser.AdditiveExpressionContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitMultiplicativeExpression(ctx: SonoParser.MultiplicativeExpressionContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitExponentiationExpression(ctx: SonoParser.ExponentiationExpressionContext): SonoNode {
        return visitGenericInfixFunction(ctx)
    }

    override fun visitPrefixUnaryExpression(ctx: SonoParser.PrefixUnaryExpressionContext): SonoNode {
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

    override fun visitPostfixUnaryExpression(ctx: SonoParser.PostfixUnaryExpressionContext): SonoNode {
        val children = ArrayDeque(ctx.children)
        var expr = children.removeFirst().visit() as ExpressionNode
        while (children.isNotEmpty()) {
            expr = UnaryFunctionNode(
                ctx, filename,
                expr,
                (children.removeFirst() as SonoParser.PostfixUnaryOperationContext).let {
                    when {
                        it.callSuffix().exist -> it.callSuffix().visit() as FunctionCallNode
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

    override fun visitIfExpression(ctx: SonoParser.IfExpressionContext): SonoNode {
        val body = ctx.controlStructureBody()
        return IfExpressionNode(
            ctx, filename,
            ctx.expression().visit() as ExpressionNode,
            body[0].visit() as BlockNode,
            (if (body.size == 2) body[1].visit() else null) as BlockNode?
        )
    }

    override fun visitControlStructureBody(ctx: SonoParser.ControlStructureBodyContext): SonoNode {
        return if (ctx.expression().exist) BlockNode(ctx, filename, listOf(ctx.expression().visit() as StatementNode)) else
            ctx.block().visit()
    }

    override fun visitCallSuffix(ctx: SonoParser.CallSuffixContext): SonoNode {
        return FunctionCallNode(ctx, filename, ctx.valueArguments().visit() as ValueArgumentsNode)
    }

    override fun visitValueArguments(ctx: SonoParser.ValueArgumentsContext): SonoNode {
        return ValueArgumentsNode(ctx, filename, ctx.valueArgument().visit() as List<ValueArgumentNode>)
    }

    override fun visitValueArgument(ctx: SonoParser.ValueArgumentContext): SonoNode {
        return ValueArgumentNode(
            ctx, filename,
            ctx.simpleIdentifier()?.visit() as SimpleIdentifierNode?,
            ctx.expression().visit() as ExpressionNode
        )
    }

    private fun visitGenericInfixFunction(ctx: ParserRuleContext, rightAssociative: Boolean = false): SonoNode {
        val children = ArrayDeque(ctx.children)
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

    override fun aggregateResult(aggregate: SonoNode?, nextResult: SonoNode?): SonoNode? {
        return nextResult ?: aggregate
    }

    // should only actually happen if the grammar is updated without updating this file
    private fun ParserRuleContext.error(): Nothing = error("Parse error $text")

    private fun ParseTree.visit() = visit(this)
    private fun List<ParseTree>.visit() = this.map { it.visit() }
    private inline val ParseTree?.exist get() = this != null
}