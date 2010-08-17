package com.octo.gwt.test.internal.patcher;

import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(classes = { "com.google.gwt.user.client.ui.PrefixTree" })
public class PrefixTreePatcher extends AutomaticPatcher {

	@PatchMethod
	public static void clear(Object prefixTree) {
		GwtTestReflectionUtils.setPrivateFieldValue(prefixTree, "size", 0);
	}

}
