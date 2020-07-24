package xyz.qwewqa.trebla.grammar.trebla

import org.antlr.v4.runtime.tree.ParseTree

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
    override val filename: String, val identifier: IdentifierNode, val isWildcard: Boolean,
) :
    TreblaNode

interface TopLevelObjectNode : StatementNode

data class FunctionDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val receiver: FunctionReceiverNode?,
    val identifier: SimpleIdentifierNode,
    val parameters: FunctionValueParametersNode,
    val type: TypeNode?,
    val body: BlockNode,
    val assigmentBody: Boolean,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode

data class FunctionReceiverNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode?,
    val type: TypeNode,
) : TreblaNode

data class FunctionValueParametersNode(
    override val context: ParseTree,
    override val filename: String, val values: List<ParameterNode>,
) :
    TreblaNode

data class PropertyDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val access: String,
    val identifier: SimpleIdentifierNode,
    val expression: ExpressionNode?,
    val type: TypeNode?,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode

data class LetDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val identifier: SimpleIdentifierNode,
    val expression: ExpressionNode,
    val type: TypeNode?,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode

data class StructDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val modifiers: ModifierListNode,
    val identifier: SimpleIdentifierNode,
    val fields: StructFieldsNode,
) : TreblaNode, DeclarationNode, ScriptMemberNode, TopLevelObjectNode

data class StructFieldsNode(
    override val context: ParseTree,
    override val filename: String,
    val value: List<ParameterNode>,
) : TreblaNode

data class ScriptDeclarationNode(
    override val context: ParseTree,
    override val filename: String,
    val identifier: SimpleIdentifierNode,
    val body: ScriptBodyNode,
) : TreblaNode, TopLevelObjectNode

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
) : TreblaNode, TopLevelObjectNode

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

interface StatementNode : TreblaNode
interface DeclarationNode : StatementNode
interface ExpressionNode : StatementNode

data class InfixFunctionNode(
    override val context: ParseTree,
    override val filename: String,
    val lhs: ExpressionNode,
    val rhs: ExpressionNode,
    val op: String,
) : ExpressionNode

data class UnaryFunctionNode(
    override val context: ParseTree,
    override val filename: String, val value: ExpressionNode, val op: UnaryOperation,
) :
    ExpressionNode

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

data class MemberAccessNode(
    override val context: ParseTree,
    override val filename: String, val identifier: SimpleIdentifierNode,
) : UnaryOperation()

data class ValueArgumentsNode(
    override val context: ParseTree,
    override val filename: String,
    val arguments: List<ValueArgumentNode>,
    val tailLambda: LambdaNode?
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
    val expression: ExpressionNode,
    val tbranch: BlockNode,
    val fbranch: BlockNode?,
) : ExpressionNode

data class WhileExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val condition: ExpressionNode,
    val body: BlockNode,
) : ExpressionNode

data class ForExpressionNode(
    override val context: ParseTree,
    override val filename: String,
    val initializer: StatementNode?,
    val condition: ExpressionNode?,
    val afterthought: ExpressionNode?,
    val body: BlockNode,
) : ExpressionNode

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
) : LiteralConstantNode

data class BooleanLiteralNode(
    override val context: ParseTree,
    override val filename: String, val value: Boolean,
) : LiteralConstantNode

data class SimpleIdentifierNode(
    override val context: ParseTree,
    override val filename: String, val value: String,
) : ExpressionNode

data class IdentifierNode(
    override val context: ParseTree,
    override val filename: String, val value: List<String>,
) : ExpressionNode

data class LambdaNode(
    override val context: ParseTree,
    override val filename: String,
    val parameters: FunctionValueParametersNode?,
    val body: List<StatementNode>,
) : ExpressionNode