package xyz.qwewqa.trebla.level

import org.antlr.v4.runtime.tree.ParseTree
import xyz.qwewqa.trebla.grammar.generated.LevelBaseVisitor
import xyz.qwewqa.trebla.grammar.generated.LevelParser

class LevelVisitor : LevelBaseVisitor<Any>() {
    override fun visitLevelFile(ctx: LevelParser.LevelFileContext): List<LevelEntity> {
        return ctx.entity().map { visitEntity(it) }
    }

    override fun visitEntity(ctx: LevelParser.EntityContext): LevelEntity {
        return LevelEntity(
            ctx.entityName()?.let { visit(it) } as String?,
            visit(ctx.archetypeName()) as String,
            ctx.parameter().map { visitParameter(it) }.toMap()
        )
    }

    override fun visitArchetypeName(ctx: LevelParser.ArchetypeNameContext): Any {
        return ctx.Identifier().text.filter { it != '`' }
    }

    override fun visitEntityName(ctx: LevelParser.EntityNameContext): Any {
        return ctx.Identifier().text.filter { it != '`' }
    }

    override fun visitParameter(ctx: LevelParser.ParameterContext): Pair<String, EntityArgument> {
        return ctx.Identifier().text.filter { it != '`' } to when {
            ctx.BooleanLiteral().exist -> NumericalArgument(if (ctx.BooleanLiteral().text == "true") 1.0 else 0.0)
            ctx.FloatLiteral().exist -> NumericalArgument(ctx.FloatLiteral().text.toDouble())
            ctx.IntegerLiteral().exist -> NumericalArgument(ctx.IntegerLiteral().text.toDouble())
            else -> IdentifierArgument(ctx.entityName().Identifier().text.filter { it != '`' })
        }
    }

    private inline val ParseTree?.exist get() = this != null
}

data class LevelEntity(val name: String?, val archetype: String, val arguments: Map<String, EntityArgument>)
sealed class EntityArgument
data class NumericalArgument(val value: Double) : EntityArgument()
data class IdentifierArgument(val value: String) : EntityArgument()
