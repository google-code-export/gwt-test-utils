package com.octo.gwt.test.internal.patcher;

import java.util.Stack;

import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(HistoryImpl.class)
public class HistoryImplPatcher extends AutomaticPatcher {

	static Stack<String> stack = new Stack<String>();

	private static String top = null;

	@PatchMethod
	public static boolean init(HistoryImpl historyImpl) {
		return true;
	}

	@PatchMethod
	public static void nativeUpdate(HistoryImpl historyImpl, String s) {

	}

	@PatchMethod
	public static void setToken(String token) {
		stack.push(token);
		top = token;
	}

	@PatchMethod
	public static String getToken() {
		return top;
	}

	public static void reset() {
		stack.clear();
	}

}
