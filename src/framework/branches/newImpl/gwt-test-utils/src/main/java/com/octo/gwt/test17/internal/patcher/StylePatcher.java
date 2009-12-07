package com.octo.gwt.test17.internal.patcher;

import java.util.HashMap;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.dom.client.Style;

public class StylePatcher extends AbstractPatcher {

	private static final String PROPERTY_MAP_FIELD = "propertyMap";

	@Override
	public void initClass(CtClass c) throws Exception {
		CtConstructor cons = c.getConstructors()[0];
		// init propertyMap
		cons.setBody("{" + PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()") + "}");
	}

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {

		if (m.getName().equals("getPropertyImpl")) {
			replaceImplementation(m, "getProperty", "this, $1");
		} else if (m.getName().equals("setPropertyImpl")) {
			replaceImplementation(m, "setProperty", "this, $1, $2");
		} else if (m.getName().equals("setPropertyPxImpl")) {
			replaceImplementation(m, "setProperty", "this, $1, $2 + \"px\"");
		} else {
			return false;
		}

		return true;
	}

	public static Object getProperty(Style style, String propertyName) {
		return PropertyHolder.get(style).get(propertyName);
	}

	public static void setProperty(Style style, String propertyName, Object value) {
		PropertyHolder.get(style).put(propertyName, value);
	}
}
