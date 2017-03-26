// Generated from com/example/Pred.g4 by ANTLR 4.3
package predmoodul;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PredParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__16=1, T__15=2, T__14=3, T__13=4, T__12=5, T__11=6, T__10=7, T__9=8, 
		T__8=9, T__7=10, T__6=11, T__5=12, T__4=13, T__3=14, T__2=15, T__1=16, 
		T__0=17, ARV=18, TAHT=19, WS=20;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'0'", "'1'", "'->'", "'v'", "'='", "'~'", "':='", 
		"'A'", "'E'", "'&'", "'('", "')'", "'*'", "'+'", "','", "'-'", "ARV", 
		"TAHT", "WS"
	};
	public static final int
		RULE_koguvalem = 0, RULE_abidef = 1, RULE_predtahis = 2, RULE_predsymbol = 3, 
		RULE_identifikaator = 4, RULE_defargumendid = 5, RULE_indiviidmuutuja = 6, 
		RULE_predvalem = 7, RULE_ekvvalem = 8, RULE_implvalem = 9, RULE_disjvalem = 10, 
		RULE_konjvalem = 11, RULE_iga = 12, RULE_eks = 13, RULE_korgvalem = 14, 
		RULE_atomaarnevalem = 15, RULE_termargumendid = 16, RULE_term = 17, RULE_pmterm = 18, 
		RULE_kjterm = 19;
	public static final String[] ruleNames = {
		"koguvalem", "abidef", "predtahis", "predsymbol", "identifikaator", "defargumendid", 
		"indiviidmuutuja", "predvalem", "ekvvalem", "implvalem", "disjvalem", 
		"konjvalem", "iga", "eks", "korgvalem", "atomaarnevalem", "termargumendid", 
		"term", "pmterm", "kjterm"
	};

	@Override
	public String getGrammarFileName() { return "Pred.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PredParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class KoguvalemContext extends ParserRuleContext {
		public AbidefContext abidef(int i) {
			return getRuleContext(AbidefContext.class,i);
		}
		public List<AbidefContext> abidef() {
			return getRuleContexts(AbidefContext.class);
		}
		public PredvalemContext predvalem() {
			return getRuleContext(PredvalemContext.class,0);
		}
		public KoguvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_koguvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterKoguvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitKoguvalem(this);
		}
	}

	public final KoguvalemContext koguvalem() throws RecognitionException {
		KoguvalemContext _localctx = new KoguvalemContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_koguvalem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(40); abidef();
					}
					} 
				}
				setState(45);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(46); predvalem();
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

	public static class AbidefContext extends ParserRuleContext {
		public PredtahisContext predtahis() {
			return getRuleContext(PredtahisContext.class,0);
		}
		public PredvalemContext predvalem() {
			return getRuleContext(PredvalemContext.class,0);
		}
		public AbidefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abidef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterAbidef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitAbidef(this);
		}
	}

	public final AbidefContext abidef() throws RecognitionException {
		AbidefContext _localctx = new AbidefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_abidef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48); predtahis();
			setState(49); match(T__9);
			setState(50); predvalem();
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

	public static class PredtahisContext extends ParserRuleContext {
		public PredsymbolContext predsymbol() {
			return getRuleContext(PredsymbolContext.class,0);
		}
		public List<DefargumendidContext> defargumendid() {
			return getRuleContexts(DefargumendidContext.class);
		}
		public DefargumendidContext defargumendid(int i) {
			return getRuleContext(DefargumendidContext.class,i);
		}
		public PredtahisContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predtahis; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterPredtahis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitPredtahis(this);
		}
	}

	public final PredtahisContext predtahis() throws RecognitionException {
		PredtahisContext _localctx = new PredtahisContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_predtahis);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52); predsymbol();
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(53); defargumendid();
				}
				}
				setState(58);
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

	public static class PredsymbolContext extends ParserRuleContext {
		public IdentifikaatorContext identifikaator() {
			return getRuleContext(IdentifikaatorContext.class,0);
		}
		public PredsymbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predsymbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterPredsymbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitPredsymbol(this);
		}
	}

	public final PredsymbolContext predsymbol() throws RecognitionException {
		PredsymbolContext _localctx = new PredsymbolContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_predsymbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59); identifikaator();
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

	public static class IdentifikaatorContext extends ParserRuleContext {
		public TerminalNode TAHT(int i) {
			return getToken(PredParser.TAHT, i);
		}
		public TerminalNode ARV(int i) {
			return getToken(PredParser.ARV, i);
		}
		public List<TerminalNode> ARV() { return getTokens(PredParser.ARV); }
		public List<TerminalNode> TAHT() { return getTokens(PredParser.TAHT); }
		public IdentifikaatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifikaator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterIdentifikaator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitIdentifikaator(this);
		}
	}

	public final IdentifikaatorContext identifikaator() throws RecognitionException {
		IdentifikaatorContext _localctx = new IdentifikaatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_identifikaator);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(61); match(TAHT);
			setState(65);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(62);
					_la = _input.LA(1);
					if ( !(_la==ARV || _la==TAHT) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					}
					} 
				}
				setState(67);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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

	public static class DefargumendidContext extends ParserRuleContext {
		public IndiviidmuutujaContext indiviidmuutuja(int i) {
			return getRuleContext(IndiviidmuutujaContext.class,i);
		}
		public List<IndiviidmuutujaContext> indiviidmuutuja() {
			return getRuleContexts(IndiviidmuutujaContext.class);
		}
		public DefargumendidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defargumendid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterDefargumendid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitDefargumendid(this);
		}
	}

	public final DefargumendidContext defargumendid() throws RecognitionException {
		DefargumendidContext _localctx = new DefargumendidContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_defargumendid);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); match(T__5);
			setState(69); indiviidmuutuja();
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(70); match(T__1);
				setState(71); indiviidmuutuja();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(77); match(T__4);
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

	public static class IndiviidmuutujaContext extends ParserRuleContext {
		public TerminalNode TAHT() { return getToken(PredParser.TAHT, 0); }
		public IndiviidmuutujaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indiviidmuutuja; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterIndiviidmuutuja(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitIndiviidmuutuja(this);
		}
	}

	public final IndiviidmuutujaContext indiviidmuutuja() throws RecognitionException {
		IndiviidmuutujaContext _localctx = new IndiviidmuutujaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_indiviidmuutuja);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(TAHT);
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

	public static class PredvalemContext extends ParserRuleContext {
		public EkvvalemContext ekvvalem(int i) {
			return getRuleContext(EkvvalemContext.class,i);
		}
		public List<EkvvalemContext> ekvvalem() {
			return getRuleContexts(EkvvalemContext.class);
		}
		public PredvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterPredvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitPredvalem(this);
		}
	}

	public final PredvalemContext predvalem() throws RecognitionException {
		PredvalemContext _localctx = new PredvalemContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_predvalem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); ekvvalem();
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(82); match(T__10);
				setState(83); ekvvalem();
				}
				}
				setState(88);
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

	public static class EkvvalemContext extends ParserRuleContext {
		public List<ImplvalemContext> implvalem() {
			return getRuleContexts(ImplvalemContext.class);
		}
		public ImplvalemContext implvalem(int i) {
			return getRuleContext(ImplvalemContext.class,i);
		}
		public EkvvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ekvvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterEkvvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitEkvvalem(this);
		}
	}

	public final EkvvalemContext ekvvalem() throws RecognitionException {
		EkvvalemContext _localctx = new EkvvalemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ekvvalem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); implvalem();
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13) {
				{
				{
				setState(90); match(T__13);
				setState(91); implvalem();
				}
				}
				setState(96);
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

	public static class ImplvalemContext extends ParserRuleContext {
		public DisjvalemContext disjvalem(int i) {
			return getRuleContext(DisjvalemContext.class,i);
		}
		public List<DisjvalemContext> disjvalem() {
			return getRuleContexts(DisjvalemContext.class);
		}
		public ImplvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterImplvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitImplvalem(this);
		}
	}

	public final ImplvalemContext implvalem() throws RecognitionException {
		ImplvalemContext _localctx = new ImplvalemContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_implvalem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); disjvalem();
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__12) {
				{
				{
				setState(98); match(T__12);
				setState(99); disjvalem();
				}
				}
				setState(104);
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

	public static class DisjvalemContext extends ParserRuleContext {
		public KonjvalemContext konjvalem(int i) {
			return getRuleContext(KonjvalemContext.class,i);
		}
		public List<KonjvalemContext> konjvalem() {
			return getRuleContexts(KonjvalemContext.class);
		}
		public DisjvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterDisjvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitDisjvalem(this);
		}
	}

	public final DisjvalemContext disjvalem() throws RecognitionException {
		DisjvalemContext _localctx = new DisjvalemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_disjvalem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105); konjvalem();
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(106); match(T__6);
				setState(107); konjvalem();
				}
				}
				setState(112);
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

	public static class KonjvalemContext extends ParserRuleContext {
		public KorgvalemContext korgvalem() {
			return getRuleContext(KorgvalemContext.class,0);
		}
		public List<IgaContext> iga() {
			return getRuleContexts(IgaContext.class);
		}
		public IgaContext iga(int i) {
			return getRuleContext(IgaContext.class,i);
		}
		public List<EksContext> eks() {
			return getRuleContexts(EksContext.class);
		}
		public EksContext eks(int i) {
			return getRuleContext(EksContext.class,i);
		}
		public KonjvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_konjvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterKonjvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitKonjvalem(this);
		}
	}

	public final KonjvalemContext konjvalem() throws RecognitionException {
		KonjvalemContext _localctx = new KonjvalemContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_konjvalem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__7) | (1L << T__0))) != 0)) {
				{
				setState(116);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(113); match(T__0);
					}
					break;
				case T__8:
					{
					setState(114); iga();
					}
					break;
				case T__7:
					{
					setState(115); eks();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121); korgvalem();
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

	public static class IgaContext extends ParserRuleContext {
		public IndiviidmuutujaContext indiviidmuutuja() {
			return getRuleContext(IndiviidmuutujaContext.class,0);
		}
		public IgaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iga; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterIga(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitIga(this);
		}
	}

	public final IgaContext iga() throws RecognitionException {
		IgaContext _localctx = new IgaContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_iga);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123); match(T__8);
			setState(124); indiviidmuutuja();
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

	public static class EksContext extends ParserRuleContext {
		public IndiviidmuutujaContext indiviidmuutuja() {
			return getRuleContext(IndiviidmuutujaContext.class,0);
		}
		public EksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eks; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterEks(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitEks(this);
		}
	}

	public final EksContext eks() throws RecognitionException {
		EksContext _localctx = new EksContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_eks);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126); match(T__7);
			setState(127); indiviidmuutuja();
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

	public static class KorgvalemContext extends ParserRuleContext {
		public AtomaarnevalemContext atomaarnevalem() {
			return getRuleContext(AtomaarnevalemContext.class,0);
		}
		public PredvalemContext predvalem() {
			return getRuleContext(PredvalemContext.class,0);
		}
		public KorgvalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_korgvalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterKorgvalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitKorgvalem(this);
		}
	}

	public final KorgvalemContext korgvalem() throws RecognitionException {
		KorgvalemContext _localctx = new KorgvalemContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_korgvalem);
		try {
			setState(134);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(129); match(T__5);
				setState(130); predvalem();
				setState(131); match(T__4);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133); atomaarnevalem();
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

	public static class AtomaarnevalemContext extends ParserRuleContext {
		public PredsymbolContext predsymbol() {
			return getRuleContext(PredsymbolContext.class,0);
		}
		public TermargumendidContext termargumendid(int i) {
			return getRuleContext(TermargumendidContext.class,i);
		}
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TermargumendidContext> termargumendid() {
			return getRuleContexts(TermargumendidContext.class);
		}
		public AtomaarnevalemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomaarnevalem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterAtomaarnevalem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitAtomaarnevalem(this);
		}
	}

	public final AtomaarnevalemContext atomaarnevalem() throws RecognitionException {
		AtomaarnevalemContext _localctx = new AtomaarnevalemContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_atomaarnevalem);
		try {
			int _alt;
			setState(147);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(136); predsymbol();
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(137); termargumendid();
						}
						} 
					}
					setState(142);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(143); term();
				setState(144); match(T__11);
				setState(145); term();
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

	public static class TermargumendidContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TermargumendidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termargumendid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterTermargumendid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitTermargumendid(this);
		}
	}

	public final TermargumendidContext termargumendid() throws RecognitionException {
		TermargumendidContext _localctx = new TermargumendidContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_termargumendid);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149); match(T__5);
			setState(150); term();
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(151); match(T__1);
				setState(152); term();
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158); match(T__4);
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

	public static class TermContext extends ParserRuleContext {
		public List<PmtermContext> pmterm() {
			return getRuleContexts(PmtermContext.class);
		}
		public PmtermContext pmterm(int i) {
			return getRuleContext(PmtermContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_term);
		int _la;
		try {
			int _alt;
			setState(176);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160); pmterm();
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(161); match(T__2);
					setState(162); pmterm();
					}
					}
					setState(167);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(168); pmterm();
				setState(173);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(169); match(T__0);
						setState(170); pmterm();
						}
						} 
					}
					setState(175);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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

	public static class PmtermContext extends ParserRuleContext {
		public List<KjtermContext> kjterm() {
			return getRuleContexts(KjtermContext.class);
		}
		public KjtermContext kjterm(int i) {
			return getRuleContext(KjtermContext.class,i);
		}
		public PmtermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pmterm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterPmterm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitPmterm(this);
		}
	}

	public final PmtermContext pmterm() throws RecognitionException {
		PmtermContext _localctx = new PmtermContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_pmterm);
		int _la;
		try {
			setState(194);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(178); kjterm();
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(179); match(T__3);
					setState(180); kjterm();
					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(186); kjterm();
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16) {
					{
					{
					setState(187); match(T__16);
					setState(188); kjterm();
					}
					}
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
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

	public static class KjtermContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public IndiviidmuutujaContext indiviidmuutuja() {
			return getRuleContext(IndiviidmuutujaContext.class,0);
		}
		public KjtermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kjterm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).enterKjterm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PredListener ) ((PredListener)listener).exitKjterm(this);
		}
	}

	public final KjtermContext kjterm() throws RecognitionException {
		KjtermContext _localctx = new KjtermContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_kjterm);
		try {
			setState(203);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(196); match(T__15);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(197); match(T__14);
				}
				break;
			case TAHT:
				enterOuterAlt(_localctx, 3);
				{
				setState(198); indiviidmuutuja();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(199); match(T__5);
				setState(200); term();
				setState(201); match(T__4);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26\u00d0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\7\2,\n\2\f\2\16\2/\13\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\7\49\n\4\f\4\16\4<\13\4\3\5\3\5\3\6\3\6\7\6B\n\6"+
		"\f\6\16\6E\13\6\3\7\3\7\3\7\3\7\7\7K\n\7\f\7\16\7N\13\7\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\7\tW\n\t\f\t\16\tZ\13\t\3\n\3\n\3\n\7\n_\n\n\f\n\16\nb"+
		"\13\n\3\13\3\13\3\13\7\13g\n\13\f\13\16\13j\13\13\3\f\3\f\3\f\7\fo\n\f"+
		"\f\f\16\fr\13\f\3\r\3\r\3\r\7\rw\n\r\f\r\16\rz\13\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\5\20\u0089\n\20\3\21\3\21"+
		"\7\21\u008d\n\21\f\21\16\21\u0090\13\21\3\21\3\21\3\21\3\21\5\21\u0096"+
		"\n\21\3\22\3\22\3\22\3\22\7\22\u009c\n\22\f\22\16\22\u009f\13\22\3\22"+
		"\3\22\3\23\3\23\3\23\7\23\u00a6\n\23\f\23\16\23\u00a9\13\23\3\23\3\23"+
		"\3\23\7\23\u00ae\n\23\f\23\16\23\u00b1\13\23\5\23\u00b3\n\23\3\24\3\24"+
		"\3\24\7\24\u00b8\n\24\f\24\16\24\u00bb\13\24\3\24\3\24\3\24\7\24\u00c0"+
		"\n\24\f\24\16\24\u00c3\13\24\5\24\u00c5\n\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00ce\n\25\3\25\2\2\26\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(\2\3\3\2\24\25\u00d3\2-\3\2\2\2\4\62\3\2\2\2\6\66\3\2\2\2"+
		"\b=\3\2\2\2\n?\3\2\2\2\fF\3\2\2\2\16Q\3\2\2\2\20S\3\2\2\2\22[\3\2\2\2"+
		"\24c\3\2\2\2\26k\3\2\2\2\30x\3\2\2\2\32}\3\2\2\2\34\u0080\3\2\2\2\36\u0088"+
		"\3\2\2\2 \u0095\3\2\2\2\"\u0097\3\2\2\2$\u00b2\3\2\2\2&\u00c4\3\2\2\2"+
		"(\u00cd\3\2\2\2*,\5\4\3\2+*\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\60"+
		"\3\2\2\2/-\3\2\2\2\60\61\5\20\t\2\61\3\3\2\2\2\62\63\5\6\4\2\63\64\7\n"+
		"\2\2\64\65\5\20\t\2\65\5\3\2\2\2\66:\5\b\5\2\679\5\f\7\28\67\3\2\2\29"+
		"<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;\7\3\2\2\2<:\3\2\2\2=>\5\n\6\2>\t\3\2\2"+
		"\2?C\7\25\2\2@B\t\2\2\2A@\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2D\13\3"+
		"\2\2\2EC\3\2\2\2FG\7\16\2\2GL\5\16\b\2HI\7\22\2\2IK\5\16\b\2JH\3\2\2\2"+
		"KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NL\3\2\2\2OP\7\17\2\2P\r\3\2\2"+
		"\2QR\7\25\2\2R\17\3\2\2\2SX\5\22\n\2TU\7\t\2\2UW\5\22\n\2VT\3\2\2\2WZ"+
		"\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\21\3\2\2\2ZX\3\2\2\2[`\5\24\13\2\\]\7\6"+
		"\2\2]_\5\24\13\2^\\\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2a\23\3\2\2\2"+
		"b`\3\2\2\2ch\5\26\f\2de\7\7\2\2eg\5\26\f\2fd\3\2\2\2gj\3\2\2\2hf\3\2\2"+
		"\2hi\3\2\2\2i\25\3\2\2\2jh\3\2\2\2kp\5\30\r\2lm\7\r\2\2mo\5\30\r\2nl\3"+
		"\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\27\3\2\2\2rp\3\2\2\2sw\7\23\2\2"+
		"tw\5\32\16\2uw\5\34\17\2vs\3\2\2\2vt\3\2\2\2vu\3\2\2\2wz\3\2\2\2xv\3\2"+
		"\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{|\5\36\20\2|\31\3\2\2\2}~\7\13\2\2"+
		"~\177\5\16\b\2\177\33\3\2\2\2\u0080\u0081\7\f\2\2\u0081\u0082\5\16\b\2"+
		"\u0082\35\3\2\2\2\u0083\u0084\7\16\2\2\u0084\u0085\5\20\t\2\u0085\u0086"+
		"\7\17\2\2\u0086\u0089\3\2\2\2\u0087\u0089\5 \21\2\u0088\u0083\3\2\2\2"+
		"\u0088\u0087\3\2\2\2\u0089\37\3\2\2\2\u008a\u008e\5\b\5\2\u008b\u008d"+
		"\5\"\22\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2"+
		"\u008e\u008f\3\2\2\2\u008f\u0096\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092"+
		"\5$\23\2\u0092\u0093\7\b\2\2\u0093\u0094\5$\23\2\u0094\u0096\3\2\2\2\u0095"+
		"\u008a\3\2\2\2\u0095\u0091\3\2\2\2\u0096!\3\2\2\2\u0097\u0098\7\16\2\2"+
		"\u0098\u009d\5$\23\2\u0099\u009a\7\22\2\2\u009a\u009c\5$\23\2\u009b\u0099"+
		"\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7\17\2\2\u00a1#\3\2\2\2"+
		"\u00a2\u00a7\5&\24\2\u00a3\u00a4\7\21\2\2\u00a4\u00a6\5&\24\2\u00a5\u00a3"+
		"\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00b3\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00af\5&\24\2\u00ab\u00ac\7\23"+
		"\2\2\u00ac\u00ae\5&\24\2\u00ad\u00ab\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af"+
		"\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2"+
		"\2\2\u00b2\u00a2\3\2\2\2\u00b2\u00aa\3\2\2\2\u00b3%\3\2\2\2\u00b4\u00b9"+
		"\5(\25\2\u00b5\u00b6\7\20\2\2\u00b6\u00b8\5(\25\2\u00b7\u00b5\3\2\2\2"+
		"\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00c5"+
		"\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00c1\5(\25\2\u00bd\u00be\7\3\2\2\u00be"+
		"\u00c0\5(\25\2\u00bf\u00bd\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4"+
		"\u00b4\3\2\2\2\u00c4\u00bc\3\2\2\2\u00c5\'\3\2\2\2\u00c6\u00ce\7\4\2\2"+
		"\u00c7\u00ce\7\5\2\2\u00c8\u00ce\5\16\b\2\u00c9\u00ca\7\16\2\2\u00ca\u00cb"+
		"\5$\23\2\u00cb\u00cc\7\17\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00c6\3\2\2\2"+
		"\u00cd\u00c7\3\2\2\2\u00cd\u00c8\3\2\2\2\u00cd\u00c9\3\2\2\2\u00ce)\3"+
		"\2\2\2\27-:CLX`hpvx\u0088\u008e\u0095\u009d\u00a7\u00af\u00b2\u00b9\u00c1"+
		"\u00c4\u00cd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}