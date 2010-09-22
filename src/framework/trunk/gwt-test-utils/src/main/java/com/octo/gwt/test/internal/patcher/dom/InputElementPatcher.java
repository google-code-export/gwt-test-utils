package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PatchType;

@PatchClass(InputElement.class)
public class InputElementPatcher extends AutomaticPropertyContainerPatcher {

	@PatchMethod
	public static void click(InputElement inputElement) {

	}

	@PatchMethod
	public static void select(InputElement inputElement) {

	}

	@PatchMethod(value = PatchType.NEW_CODE_AS_STRING)
	public static String useMap() {
		return PropertyContainerHelper.getCodeGetProperty("this", "UserMap", CtClass.booleanType);
	}

	@PatchMethod
	public static String getValue(InputElement inputElement) {
		PropertyContainer attributes = PropertyContainerHelper.getProperty(inputElement, ElementPatcher.PROPERTY_MAP_FIELD);
		String value = (String) attributes.get("value");
		return (value != null) ? value : "";
	}

}
