// $ANTLR 3.0.1 /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g 2009-09-30 16:22:05
package com.octo.gwt.test17.integ.csvrunner;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;

public class XPathLexer extends Lexer {
	public static final int SLASH = 4;
	public static final int IN = 6;
	public static final int OUT_COND = 10;
	public static final int OUT = 7;
	public static final int LABEL = 5;
	public static final int VIRG = 8;
	public static final int IN_COND = 9;
	public static final int VALUE = 11;
	public static final int EQ = 12;
	public static final int Tokens = 13;
	public static final int EOF = -1;

	public XPathLexer() {
		;
	}

	public XPathLexer(CharStream input) {
		super(input);
	}

	public String getGrammarFileName() {
		return "/me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g";
	}

	// $ANTLR start SLASH
	public final void mSLASH() throws RecognitionException {
		try {
			int _type = SLASH;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:67:7: ( '/' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:67:9: '/'
			{
				match('/');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end SLASH

	// $ANTLR start IN
	public final void mIN() throws RecognitionException {
		try {
			int _type = IN;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:68:4: ( '(' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:68:6: '('
			{
				match('(');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end IN

	// $ANTLR start OUT
	public final void mOUT() throws RecognitionException {
		try {
			int _type = OUT;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:69:5: ( ')' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:69:7: ')'
			{
				match(')');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end OUT

	// $ANTLR start IN_COND
	public final void mIN_COND() throws RecognitionException {
		try {
			int _type = IN_COND;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:70:9: ( '[' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:70:11: '['
			{
				match('[');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end IN_COND

	// $ANTLR start OUT_COND
	public final void mOUT_COND() throws RecognitionException {
		try {
			int _type = OUT_COND;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:71:10: ( ']' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:71:12: ']'
			{
				match(']');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end OUT_COND

	// $ANTLR start VIRG
	public final void mVIRG() throws RecognitionException {
		try {
			int _type = VIRG;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:72:6: ( ',' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:72:8: ','
			{
				match(',');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end VIRG

	// $ANTLR start EQ
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:73:4: ( '=' )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:73:6: '='
			{
				match('=');

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end EQ

	// $ANTLR start LABEL
	public final void mLABEL() throws RecognitionException {
		try {
			int _type = LABEL;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:74:7: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' ) )* )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:74:9: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' ) )*
			{
				if ((input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
						|| (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
					input.consume();

				} else {
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}

				// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:74:43: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' ) )*
				loop1: do {
					int alt1 = 2;
					int LA1_0 = input.LA(1);

					if (((LA1_0 >= '0' && LA1_0 <= '9') || (LA1_0 >= 'A' && LA1_0 <= 'Z') || LA1_0 == '_' || (LA1_0 >= 'a' && LA1_0 <= 'z'))) {
						alt1 = 1;
					}

					switch (alt1) {
					case 1:
						// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:74:44: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )
					{
						if ((input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_'
								|| (input.LA(1) >= 'a' && input.LA(1) <= 'z')) {
							input.consume();

						} else {
							MismatchedSetException mse = new MismatchedSetException(null, input);
							recover(mse);
							throw mse;
						}

					}
						break;

					default:
						break loop1;
					}
				} while (true);

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end LABEL

	// $ANTLR start VALUE
	public final void mVALUE() throws RecognitionException {
		try {
			int _type = VALUE;
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:75:7: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' ) ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' | '?' | '-' | ' ' | '_' ) )* )
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:75:9: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' ) ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' | '?' | '-' | ' ' | '_' ) )*
			{
				if (input.LA(1) == '.' || (input.LA(1) >= '0' && input.LA(1) <= '9') || (input.LA(1) >= 'A' && input.LA(1) <= 'Z')
						|| (input.LA(1) >= 'a' && input.LA(1) <= 'z') || input.LA(1) == '\u00E0'
						|| (input.LA(1) >= '\u00E8' && input.LA(1) <= '\u00EA')) {
					input.consume();

				} else {
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recover(mse);
					throw mse;
				}

				// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:75:93: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' | '?' | '-' | ' ' | '_' ) )*
				loop2: do {
					int alt2 = 2;
					int LA2_0 = input.LA(1);

					if ((LA2_0 == ' ' || (LA2_0 >= '-' && LA2_0 <= '.') || (LA2_0 >= '0' && LA2_0 <= '9') || LA2_0 == '?'
							|| (LA2_0 >= 'A' && LA2_0 <= 'Z') || LA2_0 == '_' || (LA2_0 >= 'a' && LA2_0 <= 'z') || LA2_0 == '\u00E0' || (LA2_0 >= '\u00E8' && LA2_0 <= '\u00EA'))) {
						alt2 = 1;
					}

					switch (alt2) {
					case 1:
						// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:75:94: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '\\u00E0' | '\\u00E8' | '\\u00E9' | '\\u00EA' | '.' | '?' | '-' | ' ' | '_' )
					{
						if (input.LA(1) == ' ' || (input.LA(1) >= '-' && input.LA(1) <= '.') || (input.LA(1) >= '0' && input.LA(1) <= '9')
								|| input.LA(1) == '?' || (input.LA(1) >= 'A' && input.LA(1) <= 'Z') || input.LA(1) == '_'
								|| (input.LA(1) >= 'a' && input.LA(1) <= 'z') || input.LA(1) == '\u00E0'
								|| (input.LA(1) >= '\u00E8' && input.LA(1) <= '\u00EA')) {
							input.consume();

						} else {
							MismatchedSetException mse = new MismatchedSetException(null, input);
							recover(mse);
							throw mse;
						}

					}
						break;

					default:
						break loop2;
					}
				} while (true);

			}

			this.type = _type;
		} finally {
		}
	}

	// $ANTLR end VALUE

	public void mTokens() throws RecognitionException {
		// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:8: ( SLASH | IN | OUT | IN_COND | OUT_COND | VIRG | EQ | LABEL | VALUE )
		int alt3 = 9;
		alt3 = dfa3.predict(input);
		switch (alt3) {
		case 1:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:10: SLASH
		{
			mSLASH();

		}
			break;
		case 2:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:16: IN
		{
			mIN();

		}
			break;
		case 3:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:19: OUT
		{
			mOUT();

		}
			break;
		case 4:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:23: IN_COND
		{
			mIN_COND();

		}
			break;
		case 5:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:31: OUT_COND
		{
			mOUT_COND();

		}
			break;
		case 6:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:40: VIRG
		{
			mVIRG();

		}
			break;
		case 7:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:45: EQ
		{
			mEQ();

		}
			break;
		case 8:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:48: LABEL
		{
			mLABEL();

		}
			break;
		case 9:
			// /me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test17/integ/csvrunner/XPath.g:1:54: VALUE
		{
			mVALUE();

		}
			break;

		}

	}

	protected DFA3 dfa3 = new DFA3(this);
	static final String DFA3_eotS = "\10\uffff\1\13\1\uffff\1\13\1\uffff";
	static final String DFA3_eofS = "\14\uffff";
	static final String DFA3_minS = "\1\50\7\uffff\1\40\1\uffff\1\40\1\uffff";
	static final String DFA3_maxS = "\1\u00ea\7\uffff\1\u00ea\1\uffff\1\u00ea\1\uffff";
	static final String DFA3_acceptS = "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\uffff\1\11\1\uffff\1\10";
	static final String DFA3_specialS = "\14\uffff}>";
	static final String[] DFA3_transitionS = {
			"\1\2\1\3\2\uffff\1\6\1\uffff\1\11\1\1\12\10\3\uffff\1\7\3\uffff" + "\32\10\1\4\1\uffff\1\5\3\uffff\32\10\145\uffff\1\11\7\uffff"
					+ "\3\11", "", "", "", "", "", "", "",
			"\1\11\14\uffff\2\11\1\uffff\12\12\5\uffff\1\11\1\uffff\32\12" + "\4\uffff\1\12\1\uffff\32\12\145\uffff\1\11\7\uffff\3\11", "",
			"\1\11\14\uffff\2\11\1\uffff\12\12\5\uffff\1\11\1\uffff\32\12" + "\4\uffff\1\12\1\uffff\32\12\145\uffff\1\11\7\uffff\3\11", "" };

	static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
	static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
	static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
	static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
	static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
	static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
	static final short[][] DFA3_transition;

	static {
		int numStates = DFA3_transitionS.length;
		DFA3_transition = new short[numStates][];
		for (int i = 0; i < numStates; i++) {
			DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
		}
	}

	class DFA3 extends DFA {

		public DFA3(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 3;
			this.eot = DFA3_eot;
			this.eof = DFA3_eof;
			this.min = DFA3_min;
			this.max = DFA3_max;
			this.accept = DFA3_accept;
			this.special = DFA3_special;
			this.transition = DFA3_transition;
		}

		public String getDescription() {
			return "1:1: Tokens : ( SLASH | IN | OUT | IN_COND | OUT_COND | VIRG | EQ | LABEL | VALUE );";
		}
	}

}