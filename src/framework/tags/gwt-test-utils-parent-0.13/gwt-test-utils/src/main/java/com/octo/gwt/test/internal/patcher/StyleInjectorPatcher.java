package com.octo.gwt.test.internal.patcher;

import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

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
