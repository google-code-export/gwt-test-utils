package com.octo.gwt.test.internal.patcher.dom;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.octo.gwt.test.internal.PatchGwtClassPool;
import com.octo.gwt.test.internal.utils.PropertyContainer;
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

		CtField targetElementField = new CtField(PatchGwtClassPool.getCtClass(Element.class), TARGET_ELEMENT, c);
		c.addField(targetElementField);

		CtConstructor constructor = new CtConstructor(new CtClass[] { PatchGwtClassPool.getCtClass(Element.class) }, c);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("this." + TARGET_ELEMENT + " = $1;");
		sb.append(PropertyContainerHelper.getCodeSetProperty("this", "whiteSpace", "\"nowrap\"") + ";");
		sb.append("}");
		constructor.setBody(sb.toString());
		c.addConstructor(constructor);
	}

	@PatchMethod
	public static void setBorderWidth(Style style, double value, Unit unit) {
		PropertyContainer pc = PropertyContainerHelper.cast(style).getProperties();
		String completeValue = value + unit.getType();
		pc.put("border-top-width", completeValue);
		pc.put("border-right-width", completeValue);
		pc.put("border-bottom-width", completeValue);
		pc.put("border-left-width", completeValue);
	}

	@PatchMethod
	public static String getBorderWidth(Style style) {
		String borderWidth = PropertyContainerHelper.getProperty(style, "border-top-width");
		return (borderWidth != null) ? borderWidth : "";
	}

	@PatchMethod
	public static void clearBorderWidth(Style style) {
		PropertyContainer pc = PropertyContainerHelper.cast(style).getProperties();
		pc.put("border-top-width", "");
		pc.put("border-right-width", "");
		pc.put("border-bottom-width", "");
		pc.put("border-left-width", "");
	}

	@PatchMethod
	public static void setFloat(Style style, Float value) {
		setPropertyImpl(style, "float", value.getCssName());
	}

	@PatchMethod
	public static void clearFloat(Style style) {
		PropertyContainerHelper.setProperty(style, "float", "");
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
