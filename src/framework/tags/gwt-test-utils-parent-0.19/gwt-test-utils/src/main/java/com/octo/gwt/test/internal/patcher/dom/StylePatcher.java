package com.octo.gwt.test.internal.patcher.dom;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.utils.PropertyContainerHelper;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Style.class)
public class StylePatcher extends AutomaticPropertyContainerPatcher {

	public static final String TARGET_ELEMENT = "TARGET_ELEMENT";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);

		ClassPool cp = PatchGwtClassPool.get();

		CtField targetElementField = new CtField(cp.get(Element.class.getName()), TARGET_ELEMENT, c);
		c.addField(targetElementField);

		CtConstructor constructor = new CtConstructor(new CtClass[] { cp.get(Element.class.getName()) }, c);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("this." + TARGET_ELEMENT + " = $1;");
		sb.append(PropertyContainerHelper.getCodeSetProperty("this", "whiteSpace", "\"nowrap\"") + ";");
		sb.append("}");
		constructor.setBody(sb.toString());
		c.addConstructor(constructor);
	}

	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		String propertyValue = PropertyContainerHelper.getProperty(style, propertyName);
		return (propertyValue != null) ? propertyValue : "";
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName, String propertyValue) {
		PropertyContainerHelper.setProperty(style, propertyName, propertyValue);
	}

}
