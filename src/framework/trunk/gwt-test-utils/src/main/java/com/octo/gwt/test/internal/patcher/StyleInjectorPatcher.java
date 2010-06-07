package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.StyleInjector;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(StyleInjector.class)
public class StyleInjectorPatcher extends AutomaticPatcher {

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
