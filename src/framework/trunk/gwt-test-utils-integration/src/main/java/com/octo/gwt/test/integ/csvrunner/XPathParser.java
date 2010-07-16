// $ANTLR 3.0.1 E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g 2010-07-08 15:37:10
package com.octo.gwt.test.integ.csvrunner;

import java.util.Stack;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

public class XPathParser extends Parser {
	public static final String[] tokenNames = new String[] { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SLASH", "LABEL", "IN", "OUT", "VIRG",
			"IN_COND", "OUT_COND", "VALUE", "EQ" };
	public static final int SLASH = 4;
	public static final int IN = 6;
	public static final int OUT_COND = 10;
	public static final int LABEL = 5;
	public static final int OUT = 7;
	public static final int VIRG = 8;
	public static final int IN_COND = 9;
	public static final int EQ = 12;
	public static final int VALUE = 11;
	public static final int EOF = -1;

	public XPathParser(TokenStream input) {
		super(input);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}

	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}

	public String[] getTokenNames() {
		return tokenNames;
	}

	public String getGrammarFileName() {
		return "/me/workspace/gwt-test/git/gwt-test-utils-integration/src/main/resources/com/octo/gwt/test/integ/csvrunner/XPath.g";
	}

	public Node root;

	public static class expr_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start expr
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:21:1: expr : SLASH t= token ;
	public final expr_return expr() throws RecognitionException {
		expr_return retval = new expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token SLASH1 = null;
		token_return t = null;

		Object SLASH1_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:21:6: ( SLASH t= token )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:21:8: SLASH t= token
			{
				root_0 = (Object) adaptor.nil();

				SLASH1 = (Token) input.LT(1);
				match(input, SLASH, FOLLOW_SLASH_in_expr50);
				SLASH1_tree = (Object) adaptor.create(SLASH1);
				adaptor.addChild(root_0, SLASH1_tree);

				pushFollow(FOLLOW_token_in_expr54);
				t = token();
				_fsp--;

				adaptor.addChild(root_0, t.getTree());

				root = t.result;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end expr

	protected static class token_scope {
		Node currentNode;
	}

	protected Stack<token_scope> token_stack = new Stack<token_scope>();

	public static class token_return extends ParserRuleReturnScope {
		public Node result;
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start token
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:25:1: token returns [Node result] : element ( SLASH t= token )? ;
	public final token_return token() throws RecognitionException {
		token_stack.push(new token_scope());
		token_return retval = new token_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token SLASH3 = null;
		token_return t = null;

		element_return element2 = null;

		Object SLASH3_tree = null;

		((token_scope) token_stack.peek()).currentNode = new Node();

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:30:1: ( element ( SLASH t= token )? )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:30:3: element ( SLASH t= token )?
			{
				root_0 = (Object) adaptor.nil();

				pushFollow(FOLLOW_element_in_token77);
				element2 = element();
				_fsp--;

				adaptor.addChild(root_0, element2.getTree());
				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:30:11: ( SLASH t= token )?
				int alt1 = 2;
				int LA1_0 = input.LA(1);

				if ((LA1_0 == SLASH)) {
					alt1 = 1;
				}
				switch (alt1) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:30:12: SLASH t= token
				{
					SLASH3 = (Token) input.LT(1);
					match(input, SLASH, FOLLOW_SLASH_in_token80);
					SLASH3_tree = (Object) adaptor.create(SLASH3);
					adaptor.addChild(root_0, SLASH3_tree);

					pushFollow(FOLLOW_token_in_token84);
					t = token();
					_fsp--;

					adaptor.addChild(root_0, t.getTree());

				}
					break;

				}

				if (t != null) {
					((token_scope) token_stack.peek()).currentNode.setNext(t.result);
				}
				retval.result = ((token_scope) token_stack.peek()).currentNode;

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
			token_stack.pop();
		}
		return retval;
	}

	// $ANTLR end token

	public static class element_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start element
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:38:1: element : label= LABEL ( sub )? ;
	public final element_return element() throws RecognitionException {
		element_return retval = new element_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token label = null;
		sub_return sub4 = null;

		Object label_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:38:9: (label= LABEL ( sub )? )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:38:11: label= LABEL ( sub )?
			{
				root_0 = (Object) adaptor.nil();

				label = (Token) input.LT(1);
				match(input, LABEL, FOLLOW_LABEL_in_element99);
				label_tree = (Object) adaptor.create(label);
				adaptor.addChild(root_0, label_tree);

				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:38:23: ( sub )?
				int alt2 = 2;
				int LA2_0 = input.LA(1);

				if ((LA2_0 == IN || LA2_0 == IN_COND)) {
					alt2 = 1;
				}
				switch (alt2) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:38:23: sub
				{
					pushFollow(FOLLOW_sub_in_element101);
					sub4 = sub();
					_fsp--;

					adaptor.addChild(root_0, sub4.getTree());

				}
					break;

				}

				((token_scope) token_stack.peek()).currentNode.setLabel(label.getText());

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end element

	public static class sub_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start sub
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:1: sub : ( parenthesis | condition ) ;
	public final sub_return sub() throws RecognitionException {
		sub_return retval = new sub_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		parenthesis_return parenthesis5 = null;

		condition_return condition6 = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:5: ( ( parenthesis | condition ) )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:7: ( parenthesis | condition )
			{
				root_0 = (Object) adaptor.nil();

				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:7: ( parenthesis | condition )
				int alt3 = 2;
				int LA3_0 = input.LA(1);

				if ((LA3_0 == IN)) {
					alt3 = 1;
				} else if ((LA3_0 == IN_COND)) {
					alt3 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("42:7: ( parenthesis | condition )", 3, 0, input);

					throw nvae;
				}
				switch (alt3) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:8: parenthesis
				{
					pushFollow(FOLLOW_parenthesis_in_sub113);
					parenthesis5 = parenthesis();
					_fsp--;

					adaptor.addChild(root_0, parenthesis5.getTree());

				}
					break;
				case 2:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:42:22: condition
				{
					pushFollow(FOLLOW_condition_in_sub117);
					condition6 = condition();
					_fsp--;

					adaptor.addChild(root_0, condition6.getTree());

				}
					break;

				}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end sub

	public static class parenthesis_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start parenthesis
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:44:1: parenthesis : IN params OUT ;
	public final parenthesis_return parenthesis() throws RecognitionException {
		parenthesis_return retval = new parenthesis_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IN7 = null;
		Token OUT9 = null;
		params_return params8 = null;

		Object IN7_tree = null;
		Object OUT9_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:44:13: ( IN params OUT )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:44:15: IN params OUT
			{
				root_0 = (Object) adaptor.nil();

				IN7 = (Token) input.LT(1);
				match(input, IN, FOLLOW_IN_in_parenthesis126);
				IN7_tree = (Object) adaptor.create(IN7);
				adaptor.addChild(root_0, IN7_tree);

				pushFollow(FOLLOW_params_in_parenthesis128);
				params8 = params();
				_fsp--;

				adaptor.addChild(root_0, params8.getTree());
				OUT9 = (Token) input.LT(1);
				match(input, OUT, FOLLOW_OUT_in_parenthesis130);
				OUT9_tree = (Object) adaptor.create(OUT9);
				adaptor.addChild(root_0, OUT9_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end parenthesis

	public static class params_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start params
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:46:1: params : v= value_expr ( VIRG params )? ;
	public final params_return params() throws RecognitionException {
		params_return retval = new params_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token VIRG10 = null;
		value_expr_return v = null;

		params_return params11 = null;

		Object VIRG10_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:46:8: (v= value_expr ( VIRG params )? )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:46:10: v= value_expr ( VIRG params )?
			{
				root_0 = (Object) adaptor.nil();

				pushFollow(FOLLOW_value_expr_in_params140);
				v = value_expr();
				_fsp--;

				adaptor.addChild(root_0, v.getTree());
				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:46:23: ( VIRG params )?
				int alt4 = 2;
				int LA4_0 = input.LA(1);

				if ((LA4_0 == VIRG)) {
					alt4 = 1;
				}
				switch (alt4) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:46:24: VIRG params
				{
					VIRG10 = (Token) input.LT(1);
					match(input, VIRG, FOLLOW_VIRG_in_params143);
					VIRG10_tree = (Object) adaptor.create(VIRG10);
					adaptor.addChild(root_0, VIRG10_tree);

					pushFollow(FOLLOW_params_in_params145);
					params11 = params();
					_fsp--;

					adaptor.addChild(root_0, params11.getTree());

				}
					break;

				}

				((token_scope) token_stack.peek()).currentNode.insertParam(input.toString(v.start, v.stop));

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end params

	public static class condition_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start condition
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:50:1: condition : IN_COND internal_condition OUT_COND ;
	public final condition_return condition() throws RecognitionException {
		condition_return retval = new condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IN_COND12 = null;
		Token OUT_COND14 = null;
		internal_condition_return internal_condition13 = null;

		Object IN_COND12_tree = null;
		Object OUT_COND14_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:50:11: ( IN_COND internal_condition OUT_COND )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:50:13: IN_COND internal_condition OUT_COND
			{
				root_0 = (Object) adaptor.nil();

				IN_COND12 = (Token) input.LT(1);
				match(input, IN_COND, FOLLOW_IN_COND_in_condition158);
				IN_COND12_tree = (Object) adaptor.create(IN_COND12);
				adaptor.addChild(root_0, IN_COND12_tree);

				pushFollow(FOLLOW_internal_condition_in_condition160);
				internal_condition13 = internal_condition();
				_fsp--;

				adaptor.addChild(root_0, internal_condition13.getTree());
				OUT_COND14 = (Token) input.LT(1);
				match(input, OUT_COND, FOLLOW_OUT_COND_in_condition162);
				OUT_COND14_tree = (Object) adaptor.create(OUT_COND14);
				adaptor.addChild(root_0, OUT_COND14_tree);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end condition

	public static class internal_condition_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start internal_condition
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:1: internal_condition : ( simple_expr | eq_expr ) ;
	public final internal_condition_return internal_condition() throws RecognitionException {
		internal_condition_return retval = new internal_condition_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		simple_expr_return simple_expr15 = null;

		eq_expr_return eq_expr16 = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:20: ( ( simple_expr | eq_expr ) )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:22: ( simple_expr | eq_expr )
			{
				root_0 = (Object) adaptor.nil();

				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:22: ( simple_expr | eq_expr )
				int alt5 = 2;
				int LA5_0 = input.LA(1);

				if ((LA5_0 == LABEL)) {
					int LA5_1 = input.LA(2);

					if ((LA5_1 == SLASH || LA5_1 == IN || LA5_1 == IN_COND || LA5_1 == EQ)) {
						alt5 = 2;
					} else if ((LA5_1 == OUT_COND)) {
						alt5 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("52:22: ( simple_expr | eq_expr )", 5, 1, input);

						throw nvae;
					}
				} else if ((LA5_0 == VALUE)) {
					alt5 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("52:22: ( simple_expr | eq_expr )", 5, 0, input);

					throw nvae;
				}
				switch (alt5) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:23: simple_expr
				{
					pushFollow(FOLLOW_simple_expr_in_internal_condition171);
					simple_expr15 = simple_expr();
					_fsp--;

					adaptor.addChild(root_0, simple_expr15.getTree());

				}
					break;
				case 2:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:52:37: eq_expr
				{
					pushFollow(FOLLOW_eq_expr_in_internal_condition175);
					eq_expr16 = eq_expr();
					_fsp--;

					adaptor.addChild(root_0, eq_expr16.getTree());

				}
					break;

				}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end internal_condition

	public static class simple_expr_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start simple_expr
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:54:1: simple_expr : s= value_expr ;
	public final simple_expr_return simple_expr() throws RecognitionException {
		simple_expr_return retval = new simple_expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		value_expr_return s = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:54:13: (s= value_expr )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:54:15: s= value_expr
			{
				root_0 = (Object) adaptor.nil();

				pushFollow(FOLLOW_value_expr_in_simple_expr186);
				s = value_expr();
				_fsp--;

				adaptor.addChild(root_0, s.getTree());

				((token_scope) token_stack.peek()).currentNode.setMap(input.toString(s.start, s.stop));

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end simple_expr

	public static class value_expr_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start value_expr
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:58:1: value_expr : ( VALUE | LABEL ) ;
	public final value_expr_return value_expr() throws RecognitionException {
		value_expr_return retval = new value_expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token set17 = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:58:12: ( ( VALUE | LABEL ) )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:58:14: ( VALUE | LABEL )
			{
				root_0 = (Object) adaptor.nil();

				set17 = (Token) input.LT(1);
				if (input.LA(1) == LABEL || input.LA(1) == VALUE) {
					input.consume();
					adaptor.addChild(root_0, adaptor.create(set17));
					errorRecovery = false;
				} else {
					MismatchedSetException mse = new MismatchedSetException(null, input);
					recoverFromMismatchedSet(input, mse, FOLLOW_set_in_value_expr196);
					throw mse;
				}

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end value_expr

	public static class value_expr_parenthesis_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start value_expr_parenthesis
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:1: value_expr_parenthesis : ( value_expr | IN | OUT ) ( ( value_expr | IN | OUT ) )* ;
	public final value_expr_parenthesis_return value_expr_parenthesis() throws RecognitionException {
		value_expr_parenthesis_return retval = new value_expr_parenthesis_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token IN19 = null;
		Token OUT20 = null;
		Token IN22 = null;
		Token OUT23 = null;
		value_expr_return value_expr18 = null;

		value_expr_return value_expr21 = null;

		Object IN19_tree = null;
		Object OUT20_tree = null;
		Object IN22_tree = null;
		Object OUT23_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:24: ( ( value_expr | IN | OUT ) ( ( value_expr | IN | OUT ) )* )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:26: ( value_expr | IN | OUT ) ( ( value_expr | IN | OUT ) )*
			{
				root_0 = (Object) adaptor.nil();

				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:26: ( value_expr | IN | OUT )
				int alt6 = 3;
				switch (input.LA(1)) {
				case LABEL:
				case VALUE: {
					alt6 = 1;
				}
					break;
				case IN: {
					alt6 = 2;
				}
					break;
				case OUT: {
					alt6 = 3;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("60:26: ( value_expr | IN | OUT )", 6, 0, input);

					throw nvae;
				}

				switch (alt6) {
				case 1:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:27: value_expr
				{
					pushFollow(FOLLOW_value_expr_in_value_expr_parenthesis211);
					value_expr18 = value_expr();
					_fsp--;

					adaptor.addChild(root_0, value_expr18.getTree());

				}
					break;
				case 2:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:40: IN
				{
					IN19 = (Token) input.LT(1);
					match(input, IN, FOLLOW_IN_in_value_expr_parenthesis215);
					IN19_tree = (Object) adaptor.create(IN19);
					adaptor.addChild(root_0, IN19_tree);

				}
					break;
				case 3:
					// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:45: OUT
				{
					OUT20 = (Token) input.LT(1);
					match(input, OUT, FOLLOW_OUT_in_value_expr_parenthesis219);
					OUT20_tree = (Object) adaptor.create(OUT20);
					adaptor.addChild(root_0, OUT20_tree);

				}
					break;

				}

				// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:50: ( ( value_expr | IN | OUT ) )*
				loop8: do {
					int alt8 = 2;
					int LA8_0 = input.LA(1);

					if (((LA8_0 >= LABEL && LA8_0 <= OUT) || LA8_0 == VALUE)) {
						alt8 = 1;
					}

					switch (alt8) {
					case 1:
						// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:51: ( value_expr | IN | OUT )
					{
						// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:51: ( value_expr | IN | OUT )
						int alt7 = 3;
						switch (input.LA(1)) {
						case LABEL:
						case VALUE: {
							alt7 = 1;
						}
							break;
						case IN: {
							alt7 = 2;
						}
							break;
						case OUT: {
							alt7 = 3;
						}
							break;
						default:
							NoViableAltException nvae = new NoViableAltException("60:51: ( value_expr | IN | OUT )", 7, 0, input);

							throw nvae;
						}

						switch (alt7) {
						case 1:
							// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:52: value_expr
						{
							pushFollow(FOLLOW_value_expr_in_value_expr_parenthesis224);
							value_expr21 = value_expr();
							_fsp--;

							adaptor.addChild(root_0, value_expr21.getTree());

						}
							break;
						case 2:
							// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:65: IN
						{
							IN22 = (Token) input.LT(1);
							match(input, IN, FOLLOW_IN_in_value_expr_parenthesis228);
							IN22_tree = (Object) adaptor.create(IN22);
							adaptor.addChild(root_0, IN22_tree);

						}
							break;
						case 3:
							// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:60:70: OUT
						{
							OUT23 = (Token) input.LT(1);
							match(input, OUT, FOLLOW_OUT_in_value_expr_parenthesis232);
							OUT23_tree = (Object) adaptor.create(OUT23);
							adaptor.addChild(root_0, OUT23_tree);

						}
							break;

						}

					}
						break;

					default:
						break loop8;
					}
				} while (true);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end value_expr_parenthesis

	public static class eq_expr_return extends ParserRuleReturnScope {
		Object tree;

		public Object getTree() {
			return tree;
		}
	};

	// $ANTLR start eq_expr
	// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:62:1: eq_expr : t= token EQ v= value_expr_parenthesis ;
	public final eq_expr_return eq_expr() throws RecognitionException {
		eq_expr_return retval = new eq_expr_return();
		retval.start = input.LT(1);

		Object root_0 = null;

		Token EQ24 = null;
		token_return t = null;

		value_expr_parenthesis_return v = null;

		Object EQ24_tree = null;

		try {
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:62:9: (t= token EQ v= value_expr_parenthesis )
			// E:\\KNCF6659\\Data\\gwt-test-utils\\gwt-test-utils-integration\\src\\main\\resources\\com\\octo\\gwt\\test\\integ\\csvrunner\\XPath.g:62:11: t= token EQ v= value_expr_parenthesis
			{
				root_0 = (Object) adaptor.nil();

				pushFollow(FOLLOW_token_in_eq_expr245);
				t = token();
				_fsp--;

				adaptor.addChild(root_0, t.getTree());
				EQ24 = (Token) input.LT(1);
				match(input, EQ, FOLLOW_EQ_in_eq_expr247);
				EQ24_tree = (Object) adaptor.create(EQ24);
				adaptor.addChild(root_0, EQ24_tree);

				pushFollow(FOLLOW_value_expr_parenthesis_in_eq_expr251);
				v = value_expr_parenthesis();
				_fsp--;

				adaptor.addChild(root_0, v.getTree());

				((token_scope) token_stack.peek()).currentNode.setMap(input.toString(v.start, v.stop));
				((token_scope) token_stack.peek()).currentNode.setMapEq(t.result);

			}

			retval.stop = input.LT(-1);

			retval.tree = (Object) adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
			throw e;
		} finally {
		}
		return retval;
	}

	// $ANTLR end eq_expr

	public static final BitSet FOLLOW_SLASH_in_expr50 = new BitSet(new long[] { 0x0000000000000020L });
	public static final BitSet FOLLOW_token_in_expr54 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_element_in_token77 = new BitSet(new long[] { 0x0000000000000012L });
	public static final BitSet FOLLOW_SLASH_in_token80 = new BitSet(new long[] { 0x0000000000000020L });
	public static final BitSet FOLLOW_token_in_token84 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_LABEL_in_element99 = new BitSet(new long[] { 0x0000000000000242L });
	public static final BitSet FOLLOW_sub_in_element101 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_parenthesis_in_sub113 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_condition_in_sub117 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_IN_in_parenthesis126 = new BitSet(new long[] { 0x0000000000000820L });
	public static final BitSet FOLLOW_params_in_parenthesis128 = new BitSet(new long[] { 0x0000000000000080L });
	public static final BitSet FOLLOW_OUT_in_parenthesis130 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_value_expr_in_params140 = new BitSet(new long[] { 0x0000000000000102L });
	public static final BitSet FOLLOW_VIRG_in_params143 = new BitSet(new long[] { 0x0000000000000820L });
	public static final BitSet FOLLOW_params_in_params145 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_IN_COND_in_condition158 = new BitSet(new long[] { 0x0000000000000820L });
	public static final BitSet FOLLOW_internal_condition_in_condition160 = new BitSet(new long[] { 0x0000000000000400L });
	public static final BitSet FOLLOW_OUT_COND_in_condition162 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_simple_expr_in_internal_condition171 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_eq_expr_in_internal_condition175 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_value_expr_in_simple_expr186 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_set_in_value_expr196 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_value_expr_in_value_expr_parenthesis211 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_IN_in_value_expr_parenthesis215 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_OUT_in_value_expr_parenthesis219 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_value_expr_in_value_expr_parenthesis224 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_IN_in_value_expr_parenthesis228 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_OUT_in_value_expr_parenthesis232 = new BitSet(new long[] { 0x00000000000008E2L });
	public static final BitSet FOLLOW_token_in_eq_expr245 = new BitSet(new long[] { 0x0000000000001000L });
	public static final BitSet FOLLOW_EQ_in_eq_expr247 = new BitSet(new long[] { 0x00000000000008E0L });
	public static final BitSet FOLLOW_value_expr_parenthesis_in_eq_expr251 = new BitSet(new long[] { 0x0000000000000002L });

}