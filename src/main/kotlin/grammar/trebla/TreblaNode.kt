package xyz.qwewqa.trebla.grammar.trebla

import org.antlr.v4.runtime.tree.ParseTree
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.context.Context
import xyz.qwewqa.trebla.frontend.context.getFullyQualified
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.StringValue
import xyz.qwewqa.trebla.frontend.expression.*

/**
 * This contains the original context for use in error messages as well as the original filename,
 * since it can't be obtained from the context itself.
 */
interface TreblaNode {
    val context: ParseTree
    val filename: String
}

data class TreblaFileNode(
    override val context: ParseTree,
    override val filename: String,
    val packageHeader: PackageHeaderNode?,
    val imports: ImportListNode,
    val topLevelObjects: List<TopLevelObjectNode>,
) : TreblaNode

data class PackageHeaderNode(
    override val context: ParseTree,
    override val filename: String, val identifier: IdentifierNode?,
) : TreblaNode

data class ImportListNode(
    override val context: ParseTree,
    override val filename: String, val imports: List<ImportHeaderNode>,
) : TreblaNode

data class ImportHeaderNode(
    override val context: ParseTree,
    override val filename: String, val identifier: IdentifierNode,
) :
    TreblaNode

interface TopLevelObjectNode : DeclarationNode

data class FunctionDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val receiver: FunctionReceiverNode?,
    val identifier: SimpleIdentifierNode,
    val parameters: ParametersNode,
    val type: TypeNode?,
    val body: BlockNode,
    val assigmentBody: Boolean,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode {
    override fun parse(context: Context) = FunctionDeclaration(this, context)
}

data class FunctionReceiverNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode?,
    val type: TypeNode,
) : TreblaNode

data class ParametersNode(
    override val context: ParseTree,
    override val filename: String, val value: List<ParameterNode>,
) : TreblaNode

data class PropertyDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val access: String,
    val identifier: SimpleIdentifierNode,
    val expression: ExpressionNode?,
    val type: TypeNode?,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode {
    override fun parse(context: Context) = PropertyDeclaration(this, context)
}

data class LetDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val identifier: SimpleIdentifierNode,
    val expression: ExpressionNode,
    val type: TypeNode?,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode {
    override fun parse(context: Context) = LetDeclaration(this, context)
}

data class StructDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val identifier: SimpleIdentifierNode,
    val typeParameters: ParametersNode,
    val fields: List<StructFieldNode>,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode {
    override fun parse(context: Context): Declaration =
        if (typeParameters.value.isEmpty()) StructDeclaration(this, context)
        else TypeParamStructDeclaration(this, context)
}

data class StructFieldNode(
    override val context: ParseTree,
    override val filename: String,
    val parameter: ParameterNode,
    val isEmbed: Boolean
) : TreblaNode

data class EnumDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val identifier: SimpleIdentifierNode,
    val variants: List<EnumVariantNode>
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode {
    override fun parse(context: Context): Declaration {
        return EnumDeclaration(
            this,
            context,
        )
    }
}

data class EnumVariantNode(
    override val context: ParseTree,
    override val filename: String,
    val definition: EnumVariantDefinitionNode,
    val ordinal: Int?,
) : TreblaNode

sealed class EnumVariantDefinitionNode : TreblaNode

data class EnumStructVariantNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
    val fields: List<StructFieldNode>,
) : EnumVariantDefinitionNode()

data class EnumUnitVariantNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
) : EnumVariantDefinitionNode()

data class ScriptDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
    val body: ScriptBodyNode,
) : TreblaNode, TopLevelObjectNode {
    override fun parse(context: Context) = ScriptDeclaration(this, context)
}

data class ScriptBodyNode(
    override val context: ParseTree,
    override val filename: String, val value: List<ScriptMemberNode>,
) : TreblaNode

interface ScriptMemberNode

data class ArchetypeDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val name: SimpleIdentifierNode,
    val isInput: Boolean,
    val script: SimpleIdentifierNode,
    val defaults: ArchetypeDefaultsNode,
) : TreblaNode, TopLevelObjectNode {
    override fun parse(context: Context) = ArchetypeDeclaration(this, context)
}

