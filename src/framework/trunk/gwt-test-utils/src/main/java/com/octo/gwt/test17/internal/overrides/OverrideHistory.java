package com.octo.gwt.test17.internal.overrides;

import java.util.Stack;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test17.ReflectionUtils;

public class OverrideHistory extends HistoryImpl {

	private static Stack<String> stack = new Stack<String>();

	private static String top = null;

	public static void setToken(String token) {
		stack.push(token);
		top = token;
	}

	public static String getToken() {
		return top;
	}

	public static void reset() {
		stack.clear();
	}

	public static void back() {
		stack.pop();
		String token = stack.pop();
		HistoryImpl impl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		impl.fireHistoryChangedImpl(token);
	}

}
