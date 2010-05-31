package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.PropertyContainerAwareHelper;

public class StylePatcher extends AutomaticPatcher {

	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		return PropertyContainerAwareHelper.getProperty(style, propertyName);
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName, String propertyValue) {
		PropertyContainerAwareHelper.setProperty(style, propertyName, propertyValue);
	}

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(PropertyContainerAwareHelper.getCodeSetProperty("this", "whiteSpace", "\"nowrap\"") + ";");
	}

}
