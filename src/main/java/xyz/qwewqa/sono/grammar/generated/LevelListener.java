// Generated from Level.g4 by ANTLR 4.8

    package xyz.qwewqa.sono.grammar.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LevelParser}.
 */
public interface LevelListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LevelParser#levelFile}.
	 * @param ctx the parse tree
	 */
	void enterLevelFile(LevelParser.LevelFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link LevelParser#levelFile}.
	 * @param ctx the parse tree
	 */
	void exitLevelFile(LevelParser.LevelFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link LevelParser#entity}.
	 * @param ctx the parse tree
	 */
	void enterEntity(LevelParser.EntityContext ctx);
	/**
	 * Exit a parse tree produced by {@link LevelParser#entity}.
	 * @param ctx the parse tree
	 */
	void exitEntity(LevelParser.EntityContext ctx);
	/**
	 * Enter a parse tree produced by {@link LevelParser#entityName}.
	 * @param ctx the parse tree
	 */
	void enterEntityName(LevelParser.EntityNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LevelParser#entityName}.
	 * @param ctx the parse tree
	 */
	void exitEntityName(LevelParser.EntityNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LevelParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void enterArchetypeName(LevelParser.ArchetypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LevelParser#archetypeName}.
	 * @param ctx the parse tree
	 */
	void exitArchetypeName(LevelParser.ArchetypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LevelParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(LevelParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link LevelParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(LevelParser.ParameterContext ctx);
}