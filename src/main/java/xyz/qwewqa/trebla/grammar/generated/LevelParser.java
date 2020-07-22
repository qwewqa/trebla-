// Generated from Level.g4 by ANTLR 4.8

    package xyz.qwewqa.trebla.grammar.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LevelParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, LineComment=5, WS=6, NL=7, Identifier=8, 
		FloatLiteral=9, IntegerLiteral=10, BooleanLiteral=11, UNICODE_CLASS_LL=12, 
		UNICODE_CLASS_LM=13, UNICODE_CLASS_LO=14, UNICODE_CLASS_LT=15, UNICODE_CLASS_LU=16, 
		UNICODE_CLASS_ND=17, UNICODE_CLASS_NL=18;
	public static final int
		RULE_levelFile = 0, RULE_entity = 1, RULE_entityName = 2, RULE_archetypeName = 3, 
		RULE_parameter = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"levelFile", "entity", "entityName", "archetypeName", "parameter"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "','", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "LineComment", "WS", "NL", "Identifier", 
			"FloatLiteral", "IntegerLiteral", "BooleanLiteral", "UNICODE_CLASS_LL", 
			"UNICODE_CLASS_LM", "UNICODE_CLASS_LO", "UNICODE_CLASS_LT", "UNICODE_CLASS_LU", 
			"UNICODE_CLASS_ND", "UNICODE_CLASS_NL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Level.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LevelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class LevelFileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(LevelParser.EOF, 0); }
		public List<EntityContext> entity() {
			return getRuleContexts(EntityContext.class);
		}
		public EntityContext entity(int i) {
			return getRuleContext(EntityContext.class,i);
		}
		public LevelFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_levelFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).enterLevelFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).exitLevelFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LevelVisitor ) return ((LevelVisitor<? extends T>)visitor).visitLevelFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LevelFileContext levelFile() throws RecognitionException {
		LevelFileContext _localctx = new LevelFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_levelFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(10);
				entity();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityContext extends ParserRuleContext {
		public ArchetypeNameContext archetypeName() {
			return getRuleContext(ArchetypeNameContext.class,0);
		}
		public EntityNameContext entityName() {
			return getRuleContext(EntityNameContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(LevelParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(LevelParser.NL, i);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public EntityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).enterEntity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).exitEntity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LevelVisitor ) return ((LevelVisitor<? extends T>)visitor).visitEntity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntityContext entity() throws RecognitionException {
		EntityContext _localctx = new EntityContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_entity);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(18);
				entityName();
				setState(19);
				match(T__0);
				}
				break;
			}
			setState(23);
			archetypeName();
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(24);
				match(NL);
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
			match(T__1);
			setState(34);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(31);
					match(NL);
					}
					} 
				}
				setState(36);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(37);
				parameter();
				setState(54);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(41);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(38);
							match(NL);
							}
							}
							setState(43);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(44);
						match(T__2);
						setState(48);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(45);
							match(NL);
							}
							}
							setState(50);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(51);
						parameter();
						}
						} 
					}
					setState(56);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(57);
					match(T__2);
					}
				}

				}
			}

			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(62);
				match(NL);
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(T__3);
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(69);
				match(NL);
				}
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(LevelParser.Identifier, 0); }
		public EntityNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entityName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).enterEntityName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).exitEntityName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LevelVisitor ) return ((LevelVisitor<? extends T>)visitor).visitEntityName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntityNameContext entityName() throws RecognitionException {
		EntityNameContext _localctx = new EntityNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_entityName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArchetypeNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(LevelParser.Identifier, 0); }
		public ArchetypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).enterArchetypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).exitArchetypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LevelVisitor ) return ((LevelVisitor<? extends T>)visitor).visitArchetypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchetypeNameContext archetypeName() throws RecognitionException {
		ArchetypeNameContext _localctx = new ArchetypeNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_archetypeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(LevelParser.Identifier, 0); }
		public TerminalNode BooleanLiteral() { return getToken(LevelParser.BooleanLiteral, 0); }
		public TerminalNode IntegerLiteral() { return getToken(LevelParser.IntegerLiteral, 0); }
		public TerminalNode FloatLiteral() { return getToken(LevelParser.FloatLiteral, 0); }
		public EntityNameContext entityName() {
			return getRuleContext(EntityNameContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LevelListener ) ((LevelListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LevelVisitor ) return ((LevelVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(Identifier);
			setState(80);
			match(T__0);
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BooleanLiteral:
				{
				setState(81);
				match(BooleanLiteral);
				}
				break;
			case IntegerLiteral:
				{
				setState(82);
				match(IntegerLiteral);
				}
				break;
			case FloatLiteral:
				{
				setState(83);
				match(FloatLiteral);
				}
				break;
			case Identifier:
				{
				setState(84);
				entityName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24Z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\7\2\16\n\2\f\2\16\2\21\13\2\3\2\3\2\3\3"+
		"\3\3\3\3\5\3\30\n\3\3\3\3\3\7\3\34\n\3\f\3\16\3\37\13\3\3\3\3\3\7\3#\n"+
		"\3\f\3\16\3&\13\3\3\3\3\3\7\3*\n\3\f\3\16\3-\13\3\3\3\3\3\7\3\61\n\3\f"+
		"\3\16\3\64\13\3\3\3\7\3\67\n\3\f\3\16\3:\13\3\3\3\5\3=\n\3\5\3?\n\3\3"+
		"\3\7\3B\n\3\f\3\16\3E\13\3\3\3\3\3\7\3I\n\3\f\3\16\3L\13\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\6\5\6X\n\6\3\6\2\2\7\2\4\6\b\n\2\2\2b\2\17"+
		"\3\2\2\2\4\27\3\2\2\2\6M\3\2\2\2\bO\3\2\2\2\nQ\3\2\2\2\f\16\5\4\3\2\r"+
		"\f\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\22\3\2\2\2\21\17"+
		"\3\2\2\2\22\23\7\2\2\3\23\3\3\2\2\2\24\25\5\6\4\2\25\26\7\3\2\2\26\30"+
		"\3\2\2\2\27\24\3\2\2\2\27\30\3\2\2\2\30\31\3\2\2\2\31\35\5\b\5\2\32\34"+
		"\7\t\2\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36 \3"+
		"\2\2\2\37\35\3\2\2\2 $\7\4\2\2!#\7\t\2\2\"!\3\2\2\2#&\3\2\2\2$\"\3\2\2"+
		"\2$%\3\2\2\2%>\3\2\2\2&$\3\2\2\2\'8\5\n\6\2(*\7\t\2\2)(\3\2\2\2*-\3\2"+
		"\2\2+)\3\2\2\2+,\3\2\2\2,.\3\2\2\2-+\3\2\2\2.\62\7\5\2\2/\61\7\t\2\2\60"+
		"/\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\65\3\2\2\2\64\62"+
		"\3\2\2\2\65\67\5\n\6\2\66+\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29"+
		"<\3\2\2\2:8\3\2\2\2;=\7\5\2\2<;\3\2\2\2<=\3\2\2\2=?\3\2\2\2>\'\3\2\2\2"+
		">?\3\2\2\2?C\3\2\2\2@B\7\t\2\2A@\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2"+
		"DF\3\2\2\2EC\3\2\2\2FJ\7\6\2\2GI\7\t\2\2HG\3\2\2\2IL\3\2\2\2JH\3\2\2\2"+
		"JK\3\2\2\2K\5\3\2\2\2LJ\3\2\2\2MN\7\n\2\2N\7\3\2\2\2OP\7\n\2\2P\t\3\2"+
		"\2\2QR\7\n\2\2RW\7\3\2\2SX\7\r\2\2TX\7\f\2\2UX\7\13\2\2VX\5\6\4\2WS\3"+
		"\2\2\2WT\3\2\2\2WU\3\2\2\2WV\3\2\2\2X\13\3\2\2\2\16\17\27\35$+\628<>C"+
		"JW";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}