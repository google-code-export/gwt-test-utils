package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.overrides.OverrideJsArrayString;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class JavaScriptObjectPatcher extends AutomaticPatcher {
	
	@PatchMethod
	public static JavaScriptObject createArray() {
		return new OverrideJsArrayString();
	}
	
}
