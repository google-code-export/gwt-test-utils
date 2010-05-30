package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PatchType;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;

public class InputElementPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void click(InputElement inputElement) {
		
	}
	
	@PatchMethod
	public static void select(InputElement inputElement) {
		
	}
	
	@PatchMethod(value=PatchType.NEW_CODE_AS_STRING)
	public static String useMap() {
		return PropertyContainerAwareHelper.getCodeGetProperty("this", "UserMap", CtClass.booleanType);
	}
	
}
