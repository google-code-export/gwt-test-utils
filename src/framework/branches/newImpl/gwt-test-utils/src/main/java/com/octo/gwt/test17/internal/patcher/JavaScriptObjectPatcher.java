package com.octo.gwt.test17.internal.patcher;

import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PatchType;

public class JavaScriptObjectPatcher extends AutomaticPatcher {
	
	@PatchMethod(value=PatchType.NEW_CODE_AS_STRING, methodName="hashCode")
	public static String javascriptObjectHashCode() {
		return "return super.hashCode();";
	}
}
