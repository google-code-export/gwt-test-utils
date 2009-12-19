package com.octo.gwt.test17.internal.patcher;

import java.util.Stack;

import javassist.CtMethod;

public class HistoryImplPatcher extends AbstractPatcher {

	static Stack<String> stack = new Stack<String>();

	private static String top = null;

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "init")) {
			return "return true";
		} else if (match(m, "nativeUpdate")) {
			return "";
		} else if (match(m, "getToken")) {
			return callMethod("getToken");
		} else if (match(m, "setToken")) {
			return callMethod("setToken", "$1");
		}

		return null;
	}

	public static void setToken(String token) {
		stack.push(token);
		top = token;
	}

	public static String getToken() {
		return top;
	}

	public static void clear() {
		stack.clear();
	}

}
