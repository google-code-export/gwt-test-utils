package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.ng.AutomaticElementSubclasser;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PatchType;
import com.octo.gwt.test17.ng.SubClassedHelper;

public class InputElementPatcher extends AutomaticElementSubclasser {

	@PatchMethod
	public static void focus(InputElement inputElement) {
		
	}
	
	@PatchMethod
	public static void blur(InputElement inputElement) {
		
	}
	
	@PatchMethod
	public static void click(InputElement inputElement) {
		
	}
	
	@PatchMethod
	public static void select(InputElement inputElement) {
		
	}
	
	@PatchMethod(value=PatchType.NEW_CODE_AS_STRING)
	public static String useMap() {
		return SubClassedHelper.getCodeGetProperty("this", "UserMap", CtClass.booleanType);
	}
	
}
