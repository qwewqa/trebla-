// Generated from TreblaParser.g4 by ANTLR 4.8

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
public class TreblaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DelimitedComment=1, LineComment=2, WS=3, NL=4, DOT=5, COMMA=6, LPAREN=7, 
		RPAREN=8, LSQUARE=9, RSQUARE=10, LCURL=11, RCURL=12, POW=13, MULT=14, 
		MOD=15, DIV=16, ADD=17, SUB=18, INCR=19, DECR=20, CONJ=21, DISJ=22, EXCL=23, 
		COLON=24, COLONCOLON=25, SEMICOLON=26, ASSIGNMENT=27, ADD_ASSIGNMENT=28, 
		SUB_ASSIGNMENT=29, MULT_ASSIGNMENT=30, DIV_ASSIGNMENT=31, MOD_ASSIGNMENT=32, 
		POW_ASSIGNMENT=33, HASH=34, AT=35, LANGLE=36, RANGLE=37, LE=38, GE=39, 
		EXCL_EQ=40, EQEQ=41, EQEQEQ=42, EXCL_EQEQ=43, SINGLE_QUOTE=44, ARROW=45, 
		DOUBLE_ARROW=46, IMPORT=47, STRUCT=48, FUNC=49, LET=50, SCRIPT=51, CALLBACK=52, 
		ARCHETYPE=53, VAR=54, VAL=55, IF=56, ELSE=57, WHILE=58, WHEN=59, TRY=60, 
		EXCEPT=61, FOR=62, GETTER=63, SETTER=64, IS=65, AS=66, IN=67, OUT=68, 
		PACKAGE=69, PUBLIC=70, PRIVATE=71, INTERNAL=72, PROTECTED=73, SHARED=74, 
		STATIC=75, OPERATOR=76, INLINE=77, INIT=78, SPAWN=79, INFIX=80, DATA=81, 
		LEVEL=82, LEVELDATA=83, TEMP=84, RAW=85, CONST=86, NOTE=87, EMBED=88, 
		ENUM=89, QUOTE_OPEN=90, FloatLiteral=91, IntegerLiteral=92, BooleanLiteral=93, 
		Identifier=94, UNICODE_CLASS_LL=95, UNICODE_CLASS_LM=96, UNICODE_CLASS_LO=97, 
		UNICODE_CLASS_LT=98, UNICODE_CLASS_LU=99, UNICODE_CLASS_ND=100, UNICODE_CLASS_NL=101, 
		Inside_Comment=102, Inside_WS=103, Inside_NL=104, QUOTE_CLOSE=105, LineStrText=106, 
		LineStrEscapedChar=107;
	public static final int
		RULE_treblaFile = 0, RULE_packageHeader = 1, RULE_importList = 2, RULE_importHeader = 3, 
		RULE_topLevelObject = 4, RULE_functionDeclaration = 5, RULE_functionReceiver = 6, 
		RULE_functionValueParameters = 7, RULE_functionBody = 8, RULE_propertyDeclaration = 9, 
		RULE_structDeclaration = 10, RULE_structTypeParameters = 11, RULE_structFields = 12, 
		RULE_structField = 13, RULE_enumDeclaration = 14, RULE_enumVariants = 15, 
		RULE_enumVariant = 16, RULE_enumVariantDefinition = 17, RULE_scriptDeclaration = 18, 
		RULE_scriptBody = 19, RULE_scriptMemberDeclaration = 20, RULE_archetypeDeclaration = 21, 
		RULE_archetypeName = 22, RULE_archetypeDefaults = 23, RULE_archetypeDefault = 24, 
		RULE_callbackDeclaration = 25, RULE_callbackOrder = 26, RULE_letDeclaration = 27, 
		RULE_initBlock = 28, RULE_parameter = 29, RULE_typeParameter = 30, RULE_type = 31, 
		RULE_block = 32, RULE_statement = 33, RULE_declaration = 34, RULE_expression = 35, 
		RULE_prefixUnaryExpression = 36, RULE_postfixUnaryExpression = 37, RULE_atomicExpression = 38, 
		RULE_parenthesizedExpression = 39, RULE_callSuffix = 40, RULE_subscriptingSuffix = 41, 
		RULE_lambda = 42, RULE_valueArguments = 43, RULE_valueArgument = 44, RULE_conditionalExpression = 45, 
		RULE_ifExpression = 46, RULE_whenExpression = 47, RULE_whenMatchExpression = 48, 
		RULE_whenEntry = 49, RULE_whenMatchEntry = 50, RULE_whenConditionalEntry = 51, 
		RULE_whenElseEntry = 52, RULE_whenMatchVariantEntry = 53, RULE_destructuringTuple = 54, 
		RULE_tryExpression = 55, RULE_loopExpression = 56, RULE_whileExpression = 57, 
		RULE_forExpression = 58, RULE_forInitializer = 59, RULE_forCondition = 60, 
		RULE_forAfterthought = 61, RULE_controlStructureBody = 62, RULE_modifierList = 63, 
		RULE_modifier = 64, RULE_identifier = 65, RULE_simpleIdentifier = 66, 
		RULE_literalConstant = 67, RULE_lineStringLiteral = 68, RULE_prefixUnaryOperation = 69, 
		RULE_postfixUnaryOperation = 70, RULE_memberAccessOperator = 71, RULE_assignmentOperator = 72, 
		RULE_semi = 73, RULE_anysemi = 74;
	private static String[] makeRuleNames() {
		return new String[] {
			"treblaFile", "packageHeader", "importList", "importHeader", "topLevelObject", 
			"functionDeclaration", "functionReceiver", "functionValueParameters", 
			"functionBody", "propertyDeclaration", "structDeclaration", "structTypeParameters", 
			"structFields", "structField", "enumDeclaration", "enumVariants", "enumVariant", 
			"enumVariantDefinition", "scriptDeclaration", "scriptBody", "scriptMemberDeclaration", 
			"archetypeDeclaration", "archetypeName", "archetypeDefaults", "archetypeDefault", 
			"callbackDeclaration", "callbackOrder", "letDeclaration", "initBlock", 
			"parameter", "typeParameter", "type", "block", "statement", "declaration", 
			"expression", "prefixUnaryExpression", "postfixUnaryExpression", "atomicExpression", 
			"parenthesizedExpression", "callSuffix", "subscriptingSuffix", "lambda", 
			"valueArguments", "valueArgument", "conditionalExpression", "ifExpression", 
			"whenExpression", "whenMatchExpression", "whenEntry", "whenMatchEntry", 
			"whenConditionalEntry", "whenElseEntry", "whenMatchVariantEntry", "destructuringTuple", 
			"tryExpression", "loopExpression", "whileExpression", "forExpression", 
			"forInitializer", "forCondition", "forAfterthought", "controlStructureBody", 
			"modifierList", "modifier", "identifier", "simpleIdentifier", "literalConstant", 
			"lineStringLiteral", "prefixUnaryOperation", "postfixUnaryOperation", 
			"memberAccessOperator", "assignmentOperator", "semi", "anysemi"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'.'", "','", "'('", null, "'['", null, 
			"'{'", "'}'", "'^'", "'*'", "'%'", "'/'", "'+'", "'-'", "'++'", "'--'", 
			"'&&'", "'||'", "'!'", "':'", "'::'", "';'", "'='", "'+='", "'-='", "'*='", 
			"'/='", "'%='", "'^='", "'#'", "'@'", "'<'", "'>'", "'<='", "'>='", "'!='", 
			"'=='", "'==='", "'!=='", "'''", "'->'", "'=>'", "'import'", "'struct'", 
			"'func'", "'let'", "'script'", "'callback'", "'archetype'", "'var'", 
			"'val'", "'if'", "'else'", "'while'", "'when'", "'try'", "'except'", 
			"'for'", "'get'", "'set'", "'is'", "'as'", "'in'", "'out'", "'package'", 
			"'public'", "'private'", "'internal'", "'protected'", "'shared'", "'static'", 
			"'operator'", "'inline'", "'init'", "'spawn'", "'infix'", "'data'", "'level'", 
			"'leveldata'", "'temp'", "'raw'", "'const'", "'note'", "'embed'", "'enum'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DelimitedComment", "LineComment", "WS", "NL", "DOT", "COMMA", 
			"LPAREN", "RPAREN", "LSQUARE", "RSQUARE", "LCURL", "RCURL", "POW", "MULT", 
			"MOD", "DIV", "ADD", "SUB", "INCR", "DECR", "CONJ", "DISJ", "EXCL", "COLON", 
			"COLONCOLON", "SEMICOLON", "ASSIGNMENT", "ADD_ASSIGNMENT", "SUB_ASSIGNMENT", 
			"MULT_ASSIGNMENT", "DIV_ASSIGNMENT", "MOD_ASSIGNMENT", "POW_ASSIGNMENT", 
			"HASH", "AT", "LANGLE", "RANGLE", "LE", "GE", "EXCL_EQ", "EQEQ", "EQEQEQ", 
			"EXCL_EQEQ", "SINGLE_QUOTE", "ARROW", "DOUBLE_ARROW", "IMPORT", "STRUCT", 
			"FUNC", "LET", "SCRIPT", "CALLBACK", "ARCHETYPE", "VAR", "VAL", "IF", 
			"ELSE", "WHILE", "WHEN", "TRY", "EXCEPT", "FOR", "GETTER", "SETTER", 
			"IS", "AS", "IN", "OUT", "PACKAGE", "PUBLIC", "PRIVATE", "INTERNAL", 
			"PROTECTED", "SHARED", "STATIC", "OPERATOR", "INLINE", "INIT", "SPAWN", 
			"INFIX", "DATA", "LEVEL", "LEVELDATA", "TEMP", "RAW", "CONST", "NOTE", 
			"EMBED", "ENUM", "QUOTE_OPEN", "FloatLiteral", "IntegerLiteral", "BooleanLiteral", 
			"Identifier", "UNICODE_CLASS_LL", "UNICODE_CLASS_LM", "UNICODE_CLASS_LO", 
			"UNICODE_CLASS_LT", "UNICODE_CLASS_LU", "UNICODE_CLASS_ND", "UNICODE_CLASS_NL", 
			"Inside_Comment", "Inside_WS", "Inside_NL", "QUOTE_CLOSE", "LineStrText", 
			"LineStrEscapedChar"
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
	public String getGrammarFileName() { return "TreblaParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TreblaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TreblaFileContext extends ParserRuleContext {
		public ImportListContext importList() {
			return getRuleContext(ImportListContext.class,0);
		}
		public TerminalNode EOF() { return getToken(TreblaParser.EOF, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public PackageHeaderContext packageHeader() {
			return getRuleContext(PackageHeaderContext.class,0);
		}
		public List<AnysemiContext> anysemi() {
			return getRuleContexts(AnysemiContext.class);
		}
		public AnysemiContext anysemi(int i) {
			return getRuleContext(AnysemiContext.class,i);
		}
		public List<TopLevelObjectContext> topLevelObject() {
			return getRuleContexts(TopLevelObjectContext.class);
		}
		public TopLevelObjectContext topLevelObject(int i) {
			return getRuleContext(TopLevelObjectContext.class,i);
		}
		public List<SemiContext> semi() {
			return getRuleContexts(SemiContext.class);
		}
		public SemiContext semi(int i) {
			return getRuleContext(SemiContext.class,i);
		}
		public TreblaFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_treblaFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterTreblaFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitTreblaFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitTreblaFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TreblaFileContext treblaFile() throws RecognitionException {
		TreblaFileContext _localctx = new TreblaFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_treblaFile);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(150);
					match(NL);
					}
					} 
				}
				setState(155);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PACKAGE) {
				{
				setState(156);
				packageHeader();
				}
			}

			setState(162);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(159);
					match(NL);
					}
					} 
				}
				setState(164);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(165);
			importList();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL || _la==SEMICOLON) {
				{
				{
				setState(166);
				anysemi();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 48)) & ~0x3f) == 0 && ((1L << (_la - 48)) & ((1L << (STRUCT - 48)) | (1L << (FUNC - 48)) | (1L << (LET - 48)) | (1L << (SCRIPT - 48)) | (1L << (ARCHETYPE - 48)) | (1L << (VAR - 48)) | (1L << (PUBLIC - 48)) | (1L << (PRIVATE - 48)) | (1L << (SHARED - 48)) | (1L << (STATIC - 48)) | (1L << (OPERATOR - 48)) | (1L << (INIT - 48)) | (1L << (SPAWN - 48)) | (1L << (INFIX - 48)) | (1L << (DATA - 48)) | (1L << (LEVEL - 48)) | (1L << (LEVELDATA - 48)) | (1L << (TEMP - 48)) | (1L << (RAW - 48)) | (1L << (CONST - 48)) | (1L << (NOTE - 48)) | (1L << (ENUM - 48)))) != 0)) {
				{
				setState(172);
				topLevelObject();
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==SEMICOLON) {
					{
					{
					setState(174); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(173);
							semi();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(176); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(179);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 48)) & ~0x3f) == 0 && ((1L << (_la - 48)) & ((1L << (STRUCT - 48)) | (1L << (FUNC - 48)) | (1L << (LET - 48)) | (1L << (SCRIPT - 48)) | (1L << (ARCHETYPE - 48)) | (1L << (VAR - 48)) | (1L << (PUBLIC - 48)) | (1L << (PRIVATE - 48)) | (1L << (SHARED - 48)) | (1L << (STATIC - 48)) | (1L << (OPERATOR - 48)) | (1L << (INIT - 48)) | (1L << (SPAWN - 48)) | (1L << (INFIX - 48)) | (1L << (DATA - 48)) | (1L << (LEVEL - 48)) | (1L << (LEVELDATA - 48)) | (1L << (TEMP - 48)) | (1L << (RAW - 48)) | (1L << (CONST - 48)) | (1L << (NOTE - 48)) | (1L << (ENUM - 48)))) != 0)) {
						{
						setState(178);
						topLevelObject();
						}
					}

					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(188);
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

	public static class PackageHeaderContext extends ParserRuleContext {
		public TerminalNode PACKAGE() { return getToken(TreblaParser.PACKAGE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public SemiContext semi() {
			return getRuleContext(SemiContext.class,0);
		}
		public PackageHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPackageHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPackageHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPackageHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageHeaderContext packageHeader() throws RecognitionException {
		PackageHeaderContext _localctx = new PackageHeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_packageHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(PACKAGE);
			setState(191);
			identifier();
			setState(192);
			semi();
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

	public static class ImportListContext extends ParserRuleContext {
		public List<ImportHeaderContext> importHeader() {
			return getRuleContexts(ImportHeaderContext.class);
		}
		public ImportHeaderContext importHeader(int i) {
			return getRuleContext(ImportHeaderContext.class,i);
		}
		public ImportListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterImportList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitImportList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitImportList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportListContext importList() throws RecognitionException {
		ImportListContext _localctx = new ImportListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(194);
				importHeader();
				}
				}
				setState(199);
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

	public static class ImportHeaderContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(TreblaParser.IMPORT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public SemiContext semi() {
			return getRuleContext(SemiContext.class,0);
		}
		public TerminalNode DOT() { return getToken(TreblaParser.DOT, 0); }
		public TerminalNode MULT() { return getToken(TreblaParser.MULT, 0); }
		public ImportHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterImportHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitImportHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitImportHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportHeaderContext importHeader() throws RecognitionException {
		ImportHeaderContext _localctx = new ImportHeaderContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importHeader);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(IMPORT);
			setState(201);
			identifier();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOT) {
				{
				setState(202);
				match(DOT);
				setState(203);
				match(MULT);
				}
			}

			setState(206);
			semi();
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

	public static class TopLevelObjectContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public LetDeclarationContext letDeclaration() {
			return getRuleContext(LetDeclarationContext.class,0);
		}
		public StructDeclarationContext structDeclaration() {
			return getRuleContext(StructDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public ScriptDeclarationContext scriptDeclaration() {
			return getRuleContext(ScriptDeclarationContext.class,0);
		}
		public ArchetypeDeclarationContext archetypeDeclaration() {
			return getRuleContext(ArchetypeDeclarationContext.class,0);
		}
		public TopLevelObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_topLevelObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterTopLevelObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitTopLevelObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitTopLevelObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TopLevelObjectContext topLevelObject() throws RecognitionException {
		TopLevelObjectContext _localctx = new TopLevelObjectContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_topLevelObject);
		try {
			setState(215);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				functionDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(209);
				propertyDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(210);
				letDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(211);
				structDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(212);
				enumDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(213);
				scriptDeclaration();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(214);
				archetypeDeclaration();
				}
				break;
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

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public TerminalNode FUNC() { return getToken(TreblaParser.FUNC, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public FunctionValueParametersContext functionValueParameters() {
			return getRuleContext(FunctionValueParametersContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public FunctionReceiverContext functionReceiver() {
			return getRuleContext(FunctionReceiverContext.class,0);
		}
		public TerminalNode DOT() { return getToken(TreblaParser.DOT, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			modifierList();
			setState(218);
			match(FUNC);
			setState(234);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(222);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(219);
					match(NL);
					}
					}
					setState(224);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(225);
				functionReceiver();
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(226);
					match(NL);
					}
					}
					setState(231);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(232);
				match(DOT);
				}
				break;
			}
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(236);
				match(NL);
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(242);
			simpleIdentifier();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(243);
				match(NL);
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(249);
			functionValueParameters();
			setState(264);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(253);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(250);
					match(NL);
					}
					}
					setState(255);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(256);
				match(COLON);
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(257);
					match(NL);
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(263);
				type();
				}
				break;
			}
			setState(269);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(266);
				match(NL);
				}
				}
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(272);
			functionBody();
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

	public static class FunctionReceiverContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public FunctionReceiverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionReceiver; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterFunctionReceiver(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitFunctionReceiver(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitFunctionReceiver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionReceiverContext functionReceiver() throws RecognitionException {
		FunctionReceiverContext _localctx = new FunctionReceiverContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_functionReceiver);
		try {
			setState(281);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				match(LPAREN);
				setState(275);
				simpleIdentifier();
				setState(276);
				match(COLON);
				setState(277);
				type();
				setState(278);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				type();
				}
				break;
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

	public static class FunctionValueParametersContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public FunctionValueParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionValueParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterFunctionValueParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitFunctionValueParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitFunctionValueParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionValueParametersContext functionValueParameters() throws RecognitionException {
		FunctionValueParametersContext _localctx = new FunctionValueParametersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionValueParameters);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(LPAREN);
			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
				{
				setState(284);
				parameter();
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(285);
						match(COMMA);
						setState(286);
						parameter();
						}
						} 
					}
					setState(291);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				setState(293);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(292);
					match(COMMA);
					}
				}

				}
			}

			setState(297);
			match(RPAREN);
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

	public static class FunctionBodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitFunctionBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitFunctionBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionBody);
		int _la;
		try {
			setState(308);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LCURL:
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				block();
				}
				break;
			case ASSIGNMENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				match(ASSIGNMENT);
				setState(304);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(301);
					match(NL);
					}
					}
					setState(306);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(307);
				expression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class PropertyDeclarationContext extends ParserRuleContext {
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public TerminalNode VAR() { return getToken(TreblaParser.VAR, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public PropertyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPropertyDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPropertyDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPropertyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyDeclarationContext propertyDeclaration() throws RecognitionException {
		PropertyDeclarationContext _localctx = new PropertyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_propertyDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			modifierList();
			setState(311);
			match(VAR);
			setState(312);
			simpleIdentifier();
			setState(315);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(313);
				match(COLON);
				setState(314);
				type();
				}
			}

			setState(331);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(320);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(317);
					match(NL);
					}
					}
					setState(322);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				{
				setState(323);
				match(ASSIGNMENT);
				}
				setState(327);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(324);
					match(NL);
					}
					}
					setState(329);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(330);
				expression(0);
				}
				break;
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

	public static class StructDeclarationContext extends ParserRuleContext {
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public TerminalNode STRUCT() { return getToken(TreblaParser.STRUCT, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public StructTypeParametersContext structTypeParameters() {
			return getRuleContext(StructTypeParametersContext.class,0);
		}
		public StructFieldsContext structFields() {
			return getRuleContext(StructFieldsContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public StructDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterStructDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitStructDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitStructDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructDeclarationContext structDeclaration() throws RecognitionException {
		StructDeclarationContext _localctx = new StructDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_structDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			modifierList();
			setState(334);
			match(STRUCT);
			setState(338);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(335);
				match(NL);
				}
				}
				setState(340);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(341);
			simpleIdentifier();
			setState(342);
			structTypeParameters();
			setState(343);
			structFields();
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

	public static class StructTypeParametersContext extends ParserRuleContext {
		public TerminalNode LSQUARE() { return getToken(TreblaParser.LSQUARE, 0); }
		public TerminalNode RSQUARE() { return getToken(TreblaParser.RSQUARE, 0); }
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public StructTypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structTypeParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterStructTypeParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitStructTypeParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitStructTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructTypeParametersContext structTypeParameters() throws RecognitionException {
		StructTypeParametersContext _localctx = new StructTypeParametersContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_structTypeParameters);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LSQUARE) {
				{
				setState(345);
				match(LSQUARE);
				setState(357);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (IN - 53)) | (1L << (OUT - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
					{
					setState(346);
					typeParameter();
					setState(351);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(347);
							match(COMMA);
							setState(348);
							typeParameter();
							}
							} 
						}
						setState(353);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
					}
					setState(355);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(354);
						match(COMMA);
						}
					}

					}
				}

				setState(359);
				match(RSQUARE);
				}
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

	public static class StructFieldsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<StructFieldContext> structField() {
			return getRuleContexts(StructFieldContext.class);
		}
		public StructFieldContext structField(int i) {
			return getRuleContext(StructFieldContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public StructFieldsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structFields; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterStructFields(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitStructFields(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitStructFields(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructFieldsContext structFields() throws RecognitionException {
		StructFieldsContext _localctx = new StructFieldsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_structFields);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(362);
				match(LPAREN);
				setState(374);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
					{
					setState(363);
					structField();
					setState(368);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(364);
							match(COMMA);
							setState(365);
							structField();
							}
							} 
						}
						setState(370);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
					}
					setState(372);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(371);
						match(COMMA);
						}
					}

					}
				}

				setState(376);
				match(RPAREN);
				}
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

	public static class StructFieldContext extends ParserRuleContext {
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public TerminalNode EMBED() { return getToken(TreblaParser.EMBED, 0); }
		public StructFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_structField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterStructField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitStructField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitStructField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructFieldContext structField() throws RecognitionException {
		StructFieldContext _localctx = new StructFieldContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_structField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(379);
				match(EMBED);
				}
				break;
			}
			setState(382);
			parameter();
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

	public static class EnumDeclarationContext extends ParserRuleContext {
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public TerminalNode ENUM() { return getToken(TreblaParser.ENUM, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public EnumVariantsContext enumVariants() {
			return getRuleContext(EnumVariantsContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public EnumDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterEnumDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitEnumDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitEnumDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclarationContext enumDeclaration() throws RecognitionException {
		EnumDeclarationContext _localctx = new EnumDeclarationContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_enumDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			modifierList();
			setState(385);
			match(ENUM);
			setState(389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(386);
				match(NL);
				}
				}
				setState(391);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(392);
			simpleIdentifier();
			setState(396);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(393);
				match(NL);
				}
				}
				setState(398);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(399);
			enumVariants();
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

	public static class EnumVariantsContext extends ParserRuleContext {
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public List<EnumVariantContext> enumVariant() {
			return getRuleContexts(EnumVariantContext.class);
		}
		public EnumVariantContext enumVariant(int i) {
			return getRuleContext(EnumVariantContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public EnumVariantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumVariants; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterEnumVariants(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitEnumVariants(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitEnumVariants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumVariantsContext enumVariants() throws RecognitionException {
		EnumVariantsContext _localctx = new EnumVariantsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_enumVariants);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			match(LCURL);
			setState(405);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(402);
					match(NL);
					}
					} 
				}
				setState(407);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			}
			setState(430);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
				{
				setState(408);
				enumVariant();
				setState(427);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(412);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(409);
							match(NL);
							}
							}
							setState(414);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(415);
						match(COMMA);
						setState(419);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(416);
								match(NL);
								}
								} 
							}
							setState(421);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
						}
						setState(423);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
							{
							setState(422);
							enumVariant();
							}
						}

						}
						} 
					}
					setState(429);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
				}
				}
			}

			setState(435);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(432);
					match(NL);
					}
					} 
				}
				setState(437);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,48,_ctx);
			}
			setState(439);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(438);
				match(COMMA);
				}
			}

			setState(444);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(441);
				match(NL);
				}
				}
				setState(446);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(447);
			match(RCURL);
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

	public static class EnumVariantContext extends ParserRuleContext {
		public EnumVariantDefinitionContext enumVariantDefinition() {
			return getRuleContext(EnumVariantDefinitionContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public TerminalNode IntegerLiteral() { return getToken(TreblaParser.IntegerLiteral, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public EnumVariantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumVariant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterEnumVariant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitEnumVariant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitEnumVariant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumVariantContext enumVariant() throws RecognitionException {
		EnumVariantContext _localctx = new EnumVariantContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_enumVariant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
			enumVariantDefinition();
			setState(464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				{
				setState(453);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(450);
					match(NL);
					}
					}
					setState(455);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(456);
				match(ASSIGNMENT);
				setState(460);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(457);
					match(NL);
					}
					}
					setState(462);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(463);
				match(IntegerLiteral);
				}
				break;
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

	public static class EnumVariantDefinitionContext extends ParserRuleContext {
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public StructFieldsContext structFields() {
			return getRuleContext(StructFieldsContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public EnumVariantDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumVariantDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterEnumVariantDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitEnumVariantDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitEnumVariantDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumVariantDefinitionContext enumVariantDefinition() throws RecognitionException {
		EnumVariantDefinitionContext _localctx = new EnumVariantDefinitionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_enumVariantDefinition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(466);
			simpleIdentifier();
			setState(470);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(467);
					match(NL);
					}
					} 
				}
				setState(472);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			}
			setState(473);
			structFields();
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

	public static class ScriptDeclarationContext extends ParserRuleContext {
		public TerminalNode SCRIPT() { return getToken(TreblaParser.SCRIPT, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public ScriptBodyContext scriptBody() {
			return getRuleContext(ScriptBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public ScriptDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterScriptDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitScriptDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitScriptDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptDeclarationContext scriptDeclaration() throws RecognitionException {
		ScriptDeclarationContext _localctx = new ScriptDeclarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_scriptDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			match(SCRIPT);
			setState(479);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(476);
				match(NL);
				}
				}
				setState(481);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(482);
			simpleIdentifier();
			setState(486);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(483);
				match(NL);
				}
				}
				setState(488);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(489);
			scriptBody();
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

	public static class ScriptBodyContext extends ParserRuleContext {
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public List<ScriptMemberDeclarationContext> scriptMemberDeclaration() {
			return getRuleContexts(ScriptMemberDeclarationContext.class);
		}
		public ScriptMemberDeclarationContext scriptMemberDeclaration(int i) {
			return getRuleContext(ScriptMemberDeclarationContext.class,i);
		}
		public ScriptBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterScriptBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitScriptBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitScriptBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptBodyContext scriptBody() throws RecognitionException {
		ScriptBodyContext _localctx = new ScriptBodyContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_scriptBody);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(491);
			match(LCURL);
			setState(495);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(492);
					match(NL);
					}
					} 
				}
				setState(497);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			}
			setState(501);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 48)) & ~0x3f) == 0 && ((1L << (_la - 48)) & ((1L << (STRUCT - 48)) | (1L << (FUNC - 48)) | (1L << (LET - 48)) | (1L << (CALLBACK - 48)) | (1L << (VAR - 48)) | (1L << (PUBLIC - 48)) | (1L << (PRIVATE - 48)) | (1L << (SHARED - 48)) | (1L << (STATIC - 48)) | (1L << (OPERATOR - 48)) | (1L << (INIT - 48)) | (1L << (SPAWN - 48)) | (1L << (INFIX - 48)) | (1L << (DATA - 48)) | (1L << (LEVEL - 48)) | (1L << (LEVELDATA - 48)) | (1L << (TEMP - 48)) | (1L << (RAW - 48)) | (1L << (CONST - 48)) | (1L << (ENUM - 48)))) != 0)) {
				{
				{
				setState(498);
				scriptMemberDeclaration();
				}
				}
				setState(503);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(507);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(504);
				match(NL);
				}
				}
				setState(509);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(510);
			match(RCURL);
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

	public static class ScriptMemberDeclarationContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public LetDeclarationContext letDeclaration() {
			return getRuleContext(LetDeclarationContext.class,0);
		}
		public StructDeclarationContext structDeclaration() {
			return getRuleContext(StructDeclarationContext.class,0);
		}
		public CallbackDeclarationContext callbackDeclaration() {
			return getRuleContext(CallbackDeclarationContext.class,0);
		}
		public InitBlockContext initBlock() {
			return getRuleContext(InitBlockContext.class,0);
		}
		public List<AnysemiContext> anysemi() {
			return getRuleContexts(AnysemiContext.class);
		}
		public AnysemiContext anysemi(int i) {
			return getRuleContext(AnysemiContext.class,i);
		}
		public ScriptMemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptMemberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterScriptMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitScriptMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitScriptMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptMemberDeclarationContext scriptMemberDeclaration() throws RecognitionException {
		ScriptMemberDeclarationContext _localctx = new ScriptMemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_scriptMemberDeclaration);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(519);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				{
				setState(512);
				functionDeclaration();
				}
				break;
			case 2:
				{
				setState(513);
				enumDeclaration();
				}
				break;
			case 3:
				{
				setState(514);
				propertyDeclaration();
				}
				break;
			case 4:
				{
				setState(515);
				letDeclaration();
				}
				break;
			case 5:
				{
				setState(516);
				structDeclaration();
				}
				break;
			case 6:
				{
				setState(517);
				callbackDeclaration();
				}
				break;
			case 7:
				{
				setState(518);
				initBlock();
				}
				break;
			}
			setState(524);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(521);
					anysemi();
					}
					} 
				}
				setState(526);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
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

	public static class ArchetypeDeclarationContext extends ParserRuleContext {
		public TerminalNode ARCHETYPE() { return getToken(TreblaParser.ARCHETYPE, 0); }
		public ArchetypeNameContext archetypeName() {
			return getRuleContext(ArchetypeNameContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TreblaParser.ARROW, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public ArchetypeDefaultsContext archetypeDefaults() {
			return getRuleContext(ArchetypeDefaultsContext.class,0);
		}
		public TerminalNode NOTE() { return getToken(TreblaParser.NOTE, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public ArchetypeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetypeDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterArchetypeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitArchetypeDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitArchetypeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchetypeDeclarationContext archetypeDeclaration() throws RecognitionException {
		ArchetypeDeclarationContext _localctx = new ArchetypeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_archetypeDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOTE) {
				{
				setState(527);
				match(NOTE);
				}
			}

			setState(530);
			match(ARCHETYPE);
			setState(534);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(531);
				match(NL);
				}
				}
				setState(536);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(537);
			archetypeName();
			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(538);
				match(NL);
				}
				}
				setState(543);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(544);
			match(ARROW);
			setState(548);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(545);
				match(NL);
				}
				}
				setState(550);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(551);
			simpleIdentifier();
			setState(552);
			archetypeDefaults();
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
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public ArchetypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterArchetypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitArchetypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitArchetypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchetypeNameContext archetypeName() throws RecognitionException {
		ArchetypeNameContext _localctx = new ArchetypeNameContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_archetypeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(554);
			simpleIdentifier();
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

	public static class ArchetypeDefaultsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<ArchetypeDefaultContext> archetypeDefault() {
			return getRuleContexts(ArchetypeDefaultContext.class);
		}
		public ArchetypeDefaultContext archetypeDefault(int i) {
			return getRuleContext(ArchetypeDefaultContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public ArchetypeDefaultsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetypeDefaults; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterArchetypeDefaults(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitArchetypeDefaults(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitArchetypeDefaults(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchetypeDefaultsContext archetypeDefaults() throws RecognitionException {
		ArchetypeDefaultsContext _localctx = new ArchetypeDefaultsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_archetypeDefaults);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			match(LPAREN);
			setState(565);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
				{
				setState(557);
				archetypeDefault();
				setState(562);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(558);
					match(COMMA);
					setState(559);
					archetypeDefault();
					}
					}
					setState(564);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(567);
			match(RPAREN);
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

	public static class ArchetypeDefaultContext extends ParserRuleContext {
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArchetypeDefaultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetypeDefault; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterArchetypeDefault(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitArchetypeDefault(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitArchetypeDefault(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArchetypeDefaultContext archetypeDefault() throws RecognitionException {
		ArchetypeDefaultContext _localctx = new ArchetypeDefaultContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_archetypeDefault);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			simpleIdentifier();
			setState(570);
			match(ASSIGNMENT);
			setState(571);
			expression(0);
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

	public static class CallbackDeclarationContext extends ParserRuleContext {
		public TerminalNode CALLBACK() { return getToken(TreblaParser.CALLBACK, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public TerminalNode AT() { return getToken(TreblaParser.AT, 0); }
		public CallbackOrderContext callbackOrder() {
			return getRuleContext(CallbackOrderContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public CallbackDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callbackDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterCallbackDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitCallbackDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitCallbackDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallbackDeclarationContext callbackDeclaration() throws RecognitionException {
		CallbackDeclarationContext _localctx = new CallbackDeclarationContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_callbackDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			match(CALLBACK);
			setState(576);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AT) {
				{
				setState(574);
				match(AT);
				setState(575);
				callbackOrder();
				}
			}

			setState(581);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(578);
				match(NL);
				}
				}
				setState(583);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(584);
			simpleIdentifier();
			setState(588);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(585);
				match(NL);
				}
				}
				setState(590);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(591);
			functionBody();
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

	public static class CallbackOrderContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallbackOrderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callbackOrder; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterCallbackOrder(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitCallbackOrder(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitCallbackOrder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallbackOrderContext callbackOrder() throws RecognitionException {
		CallbackOrderContext _localctx = new CallbackOrderContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_callbackOrder);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(593);
			expression(0);
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

	public static class LetDeclarationContext extends ParserRuleContext {
		public ModifierListContext modifierList() {
			return getRuleContext(ModifierListContext.class,0);
		}
		public TerminalNode LET() { return getToken(TreblaParser.LET, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public LetDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterLetDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitLetDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitLetDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LetDeclarationContext letDeclaration() throws RecognitionException {
		LetDeclarationContext _localctx = new LetDeclarationContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_letDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(595);
			modifierList();
			setState(596);
			match(LET);
			setState(597);
			simpleIdentifier();
			setState(600);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(598);
				match(COLON);
				setState(599);
				type();
				}
			}

			setState(605);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(602);
				match(NL);
				}
				}
				setState(607);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			{
			setState(608);
			match(ASSIGNMENT);
			}
			setState(612);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(609);
				match(NL);
				}
				}
				setState(614);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(615);
			expression(0);
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

	public static class InitBlockContext extends ParserRuleContext {
		public TerminalNode INIT() { return getToken(TreblaParser.INIT, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public InitBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterInitBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitInitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitInitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitBlockContext initBlock() throws RecognitionException {
		InitBlockContext _localctx = new InitBlockContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_initBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(617);
			match(INIT);
			setState(621);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(618);
				match(NL);
				}
				}
				setState(623);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(624);
			block();
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
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(626);
			simpleIdentifier();
			setState(629);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(627);
				match(COLON);
				setState(628);
				type();
				}
			}

			setState(633);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(631);
				match(ASSIGNMENT);
				setState(632);
				expression(0);
				}
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

	public static class TypeParameterContext extends ParserRuleContext {
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode COLON() { return getToken(TreblaParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IN() { return getToken(TreblaParser.IN, 0); }
		public TerminalNode OUT() { return getToken(TreblaParser.OUT, 0); }
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitTypeParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(636);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IN || _la==OUT) {
				{
				setState(635);
				_la = _input.LA(1);
				if ( !(_la==IN || _la==OUT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(638);
			simpleIdentifier();
			setState(641);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(639);
				match(COLON);
				setState(640);
				type();
				}
			}

			setState(645);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGNMENT) {
				{
				setState(643);
				match(ASSIGNMENT);
				setState(644);
				expression(0);
				}
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

	public static class TypeContext extends ParserRuleContext {
		public PrefixUnaryExpressionContext prefixUnaryExpression() {
			return getRuleContext(PrefixUnaryExpressionContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(647);
			prefixUnaryExpression();
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

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public List<AnysemiContext> anysemi() {
			return getRuleContexts(AnysemiContext.class);
		}
		public AnysemiContext anysemi(int i) {
			return getRuleContext(AnysemiContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_block);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(649);
			match(LCURL);
			setState(653);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL || _la==SEMICOLON) {
				{
				{
				setState(650);
				anysemi();
				}
				}
				setState(655);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(670);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << STRUCT) | (1L << FUNC) | (1L << LET) | (1L << ARCHETYPE) | (1L << VAR) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (INFIX - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (ENUM - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(656);
				statement();
				setState(667);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==SEMICOLON) {
					{
					{
					setState(658); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(657);
							anysemi();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(660); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,81,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(663);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << STRUCT) | (1L << FUNC) | (1L << LET) | (1L << ARCHETYPE) | (1L << VAR) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (INFIX - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (ENUM - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
						{
						setState(662);
						statement();
						}
					}

					}
					}
					setState(669);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(672);
			match(RCURL);
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

	public static class StatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_statement);
		try {
			setState(676);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(674);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(675);
				declaration();
				}
				break;
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

	public static class DeclarationContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public LetDeclarationContext letDeclaration() {
			return getRuleContext(LetDeclarationContext.class,0);
		}
		public StructDeclarationContext structDeclaration() {
			return getRuleContext(StructDeclarationContext.class,0);
		}
		public EnumDeclarationContext enumDeclaration() {
			return getRuleContext(EnumDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_declaration);
		try {
			setState(683);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(678);
				functionDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(679);
				propertyDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(680);
				letDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(681);
				structDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(682);
				enumDeclaration();
				}
				break;
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

	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentOperatorContext assignment;
		public PrefixUnaryExpressionContext prefixUnaryExpression() {
			return getRuleContext(PrefixUnaryExpressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode POW() { return getToken(TreblaParser.POW, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode MULT() { return getToken(TreblaParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(TreblaParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(TreblaParser.MOD, 0); }
		public TerminalNode ADD() { return getToken(TreblaParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(TreblaParser.SUB, 0); }
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode LANGLE() { return getToken(TreblaParser.LANGLE, 0); }
		public TerminalNode RANGLE() { return getToken(TreblaParser.RANGLE, 0); }
		public TerminalNode LE() { return getToken(TreblaParser.LE, 0); }
		public TerminalNode GE() { return getToken(TreblaParser.GE, 0); }
		public TerminalNode EXCL_EQ() { return getToken(TreblaParser.EXCL_EQ, 0); }
		public TerminalNode EQEQ() { return getToken(TreblaParser.EQEQ, 0); }
		public TerminalNode EXCL_EQEQ() { return getToken(TreblaParser.EXCL_EQEQ, 0); }
		public TerminalNode EQEQEQ() { return getToken(TreblaParser.EQEQEQ, 0); }
		public TerminalNode CONJ() { return getToken(TreblaParser.CONJ, 0); }
		public TerminalNode DISJ() { return getToken(TreblaParser.DISJ, 0); }
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(686);
			prefixUnaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(785);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,99,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(783);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(688);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(689);
						match(POW);
						setState(693);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(690);
							match(NL);
							}
							}
							setState(695);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(696);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(697);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(698);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << MOD) | (1L << DIV))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(702);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(699);
							match(NL);
							}
							}
							setState(704);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(705);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(706);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(707);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(711);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(708);
							match(NL);
							}
							}
							setState(713);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(714);
						expression(9);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(715);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(716);
						simpleIdentifier();
						setState(720);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(717);
							match(NL);
							}
							}
							setState(722);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(723);
						expression(8);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(725);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(726);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LANGLE) | (1L << RANGLE) | (1L << LE) | (1L << GE))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(730);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(727);
							match(NL);
							}
							}
							setState(732);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(733);
						expression(7);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(734);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(735);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EXCL_EQ) | (1L << EQEQ) | (1L << EQEQEQ) | (1L << EXCL_EQEQ))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(739);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(736);
							match(NL);
							}
							}
							setState(741);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(742);
						expression(6);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(743);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(747);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(744);
							match(NL);
							}
							}
							setState(749);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(750);
						match(CONJ);
						setState(754);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(751);
							match(NL);
							}
							}
							setState(756);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(757);
						expression(5);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(758);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(762);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(759);
							match(NL);
							}
							}
							setState(764);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(765);
						match(DISJ);
						setState(769);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(766);
							match(NL);
							}
							}
							setState(771);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(772);
						expression(4);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(773);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(774);
						((ExpressionContext)_localctx).assignment = assignmentOperator();
						setState(778);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==NL) {
							{
							{
							setState(775);
							match(NL);
							}
							}
							setState(780);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(781);
						expression(3);
						}
						break;
					}
					} 
				}
				setState(787);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,99,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrefixUnaryExpressionContext extends ParserRuleContext {
		public PostfixUnaryExpressionContext postfixUnaryExpression() {
			return getRuleContext(PostfixUnaryExpressionContext.class,0);
		}
		public List<PrefixUnaryOperationContext> prefixUnaryOperation() {
			return getRuleContexts(PrefixUnaryOperationContext.class);
		}
		public PrefixUnaryOperationContext prefixUnaryOperation(int i) {
			return getRuleContext(PrefixUnaryOperationContext.class,i);
		}
		public PrefixUnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixUnaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPrefixUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPrefixUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPrefixUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixUnaryExpressionContext prefixUnaryExpression() throws RecognitionException {
		PrefixUnaryExpressionContext _localctx = new PrefixUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_prefixUnaryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(791);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL))) != 0)) {
				{
				{
				setState(788);
				prefixUnaryOperation();
				}
				}
				setState(793);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(794);
			postfixUnaryExpression();
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

	public static class PostfixUnaryExpressionContext extends ParserRuleContext {
		public AtomicExpressionContext atomicExpression() {
			return getRuleContext(AtomicExpressionContext.class,0);
		}
		public List<PostfixUnaryOperationContext> postfixUnaryOperation() {
			return getRuleContexts(PostfixUnaryOperationContext.class);
		}
		public PostfixUnaryOperationContext postfixUnaryOperation(int i) {
			return getRuleContext(PostfixUnaryOperationContext.class,i);
		}
		public PostfixUnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixUnaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPostfixUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPostfixUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPostfixUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixUnaryExpressionContext postfixUnaryExpression() throws RecognitionException {
		PostfixUnaryExpressionContext _localctx = new PostfixUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_postfixUnaryExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(796);
			atomicExpression();
			setState(800);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(797);
					postfixUnaryOperation();
					}
					} 
				}
				setState(802);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
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

	public static class AtomicExpressionContext extends ParserRuleContext {
		public ParenthesizedExpressionContext parenthesizedExpression() {
			return getRuleContext(ParenthesizedExpressionContext.class,0);
		}
		public LiteralConstantContext literalConstant() {
			return getRuleContext(LiteralConstantContext.class,0);
		}
		public LineStringLiteralContext lineStringLiteral() {
			return getRuleContext(LineStringLiteralContext.class,0);
		}
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public LoopExpressionContext loopExpression() {
			return getRuleContext(LoopExpressionContext.class,0);
		}
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public LambdaContext lambda() {
			return getRuleContext(LambdaContext.class,0);
		}
		public AtomicExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterAtomicExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitAtomicExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitAtomicExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicExpressionContext atomicExpression() throws RecognitionException {
		AtomicExpressionContext _localctx = new AtomicExpressionContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_atomicExpression);
		try {
			setState(810);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(803);
				parenthesizedExpression();
				}
				break;
			case FloatLiteral:
			case IntegerLiteral:
			case BooleanLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(804);
				literalConstant();
				}
				break;
			case QUOTE_OPEN:
				enterOuterAlt(_localctx, 3);
				{
				setState(805);
				lineStringLiteral();
				}
				break;
			case IF:
			case WHEN:
			case TRY:
			case CONST:
				enterOuterAlt(_localctx, 4);
				{
				setState(806);
				conditionalExpression();
				}
				break;
			case WHILE:
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(807);
				loopExpression();
				}
				break;
			case ARCHETYPE:
			case GETTER:
			case SETTER:
			case PACKAGE:
			case PUBLIC:
			case PRIVATE:
			case INTERNAL:
			case PROTECTED:
			case SHARED:
			case STATIC:
			case OPERATOR:
			case INLINE:
			case INIT:
			case SPAWN:
			case DATA:
			case LEVEL:
			case LEVELDATA:
			case TEMP:
			case RAW:
			case NOTE:
			case EMBED:
			case Identifier:
				enterOuterAlt(_localctx, 6);
				{
				setState(808);
				simpleIdentifier();
				}
				break;
			case LCURL:
				enterOuterAlt(_localctx, 7);
				{
				setState(809);
				lambda();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class ParenthesizedExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public ParenthesizedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesizedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenthesizedExpressionContext parenthesizedExpression() throws RecognitionException {
		ParenthesizedExpressionContext _localctx = new ParenthesizedExpressionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_parenthesizedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(812);
			match(LPAREN);
			setState(813);
			expression(0);
			setState(814);
			match(RPAREN);
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

	public static class CallSuffixContext extends ParserRuleContext {
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public CallSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterCallSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitCallSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitCallSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallSuffixContext callSuffix() throws RecognitionException {
		CallSuffixContext _localctx = new CallSuffixContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_callSuffix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(816);
			valueArguments();
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

	public static class SubscriptingSuffixContext extends ParserRuleContext {
		public TerminalNode LSQUARE() { return getToken(TreblaParser.LSQUARE, 0); }
		public TerminalNode RSQUARE() { return getToken(TreblaParser.RSQUARE, 0); }
		public List<ValueArgumentContext> valueArgument() {
			return getRuleContexts(ValueArgumentContext.class);
		}
		public ValueArgumentContext valueArgument(int i) {
			return getRuleContext(ValueArgumentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public SubscriptingSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subscriptingSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterSubscriptingSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitSubscriptingSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitSubscriptingSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubscriptingSuffixContext subscriptingSuffix() throws RecognitionException {
		SubscriptingSuffixContext _localctx = new SubscriptingSuffixContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_subscriptingSuffix);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(818);
			match(LSQUARE);
			setState(830);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << ARCHETYPE) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(819);
				valueArgument();
				setState(824);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(820);
						match(COMMA);
						setState(821);
						valueArgument();
						}
						} 
					}
					setState(826);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				}
				setState(828);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(827);
					match(COMMA);
					}
				}

				}
			}

			setState(832);
			match(RSQUARE);
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

	public static class LambdaContext extends ParserRuleContext {
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public FunctionValueParametersContext functionValueParameters() {
			return getRuleContext(FunctionValueParametersContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TreblaParser.ARROW, 0); }
		public List<AnysemiContext> anysemi() {
			return getRuleContexts(AnysemiContext.class);
		}
		public AnysemiContext anysemi(int i) {
			return getRuleContext(AnysemiContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public LambdaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambda; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitLambda(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaContext lambda() throws RecognitionException {
		LambdaContext _localctx = new LambdaContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_lambda);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(834);
			match(LCURL);
			setState(838);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(835);
					match(NL);
					}
					} 
				}
				setState(840);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			}
			setState(850);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				{
				setState(841);
				functionValueParameters();
				setState(845);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(842);
					match(NL);
					}
					}
					setState(847);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(848);
				match(ARROW);
				}
				break;
			}
			setState(855);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,109,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(852);
					match(NL);
					}
					} 
				}
				setState(857);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,109,_ctx);
			}
			setState(861);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL || _la==SEMICOLON) {
				{
				{
				setState(858);
				anysemi();
				}
				}
				setState(863);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(878);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << STRUCT) | (1L << FUNC) | (1L << LET) | (1L << ARCHETYPE) | (1L << VAR) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (INFIX - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (ENUM - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(864);
				statement();
				setState(875);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL || _la==SEMICOLON) {
					{
					{
					setState(866); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(865);
							anysemi();
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(868); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,111,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(871);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << STRUCT) | (1L << FUNC) | (1L << LET) | (1L << ARCHETYPE) | (1L << VAR) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (INFIX - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (ENUM - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
						{
						setState(870);
						statement();
						}
					}

					}
					}
					setState(877);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(880);
			match(RCURL);
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

	public static class ValueArgumentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<ValueArgumentContext> valueArgument() {
			return getRuleContexts(ValueArgumentContext.class);
		}
		public ValueArgumentContext valueArgument(int i) {
			return getRuleContext(ValueArgumentContext.class,i);
		}
		public LambdaContext lambda() {
			return getRuleContext(LambdaContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public ValueArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterValueArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitValueArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitValueArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueArgumentsContext valueArguments() throws RecognitionException {
		ValueArgumentsContext _localctx = new ValueArgumentsContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_valueArguments);
		int _la;
		try {
			int _alt;
			setState(901);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(882);
				match(LPAREN);
				setState(894);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << ARCHETYPE) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
					{
					setState(883);
					valueArgument();
					setState(888);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(884);
							match(COMMA);
							setState(885);
							valueArgument();
							}
							} 
						}
						setState(890);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
					}
					setState(892);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(891);
						match(COMMA);
						}
					}

					}
				}

				setState(896);
				match(RPAREN);
				setState(898);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,118,_ctx) ) {
				case 1:
					{
					setState(897);
					lambda();
					}
					break;
				}
				}
				break;
			case LCURL:
				enterOuterAlt(_localctx, 2);
				{
				setState(900);
				lambda();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class ValueArgumentContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public ValueArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterValueArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitValueArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitValueArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueArgumentContext valueArgument() throws RecognitionException {
		ValueArgumentContext _localctx = new ValueArgumentContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_valueArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(906);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,120,_ctx) ) {
			case 1:
				{
				setState(903);
				simpleIdentifier();
				setState(904);
				match(ASSIGNMENT);
				}
				break;
			}
			setState(908);
			expression(0);
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

	public static class ConditionalExpressionContext extends ParserRuleContext {
		public IfExpressionContext ifExpression() {
			return getRuleContext(IfExpressionContext.class,0);
		}
		public WhenExpressionContext whenExpression() {
			return getRuleContext(WhenExpressionContext.class,0);
		}
		public WhenMatchExpressionContext whenMatchExpression() {
			return getRuleContext(WhenMatchExpressionContext.class,0);
		}
		public TryExpressionContext tryExpression() {
			return getRuleContext(TryExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_conditionalExpression);
		try {
			setState(914);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,121,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(910);
				ifExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(911);
				whenExpression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(912);
				whenMatchExpression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(913);
				tryExpression();
				}
				break;
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

	public static class IfExpressionContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(TreblaParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<ControlStructureBodyContext> controlStructureBody() {
			return getRuleContexts(ControlStructureBodyContext.class);
		}
		public ControlStructureBodyContext controlStructureBody(int i) {
			return getRuleContext(ControlStructureBodyContext.class,i);
		}
		public TerminalNode CONST() { return getToken(TreblaParser.CONST, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode SEMICOLON() { return getToken(TreblaParser.SEMICOLON, 0); }
		public TerminalNode ELSE() { return getToken(TreblaParser.ELSE, 0); }
		public IfExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterIfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitIfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitIfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfExpressionContext ifExpression() throws RecognitionException {
		IfExpressionContext _localctx = new IfExpressionContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_ifExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(917);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONST) {
				{
				setState(916);
				match(CONST);
				}
			}

			setState(919);
			match(IF);
			setState(923);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(920);
				match(NL);
				}
				}
				setState(925);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(926);
			match(LPAREN);
			setState(927);
			expression(0);
			setState(928);
			match(RPAREN);
			setState(932);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(929);
				match(NL);
				}
				}
				setState(934);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(935);
			controlStructureBody();
			setState(937);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,125,_ctx) ) {
			case 1:
				{
				setState(936);
				match(SEMICOLON);
				}
				break;
			}
			setState(953);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,128,_ctx) ) {
			case 1:
				{
				setState(942);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(939);
					match(NL);
					}
					}
					setState(944);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(945);
				match(ELSE);
				setState(949);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(946);
					match(NL);
					}
					}
					setState(951);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(952);
				controlStructureBody();
				}
				break;
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

	public static class WhenExpressionContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(TreblaParser.WHEN, 0); }
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public TerminalNode CONST() { return getToken(TreblaParser.CONST, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public List<WhenEntryContext> whenEntry() {
			return getRuleContexts(WhenEntryContext.class);
		}
		public WhenEntryContext whenEntry(int i) {
			return getRuleContext(WhenEntryContext.class,i);
		}
		public WhenExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenExpressionContext whenExpression() throws RecognitionException {
		WhenExpressionContext _localctx = new WhenExpressionContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_whenExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(956);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONST) {
				{
				setState(955);
				match(CONST);
				}
			}

			setState(958);
			match(WHEN);
			setState(962);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(959);
				match(NL);
				}
				}
				setState(964);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(965);
			match(LCURL);
			setState(969);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(966);
				match(NL);
				}
				}
				setState(971);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(981);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << ARCHETYPE) | (1L << IF) | (1L << ELSE) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				{
				setState(972);
				whenEntry();
				setState(976);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(973);
					match(NL);
					}
					}
					setState(978);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(983);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(984);
			match(RCURL);
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

	public static class WhenMatchExpressionContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(TreblaParser.WHEN, 0); }
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public TerminalNode LCURL() { return getToken(TreblaParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(TreblaParser.RCURL, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public List<WhenMatchEntryContext> whenMatchEntry() {
			return getRuleContexts(WhenMatchEntryContext.class);
		}
		public WhenMatchEntryContext whenMatchEntry(int i) {
			return getRuleContext(WhenMatchEntryContext.class,i);
		}
		public WhenMatchExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenMatchExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenMatchExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenMatchExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenMatchExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenMatchExpressionContext whenMatchExpression() throws RecognitionException {
		WhenMatchExpressionContext _localctx = new WhenMatchExpressionContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_whenMatchExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(986);
			match(WHEN);
			setState(990);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(987);
				match(NL);
				}
				}
				setState(992);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(993);
			match(LPAREN);
			setState(994);
			expression(0);
			setState(995);
			match(RPAREN);
			setState(999);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(996);
				match(NL);
				}
				}
				setState(1001);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1002);
			match(LCURL);
			setState(1006);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1003);
				match(NL);
				}
				}
				setState(1008);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1018);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELSE || _la==IS) {
				{
				{
				setState(1009);
				whenMatchEntry();
				setState(1013);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(1010);
					match(NL);
					}
					}
					setState(1015);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(1020);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1021);
			match(RCURL);
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

	public static class WhenEntryContext extends ParserRuleContext {
		public WhenConditionalEntryContext whenConditionalEntry() {
			return getRuleContext(WhenConditionalEntryContext.class,0);
		}
		public WhenElseEntryContext whenElseEntry() {
			return getRuleContext(WhenElseEntryContext.class,0);
		}
		public WhenEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenEntryContext whenEntry() throws RecognitionException {
		WhenEntryContext _localctx = new WhenEntryContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_whenEntry);
		try {
			setState(1025);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case LCURL:
			case MULT:
			case ADD:
			case SUB:
			case INCR:
			case DECR:
			case EXCL:
			case ARCHETYPE:
			case IF:
			case WHILE:
			case WHEN:
			case TRY:
			case FOR:
			case GETTER:
			case SETTER:
			case PACKAGE:
			case PUBLIC:
			case PRIVATE:
			case INTERNAL:
			case PROTECTED:
			case SHARED:
			case STATIC:
			case OPERATOR:
			case INLINE:
			case INIT:
			case SPAWN:
			case DATA:
			case LEVEL:
			case LEVELDATA:
			case TEMP:
			case RAW:
			case CONST:
			case NOTE:
			case EMBED:
			case QUOTE_OPEN:
			case FloatLiteral:
			case IntegerLiteral:
			case BooleanLiteral:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(1023);
				whenConditionalEntry();
				}
				break;
			case ELSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1024);
				whenElseEntry();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class WhenMatchEntryContext extends ParserRuleContext {
		public WhenMatchVariantEntryContext whenMatchVariantEntry() {
			return getRuleContext(WhenMatchVariantEntryContext.class,0);
		}
		public WhenElseEntryContext whenElseEntry() {
			return getRuleContext(WhenElseEntryContext.class,0);
		}
		public WhenMatchEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenMatchEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenMatchEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenMatchEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenMatchEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenMatchEntryContext whenMatchEntry() throws RecognitionException {
		WhenMatchEntryContext _localctx = new WhenMatchEntryContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_whenMatchEntry);
		try {
			setState(1029);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IS:
				enterOuterAlt(_localctx, 1);
				{
				setState(1027);
				whenMatchVariantEntry();
				}
				break;
			case ELSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1028);
				whenElseEntry();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class WhenConditionalEntryContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TreblaParser.ARROW, 0); }
		public ControlStructureBodyContext controlStructureBody() {
			return getRuleContext(ControlStructureBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public WhenConditionalEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenConditionalEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenConditionalEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenConditionalEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenConditionalEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenConditionalEntryContext whenConditionalEntry() throws RecognitionException {
		WhenConditionalEntryContext _localctx = new WhenConditionalEntryContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_whenConditionalEntry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1031);
			expression(0);
			setState(1035);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1032);
				match(NL);
				}
				}
				setState(1037);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1038);
			match(ARROW);
			setState(1042);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1039);
				match(NL);
				}
				}
				setState(1044);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1045);
			controlStructureBody();
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

	public static class WhenElseEntryContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(TreblaParser.ELSE, 0); }
		public TerminalNode ARROW() { return getToken(TreblaParser.ARROW, 0); }
		public ControlStructureBodyContext controlStructureBody() {
			return getRuleContext(ControlStructureBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public WhenElseEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenElseEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenElseEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenElseEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenElseEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenElseEntryContext whenElseEntry() throws RecognitionException {
		WhenElseEntryContext _localctx = new WhenElseEntryContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_whenElseEntry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1047);
			match(ELSE);
			setState(1051);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1048);
				match(NL);
				}
				}
				setState(1053);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1054);
			match(ARROW);
			setState(1058);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1055);
				match(NL);
				}
				}
				setState(1060);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1061);
			controlStructureBody();
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

	public static class WhenMatchVariantEntryContext extends ParserRuleContext {
		public TerminalNode IS() { return getToken(TreblaParser.IS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(TreblaParser.ARROW, 0); }
		public ControlStructureBodyContext controlStructureBody() {
			return getRuleContext(ControlStructureBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode AS() { return getToken(TreblaParser.AS, 0); }
		public DestructuringTupleContext destructuringTuple() {
			return getRuleContext(DestructuringTupleContext.class,0);
		}
		public WhenMatchVariantEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenMatchVariantEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhenMatchVariantEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhenMatchVariantEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhenMatchVariantEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenMatchVariantEntryContext whenMatchVariantEntry() throws RecognitionException {
		WhenMatchVariantEntryContext _localctx = new WhenMatchVariantEntryContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_whenMatchVariantEntry);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1063);
			match(IS);
			setState(1067);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1064);
				match(NL);
				}
				}
				setState(1069);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1070);
			expression(0);
			setState(1074);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1071);
					match(NL);
					}
					} 
				}
				setState(1076);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,146,_ctx);
			}
			setState(1085);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1077);
				match(AS);
				setState(1081);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(1078);
					match(NL);
					}
					}
					setState(1083);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1084);
				destructuringTuple();
				}
			}

			setState(1090);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1087);
				match(NL);
				}
				}
				setState(1092);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1093);
			match(ARROW);
			setState(1097);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1094);
				match(NL);
				}
				}
				setState(1099);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1100);
			controlStructureBody();
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

	public static class DestructuringTupleContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public List<SimpleIdentifierContext> simpleIdentifier() {
			return getRuleContexts(SimpleIdentifierContext.class);
		}
		public SimpleIdentifierContext simpleIdentifier(int i) {
			return getRuleContext(SimpleIdentifierContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(TreblaParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(TreblaParser.COMMA, i);
		}
		public DestructuringTupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_destructuringTuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterDestructuringTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitDestructuringTuple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitDestructuringTuple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DestructuringTupleContext destructuringTuple() throws RecognitionException {
		DestructuringTupleContext _localctx = new DestructuringTupleContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_destructuringTuple);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1102);
			match(LPAREN);
			setState(1114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) {
				{
				setState(1103);
				simpleIdentifier();
				setState(1108);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1104);
						match(COMMA);
						setState(1105);
						simpleIdentifier();
						}
						} 
					}
					setState(1110);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
				}
				setState(1112);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(1111);
					match(COMMA);
					}
				}

				}
			}

			setState(1116);
			match(RPAREN);
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

	public static class TryExpressionContext extends ParserRuleContext {
		public TerminalNode TRY() { return getToken(TreblaParser.TRY, 0); }
		public List<ControlStructureBodyContext> controlStructureBody() {
			return getRuleContexts(ControlStructureBodyContext.class);
		}
		public ControlStructureBodyContext controlStructureBody(int i) {
			return getRuleContext(ControlStructureBodyContext.class,i);
		}
		public TerminalNode EXCEPT() { return getToken(TreblaParser.EXCEPT, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode SEMICOLON() { return getToken(TreblaParser.SEMICOLON, 0); }
		public TryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterTryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitTryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitTryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryExpressionContext tryExpression() throws RecognitionException {
		TryExpressionContext _localctx = new TryExpressionContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_tryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1118);
			match(TRY);
			setState(1122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1119);
				match(NL);
				}
				}
				setState(1124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1125);
			controlStructureBody();
			setState(1127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(1126);
				match(SEMICOLON);
				}
			}

			setState(1132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1129);
				match(NL);
				}
				}
				setState(1134);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1135);
			match(EXCEPT);
			setState(1139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1136);
				match(NL);
				}
				}
				setState(1141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1142);
			controlStructureBody();
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

	public static class LoopExpressionContext extends ParserRuleContext {
		public WhileExpressionContext whileExpression() {
			return getRuleContext(WhileExpressionContext.class,0);
		}
		public ForExpressionContext forExpression() {
			return getRuleContext(ForExpressionContext.class,0);
		}
		public LoopExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterLoopExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitLoopExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitLoopExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopExpressionContext loopExpression() throws RecognitionException {
		LoopExpressionContext _localctx = new LoopExpressionContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_loopExpression);
		try {
			setState(1146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1144);
				whileExpression();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(1145);
				forExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class WhileExpressionContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(TreblaParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public ControlStructureBodyContext controlStructureBody() {
			return getRuleContext(ControlStructureBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public WhileExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterWhileExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitWhileExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitWhileExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileExpressionContext whileExpression() throws RecognitionException {
		WhileExpressionContext _localctx = new WhileExpressionContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_whileExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1148);
			match(WHILE);
			setState(1152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1149);
				match(NL);
				}
				}
				setState(1154);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1155);
			match(LPAREN);
			setState(1156);
			expression(0);
			setState(1157);
			match(RPAREN);
			setState(1161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1158);
				match(NL);
				}
				}
				setState(1163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1164);
			controlStructureBody();
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

	public static class ForExpressionContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(TreblaParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(TreblaParser.LPAREN, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(TreblaParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(TreblaParser.SEMICOLON, i);
		}
		public TerminalNode RPAREN() { return getToken(TreblaParser.RPAREN, 0); }
		public ControlStructureBodyContext controlStructureBody() {
			return getRuleContext(ControlStructureBodyContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public ForInitializerContext forInitializer() {
			return getRuleContext(ForInitializerContext.class,0);
		}
		public ForConditionContext forCondition() {
			return getRuleContext(ForConditionContext.class,0);
		}
		public ForAfterthoughtContext forAfterthought() {
			return getRuleContext(ForAfterthoughtContext.class,0);
		}
		public ForExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterForExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitForExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitForExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForExpressionContext forExpression() throws RecognitionException {
		ForExpressionContext _localctx = new ForExpressionContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_forExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1166);
			match(FOR);
			setState(1170);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1167);
				match(NL);
				}
				}
				setState(1172);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1173);
			match(LPAREN);
			setState(1175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << STRUCT) | (1L << FUNC) | (1L << LET) | (1L << ARCHETYPE) | (1L << VAR) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (INFIX - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (ENUM - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1174);
				forInitializer();
				}
			}

			setState(1177);
			match(SEMICOLON);
			setState(1179);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << ARCHETYPE) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1178);
				forCondition();
				}
			}

			setState(1181);
			match(SEMICOLON);
			setState(1183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LCURL) | (1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL) | (1L << ARCHETYPE) | (1L << IF) | (1L << WHILE) | (1L << WHEN) | (1L << TRY) | (1L << FOR) | (1L << GETTER))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (SETTER - 64)) | (1L << (PACKAGE - 64)) | (1L << (PUBLIC - 64)) | (1L << (PRIVATE - 64)) | (1L << (INTERNAL - 64)) | (1L << (PROTECTED - 64)) | (1L << (SHARED - 64)) | (1L << (STATIC - 64)) | (1L << (OPERATOR - 64)) | (1L << (INLINE - 64)) | (1L << (INIT - 64)) | (1L << (SPAWN - 64)) | (1L << (DATA - 64)) | (1L << (LEVEL - 64)) | (1L << (LEVELDATA - 64)) | (1L << (TEMP - 64)) | (1L << (RAW - 64)) | (1L << (CONST - 64)) | (1L << (NOTE - 64)) | (1L << (EMBED - 64)) | (1L << (QUOTE_OPEN - 64)) | (1L << (FloatLiteral - 64)) | (1L << (IntegerLiteral - 64)) | (1L << (BooleanLiteral - 64)) | (1L << (Identifier - 64)))) != 0)) {
				{
				setState(1182);
				forAfterthought();
				}
			}

			setState(1185);
			match(RPAREN);
			setState(1189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1186);
				match(NL);
				}
				}
				setState(1191);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1192);
			controlStructureBody();
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

	public static class ForInitializerContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterForInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitForInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitForInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitializerContext forInitializer() throws RecognitionException {
		ForInitializerContext _localctx = new ForInitializerContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_forInitializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1194);
			statement();
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

	public static class ForConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterForCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitForCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitForCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForConditionContext forCondition() throws RecognitionException {
		ForConditionContext _localctx = new ForConditionContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_forCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1196);
			expression(0);
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

	public static class ForAfterthoughtContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForAfterthoughtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAfterthought; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterForAfterthought(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitForAfterthought(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitForAfterthought(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForAfterthoughtContext forAfterthought() throws RecognitionException {
		ForAfterthoughtContext _localctx = new ForAfterthoughtContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_forAfterthought);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1198);
			expression(0);
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

	public static class ControlStructureBodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ControlStructureBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_controlStructureBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterControlStructureBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitControlStructureBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitControlStructureBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlStructureBodyContext controlStructureBody() throws RecognitionException {
		ControlStructureBodyContext _localctx = new ControlStructureBodyContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_controlStructureBody);
		try {
			setState(1202);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1200);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1201);
				expression(0);
				}
				break;
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

	public static class ModifierListContext extends ParserRuleContext {
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ModifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterModifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitModifierList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitModifierList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierListContext modifierList() throws RecognitionException {
		ModifierListContext _localctx = new ModifierListContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_modifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (PUBLIC - 70)) | (1L << (PRIVATE - 70)) | (1L << (SHARED - 70)) | (1L << (STATIC - 70)) | (1L << (OPERATOR - 70)) | (1L << (INIT - 70)) | (1L << (SPAWN - 70)) | (1L << (INFIX - 70)) | (1L << (DATA - 70)) | (1L << (LEVEL - 70)) | (1L << (LEVELDATA - 70)) | (1L << (TEMP - 70)) | (1L << (RAW - 70)) | (1L << (CONST - 70)))) != 0)) {
				{
				{
				setState(1204);
				modifier();
				}
				}
				setState(1209);
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

	public static class ModifierContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(TreblaParser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(TreblaParser.PRIVATE, 0); }
		public TerminalNode SHARED() { return getToken(TreblaParser.SHARED, 0); }
		public TerminalNode STATIC() { return getToken(TreblaParser.STATIC, 0); }
		public TerminalNode OPERATOR() { return getToken(TreblaParser.OPERATOR, 0); }
		public TerminalNode INIT() { return getToken(TreblaParser.INIT, 0); }
		public TerminalNode SPAWN() { return getToken(TreblaParser.SPAWN, 0); }
		public TerminalNode DATA() { return getToken(TreblaParser.DATA, 0); }
		public TerminalNode LEVEL() { return getToken(TreblaParser.LEVEL, 0); }
		public TerminalNode LEVELDATA() { return getToken(TreblaParser.LEVELDATA, 0); }
		public TerminalNode TEMP() { return getToken(TreblaParser.TEMP, 0); }
		public TerminalNode RAW() { return getToken(TreblaParser.RAW, 0); }
		public TerminalNode CONST() { return getToken(TreblaParser.CONST, 0); }
		public TerminalNode INFIX() { return getToken(TreblaParser.INFIX, 0); }
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_modifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1210);
			_la = _input.LA(1);
			if ( !(((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (PUBLIC - 70)) | (1L << (PRIVATE - 70)) | (1L << (SHARED - 70)) | (1L << (STATIC - 70)) | (1L << (OPERATOR - 70)) | (1L << (INIT - 70)) | (1L << (SPAWN - 70)) | (1L << (INFIX - 70)) | (1L << (DATA - 70)) | (1L << (LEVEL - 70)) | (1L << (LEVELDATA - 70)) | (1L << (TEMP - 70)) | (1L << (RAW - 70)) | (1L << (CONST - 70)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1214);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NL) {
				{
				{
				setState(1211);
				match(NL);
				}
				}
				setState(1216);
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

	public static class IdentifierContext extends ParserRuleContext {
		public List<SimpleIdentifierContext> simpleIdentifier() {
			return getRuleContexts(SimpleIdentifierContext.class);
		}
		public SimpleIdentifierContext simpleIdentifier(int i) {
			return getRuleContext(SimpleIdentifierContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(TreblaParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(TreblaParser.DOT, i);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_identifier);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1217);
			simpleIdentifier();
			setState(1228);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1221);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==NL) {
						{
						{
						setState(1218);
						match(NL);
						}
						}
						setState(1223);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1224);
					match(DOT);
					setState(1225);
					simpleIdentifier();
					}
					} 
				}
				setState(1230);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
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

	public static class SimpleIdentifierContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TreblaParser.Identifier, 0); }
		public TerminalNode PUBLIC() { return getToken(TreblaParser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(TreblaParser.PRIVATE, 0); }
		public TerminalNode INTERNAL() { return getToken(TreblaParser.INTERNAL, 0); }
		public TerminalNode PROTECTED() { return getToken(TreblaParser.PROTECTED, 0); }
		public TerminalNode SHARED() { return getToken(TreblaParser.SHARED, 0); }
		public TerminalNode STATIC() { return getToken(TreblaParser.STATIC, 0); }
		public TerminalNode OPERATOR() { return getToken(TreblaParser.OPERATOR, 0); }
		public TerminalNode INLINE() { return getToken(TreblaParser.INLINE, 0); }
		public TerminalNode INIT() { return getToken(TreblaParser.INIT, 0); }
		public TerminalNode SPAWN() { return getToken(TreblaParser.SPAWN, 0); }
		public TerminalNode DATA() { return getToken(TreblaParser.DATA, 0); }
		public TerminalNode LEVEL() { return getToken(TreblaParser.LEVEL, 0); }
		public TerminalNode LEVELDATA() { return getToken(TreblaParser.LEVELDATA, 0); }
		public TerminalNode RAW() { return getToken(TreblaParser.RAW, 0); }
		public TerminalNode PACKAGE() { return getToken(TreblaParser.PACKAGE, 0); }
		public TerminalNode TEMP() { return getToken(TreblaParser.TEMP, 0); }
		public TerminalNode ARCHETYPE() { return getToken(TreblaParser.ARCHETYPE, 0); }
		public TerminalNode NOTE() { return getToken(TreblaParser.NOTE, 0); }
		public TerminalNode SETTER() { return getToken(TreblaParser.SETTER, 0); }
		public TerminalNode GETTER() { return getToken(TreblaParser.GETTER, 0); }
		public TerminalNode EMBED() { return getToken(TreblaParser.EMBED, 0); }
		public SimpleIdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleIdentifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterSimpleIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitSimpleIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitSimpleIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleIdentifierContext simpleIdentifier() throws RecognitionException {
		SimpleIdentifierContext _localctx = new SimpleIdentifierContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_simpleIdentifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1231);
			_la = _input.LA(1);
			if ( !(((((_la - 53)) & ~0x3f) == 0 && ((1L << (_la - 53)) & ((1L << (ARCHETYPE - 53)) | (1L << (GETTER - 53)) | (1L << (SETTER - 53)) | (1L << (PACKAGE - 53)) | (1L << (PUBLIC - 53)) | (1L << (PRIVATE - 53)) | (1L << (INTERNAL - 53)) | (1L << (PROTECTED - 53)) | (1L << (SHARED - 53)) | (1L << (STATIC - 53)) | (1L << (OPERATOR - 53)) | (1L << (INLINE - 53)) | (1L << (INIT - 53)) | (1L << (SPAWN - 53)) | (1L << (DATA - 53)) | (1L << (LEVEL - 53)) | (1L << (LEVELDATA - 53)) | (1L << (TEMP - 53)) | (1L << (RAW - 53)) | (1L << (NOTE - 53)) | (1L << (EMBED - 53)) | (1L << (Identifier - 53)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class LiteralConstantContext extends ParserRuleContext {
		public TerminalNode BooleanLiteral() { return getToken(TreblaParser.BooleanLiteral, 0); }
		public TerminalNode IntegerLiteral() { return getToken(TreblaParser.IntegerLiteral, 0); }
		public TerminalNode FloatLiteral() { return getToken(TreblaParser.FloatLiteral, 0); }
		public LiteralConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterLiteralConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitLiteralConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitLiteralConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralConstantContext literalConstant() throws RecognitionException {
		LiteralConstantContext _localctx = new LiteralConstantContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_literalConstant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1233);
			_la = _input.LA(1);
			if ( !(((((_la - 91)) & ~0x3f) == 0 && ((1L << (_la - 91)) & ((1L << (FloatLiteral - 91)) | (1L << (IntegerLiteral - 91)) | (1L << (BooleanLiteral - 91)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class LineStringLiteralContext extends ParserRuleContext {
		public TerminalNode QUOTE_OPEN() { return getToken(TreblaParser.QUOTE_OPEN, 0); }
		public TerminalNode QUOTE_CLOSE() { return getToken(TreblaParser.QUOTE_CLOSE, 0); }
		public List<TerminalNode> LineStrText() { return getTokens(TreblaParser.LineStrText); }
		public TerminalNode LineStrText(int i) {
			return getToken(TreblaParser.LineStrText, i);
		}
		public List<TerminalNode> LineStrEscapedChar() { return getTokens(TreblaParser.LineStrEscapedChar); }
		public TerminalNode LineStrEscapedChar(int i) {
			return getToken(TreblaParser.LineStrEscapedChar, i);
		}
		public LineStringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lineStringLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterLineStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitLineStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitLineStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineStringLiteralContext lineStringLiteral() throws RecognitionException {
		LineStringLiteralContext _localctx = new LineStringLiteralContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_lineStringLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1235);
			match(QUOTE_OPEN);
			setState(1239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LineStrText || _la==LineStrEscapedChar) {
				{
				{
				setState(1236);
				_la = _input.LA(1);
				if ( !(_la==LineStrText || _la==LineStrEscapedChar) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(1241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1242);
			match(QUOTE_CLOSE);
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

	public static class PrefixUnaryOperationContext extends ParserRuleContext {
		public TerminalNode INCR() { return getToken(TreblaParser.INCR, 0); }
		public TerminalNode DECR() { return getToken(TreblaParser.DECR, 0); }
		public TerminalNode ADD() { return getToken(TreblaParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(TreblaParser.SUB, 0); }
		public TerminalNode EXCL() { return getToken(TreblaParser.EXCL, 0); }
		public TerminalNode MULT() { return getToken(TreblaParser.MULT, 0); }
		public PrefixUnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixUnaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPrefixUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPrefixUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPrefixUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixUnaryOperationContext prefixUnaryOperation() throws RecognitionException {
		PrefixUnaryOperationContext _localctx = new PrefixUnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_prefixUnaryOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1244);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << ADD) | (1L << SUB) | (1L << INCR) | (1L << DECR) | (1L << EXCL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class PostfixUnaryOperationContext extends ParserRuleContext {
		public TerminalNode INCR() { return getToken(TreblaParser.INCR, 0); }
		public TerminalNode DECR() { return getToken(TreblaParser.DECR, 0); }
		public List<TerminalNode> EXCL() { return getTokens(TreblaParser.EXCL); }
		public TerminalNode EXCL(int i) {
			return getToken(TreblaParser.EXCL, i);
		}
		public CallSuffixContext callSuffix() {
			return getRuleContext(CallSuffixContext.class,0);
		}
		public SubscriptingSuffixContext subscriptingSuffix() {
			return getRuleContext(SubscriptingSuffixContext.class,0);
		}
		public MemberAccessOperatorContext memberAccessOperator() {
			return getRuleContext(MemberAccessOperatorContext.class,0);
		}
		public SimpleIdentifierContext simpleIdentifier() {
			return getRuleContext(SimpleIdentifierContext.class,0);
		}
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public PostfixUnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixUnaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterPostfixUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitPostfixUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitPostfixUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixUnaryOperationContext postfixUnaryOperation() throws RecognitionException {
		PostfixUnaryOperationContext _localctx = new PostfixUnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_postfixUnaryOperation);
		int _la;
		try {
			setState(1261);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INCR:
				enterOuterAlt(_localctx, 1);
				{
				setState(1246);
				match(INCR);
				}
				break;
			case DECR:
				enterOuterAlt(_localctx, 2);
				{
				setState(1247);
				match(DECR);
				}
				break;
			case EXCL:
				enterOuterAlt(_localctx, 3);
				{
				setState(1248);
				match(EXCL);
				setState(1249);
				match(EXCL);
				}
				break;
			case LPAREN:
			case LCURL:
				enterOuterAlt(_localctx, 4);
				{
				setState(1250);
				callSuffix();
				}
				break;
			case LSQUARE:
				enterOuterAlt(_localctx, 5);
				{
				setState(1251);
				subscriptingSuffix();
				}
				break;
			case NL:
			case DOT:
				enterOuterAlt(_localctx, 6);
				{
				setState(1255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(1252);
					match(NL);
					}
					}
					setState(1257);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1258);
				memberAccessOperator();
				setState(1259);
				simpleIdentifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class MemberAccessOperatorContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(TreblaParser.DOT, 0); }
		public MemberAccessOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberAccessOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterMemberAccessOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitMemberAccessOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitMemberAccessOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberAccessOperatorContext memberAccessOperator() throws RecognitionException {
		MemberAccessOperatorContext _localctx = new MemberAccessOperatorContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_memberAccessOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1263);
			match(DOT);
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

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public TerminalNode ASSIGNMENT() { return getToken(TreblaParser.ASSIGNMENT, 0); }
		public TerminalNode ADD_ASSIGNMENT() { return getToken(TreblaParser.ADD_ASSIGNMENT, 0); }
		public TerminalNode SUB_ASSIGNMENT() { return getToken(TreblaParser.SUB_ASSIGNMENT, 0); }
		public TerminalNode MULT_ASSIGNMENT() { return getToken(TreblaParser.MULT_ASSIGNMENT, 0); }
		public TerminalNode DIV_ASSIGNMENT() { return getToken(TreblaParser.DIV_ASSIGNMENT, 0); }
		public TerminalNode MOD_ASSIGNMENT() { return getToken(TreblaParser.MOD_ASSIGNMENT, 0); }
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1265);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGNMENT) | (1L << ADD_ASSIGNMENT) | (1L << SUB_ASSIGNMENT) | (1L << MULT_ASSIGNMENT) | (1L << DIV_ASSIGNMENT) | (1L << MOD_ASSIGNMENT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class SemiContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(TreblaParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(TreblaParser.NL, i);
		}
		public TerminalNode SEMICOLON() { return getToken(TreblaParser.SEMICOLON, 0); }
		public SemiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semi; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterSemi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitSemi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitSemi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SemiContext semi() throws RecognitionException {
		SemiContext _localctx = new SemiContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_semi);
		int _la;
		try {
			int _alt;
			setState(1285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,177,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1268); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1267);
						match(NL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1270); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,174,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1275);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(1272);
					match(NL);
					}
					}
					setState(1277);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1278);
				match(SEMICOLON);
				setState(1282);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,176,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1279);
						match(NL);
						}
						} 
					}
					setState(1284);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,176,_ctx);
				}
				}
				break;
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

	public static class AnysemiContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(TreblaParser.NL, 0); }
		public TerminalNode SEMICOLON() { return getToken(TreblaParser.SEMICOLON, 0); }
		public AnysemiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anysemi; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).enterAnysemi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TreblaParserListener ) ((TreblaParserListener)listener).exitAnysemi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TreblaParserVisitor ) return ((TreblaParserVisitor<? extends T>)visitor).visitAnysemi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnysemiContext anysemi() throws RecognitionException {
		AnysemiContext _localctx = new AnysemiContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_anysemi);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1287);
			_la = _input.LA(1);
			if ( !(_la==NL || _la==SEMICOLON) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 35:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3m\u050c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\3\2\7\2\u009a\n\2\f\2\16\2\u009d\13\2\3\2\5\2\u00a0"+
		"\n\2\3\2\7\2\u00a3\n\2\f\2\16\2\u00a6\13\2\3\2\3\2\7\2\u00aa\n\2\f\2\16"+
		"\2\u00ad\13\2\3\2\3\2\6\2\u00b1\n\2\r\2\16\2\u00b2\3\2\5\2\u00b6\n\2\7"+
		"\2\u00b8\n\2\f\2\16\2\u00bb\13\2\5\2\u00bd\n\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\4\7\4\u00c6\n\4\f\4\16\4\u00c9\13\4\3\5\3\5\3\5\3\5\5\5\u00cf\n\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00da\n\6\3\7\3\7\3\7\7\7\u00df"+
		"\n\7\f\7\16\7\u00e2\13\7\3\7\3\7\7\7\u00e6\n\7\f\7\16\7\u00e9\13\7\3\7"+
		"\3\7\5\7\u00ed\n\7\3\7\7\7\u00f0\n\7\f\7\16\7\u00f3\13\7\3\7\3\7\7\7\u00f7"+
		"\n\7\f\7\16\7\u00fa\13\7\3\7\3\7\7\7\u00fe\n\7\f\7\16\7\u0101\13\7\3\7"+
		"\3\7\7\7\u0105\n\7\f\7\16\7\u0108\13\7\3\7\5\7\u010b\n\7\3\7\7\7\u010e"+
		"\n\7\f\7\16\7\u0111\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u011c"+
		"\n\b\3\t\3\t\3\t\3\t\7\t\u0122\n\t\f\t\16\t\u0125\13\t\3\t\5\t\u0128\n"+
		"\t\5\t\u012a\n\t\3\t\3\t\3\n\3\n\3\n\7\n\u0131\n\n\f\n\16\n\u0134\13\n"+
		"\3\n\5\n\u0137\n\n\3\13\3\13\3\13\3\13\3\13\5\13\u013e\n\13\3\13\7\13"+
		"\u0141\n\13\f\13\16\13\u0144\13\13\3\13\3\13\7\13\u0148\n\13\f\13\16\13"+
		"\u014b\13\13\3\13\5\13\u014e\n\13\3\f\3\f\3\f\7\f\u0153\n\f\f\f\16\f\u0156"+
		"\13\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\7\r\u0160\n\r\f\r\16\r\u0163\13"+
		"\r\3\r\5\r\u0166\n\r\5\r\u0168\n\r\3\r\5\r\u016b\n\r\3\16\3\16\3\16\3"+
		"\16\7\16\u0171\n\16\f\16\16\16\u0174\13\16\3\16\5\16\u0177\n\16\5\16\u0179"+
		"\n\16\3\16\5\16\u017c\n\16\3\17\5\17\u017f\n\17\3\17\3\17\3\20\3\20\3"+
		"\20\7\20\u0186\n\20\f\20\16\20\u0189\13\20\3\20\3\20\7\20\u018d\n\20\f"+
		"\20\16\20\u0190\13\20\3\20\3\20\3\21\3\21\7\21\u0196\n\21\f\21\16\21\u0199"+
		"\13\21\3\21\3\21\7\21\u019d\n\21\f\21\16\21\u01a0\13\21\3\21\3\21\7\21"+
		"\u01a4\n\21\f\21\16\21\u01a7\13\21\3\21\5\21\u01aa\n\21\7\21\u01ac\n\21"+
		"\f\21\16\21\u01af\13\21\5\21\u01b1\n\21\3\21\7\21\u01b4\n\21\f\21\16\21"+
		"\u01b7\13\21\3\21\5\21\u01ba\n\21\3\21\7\21\u01bd\n\21\f\21\16\21\u01c0"+
		"\13\21\3\21\3\21\3\22\3\22\7\22\u01c6\n\22\f\22\16\22\u01c9\13\22\3\22"+
		"\3\22\7\22\u01cd\n\22\f\22\16\22\u01d0\13\22\3\22\5\22\u01d3\n\22\3\23"+
		"\3\23\7\23\u01d7\n\23\f\23\16\23\u01da\13\23\3\23\3\23\3\24\3\24\7\24"+
		"\u01e0\n\24\f\24\16\24\u01e3\13\24\3\24\3\24\7\24\u01e7\n\24\f\24\16\24"+
		"\u01ea\13\24\3\24\3\24\3\25\3\25\7\25\u01f0\n\25\f\25\16\25\u01f3\13\25"+
		"\3\25\7\25\u01f6\n\25\f\25\16\25\u01f9\13\25\3\25\7\25\u01fc\n\25\f\25"+
		"\16\25\u01ff\13\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u020a"+
		"\n\26\3\26\7\26\u020d\n\26\f\26\16\26\u0210\13\26\3\27\5\27\u0213\n\27"+
		"\3\27\3\27\7\27\u0217\n\27\f\27\16\27\u021a\13\27\3\27\3\27\7\27\u021e"+
		"\n\27\f\27\16\27\u0221\13\27\3\27\3\27\7\27\u0225\n\27\f\27\16\27\u0228"+
		"\13\27\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\7\31\u0233\n\31\f"+
		"\31\16\31\u0236\13\31\5\31\u0238\n\31\3\31\3\31\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\33\5\33\u0243\n\33\3\33\7\33\u0246\n\33\f\33\16\33\u0249\13"+
		"\33\3\33\3\33\7\33\u024d\n\33\f\33\16\33\u0250\13\33\3\33\3\33\3\34\3"+
		"\34\3\35\3\35\3\35\3\35\3\35\5\35\u025b\n\35\3\35\7\35\u025e\n\35\f\35"+
		"\16\35\u0261\13\35\3\35\3\35\7\35\u0265\n\35\f\35\16\35\u0268\13\35\3"+
		"\35\3\35\3\36\3\36\7\36\u026e\n\36\f\36\16\36\u0271\13\36\3\36\3\36\3"+
		"\37\3\37\3\37\5\37\u0278\n\37\3\37\3\37\5\37\u027c\n\37\3 \5 \u027f\n"+
		" \3 \3 \3 \5 \u0284\n \3 \3 \5 \u0288\n \3!\3!\3\"\3\"\7\"\u028e\n\"\f"+
		"\"\16\"\u0291\13\"\3\"\3\"\6\"\u0295\n\"\r\"\16\"\u0296\3\"\5\"\u029a"+
		"\n\"\7\"\u029c\n\"\f\"\16\"\u029f\13\"\5\"\u02a1\n\"\3\"\3\"\3#\3#\5#"+
		"\u02a7\n#\3$\3$\3$\3$\3$\5$\u02ae\n$\3%\3%\3%\3%\3%\3%\7%\u02b6\n%\f%"+
		"\16%\u02b9\13%\3%\3%\3%\3%\7%\u02bf\n%\f%\16%\u02c2\13%\3%\3%\3%\3%\7"+
		"%\u02c8\n%\f%\16%\u02cb\13%\3%\3%\3%\3%\7%\u02d1\n%\f%\16%\u02d4\13%\3"+
		"%\3%\3%\3%\3%\7%\u02db\n%\f%\16%\u02de\13%\3%\3%\3%\3%\7%\u02e4\n%\f%"+
		"\16%\u02e7\13%\3%\3%\3%\7%\u02ec\n%\f%\16%\u02ef\13%\3%\3%\7%\u02f3\n"+
		"%\f%\16%\u02f6\13%\3%\3%\3%\7%\u02fb\n%\f%\16%\u02fe\13%\3%\3%\7%\u0302"+
		"\n%\f%\16%\u0305\13%\3%\3%\3%\3%\7%\u030b\n%\f%\16%\u030e\13%\3%\3%\7"+
		"%\u0312\n%\f%\16%\u0315\13%\3&\7&\u0318\n&\f&\16&\u031b\13&\3&\3&\3\'"+
		"\3\'\7\'\u0321\n\'\f\'\16\'\u0324\13\'\3(\3(\3(\3(\3(\3(\3(\5(\u032d\n"+
		"(\3)\3)\3)\3)\3*\3*\3+\3+\3+\3+\7+\u0339\n+\f+\16+\u033c\13+\3+\5+\u033f"+
		"\n+\5+\u0341\n+\3+\3+\3,\3,\7,\u0347\n,\f,\16,\u034a\13,\3,\3,\7,\u034e"+
		"\n,\f,\16,\u0351\13,\3,\3,\5,\u0355\n,\3,\7,\u0358\n,\f,\16,\u035b\13"+
		",\3,\7,\u035e\n,\f,\16,\u0361\13,\3,\3,\6,\u0365\n,\r,\16,\u0366\3,\5"+
		",\u036a\n,\7,\u036c\n,\f,\16,\u036f\13,\5,\u0371\n,\3,\3,\3-\3-\3-\3-"+
		"\7-\u0379\n-\f-\16-\u037c\13-\3-\5-\u037f\n-\5-\u0381\n-\3-\3-\5-\u0385"+
		"\n-\3-\5-\u0388\n-\3.\3.\3.\5.\u038d\n.\3.\3.\3/\3/\3/\3/\5/\u0395\n/"+
		"\3\60\5\60\u0398\n\60\3\60\3\60\7\60\u039c\n\60\f\60\16\60\u039f\13\60"+
		"\3\60\3\60\3\60\3\60\7\60\u03a5\n\60\f\60\16\60\u03a8\13\60\3\60\3\60"+
		"\5\60\u03ac\n\60\3\60\7\60\u03af\n\60\f\60\16\60\u03b2\13\60\3\60\3\60"+
		"\7\60\u03b6\n\60\f\60\16\60\u03b9\13\60\3\60\5\60\u03bc\n\60\3\61\5\61"+
		"\u03bf\n\61\3\61\3\61\7\61\u03c3\n\61\f\61\16\61\u03c6\13\61\3\61\3\61"+
		"\7\61\u03ca\n\61\f\61\16\61\u03cd\13\61\3\61\3\61\7\61\u03d1\n\61\f\61"+
		"\16\61\u03d4\13\61\7\61\u03d6\n\61\f\61\16\61\u03d9\13\61\3\61\3\61\3"+
		"\62\3\62\7\62\u03df\n\62\f\62\16\62\u03e2\13\62\3\62\3\62\3\62\3\62\7"+
		"\62\u03e8\n\62\f\62\16\62\u03eb\13\62\3\62\3\62\7\62\u03ef\n\62\f\62\16"+
		"\62\u03f2\13\62\3\62\3\62\7\62\u03f6\n\62\f\62\16\62\u03f9\13\62\7\62"+
		"\u03fb\n\62\f\62\16\62\u03fe\13\62\3\62\3\62\3\63\3\63\5\63\u0404\n\63"+
		"\3\64\3\64\5\64\u0408\n\64\3\65\3\65\7\65\u040c\n\65\f\65\16\65\u040f"+
		"\13\65\3\65\3\65\7\65\u0413\n\65\f\65\16\65\u0416\13\65\3\65\3\65\3\66"+
		"\3\66\7\66\u041c\n\66\f\66\16\66\u041f\13\66\3\66\3\66\7\66\u0423\n\66"+
		"\f\66\16\66\u0426\13\66\3\66\3\66\3\67\3\67\7\67\u042c\n\67\f\67\16\67"+
		"\u042f\13\67\3\67\3\67\7\67\u0433\n\67\f\67\16\67\u0436\13\67\3\67\3\67"+
		"\7\67\u043a\n\67\f\67\16\67\u043d\13\67\3\67\5\67\u0440\n\67\3\67\7\67"+
		"\u0443\n\67\f\67\16\67\u0446\13\67\3\67\3\67\7\67\u044a\n\67\f\67\16\67"+
		"\u044d\13\67\3\67\3\67\38\38\38\38\78\u0455\n8\f8\168\u0458\138\38\58"+
		"\u045b\n8\58\u045d\n8\38\38\39\39\79\u0463\n9\f9\169\u0466\139\39\39\5"+
		"9\u046a\n9\39\79\u046d\n9\f9\169\u0470\139\39\39\79\u0474\n9\f9\169\u0477"+
		"\139\39\39\3:\3:\5:\u047d\n:\3;\3;\7;\u0481\n;\f;\16;\u0484\13;\3;\3;"+
		"\3;\3;\7;\u048a\n;\f;\16;\u048d\13;\3;\3;\3<\3<\7<\u0493\n<\f<\16<\u0496"+
		"\13<\3<\3<\5<\u049a\n<\3<\3<\5<\u049e\n<\3<\3<\5<\u04a2\n<\3<\3<\7<\u04a6"+
		"\n<\f<\16<\u04a9\13<\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\5@\u04b5\n@\3A\7A\u04b8"+
		"\nA\fA\16A\u04bb\13A\3B\3B\7B\u04bf\nB\fB\16B\u04c2\13B\3C\3C\7C\u04c6"+
		"\nC\fC\16C\u04c9\13C\3C\3C\7C\u04cd\nC\fC\16C\u04d0\13C\3D\3D\3E\3E\3"+
		"F\3F\7F\u04d8\nF\fF\16F\u04db\13F\3F\3F\3G\3G\3H\3H\3H\3H\3H\3H\3H\7H"+
		"\u04e8\nH\fH\16H\u04eb\13H\3H\3H\3H\5H\u04f0\nH\3I\3I\3J\3J\3K\6K\u04f7"+
		"\nK\rK\16K\u04f8\3K\7K\u04fc\nK\fK\16K\u04ff\13K\3K\3K\7K\u0503\nK\fK"+
		"\16K\u0506\13K\5K\u0508\nK\3L\3L\3L\2\3HM\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080"+
		"\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\2\16"+
		"\3\2EF\3\2\20\22\3\2\23\24\3\2&)\3\2*-\5\2HILNPX\b\2\67\67ABGQSWYZ``\3"+
		"\2]_\3\2lm\5\2\20\20\23\26\31\31\3\2\35\"\4\2\6\6\34\34\2\u0591\2\u009b"+
		"\3\2\2\2\4\u00c0\3\2\2\2\6\u00c7\3\2\2\2\b\u00ca\3\2\2\2\n\u00d9\3\2\2"+
		"\2\f\u00db\3\2\2\2\16\u011b\3\2\2\2\20\u011d\3\2\2\2\22\u0136\3\2\2\2"+
		"\24\u0138\3\2\2\2\26\u014f\3\2\2\2\30\u016a\3\2\2\2\32\u017b\3\2\2\2\34"+
		"\u017e\3\2\2\2\36\u0182\3\2\2\2 \u0193\3\2\2\2\"\u01c3\3\2\2\2$\u01d4"+
		"\3\2\2\2&\u01dd\3\2\2\2(\u01ed\3\2\2\2*\u0209\3\2\2\2,\u0212\3\2\2\2."+
		"\u022c\3\2\2\2\60\u022e\3\2\2\2\62\u023b\3\2\2\2\64\u023f\3\2\2\2\66\u0253"+
		"\3\2\2\28\u0255\3\2\2\2:\u026b\3\2\2\2<\u0274\3\2\2\2>\u027e\3\2\2\2@"+
		"\u0289\3\2\2\2B\u028b\3\2\2\2D\u02a6\3\2\2\2F\u02ad\3\2\2\2H\u02af\3\2"+
		"\2\2J\u0319\3\2\2\2L\u031e\3\2\2\2N\u032c\3\2\2\2P\u032e\3\2\2\2R\u0332"+
		"\3\2\2\2T\u0334\3\2\2\2V\u0344\3\2\2\2X\u0387\3\2\2\2Z\u038c\3\2\2\2\\"+
		"\u0394\3\2\2\2^\u0397\3\2\2\2`\u03be\3\2\2\2b\u03dc\3\2\2\2d\u0403\3\2"+
		"\2\2f\u0407\3\2\2\2h\u0409\3\2\2\2j\u0419\3\2\2\2l\u0429\3\2\2\2n\u0450"+
		"\3\2\2\2p\u0460\3\2\2\2r\u047c\3\2\2\2t\u047e\3\2\2\2v\u0490\3\2\2\2x"+
		"\u04ac\3\2\2\2z\u04ae\3\2\2\2|\u04b0\3\2\2\2~\u04b4\3\2\2\2\u0080\u04b9"+
		"\3\2\2\2\u0082\u04bc\3\2\2\2\u0084\u04c3\3\2\2\2\u0086\u04d1\3\2\2\2\u0088"+
		"\u04d3\3\2\2\2\u008a\u04d5\3\2\2\2\u008c\u04de\3\2\2\2\u008e\u04ef\3\2"+
		"\2\2\u0090\u04f1\3\2\2\2\u0092\u04f3\3\2\2\2\u0094\u0507\3\2\2\2\u0096"+
		"\u0509\3\2\2\2\u0098\u009a\7\6\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2"+
		"\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009f\3\2\2\2\u009d"+
		"\u009b\3\2\2\2\u009e\u00a0\5\4\3\2\u009f\u009e\3\2\2\2\u009f\u00a0\3\2"+
		"\2\2\u00a0\u00a4\3\2\2\2\u00a1\u00a3\7\6\2\2\u00a2\u00a1\3\2\2\2\u00a3"+
		"\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2"+
		"\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00ab\5\6\4\2\u00a8\u00aa\5\u0096L\2\u00a9"+
		"\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2"+
		"\2\2\u00ac\u00bc\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b9\5\n\6\2\u00af"+
		"\u00b1\5\u0094K\2\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b0"+
		"\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00b6\5\n\6\2\u00b5"+
		"\u00b4\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8\3\2\2\2\u00b7\u00b0\3\2"+
		"\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00bd\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00ae\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\7\2\2\3\u00bf\3\3\2\2\2\u00c0\u00c1"+
		"\7G\2\2\u00c1\u00c2\5\u0084C\2\u00c2\u00c3\5\u0094K\2\u00c3\5\3\2\2\2"+
		"\u00c4\u00c6\5\b\5\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7\u00c5"+
		"\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\7\3\2\2\2\u00c9\u00c7\3\2\2\2\u00ca"+
		"\u00cb\7\61\2\2\u00cb\u00ce\5\u0084C\2\u00cc\u00cd\7\7\2\2\u00cd\u00cf"+
		"\7\20\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d0\3\2\2\2"+
		"\u00d0\u00d1\5\u0094K\2\u00d1\t\3\2\2\2\u00d2\u00da\5\f\7\2\u00d3\u00da"+
		"\5\24\13\2\u00d4\u00da\58\35\2\u00d5\u00da\5\26\f\2\u00d6\u00da\5\36\20"+
		"\2\u00d7\u00da\5&\24\2\u00d8\u00da\5,\27\2\u00d9\u00d2\3\2\2\2\u00d9\u00d3"+
		"\3\2\2\2\u00d9\u00d4\3\2\2\2\u00d9\u00d5\3\2\2\2\u00d9\u00d6\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da\13\3\2\2\2\u00db\u00dc\5\u0080"+
		"A\2\u00dc\u00ec\7\63\2\2\u00dd\u00df\7\6\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2"+
		"\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e7\5\16\b\2\u00e4\u00e6\7\6\2\2\u00e5"+
		"\u00e4\3\2\2\2\u00e6\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2"+
		"\2\2\u00e8\u00ea\3\2\2\2\u00e9\u00e7\3\2\2\2\u00ea\u00eb\7\7\2\2\u00eb"+
		"\u00ed\3\2\2\2\u00ec\u00e0\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00f1\3\2"+
		"\2\2\u00ee\u00f0\7\6\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1"+
		"\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1\3\2"+
		"\2\2\u00f4\u00f8\5\u0086D\2\u00f5\u00f7\7\6\2\2\u00f6\u00f5\3\2\2\2\u00f7"+
		"\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2"+
		"\2\2\u00fa\u00f8\3\2\2\2\u00fb\u010a\5\20\t\2\u00fc\u00fe\7\6\2\2\u00fd"+
		"\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2"+
		"\2\2\u0100\u0102\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0106\7\32\2\2\u0103"+
		"\u0105\7\6\2\2\u0104\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2"+
		"\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109"+
		"\u010b\5@!\2\u010a\u00ff\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010f\3\2\2"+
		"\2\u010c\u010e\7\6\2\2\u010d\u010c\3\2\2\2\u010e\u0111\3\2\2\2\u010f\u010d"+
		"\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u010f\3\2\2\2\u0112"+
		"\u0113\5\22\n\2\u0113\r\3\2\2\2\u0114\u0115\7\t\2\2\u0115\u0116\5\u0086"+
		"D\2\u0116\u0117\7\32\2\2\u0117\u0118\5@!\2\u0118\u0119\7\n\2\2\u0119\u011c"+
		"\3\2\2\2\u011a\u011c\5@!\2\u011b\u0114\3\2\2\2\u011b\u011a\3\2\2\2\u011c"+
		"\17\3\2\2\2\u011d\u0129\7\t\2\2\u011e\u0123\5<\37\2\u011f\u0120\7\b\2"+
		"\2\u0120\u0122\5<\37\2\u0121\u011f\3\2\2\2\u0122\u0125\3\2\2\2\u0123\u0121"+
		"\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0126"+
		"\u0128\7\b\2\2\u0127\u0126\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u012a\3\2"+
		"\2\2\u0129\u011e\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\7\n\2\2\u012c\21\3\2\2\2\u012d\u0137\5B\"\2\u012e\u0132\7\35\2"+
		"\2\u012f\u0131\7\6\2\2\u0130\u012f\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130"+
		"\3\2\2\2\u0132\u0133\3\2\2\2\u0133\u0135\3\2\2\2\u0134\u0132\3\2\2\2\u0135"+
		"\u0137\5H%\2\u0136\u012d\3\2\2\2\u0136\u012e\3\2\2\2\u0137\23\3\2\2\2"+
		"\u0138\u0139\5\u0080A\2\u0139\u013a\78\2\2\u013a\u013d\5\u0086D\2\u013b"+
		"\u013c\7\32\2\2\u013c\u013e\5@!\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2"+
		"\2\2\u013e\u014d\3\2\2\2\u013f\u0141\7\6\2\2\u0140\u013f\3\2\2\2\u0141"+
		"\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2\2\2\u0143\u0145\3\2"+
		"\2\2\u0144\u0142\3\2\2\2\u0145\u0149\7\35\2\2\u0146\u0148\7\6\2\2\u0147"+
		"\u0146\3\2\2\2\u0148\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2"+
		"\2\2\u014a\u014c\3\2\2\2\u014b\u0149\3\2\2\2\u014c\u014e\5H%\2\u014d\u0142"+
		"\3\2\2\2\u014d\u014e\3\2\2\2\u014e\25\3\2\2\2\u014f\u0150\5\u0080A\2\u0150"+
		"\u0154\7\62\2\2\u0151\u0153\7\6\2\2\u0152\u0151\3\2\2\2\u0153\u0156\3"+
		"\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0157\3\2\2\2\u0156"+
		"\u0154\3\2\2\2\u0157\u0158\5\u0086D\2\u0158\u0159\5\30\r\2\u0159\u015a"+
		"\5\32\16\2\u015a\27\3\2\2\2\u015b\u0167\7\13\2\2\u015c\u0161\5> \2\u015d"+
		"\u015e\7\b\2\2\u015e\u0160\5> \2\u015f\u015d\3\2\2\2\u0160\u0163\3\2\2"+
		"\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0165\3\2\2\2\u0163\u0161"+
		"\3\2\2\2\u0164\u0166\7\b\2\2\u0165\u0164\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"\u0168\3\2\2\2\u0167\u015c\3\2\2\2\u0167\u0168\3\2\2\2\u0168\u0169\3\2"+
		"\2\2\u0169\u016b\7\f\2\2\u016a\u015b\3\2\2\2\u016a\u016b\3\2\2\2\u016b"+
		"\31\3\2\2\2\u016c\u0178\7\t\2\2\u016d\u0172\5\34\17\2\u016e\u016f\7\b"+
		"\2\2\u016f\u0171\5\34\17\2\u0170\u016e\3\2\2\2\u0171\u0174\3\2\2\2\u0172"+
		"\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2"+
		"\2\2\u0175\u0177\7\b\2\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2\2\2\u0177"+
		"\u0179\3\2\2\2\u0178\u016d\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017a\3\2"+
		"\2\2\u017a\u017c\7\n\2\2\u017b\u016c\3\2\2\2\u017b\u017c\3\2\2\2\u017c"+
		"\33\3\2\2\2\u017d\u017f\7Z\2\2\u017e\u017d\3\2\2\2\u017e\u017f\3\2\2\2"+
		"\u017f\u0180\3\2\2\2\u0180\u0181\5<\37\2\u0181\35\3\2\2\2\u0182\u0183"+
		"\5\u0080A\2\u0183\u0187\7[\2\2\u0184\u0186\7\6\2\2\u0185\u0184\3\2\2\2"+
		"\u0186\u0189\3\2\2\2\u0187\u0185\3\2\2\2\u0187\u0188\3\2\2\2\u0188\u018a"+
		"\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u018e\5\u0086D\2\u018b\u018d\7\6\2"+
		"\2\u018c\u018b\3\2\2\2\u018d\u0190\3\2\2\2\u018e\u018c\3\2\2\2\u018e\u018f"+
		"\3\2\2\2\u018f\u0191\3\2\2\2\u0190\u018e\3\2\2\2\u0191\u0192\5 \21\2\u0192"+
		"\37\3\2\2\2\u0193\u0197\7\r\2\2\u0194\u0196\7\6\2\2\u0195\u0194\3\2\2"+
		"\2\u0196\u0199\3\2\2\2\u0197\u0195\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u01b0"+
		"\3\2\2\2\u0199\u0197\3\2\2\2\u019a\u01ad\5\"\22\2\u019b\u019d\7\6\2\2"+
		"\u019c\u019b\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3\2\2\2\u019e\u019f"+
		"\3\2\2\2\u019f\u01a1\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1\u01a5\7\b\2\2\u01a2"+
		"\u01a4\7\6\2\2\u01a3\u01a2\3\2\2\2\u01a4\u01a7\3\2\2\2\u01a5\u01a3\3\2"+
		"\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a8"+
		"\u01aa\5\"\22\2\u01a9\u01a8\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ac\3"+
		"\2\2\2\u01ab\u019e\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad"+
		"\u01ae\3\2\2\2\u01ae\u01b1\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u019a\3\2"+
		"\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b5\3\2\2\2\u01b2\u01b4\7\6\2\2\u01b3"+
		"\u01b2\3\2\2\2\u01b4\u01b7\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2"+
		"\2\2\u01b6\u01b9\3\2\2\2\u01b7\u01b5\3\2\2\2\u01b8\u01ba\7\b\2\2\u01b9"+
		"\u01b8\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01be\3\2\2\2\u01bb\u01bd\7\6"+
		"\2\2\u01bc\u01bb\3\2\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bc\3\2\2\2\u01be"+
		"\u01bf\3\2\2\2\u01bf\u01c1\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c2\7\16"+
		"\2\2\u01c2!\3\2\2\2\u01c3\u01d2\5$\23\2\u01c4\u01c6\7\6\2\2\u01c5\u01c4"+
		"\3\2\2\2\u01c6\u01c9\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8"+
		"\u01ca\3\2\2\2\u01c9\u01c7\3\2\2\2\u01ca\u01ce\7\35\2\2\u01cb\u01cd\7"+
		"\6\2\2\u01cc\u01cb\3\2\2\2\u01cd\u01d0\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce"+
		"\u01cf\3\2\2\2\u01cf\u01d1\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d1\u01d3\7^"+
		"\2\2\u01d2\u01c7\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3#\3\2\2\2\u01d4\u01d8"+
		"\5\u0086D\2\u01d5\u01d7\7\6\2\2\u01d6\u01d5\3\2\2\2\u01d7\u01da\3\2\2"+
		"\2\u01d8\u01d6\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01db\3\2\2\2\u01da\u01d8"+
		"\3\2\2\2\u01db\u01dc\5\32\16\2\u01dc%\3\2\2\2\u01dd\u01e1\7\65\2\2\u01de"+
		"\u01e0\7\6\2\2\u01df\u01de\3\2\2\2\u01e0\u01e3\3\2\2\2\u01e1\u01df\3\2"+
		"\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e4\3\2\2\2\u01e3\u01e1\3\2\2\2\u01e4"+
		"\u01e8\5\u0086D\2\u01e5\u01e7\7\6\2\2\u01e6\u01e5\3\2\2\2\u01e7\u01ea"+
		"\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01eb\3\2\2\2\u01ea"+
		"\u01e8\3\2\2\2\u01eb\u01ec\5(\25\2\u01ec\'\3\2\2\2\u01ed\u01f1\7\r\2\2"+
		"\u01ee\u01f0\7\6\2\2\u01ef\u01ee\3\2\2\2\u01f0\u01f3\3\2\2\2\u01f1\u01ef"+
		"\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f7\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f4"+
		"\u01f6\5*\26\2\u01f5\u01f4\3\2\2\2\u01f6\u01f9\3\2\2\2\u01f7\u01f5\3\2"+
		"\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01fd\3\2\2\2\u01f9\u01f7\3\2\2\2\u01fa"+
		"\u01fc\7\6\2\2\u01fb\u01fa\3\2\2\2\u01fc\u01ff\3\2\2\2\u01fd\u01fb\3\2"+
		"\2\2\u01fd\u01fe\3\2\2\2\u01fe\u0200\3\2\2\2\u01ff\u01fd\3\2\2\2\u0200"+
		"\u0201\7\16\2\2\u0201)\3\2\2\2\u0202\u020a\5\f\7\2\u0203\u020a\5\36\20"+
		"\2\u0204\u020a\5\24\13\2\u0205\u020a\58\35\2\u0206\u020a\5\26\f\2\u0207"+
		"\u020a\5\64\33\2\u0208\u020a\5:\36\2\u0209\u0202\3\2\2\2\u0209\u0203\3"+
		"\2\2\2\u0209\u0204\3\2\2\2\u0209\u0205\3\2\2\2\u0209\u0206\3\2\2\2\u0209"+
		"\u0207\3\2\2\2\u0209\u0208\3\2\2\2\u020a\u020e\3\2\2\2\u020b\u020d\5\u0096"+
		"L\2\u020c\u020b\3\2\2\2\u020d\u0210\3\2\2\2\u020e\u020c\3\2\2\2\u020e"+
		"\u020f\3\2\2\2\u020f+\3\2\2\2\u0210\u020e\3\2\2\2\u0211\u0213\7Y\2\2\u0212"+
		"\u0211\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0214\3\2\2\2\u0214\u0218\7\67"+
		"\2\2\u0215\u0217\7\6\2\2\u0216\u0215\3\2\2\2\u0217\u021a\3\2\2\2\u0218"+
		"\u0216\3\2\2\2\u0218\u0219\3\2\2\2\u0219\u021b\3\2\2\2\u021a\u0218\3\2"+
		"\2\2\u021b\u021f\5.\30\2\u021c\u021e\7\6\2\2\u021d\u021c\3\2\2\2\u021e"+
		"\u0221\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0222\3\2"+
		"\2\2\u0221\u021f\3\2\2\2\u0222\u0226\7/\2\2\u0223\u0225\7\6\2\2\u0224"+
		"\u0223\3\2\2\2\u0225\u0228\3\2\2\2\u0226\u0224\3\2\2\2\u0226\u0227\3\2"+
		"\2\2\u0227\u0229\3\2\2\2\u0228\u0226\3\2\2\2\u0229\u022a\5\u0086D\2\u022a"+
		"\u022b\5\60\31\2\u022b-\3\2\2\2\u022c\u022d\5\u0086D\2\u022d/\3\2\2\2"+
		"\u022e\u0237\7\t\2\2\u022f\u0234\5\62\32\2\u0230\u0231\7\b\2\2\u0231\u0233"+
		"\5\62\32\2\u0232\u0230\3\2\2\2\u0233\u0236\3\2\2\2\u0234\u0232\3\2\2\2"+
		"\u0234\u0235\3\2\2\2\u0235\u0238\3\2\2\2\u0236\u0234\3\2\2\2\u0237\u022f"+
		"\3\2\2\2\u0237\u0238\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023a\7\n\2\2\u023a"+
		"\61\3\2\2\2\u023b\u023c\5\u0086D\2\u023c\u023d\7\35\2\2\u023d\u023e\5"+
		"H%\2\u023e\63\3\2\2\2\u023f\u0242\7\66\2\2\u0240\u0241\7%\2\2\u0241\u0243"+
		"\5\66\34\2\u0242\u0240\3\2\2\2\u0242\u0243\3\2\2\2\u0243\u0247\3\2\2\2"+
		"\u0244\u0246\7\6\2\2\u0245\u0244\3\2\2\2\u0246\u0249\3\2\2\2\u0247\u0245"+
		"\3\2\2\2\u0247\u0248\3\2\2\2\u0248\u024a\3\2\2\2\u0249\u0247\3\2\2\2\u024a"+
		"\u024e\5\u0086D\2\u024b\u024d\7\6\2\2\u024c\u024b\3\2\2\2\u024d\u0250"+
		"\3\2\2\2\u024e\u024c\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u0251\3\2\2\2\u0250"+
		"\u024e\3\2\2\2\u0251\u0252\5\22\n\2\u0252\65\3\2\2\2\u0253\u0254\5H%\2"+
		"\u0254\67\3\2\2\2\u0255\u0256\5\u0080A\2\u0256\u0257\7\64\2\2\u0257\u025a"+
		"\5\u0086D\2\u0258\u0259\7\32\2\2\u0259\u025b\5@!\2\u025a\u0258\3\2\2\2"+
		"\u025a\u025b\3\2\2\2\u025b\u025f\3\2\2\2\u025c\u025e\7\6\2\2\u025d\u025c"+
		"\3\2\2\2\u025e\u0261\3\2\2\2\u025f\u025d\3\2\2\2\u025f\u0260\3\2\2\2\u0260"+
		"\u0262\3\2\2\2\u0261\u025f\3\2\2\2\u0262\u0266\7\35\2\2\u0263\u0265\7"+
		"\6\2\2\u0264\u0263\3\2\2\2\u0265\u0268\3\2\2\2\u0266\u0264\3\2\2\2\u0266"+
		"\u0267\3\2\2\2\u0267\u0269\3\2\2\2\u0268\u0266\3\2\2\2\u0269\u026a\5H"+
		"%\2\u026a9\3\2\2\2\u026b\u026f\7P\2\2\u026c\u026e\7\6\2\2\u026d\u026c"+
		"\3\2\2\2\u026e\u0271\3\2\2\2\u026f\u026d\3\2\2\2\u026f\u0270\3\2\2\2\u0270"+
		"\u0272\3\2\2\2\u0271\u026f\3\2\2\2\u0272\u0273\5B\"\2\u0273;\3\2\2\2\u0274"+
		"\u0277\5\u0086D\2\u0275\u0276\7\32\2\2\u0276\u0278\5@!\2\u0277\u0275\3"+
		"\2\2\2\u0277\u0278\3\2\2\2\u0278\u027b\3\2\2\2\u0279\u027a\7\35\2\2\u027a"+
		"\u027c\5H%\2\u027b\u0279\3\2\2\2\u027b\u027c\3\2\2\2\u027c=\3\2\2\2\u027d"+
		"\u027f\t\2\2\2\u027e\u027d\3\2\2\2\u027e\u027f\3\2\2\2\u027f\u0280\3\2"+
		"\2\2\u0280\u0283\5\u0086D\2\u0281\u0282\7\32\2\2\u0282\u0284\5@!\2\u0283"+
		"\u0281\3\2\2\2\u0283\u0284\3\2\2\2\u0284\u0287\3\2\2\2\u0285\u0286\7\35"+
		"\2\2\u0286\u0288\5H%\2\u0287\u0285\3\2\2\2\u0287\u0288\3\2\2\2\u0288?"+
		"\3\2\2\2\u0289\u028a\5J&\2\u028aA\3\2\2\2\u028b\u028f\7\r\2\2\u028c\u028e"+
		"\5\u0096L\2\u028d\u028c\3\2\2\2\u028e\u0291\3\2\2\2\u028f\u028d\3\2\2"+
		"\2\u028f\u0290\3\2\2\2\u0290\u02a0\3\2\2\2\u0291\u028f\3\2\2\2\u0292\u029d"+
		"\5D#\2\u0293\u0295\5\u0096L\2\u0294\u0293\3\2\2\2\u0295\u0296\3\2\2\2"+
		"\u0296\u0294\3\2\2\2\u0296\u0297\3\2\2\2\u0297\u0299\3\2\2\2\u0298\u029a"+
		"\5D#\2\u0299\u0298\3\2\2\2\u0299\u029a\3\2\2\2\u029a\u029c\3\2\2\2\u029b"+
		"\u0294\3\2\2\2\u029c\u029f\3\2\2\2\u029d\u029b\3\2\2\2\u029d\u029e\3\2"+
		"\2\2\u029e\u02a1\3\2\2\2\u029f\u029d\3\2\2\2\u02a0\u0292\3\2\2\2\u02a0"+
		"\u02a1\3\2\2\2\u02a1\u02a2\3\2\2\2\u02a2\u02a3\7\16\2\2\u02a3C\3\2\2\2"+
		"\u02a4\u02a7\5H%\2\u02a5\u02a7\5F$\2\u02a6\u02a4\3\2\2\2\u02a6\u02a5\3"+
		"\2\2\2\u02a7E\3\2\2\2\u02a8\u02ae\5\f\7\2\u02a9\u02ae\5\24\13\2\u02aa"+
		"\u02ae\58\35\2\u02ab\u02ae\5\26\f\2\u02ac\u02ae\5\36\20\2\u02ad\u02a8"+
		"\3\2\2\2\u02ad\u02a9\3\2\2\2\u02ad\u02aa\3\2\2\2\u02ad\u02ab\3\2\2\2\u02ad"+
		"\u02ac\3\2\2\2\u02aeG\3\2\2\2\u02af\u02b0\b%\1\2\u02b0\u02b1\5J&\2\u02b1"+
		"\u0313\3\2\2\2\u02b2\u02b3\f\f\2\2\u02b3\u02b7\7\17\2\2\u02b4\u02b6\7"+
		"\6\2\2\u02b5\u02b4\3\2\2\2\u02b6\u02b9\3\2\2\2\u02b7\u02b5\3\2\2\2\u02b7"+
		"\u02b8\3\2\2\2\u02b8\u02ba\3\2\2\2\u02b9\u02b7\3\2\2\2\u02ba\u0312\5H"+
		"%\r\u02bb\u02bc\f\13\2\2\u02bc\u02c0\t\3\2\2\u02bd\u02bf\7\6\2\2\u02be"+
		"\u02bd\3\2\2\2\u02bf\u02c2\3\2\2\2\u02c0\u02be\3\2\2\2\u02c0\u02c1\3\2"+
		"\2\2\u02c1\u02c3\3\2\2\2\u02c2\u02c0\3\2\2\2\u02c3\u0312\5H%\f\u02c4\u02c5"+
		"\f\n\2\2\u02c5\u02c9\t\4\2\2\u02c6\u02c8\7\6\2\2\u02c7\u02c6\3\2\2\2\u02c8"+
		"\u02cb\3\2\2\2\u02c9\u02c7\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cc\3\2"+
		"\2\2\u02cb\u02c9\3\2\2\2\u02cc\u0312\5H%\13\u02cd\u02ce\f\t\2\2\u02ce"+
		"\u02d2\5\u0086D\2\u02cf\u02d1\7\6\2\2\u02d0\u02cf\3\2\2\2\u02d1\u02d4"+
		"\3\2\2\2\u02d2\u02d0\3\2\2\2\u02d2\u02d3\3\2\2\2\u02d3\u02d5\3\2\2\2\u02d4"+
		"\u02d2\3\2\2\2\u02d5\u02d6\5H%\n\u02d6\u0312\3\2\2\2\u02d7\u02d8\f\b\2"+
		"\2\u02d8\u02dc\t\5\2\2\u02d9\u02db\7\6\2\2\u02da\u02d9\3\2\2\2\u02db\u02de"+
		"\3\2\2\2\u02dc\u02da\3\2\2\2\u02dc\u02dd\3\2\2\2\u02dd\u02df\3\2\2\2\u02de"+
		"\u02dc\3\2\2\2\u02df\u0312\5H%\t\u02e0\u02e1\f\7\2\2\u02e1\u02e5\t\6\2"+
		"\2\u02e2\u02e4\7\6\2\2\u02e3\u02e2\3\2\2\2\u02e4\u02e7\3\2\2\2\u02e5\u02e3"+
		"\3\2\2\2\u02e5\u02e6\3\2\2\2\u02e6\u02e8\3\2\2\2\u02e7\u02e5\3\2\2\2\u02e8"+
		"\u0312\5H%\b\u02e9\u02ed\f\6\2\2\u02ea\u02ec\7\6\2\2\u02eb\u02ea\3\2\2"+
		"\2\u02ec\u02ef\3\2\2\2\u02ed\u02eb\3\2\2\2\u02ed\u02ee\3\2\2\2\u02ee\u02f0"+
		"\3\2\2\2\u02ef\u02ed\3\2\2\2\u02f0\u02f4\7\27\2\2\u02f1\u02f3\7\6\2\2"+
		"\u02f2\u02f1\3\2\2\2\u02f3\u02f6\3\2\2\2\u02f4\u02f2\3\2\2\2\u02f4\u02f5"+
		"\3\2\2\2\u02f5\u02f7\3\2\2\2\u02f6\u02f4\3\2\2\2\u02f7\u0312\5H%\7\u02f8"+
		"\u02fc\f\5\2\2\u02f9\u02fb\7\6\2\2\u02fa\u02f9\3\2\2\2\u02fb\u02fe\3\2"+
		"\2\2\u02fc\u02fa\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fd\u02ff\3\2\2\2\u02fe"+
		"\u02fc\3\2\2\2\u02ff\u0303\7\30\2\2\u0300\u0302\7\6\2\2\u0301\u0300\3"+
		"\2\2\2\u0302\u0305\3\2\2\2\u0303\u0301\3\2\2\2\u0303\u0304\3\2\2\2\u0304"+
		"\u0306\3\2\2\2\u0305\u0303\3\2\2\2\u0306\u0312\5H%\6\u0307\u0308\f\4\2"+
		"\2\u0308\u030c\5\u0092J\2\u0309\u030b\7\6\2\2\u030a\u0309\3\2\2\2\u030b"+
		"\u030e\3\2\2\2\u030c\u030a\3\2\2\2\u030c\u030d\3\2\2\2\u030d\u030f\3\2"+
		"\2\2\u030e\u030c\3\2\2\2\u030f\u0310\5H%\5\u0310\u0312\3\2\2\2\u0311\u02b2"+
		"\3\2\2\2\u0311\u02bb\3\2\2\2\u0311\u02c4\3\2\2\2\u0311\u02cd\3\2\2\2\u0311"+
		"\u02d7\3\2\2\2\u0311\u02e0\3\2\2\2\u0311\u02e9\3\2\2\2\u0311\u02f8\3\2"+
		"\2\2\u0311\u0307\3\2\2\2\u0312\u0315\3\2\2\2\u0313\u0311\3\2\2\2\u0313"+
		"\u0314\3\2\2\2\u0314I\3\2\2\2\u0315\u0313\3\2\2\2\u0316\u0318\5\u008c"+
		"G\2\u0317\u0316\3\2\2\2\u0318\u031b\3\2\2\2\u0319\u0317\3\2\2\2\u0319"+
		"\u031a\3\2\2\2\u031a\u031c\3\2\2\2\u031b\u0319\3\2\2\2\u031c\u031d\5L"+
		"\'\2\u031dK\3\2\2\2\u031e\u0322\5N(\2\u031f\u0321\5\u008eH\2\u0320\u031f"+
		"\3\2\2\2\u0321\u0324\3\2\2\2\u0322\u0320\3\2\2\2\u0322\u0323\3\2\2\2\u0323"+
		"M\3\2\2\2\u0324\u0322\3\2\2\2\u0325\u032d\5P)\2\u0326\u032d\5\u0088E\2"+
		"\u0327\u032d\5\u008aF\2\u0328\u032d\5\\/\2\u0329\u032d\5r:\2\u032a\u032d"+
		"\5\u0086D\2\u032b\u032d\5V,\2\u032c\u0325\3\2\2\2\u032c\u0326\3\2\2\2"+
		"\u032c\u0327\3\2\2\2\u032c\u0328\3\2\2\2\u032c\u0329\3\2\2\2\u032c\u032a"+
		"\3\2\2\2\u032c\u032b\3\2\2\2\u032dO\3\2\2\2\u032e\u032f\7\t\2\2\u032f"+
		"\u0330\5H%\2\u0330\u0331\7\n\2\2\u0331Q\3\2\2\2\u0332\u0333\5X-\2\u0333"+
		"S\3\2\2\2\u0334\u0340\7\13\2\2\u0335\u033a\5Z.\2\u0336\u0337\7\b\2\2\u0337"+
		"\u0339\5Z.\2\u0338\u0336\3\2\2\2\u0339\u033c\3\2\2\2\u033a\u0338\3\2\2"+
		"\2\u033a\u033b\3\2\2\2\u033b\u033e\3\2\2\2\u033c\u033a\3\2\2\2\u033d\u033f"+
		"\7\b\2\2\u033e\u033d\3\2\2\2\u033e\u033f\3\2\2\2\u033f\u0341\3\2\2\2\u0340"+
		"\u0335\3\2\2\2\u0340\u0341\3\2\2\2\u0341\u0342\3\2\2\2\u0342\u0343\7\f"+
		"\2\2\u0343U\3\2\2\2\u0344\u0348\7\r\2\2\u0345\u0347\7\6\2\2\u0346\u0345"+
		"\3\2\2\2\u0347\u034a\3\2\2\2\u0348\u0346\3\2\2\2\u0348\u0349\3\2\2\2\u0349"+
		"\u0354\3\2\2\2\u034a\u0348\3\2\2\2\u034b\u034f\5\20\t\2\u034c\u034e\7"+
		"\6\2\2\u034d\u034c\3\2\2\2\u034e\u0351\3\2\2\2\u034f\u034d\3\2\2\2\u034f"+
		"\u0350\3\2\2\2\u0350\u0352\3\2\2\2\u0351\u034f\3\2\2\2\u0352\u0353\7/"+
		"\2\2\u0353\u0355\3\2\2\2\u0354\u034b\3\2\2\2\u0354\u0355\3\2\2\2\u0355"+
		"\u0359\3\2\2\2\u0356\u0358\7\6\2\2\u0357\u0356\3\2\2\2\u0358\u035b\3\2"+
		"\2\2\u0359\u0357\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u035f\3\2\2\2\u035b"+
		"\u0359\3\2\2\2\u035c\u035e\5\u0096L\2\u035d\u035c\3\2\2\2\u035e\u0361"+
		"\3\2\2\2\u035f\u035d\3\2\2\2\u035f\u0360\3\2\2\2\u0360\u0370\3\2\2\2\u0361"+
		"\u035f\3\2\2\2\u0362\u036d\5D#\2\u0363\u0365\5\u0096L\2\u0364\u0363\3"+
		"\2\2\2\u0365\u0366\3\2\2\2\u0366\u0364\3\2\2\2\u0366\u0367\3\2\2\2\u0367"+
		"\u0369\3\2\2\2\u0368\u036a\5D#\2\u0369\u0368\3\2\2\2\u0369\u036a\3\2\2"+
		"\2\u036a\u036c\3\2\2\2\u036b\u0364\3\2\2\2\u036c\u036f\3\2\2\2\u036d\u036b"+
		"\3\2\2\2\u036d\u036e\3\2\2\2\u036e\u0371\3\2\2\2\u036f\u036d\3\2\2\2\u0370"+
		"\u0362\3\2\2\2\u0370\u0371\3\2\2\2\u0371\u0372\3\2\2\2\u0372\u0373\7\16"+
		"\2\2\u0373W\3\2\2\2\u0374\u0380\7\t\2\2\u0375\u037a\5Z.\2\u0376\u0377"+
		"\7\b\2\2\u0377\u0379\5Z.\2\u0378\u0376\3\2\2\2\u0379\u037c\3\2\2\2\u037a"+
		"\u0378\3\2\2\2\u037a\u037b\3\2\2\2\u037b\u037e\3\2\2\2\u037c\u037a\3\2"+
		"\2\2\u037d\u037f\7\b\2\2\u037e\u037d\3\2\2\2\u037e\u037f\3\2\2\2\u037f"+
		"\u0381\3\2\2\2\u0380\u0375\3\2\2\2\u0380\u0381\3\2\2\2\u0381\u0382\3\2"+
		"\2\2\u0382\u0384\7\n\2\2\u0383\u0385\5V,\2\u0384\u0383\3\2\2\2\u0384\u0385"+
		"\3\2\2\2\u0385\u0388\3\2\2\2\u0386\u0388\5V,\2\u0387\u0374\3\2\2\2\u0387"+
		"\u0386\3\2\2\2\u0388Y\3\2\2\2\u0389\u038a\5\u0086D\2\u038a\u038b\7\35"+
		"\2\2\u038b\u038d\3\2\2\2\u038c\u0389\3\2\2\2\u038c\u038d\3\2\2\2\u038d"+
		"\u038e\3\2\2\2\u038e\u038f\5H%\2\u038f[\3\2\2\2\u0390\u0395\5^\60\2\u0391"+
		"\u0395\5`\61\2\u0392\u0395\5b\62\2\u0393\u0395\5p9\2\u0394\u0390\3\2\2"+
		"\2\u0394\u0391\3\2\2\2\u0394\u0392\3\2\2\2\u0394\u0393\3\2\2\2\u0395]"+
		"\3\2\2\2\u0396\u0398\7X\2\2\u0397\u0396\3\2\2\2\u0397\u0398\3\2\2\2\u0398"+
		"\u0399\3\2\2\2\u0399\u039d\7:\2\2\u039a\u039c\7\6\2\2\u039b\u039a\3\2"+
		"\2\2\u039c\u039f\3\2\2\2\u039d\u039b\3\2\2\2\u039d\u039e\3\2\2\2\u039e"+
		"\u03a0\3\2\2\2\u039f\u039d\3\2\2\2\u03a0\u03a1\7\t\2\2\u03a1\u03a2\5H"+
		"%\2\u03a2\u03a6\7\n\2\2\u03a3\u03a5\7\6\2\2\u03a4\u03a3\3\2\2\2\u03a5"+
		"\u03a8\3\2\2\2\u03a6\u03a4\3\2\2\2\u03a6\u03a7\3\2\2\2\u03a7\u03a9\3\2"+
		"\2\2\u03a8\u03a6\3\2\2\2\u03a9\u03ab\5~@\2\u03aa\u03ac\7\34\2\2\u03ab"+
		"\u03aa\3\2\2\2\u03ab\u03ac\3\2\2\2\u03ac\u03bb\3\2\2\2\u03ad\u03af\7\6"+
		"\2\2\u03ae\u03ad\3\2\2\2\u03af\u03b2\3\2\2\2\u03b0\u03ae\3\2\2\2\u03b0"+
		"\u03b1\3\2\2\2\u03b1\u03b3\3\2\2\2\u03b2\u03b0\3\2\2\2\u03b3\u03b7\7;"+
		"\2\2\u03b4\u03b6\7\6\2\2\u03b5\u03b4\3\2\2\2\u03b6\u03b9\3\2\2\2\u03b7"+
		"\u03b5\3\2\2\2\u03b7\u03b8\3\2\2\2\u03b8\u03ba\3\2\2\2\u03b9\u03b7\3\2"+
		"\2\2\u03ba\u03bc\5~@\2\u03bb\u03b0\3\2\2\2\u03bb\u03bc\3\2\2\2\u03bc_"+
		"\3\2\2\2\u03bd\u03bf\7X\2\2\u03be\u03bd\3\2\2\2\u03be\u03bf\3\2\2\2\u03bf"+
		"\u03c0\3\2\2\2\u03c0\u03c4\7=\2\2\u03c1\u03c3\7\6\2\2\u03c2\u03c1\3\2"+
		"\2\2\u03c3\u03c6\3\2\2\2\u03c4\u03c2\3\2\2\2\u03c4\u03c5\3\2\2\2\u03c5"+
		"\u03c7\3\2\2\2\u03c6\u03c4\3\2\2\2\u03c7\u03cb\7\r\2\2\u03c8\u03ca\7\6"+
		"\2\2\u03c9\u03c8\3\2\2\2\u03ca\u03cd\3\2\2\2\u03cb\u03c9\3\2\2\2\u03cb"+
		"\u03cc\3\2\2\2\u03cc\u03d7\3\2\2\2\u03cd\u03cb\3\2\2\2\u03ce\u03d2\5d"+
		"\63\2\u03cf\u03d1\7\6\2\2\u03d0\u03cf\3\2\2\2\u03d1\u03d4\3\2\2\2\u03d2"+
		"\u03d0\3\2\2\2\u03d2\u03d3\3\2\2\2\u03d3\u03d6\3\2\2\2\u03d4\u03d2\3\2"+
		"\2\2\u03d5\u03ce\3\2\2\2\u03d6\u03d9\3\2\2\2\u03d7\u03d5\3\2\2\2\u03d7"+
		"\u03d8\3\2\2\2\u03d8\u03da\3\2\2\2\u03d9\u03d7\3\2\2\2\u03da\u03db\7\16"+
		"\2\2\u03dba\3\2\2\2\u03dc\u03e0\7=\2\2\u03dd\u03df\7\6\2\2\u03de\u03dd"+
		"\3\2\2\2\u03df\u03e2\3\2\2\2\u03e0\u03de\3\2\2\2\u03e0\u03e1\3\2\2\2\u03e1"+
		"\u03e3\3\2\2\2\u03e2\u03e0\3\2\2\2\u03e3\u03e4\7\t\2\2\u03e4\u03e5\5H"+
		"%\2\u03e5\u03e9\7\n\2\2\u03e6\u03e8\7\6\2\2\u03e7\u03e6\3\2\2\2\u03e8"+
		"\u03eb\3\2\2\2\u03e9\u03e7\3\2\2\2\u03e9\u03ea\3\2\2\2\u03ea\u03ec\3\2"+
		"\2\2\u03eb\u03e9\3\2\2\2\u03ec\u03f0\7\r\2\2\u03ed\u03ef\7\6\2\2\u03ee"+
		"\u03ed\3\2\2\2\u03ef\u03f2\3\2\2\2\u03f0\u03ee\3\2\2\2\u03f0\u03f1\3\2"+
		"\2\2\u03f1\u03fc\3\2\2\2\u03f2\u03f0\3\2\2\2\u03f3\u03f7\5f\64\2\u03f4"+
		"\u03f6\7\6\2\2\u03f5\u03f4\3\2\2\2\u03f6\u03f9\3\2\2\2\u03f7\u03f5\3\2"+
		"\2\2\u03f7\u03f8\3\2\2\2\u03f8\u03fb\3\2\2\2\u03f9\u03f7\3\2\2\2\u03fa"+
		"\u03f3\3\2\2\2\u03fb\u03fe\3\2\2\2\u03fc\u03fa\3\2\2\2\u03fc\u03fd\3\2"+
		"\2\2\u03fd\u03ff\3\2\2\2\u03fe\u03fc\3\2\2\2\u03ff\u0400\7\16\2\2\u0400"+
		"c\3\2\2\2\u0401\u0404\5h\65\2\u0402\u0404\5j\66\2\u0403\u0401\3\2\2\2"+
		"\u0403\u0402\3\2\2\2\u0404e\3\2\2\2\u0405\u0408\5l\67\2\u0406\u0408\5"+
		"j\66\2\u0407\u0405\3\2\2\2\u0407\u0406\3\2\2\2\u0408g\3\2\2\2\u0409\u040d"+
		"\5H%\2\u040a\u040c\7\6\2\2\u040b\u040a\3\2\2\2\u040c\u040f\3\2\2\2\u040d"+
		"\u040b\3\2\2\2\u040d\u040e\3\2\2\2\u040e\u0410\3\2\2\2\u040f\u040d\3\2"+
		"\2\2\u0410\u0414\7/\2\2\u0411\u0413\7\6\2\2\u0412\u0411\3\2\2\2\u0413"+
		"\u0416\3\2\2\2\u0414\u0412\3\2\2\2\u0414\u0415\3\2\2\2\u0415\u0417\3\2"+
		"\2\2\u0416\u0414\3\2\2\2\u0417\u0418\5~@\2\u0418i\3\2\2\2\u0419\u041d"+
		"\7;\2\2\u041a\u041c\7\6\2\2\u041b\u041a\3\2\2\2\u041c\u041f\3\2\2\2\u041d"+
		"\u041b\3\2\2\2\u041d\u041e\3\2\2\2\u041e\u0420\3\2\2\2\u041f\u041d\3\2"+
		"\2\2\u0420\u0424\7/\2\2\u0421\u0423\7\6\2\2\u0422\u0421\3\2\2\2\u0423"+
		"\u0426\3\2\2\2\u0424\u0422\3\2\2\2\u0424\u0425\3\2\2\2\u0425\u0427\3\2"+
		"\2\2\u0426\u0424\3\2\2\2\u0427\u0428\5~@\2\u0428k\3\2\2\2\u0429\u042d"+
		"\7C\2\2\u042a\u042c\7\6\2\2\u042b\u042a\3\2\2\2\u042c\u042f\3\2\2\2\u042d"+
		"\u042b\3\2\2\2\u042d\u042e\3\2\2\2\u042e\u0430\3\2\2\2\u042f\u042d\3\2"+
		"\2\2\u0430\u0434\5H%\2\u0431\u0433\7\6\2\2\u0432\u0431\3\2\2\2\u0433\u0436"+
		"\3\2\2\2\u0434\u0432\3\2\2\2\u0434\u0435\3\2\2\2\u0435\u043f\3\2\2\2\u0436"+
		"\u0434\3\2\2\2\u0437\u043b\7D\2\2\u0438\u043a\7\6\2\2\u0439\u0438\3\2"+
		"\2\2\u043a\u043d\3\2\2\2\u043b\u0439\3\2\2\2\u043b\u043c\3\2\2\2\u043c"+
		"\u043e\3\2\2\2\u043d\u043b\3\2\2\2\u043e\u0440\5n8\2\u043f\u0437\3\2\2"+
		"\2\u043f\u0440\3\2\2\2\u0440\u0444\3\2\2\2\u0441\u0443\7\6\2\2\u0442\u0441"+
		"\3\2\2\2\u0443\u0446\3\2\2\2\u0444\u0442\3\2\2\2\u0444\u0445\3\2\2\2\u0445"+
		"\u0447\3\2\2\2\u0446\u0444\3\2\2\2\u0447\u044b\7/\2\2\u0448\u044a\7\6"+
		"\2\2\u0449\u0448\3\2\2\2\u044a\u044d\3\2\2\2\u044b\u0449\3\2\2\2\u044b"+
		"\u044c\3\2\2\2\u044c\u044e\3\2\2\2\u044d\u044b\3\2\2\2\u044e\u044f\5~"+
		"@\2\u044fm\3\2\2\2\u0450\u045c\7\t\2\2\u0451\u0456\5\u0086D\2\u0452\u0453"+
		"\7\b\2\2\u0453\u0455\5\u0086D\2\u0454\u0452\3\2\2\2\u0455\u0458\3\2\2"+
		"\2\u0456\u0454\3\2\2\2\u0456\u0457\3\2\2\2\u0457\u045a\3\2\2\2\u0458\u0456"+
		"\3\2\2\2\u0459\u045b\7\b\2\2\u045a\u0459\3\2\2\2\u045a\u045b\3\2\2\2\u045b"+
		"\u045d\3\2\2\2\u045c\u0451\3\2\2\2\u045c\u045d\3\2\2\2\u045d\u045e\3\2"+
		"\2\2\u045e\u045f\7\n\2\2\u045fo\3\2\2\2\u0460\u0464\7>\2\2\u0461\u0463"+
		"\7\6\2\2\u0462\u0461\3\2\2\2\u0463\u0466\3\2\2\2\u0464\u0462\3\2\2\2\u0464"+
		"\u0465\3\2\2\2\u0465\u0467\3\2\2\2\u0466\u0464\3\2\2\2\u0467\u0469\5~"+
		"@\2\u0468\u046a\7\34\2\2\u0469\u0468\3\2\2\2\u0469\u046a\3\2\2\2\u046a"+
		"\u046e\3\2\2\2\u046b\u046d\7\6\2\2\u046c\u046b\3\2\2\2\u046d\u0470\3\2"+
		"\2\2\u046e\u046c\3\2\2\2\u046e\u046f\3\2\2\2\u046f\u0471\3\2\2\2\u0470"+
		"\u046e\3\2\2\2\u0471\u0475\7?\2\2\u0472\u0474\7\6\2\2\u0473\u0472\3\2"+
		"\2\2\u0474\u0477\3\2\2\2\u0475\u0473\3\2\2\2\u0475\u0476\3\2\2\2\u0476"+
		"\u0478\3\2\2\2\u0477\u0475\3\2\2\2\u0478\u0479\5~@\2\u0479q\3\2\2\2\u047a"+
		"\u047d\5t;\2\u047b\u047d\5v<\2\u047c\u047a\3\2\2\2\u047c\u047b\3\2\2\2"+
		"\u047ds\3\2\2\2\u047e\u0482\7<\2\2\u047f\u0481\7\6\2\2\u0480\u047f\3\2"+
		"\2\2\u0481\u0484\3\2\2\2\u0482\u0480\3\2\2\2\u0482\u0483\3\2\2\2\u0483"+
		"\u0485\3\2\2\2\u0484\u0482\3\2\2\2\u0485\u0486\7\t\2\2\u0486\u0487\5H"+
		"%\2\u0487\u048b\7\n\2\2\u0488\u048a\7\6\2\2\u0489\u0488\3\2\2\2\u048a"+
		"\u048d\3\2\2\2\u048b\u0489\3\2\2\2\u048b\u048c\3\2\2\2\u048c\u048e\3\2"+
		"\2\2\u048d\u048b\3\2\2\2\u048e\u048f\5~@\2\u048fu\3\2\2\2\u0490\u0494"+
		"\7@\2\2\u0491\u0493\7\6\2\2\u0492\u0491\3\2\2\2\u0493\u0496\3\2\2\2\u0494"+
		"\u0492\3\2\2\2\u0494\u0495\3\2\2\2\u0495\u0497\3\2\2\2\u0496\u0494\3\2"+
		"\2\2\u0497\u0499\7\t\2\2\u0498\u049a\5x=\2\u0499\u0498\3\2\2\2\u0499\u049a"+
		"\3\2\2\2\u049a\u049b\3\2\2\2\u049b\u049d\7\34\2\2\u049c\u049e\5z>\2\u049d"+
		"\u049c\3\2\2\2\u049d\u049e\3\2\2\2\u049e\u049f\3\2\2\2\u049f\u04a1\7\34"+
		"\2\2\u04a0\u04a2\5|?\2\u04a1\u04a0\3\2\2\2\u04a1\u04a2\3\2\2\2\u04a2\u04a3"+
		"\3\2\2\2\u04a3\u04a7\7\n\2\2\u04a4\u04a6\7\6\2\2\u04a5\u04a4\3\2\2\2\u04a6"+
		"\u04a9\3\2\2\2\u04a7\u04a5\3\2\2\2\u04a7\u04a8\3\2\2\2\u04a8\u04aa\3\2"+
		"\2\2\u04a9\u04a7\3\2\2\2\u04aa\u04ab\5~@\2\u04abw\3\2\2\2\u04ac\u04ad"+
		"\5D#\2\u04ady\3\2\2\2\u04ae\u04af\5H%\2\u04af{\3\2\2\2\u04b0\u04b1\5H"+
		"%\2\u04b1}\3\2\2\2\u04b2\u04b5\5B\"\2\u04b3\u04b5\5H%\2\u04b4\u04b2\3"+
		"\2\2\2\u04b4\u04b3\3\2\2\2\u04b5\177\3\2\2\2\u04b6\u04b8\5\u0082B\2\u04b7"+
		"\u04b6\3\2\2\2\u04b8\u04bb\3\2\2\2\u04b9\u04b7\3\2\2\2\u04b9\u04ba\3\2"+
		"\2\2\u04ba\u0081\3\2\2\2\u04bb\u04b9\3\2\2\2\u04bc\u04c0\t\7\2\2\u04bd"+
		"\u04bf\7\6\2\2\u04be\u04bd\3\2\2\2\u04bf\u04c2\3\2\2\2\u04c0\u04be\3\2"+
		"\2\2\u04c0\u04c1\3\2\2\2\u04c1\u0083\3\2\2\2\u04c2\u04c0\3\2\2\2\u04c3"+
		"\u04ce\5\u0086D\2\u04c4\u04c6\7\6\2\2\u04c5\u04c4\3\2\2\2\u04c6\u04c9"+
		"\3\2\2\2\u04c7\u04c5\3\2\2\2\u04c7\u04c8\3\2\2\2\u04c8\u04ca\3\2\2\2\u04c9"+
		"\u04c7\3\2\2\2\u04ca\u04cb\7\7\2\2\u04cb\u04cd\5\u0086D\2\u04cc\u04c7"+
		"\3\2\2\2\u04cd\u04d0\3\2\2\2\u04ce\u04cc\3\2\2\2\u04ce\u04cf\3\2\2\2\u04cf"+
		"\u0085\3\2\2\2\u04d0\u04ce\3\2\2\2\u04d1\u04d2\t\b\2\2\u04d2\u0087\3\2"+
		"\2\2\u04d3\u04d4\t\t\2\2\u04d4\u0089\3\2\2\2\u04d5\u04d9\7\\\2\2\u04d6"+
		"\u04d8\t\n\2\2\u04d7\u04d6\3\2\2\2\u04d8\u04db\3\2\2\2\u04d9\u04d7\3\2"+
		"\2\2\u04d9\u04da\3\2\2\2\u04da\u04dc\3\2\2\2\u04db\u04d9\3\2\2\2\u04dc"+
		"\u04dd\7k\2\2\u04dd\u008b\3\2\2\2\u04de\u04df\t\13\2\2\u04df\u008d\3\2"+
		"\2\2\u04e0\u04f0\7\25\2\2\u04e1\u04f0\7\26\2\2\u04e2\u04e3\7\31\2\2\u04e3"+
		"\u04f0\7\31\2\2\u04e4\u04f0\5R*\2\u04e5\u04f0\5T+\2\u04e6\u04e8\7\6\2"+
		"\2\u04e7\u04e6\3\2\2\2\u04e8\u04eb\3\2\2\2\u04e9\u04e7\3\2\2\2\u04e9\u04ea"+
		"\3\2\2\2\u04ea\u04ec\3\2\2\2\u04eb\u04e9\3\2\2\2\u04ec\u04ed\5\u0090I"+
		"\2\u04ed\u04ee\5\u0086D\2\u04ee\u04f0\3\2\2\2\u04ef\u04e0\3\2\2\2\u04ef"+
		"\u04e1\3\2\2\2\u04ef\u04e2\3\2\2\2\u04ef\u04e4\3\2\2\2\u04ef\u04e5\3\2"+
		"\2\2\u04ef\u04e9\3\2\2\2\u04f0\u008f\3\2\2\2\u04f1\u04f2\7\7\2\2\u04f2"+
		"\u0091\3\2\2\2\u04f3\u04f4\t\f\2\2\u04f4\u0093\3\2\2\2\u04f5\u04f7\7\6"+
		"\2\2\u04f6\u04f5\3\2\2\2\u04f7\u04f8\3\2\2\2\u04f8\u04f6\3\2\2\2\u04f8"+
		"\u04f9\3\2\2\2\u04f9\u0508\3\2\2\2\u04fa\u04fc\7\6\2\2\u04fb\u04fa\3\2"+
		"\2\2\u04fc\u04ff\3\2\2\2\u04fd\u04fb\3\2\2\2\u04fd\u04fe\3\2\2\2\u04fe"+
		"\u0500\3\2\2\2\u04ff\u04fd\3\2\2\2\u0500\u0504\7\34\2\2\u0501\u0503\7"+
		"\6\2\2\u0502\u0501\3\2\2\2\u0503\u0506\3\2\2\2\u0504\u0502\3\2\2\2\u0504"+
		"\u0505\3\2\2\2\u0505\u0508\3\2\2\2\u0506\u0504\3\2\2\2\u0507\u04f6\3\2"+
		"\2\2\u0507\u04fd\3\2\2\2\u0508\u0095\3\2\2\2\u0509\u050a\t\r\2\2\u050a"+
		"\u0097\3\2\2\2\u00b4\u009b\u009f\u00a4\u00ab\u00b2\u00b5\u00b9\u00bc\u00c7"+
		"\u00ce\u00d9\u00e0\u00e7\u00ec\u00f1\u00f8\u00ff\u0106\u010a\u010f\u011b"+
		"\u0123\u0127\u0129\u0132\u0136\u013d\u0142\u0149\u014d\u0154\u0161\u0165"+
		"\u0167\u016a\u0172\u0176\u0178\u017b\u017e\u0187\u018e\u0197\u019e\u01a5"+
		"\u01a9\u01ad\u01b0\u01b5\u01b9\u01be\u01c7\u01ce\u01d2\u01d8\u01e1\u01e8"+
		"\u01f1\u01f7\u01fd\u0209\u020e\u0212\u0218\u021f\u0226\u0234\u0237\u0242"+
		"\u0247\u024e\u025a\u025f\u0266\u026f\u0277\u027b\u027e\u0283\u0287\u028f"+
		"\u0296\u0299\u029d\u02a0\u02a6\u02ad\u02b7\u02c0\u02c9\u02d2\u02dc\u02e5"+
		"\u02ed\u02f4\u02fc\u0303\u030c\u0311\u0313\u0319\u0322\u032c\u033a\u033e"+
		"\u0340\u0348\u034f\u0354\u0359\u035f\u0366\u0369\u036d\u0370\u037a\u037e"+
		"\u0380\u0384\u0387\u038c\u0394\u0397\u039d\u03a6\u03ab\u03b0\u03b7\u03bb"+
		"\u03be\u03c4\u03cb\u03d2\u03d7\u03e0\u03e9\u03f0\u03f7\u03fc\u0403\u0407"+
		"\u040d\u0414\u041d\u0424\u042d\u0434\u043b\u043f\u0444\u044b\u0456\u045a"+
		"\u045c\u0464\u0469\u046e\u0475\u047c\u0482\u048b\u0494\u0499\u049d\u04a1"+
		"\u04a7\u04b4\u04b9\u04c0\u04c7\u04ce\u04d9\u04e9\u04ef\u04f8\u04fd\u0504"+
		"\u0507";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}