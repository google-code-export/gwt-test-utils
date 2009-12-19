package com.octo.gwt.test17.internal.patcher.dom;

import java.util.HashMap;

import javassist.CtConstructor;
import javassist.CtMethod;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class StylePatcher extends AbstractPatcher {

	private static final String PROPERTY_MAP_FIELD = "propertyMap";

	@Override
	public void initClass() throws Exception {
		CtConstructor cons = findConstructor();
		// init propertyMap
		cons.setBody("{" + PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()") + "}");
	}

	@Override
	public String getNewBody(CtMethod m) {

		if (match(m, "getPropertyImpl")) {
			return callMethod("getProperty", "this, $1");
		} else if (match(m, "setPropertyImpl")) {
			return callMethod("setProperty", "this, $1, $2");
		} else if (match(m, "getPropertyPxImpl")) {
			return callMethod("setProperty", "this, $1, $2 + \"px\"");
		}

		return null;
	}

	public static Object getProperty(Style style, String propertyName) {
		return PropertyHolder.get(style).get(propertyName);
	}

	public static void setProperty(Style style, String propertyName, Object value) {
		PropertyHolder.get(style).put(propertyName, value);
	}
}
