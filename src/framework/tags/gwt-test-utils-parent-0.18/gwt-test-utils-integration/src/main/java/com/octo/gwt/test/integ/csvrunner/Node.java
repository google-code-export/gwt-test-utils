package com.octo.gwt.test.integ.csvrunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;

public class Node {

	private Node next;

	private String label;

	private String map;

	private Node map_eq;

	private List<String> paramList;

	public Node(String label) {
		this.label = label;
	}

	public Node() {
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void insertParam(String param) {
		if (this.paramList == null) {
			this.paramList = new ArrayList<String>();
		}
		this.paramList.add(0, param);
	}

	public List<String> getParams() {
		return paramList == null ? null : Collections.unmodifiableList(paramList);
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getMap() {
		return map;
	}

	public void setMapEq(Node n) {
		this.map_eq = n;
	}

	public Node getMapEq() {
		return map_eq;
	}

	public String toString() {
		String s = "/" + label;
		if (paramList != null) {
			s += "(";
			for (String p : paramList) {
				s += p + ",";
			}
			s += ")";
		}
		if (map_eq == null && map != null) {
			s += "{" + map + "}";
		}
		if (map_eq != null && map != null) {
			s += "[" + map_eq.toString() + "=" + map + "]";
		}

		if (next != null) {
			s += next.toString();
		}
		return s;
	}

	static class TokenException extends RuntimeException {

		private static final long serialVersionUID = 7666850302524423170L;

	}

	public static Node parse(String s) {
		try {
			CharStream is = new ANTLRStringStream(s);
			XPathLexer lexer = new XPathLexer(is) {

				public void recover(RecognitionException re) {
					throw new TokenException();
				}

			};
			CommonTokenStream stream = new CommonTokenStream(lexer);
			XPathParser parser = new XPathParser(stream) {

				protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
					throw new MismatchedTokenException(ttype, input);
				}

			};
			parser.expr();
			return parser.root;
		} catch (RecognitionException e) {
			return null;
		} catch (TokenException e) {
			return null;
		}

	}
}
