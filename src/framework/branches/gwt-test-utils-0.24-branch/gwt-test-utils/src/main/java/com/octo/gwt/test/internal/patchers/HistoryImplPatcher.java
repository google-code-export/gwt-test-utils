package com.octo.gwt.test.internal.patchers;

import java.util.Stack;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test.internal.AfterTestCallback;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(HistoryImpl.class)
public class HistoryImplPatcher {

	static class HistoryHolder implements AfterTestCallback {

		final Stack<String> stack;
		private String top;

		HistoryHolder() {
			this.stack = new Stack<String>();
			this.top = "";
			AfterTestCallbackManager.get().registerCallback(this);
		}

		public void afterTest() throws Throwable {
			stack.clear();
			top = "";

			HistoryImpl historyImpl = GwtReflectionUtils.getStaticFieldValue(
					History.class, "impl");
			GwtReflectionUtils.callPrivateMethod(GwtReflectionUtils
					.getPrivateFieldValue(GwtReflectionUtils
							.getPrivateFieldValue(GwtReflectionUtils
									.getPrivateFieldValue(historyImpl,
											"handlers"), "eventBus"), "map"),
					"clear");
		}

	}

	static HistoryHolder HISTORY_HOLDER = new HistoryHolder();

	@PatchMethod
	public static String getToken() {
		return HISTORY_HOLDER.top;
	}

	@PatchMethod
	public static boolean init(HistoryImpl historyImpl) {
		return true;
	}

	@PatchMethod
	public static void nativeUpdate(HistoryImpl historyImpl, String s) {

	}

	@PatchMethod
	public static void setToken(String token) {
		if (token == null) {
			token = "";
		}

		HISTORY_HOLDER.stack.push(token);
		HISTORY_HOLDER.top = token;
	}

}
