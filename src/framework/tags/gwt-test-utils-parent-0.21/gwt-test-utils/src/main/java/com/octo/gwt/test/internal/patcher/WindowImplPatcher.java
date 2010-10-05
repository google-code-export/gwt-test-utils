package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.impl.WindowImpl;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(WindowImpl.class)
public class WindowImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String getHash(WindowImpl windowImpl) {
		return "";
	}

	@PatchMethod
	public static String getQueryString(WindowImpl windowImpl) {
		return "";
	}

	@PatchMethod
	public static void initWindowCloseHandler(WindowImpl windowImpl) {

	}

	@PatchMethod
	public static void initWindowResizeHandler(WindowImpl windowImpl) {

	}

	@PatchMethod
	public static void initWindowScrollHandler(WindowImpl windowImpl) {

	}

}