data class ArchetypeDefaultsNode(
    override val context: ParseTree,
    override val filename: String, val value: List<ArchetypeDefaultNode>,
) : TreblaNode

data class ArchetypeDefaultNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
    val value: ExpressionNode,
) : TreblaNode

data class CallbackDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val order: ExpressionNode?,
    val identifier: SimpleIdentifierNode,
    val body: BlockNode,
) : TreblaNode, ScriptMemberNode

data class InitBlockNode(
    override val context: ParseTree,
    override val filename: String,
    val body: BlockNode,
) : TreblaNode, ScriptMemberNode

data class ParameterNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
    val type: TypeNode?,
    val default: ExpressionNode?,
) : TreblaNode

data class BlockNode(
    override val context: ParseTree,
    override val filename: String, val value: List<StatementNode>,
) : TreblaNode

interface StatementNode : TreblaNode {
    fun parse(context: Context): Expression
}

interface DeclarationNode : StatementNode {
    override fun parse(context: Context): Declaration
}

interface ExpressionNode : StatementNode

data class InfixFunctionNode(
    override val context: ParseTree,
    override val filename: String,
    val lhs: ExpressionNode,
    val rhs: ExpressionNode,
    val op: String,
) : ExpressionNode {
    override fun parse(context: Context) = InfixFunctionExpression(this)
}

data class UnaryFunctionNode(
    override val context: ParseTree,
    override val filename: String, val value: ExpressionNode, val op: UnaryOperation,
) : ExpressionNode {
    override fun parse(context: Context) = when (op) {
        is MemberAccessNode -> MemberAccessExpression(this, op)
        is PrefixUnaryFunctionNode -> UnaryFunctionExpression(this)
        is PostfixUnaryFunctionNode -> UnaryFunctionExpression(this)
        is FunctionCallNode -> CallExpression(this, op)
        is SubscriptNode -> SubscriptExpression(this, op)
    }
}

sealed class UnaryOperation : TreblaNode
data class PrefixUnaryFunctionNode(
    override val context: ParseTree,
    override val filename: String, val value: String,
) : UnaryOperation()

data class PostfixUnaryFunctionNode(
    override val context: ParseTree,
    override val filename: String, val value: String,
) : UnaryOperation()

data class FunctionCallNode(
    override val context: ParseTree,
    override val filename: String,
    val arguments: ValueArgumentsNode,
) : UnaryOperation()

data class SubscriptNode(
    override val context: ParseTree,
    override val filename: String,
    val arguments: List<ValueArgumentNode>,
) : UnaryOperation()

data class MemberAccessNode(
    override val context: ParseTree,
    override val filename: String, val identifier: SimpleIdentifierNode,
) : UnaryOperation()

data class ValueArgumentsNode(
    override val context: ParseTree,
    override val filename: String,
    val arguments: List<ValueArgumentNode>,
    val tailLambda: LambdaNode?,
) : TreblaNode

data class ValueArgumentNode(
    override val context: ParseTree,
    override val filename: String,
    val name: SimpleIdentifierNode?,
    val expression: ExpressionNode,
) : TreblaNode

data class IfExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val isConst: Boolean,
    val condition: ExpressionNode,
    val tbranch: BlockNode,
    val fbranch: BlockNode?,
) : ExpressionNode {
    override fun parse(context: Context) = IfExpression(this)
}

interface WhenEntryNode : TreblaNode
interface WhenMatchEntryNode : TreblaNode

data class WhenExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val isConst: Boolean,
    val entries: List<WhenEntryNode>,
) : ExpressionNode {
    override fun parse(context: Context) = WhenExpression(this)
}

data class WhenConditionalEntryNode(
    override val context: ParseTree,
    override val filename: String,
    val condition: ExpressionNode,
    val body: BlockNode,
) : TreblaNode, WhenEntryNode

data class WhenMatchExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val expression: ExpressionNode,
    val entries: List<WhenMatchEntryNode>,
) : ExpressionNode {
    override fun parse(context: Context) = WhenMatchExpression(this)
}

