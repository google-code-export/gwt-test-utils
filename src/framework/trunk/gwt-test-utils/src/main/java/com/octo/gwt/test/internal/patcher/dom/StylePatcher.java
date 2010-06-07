package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.patcher.tools.SubClassedHelper;

@PatchClass(Style.class)
public class StylePatcher extends AutomaticSubclasser {

	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		return SubClassedHelper.getProperty(style, propertyName);
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName,
			String propertyValue) {
		SubClassedHelper.setProperty(style, propertyName, propertyValue);
	}

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		CtConstructor cons = findConstructor(c);

		cons.insertAfter(SubClassedHelper.getCodeSetProperty("this",
				"whiteSpace", "\"nowrap\"", false)
				+ ";");
	}

}
