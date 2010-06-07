package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.patcher.tools.AutomaticElementSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PatchType;
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;

@PatchClass(InputElement.class)
public class InputElementPatcher extends AutomaticElementSubclasser {

	@PatchMethod
	public static void click(InputElement inputElement) {

	}

	@PatchMethod
	public static void select(InputElement inputElement) {

	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String useMap() {
		return SubClassedHelper.getCodeGetProperty("this", "UserMap", CtClass.booleanType);
	}

}