data class WhenMatchVariantNode(
    override val context: ParseTree,
    override val filename: String,
    val variant: ExpressionNode,
    val destructure: DestructuringTupleNode?,
    val body: BlockNode,
) : TreblaNode, WhenMatchEntryNode

data class WhenElseEntryNode(
    override val context: ParseTree,
    override val filename: String,
    val body: BlockNode,
) : TreblaNode, WhenEntryNode, WhenMatchEntryNode

data class TryExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val tryBranch: BlockNode,
    val exceptBranch: BlockNode,
) : ExpressionNode {
    override fun parse(context: Context) = TryExpression(this)
}

data class WhileExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val condition: ExpressionNode,
    val body: BlockNode,
) : ExpressionNode {
    override fun parse(context: Context) = WhileExpression(this)
}

data class ForExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val initializer: StatementNode?,
    val condition: ExpressionNode?,
    val afterthought: ExpressionNode?,
    val body: BlockNode,
) : ExpressionNode {
    override fun parse(context: Context) = ForExpression(this)
}

data class ModifierListNode(
    override val context: ParseTree,
    override val filename: String, val value: List<String>,
) : TreblaNode

data class ModifierNode(
    override val context: ParseTree,
    override val filename: String, val value: String,
) : TreblaNode

data class TypeNode(
    override val context: ParseTree,
    override val filename: String, val value: ExpressionNode,
) : TreblaNode

data class SimpleUserTypeNode(
    override val context: ParseTree,
    override val filename: String, val value: SimpleIdentifierNode,
) : TreblaNode

interface AtomicLiteralNode : ExpressionNode
interface LiteralConstantNode : AtomicLiteralNode
data class NumberLiteralNode(
    override val context: ParseTree,
    override val filename: String, val value: Double,
) : LiteralConstantNode {
    override fun parse(context: Context) = ValueExpression(
        RawStructValue(
            LiteralRawValue(this.value),
            (context.getFullyQualified("std", "Number") as StructDeclaration),
        )
    )
}

data class BooleanLiteralNode(
    override val context: ParseTree,
    override val filename: String, val value: Boolean,
) : LiteralConstantNode {
    override fun parse(context: Context) = ValueExpression(
        RawStructValue(
            LiteralRawValue(if (this.value) 1.0 else 0.0),
            (context.getFullyQualified("std", "Boolean") as StructDeclaration),
        )
    )
}

data class StringLiteralNode(
    override val context: ParseTree,
    override val filename: String, val value: List<StringComponent>,
) : AtomicLiteralNode {
    override fun parse(context: Context): Expression {
        return ValueExpression(StringValue(
            value.joinToString("") {
                when (it) {
                    is StringText -> it.value
                    is StringEscape -> it.convert() ?: compileError("Illegal escape in string literal.", it)
                }
            }
        ))
    }
}

sealed class StringComponent : TreblaNode
data class StringText(override val context: ParseTree, override val filename: String, val value: String) :
    StringComponent()

data class StringEscape(override val context: ParseTree, override val filename: String, val value: Char) :
    StringComponent() {
    fun convert() = when (value) {
        '\\' -> "\\"
        'r' -> "\r"
        'n' -> "\n"
        'b' -> "\b"
        't' -> "\t"
        '\'' -> "'"
        '\"' -> "\""
        else -> null
    }
}

data class SimpleIdentifierNode(
    override val context: ParseTree,
    override val filename: String, val value: String,
) : ExpressionNode {
    override fun parse(context: Context) = SimpleIdentifierExpression(this)
}

data class IdentifierNode(
    override val context: ParseTree,
    override val filename: String, val value: List<String>,
) : TreblaNode

data class LambdaNode(
    override val context: ParseTree,
    override val filename: String,
    val parameters: ParametersNode?,
    val body: List<StatementNode>,
) : ExpressionNode {
    override fun parse(context: Context) = LambdaExpression(this, context)
}

data class DestructuringTupleNode(
    override val context: ParseTree,
    override val filename: String,
    val value: List<String?>,
) : TreblaNode
