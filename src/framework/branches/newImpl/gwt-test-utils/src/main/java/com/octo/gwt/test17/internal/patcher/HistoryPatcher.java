package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.impl.HistoryImpl;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class HistoryPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void back() {
		HistoryImplPatcher.stack.pop();
		String token = HistoryImplPatcher.stack.pop();
		HistoryImpl impl = ReflectionUtils.getStaticFieldValue(History.class, "impl");
		impl.fireHistoryChangedImpl(token);
	}

}
