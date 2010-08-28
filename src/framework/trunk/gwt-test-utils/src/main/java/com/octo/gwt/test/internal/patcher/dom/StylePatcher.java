package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;
import com.octo.gwt.test.patcher.PropertyContainerHelper;

@PatchClass(Style.class)
public class StylePatcher extends AutomaticPropertyContainerPatcher {

	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		String propertyValue = PropertyContainerHelper.getProperty(style, propertyName);
		return (propertyValue != null) ? propertyValue : "";
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName, String propertyValue) {
		PropertyContainerHelper.setProperty(style, propertyName, propertyValue);
	}

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(PropertyContainerHelper.getCodeSetProperty("this", "whiteSpace", "\"nowrap\"") + ";");
	}

}
