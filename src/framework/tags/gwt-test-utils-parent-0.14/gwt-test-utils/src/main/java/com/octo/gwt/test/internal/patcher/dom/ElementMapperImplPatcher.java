package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(ElementMapperImpl.class)
public class ElementMapperImplPatcher extends AutomaticPatcher {

	private static final String widgetId = "__widgetID";

	@PatchMethod
	public static void setIndex(Element e, int index) {
		e.setPropertyString(widgetId, Integer.toString(index));
	}

	@PatchMethod
	public static int getIndex(Element e) {
		String index = e.getPropertyString(widgetId);
		return index == null ? -1 : Integer.parseInt(index);
	}

	@PatchMethod
	public static void clearIndex(Element e) {
		e.setPropertyString(widgetId, null);
	}

}
