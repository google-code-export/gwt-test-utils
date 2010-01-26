package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.ng.AutomaticElementSubclasser;
import com.octo.gwt.test17.ng.PatchMethod;
import com.octo.gwt.test17.ng.PatchType;

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
		try {
			return "{ " + PropertyHolder.callGet("UseMap", CtClass.booleanType) + " }";
		} catch (Exception e) {
			throw new RuntimeException("Error while patching InputElement.useMap()", e);
		}
	}
	
}
