// Generated from Level.g4 by ANTLR 4.8

    package xyz.qwewqa.trebla.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LevelParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LevelVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LevelParser#levelFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLevelFile(LevelParser.LevelFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link LevelParser#entity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntity(LevelParser.EntityContext ctx);
	/**
	 * Visit a parse tree produced by {@link LevelParser#entityName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntityName(LevelParser.EntityNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LevelParser#archetypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetypeName(LevelParser.ArchetypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LevelParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(LevelParser.ParameterContext ctx);
}