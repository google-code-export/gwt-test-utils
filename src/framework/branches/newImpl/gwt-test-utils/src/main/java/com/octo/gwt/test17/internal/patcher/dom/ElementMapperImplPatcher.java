package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class ElementMapperImplPatcher extends AbstractPatcher {

	private static final String widgetId = "__widgetID";

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "setIndex")) {
			return callMethod("setIndex", "$1, $2");
		} else if (match(m, "getIndex")) {
			return callMethod("getIndex", "$1");
		} else if (match(m, "clearIndex")) {
			return callMethod("clearIndex", "$1");
		}

		return null;
	}

	public static void setIndex(Element e, int index) {
		e.setPropertyString(widgetId, Integer.toString(index));
	}

	public static int getIndex(Element e) {
		String index = e.getPropertyString(widgetId);
		return index == null ? -1 : Integer.parseInt(index);
	}

	public static void clearIndex(Element e) {
		e.setPropertyString(widgetId, null);
	}

}
