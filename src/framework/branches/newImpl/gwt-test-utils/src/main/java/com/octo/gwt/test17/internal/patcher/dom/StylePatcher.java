package com.octo.gwt.test17.internal.patcher.dom;

import java.util.HashMap;

import javassist.CtClass;
import javassist.CtConstructor;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class StylePatcher extends AutomaticPatcher {

	private static final String PROPERTY_MAP_FIELD = "propertyMap";

	@Override
	public void initClass(CtClass c) throws Exception {
		super.initClass(c);
		
		CtConstructor cons = findConstructor(c);
		// init propertyMap
		cons.setBody("{" + PropertyHolder.callSet(PROPERTY_MAP_FIELD, "new " + HashMap.class.getCanonicalName() + "()") + "}");
	}

	@PatchMethod
	public static String getPropertyImpl(Style style, String propertyName) {
		return (String) PropertyHolder.get(style).get(propertyName);
	}

	@PatchMethod
	public static void setPropertyImpl(Style style, String propertyName, String value) {
		PropertyHolder.get(style).put(propertyName, value);
	}
}
