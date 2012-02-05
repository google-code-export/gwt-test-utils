package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.overrides.OverrideJsArrayString;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JavaScriptObject.class)
public class JavaScriptObjectPatcher {

	@PatchMethod
	public static JavaScriptObject createArray() {
		return new OverrideJsArrayString();
	}

}
