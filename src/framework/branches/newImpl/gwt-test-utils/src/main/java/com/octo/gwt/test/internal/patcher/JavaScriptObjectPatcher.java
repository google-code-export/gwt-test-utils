package com.octo.gwt.test.internal.patcher;

import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PatchType;

public class JavaScriptObjectPatcher extends AutomaticPatcher {
	
	@PatchMethod(value=PatchType.NEW_CODE_AS_STRING, methodName="hashCode")
	public static String javascriptObjectHashCode() {
		return "return super.hashCode();";
	}
}
