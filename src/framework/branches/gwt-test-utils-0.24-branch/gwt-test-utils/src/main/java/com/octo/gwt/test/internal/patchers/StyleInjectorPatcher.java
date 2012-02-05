package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.StyleInjector;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(StyleInjector.class)
public class StyleInjectorPatcher {

	@PatchMethod
	public static void inject(String css, boolean immediate) {

	}

	@PatchMethod
	public static void injectAtEnd(String css, boolean immediate) {

	}

	@PatchMethod
	public static void injectAtStart(String css, boolean immediate) {

	}

}
