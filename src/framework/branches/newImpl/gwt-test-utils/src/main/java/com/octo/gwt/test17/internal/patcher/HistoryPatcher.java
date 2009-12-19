package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test17.ReflectionUtils;

public class HistoryPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "back")) {
			return callMethod("back");
		}

		return null;
	}

	public static void back() {
		HistoryImplPatcher.stack.pop();
		String token = HistoryImplPatcher.stack.pop();
		HistoryImpl impl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		impl.fireHistoryChangedImpl(token);
	}

}
