package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchElementMapperImpl {
	
	private static final String widgetId = "__widgetID";
	
	public static void setWidgetIndex(Element e, int index) {
		UserElement elem = UserElement.overrideCast(e);
		elem.setOverrideProperty(widgetId, Integer.toString(index));
	}

	public static int getWidgetIndex(Element e) {
		UserElement elem = UserElement.overrideCast(e);
		String index = elem.getOverrideProperty(widgetId);
		return index == null ? -1 : Integer.parseInt(index);
	}

	public static void clearWidgetIndex(Element e) {
		UserElement elem = UserElement.overrideCast(e);
		elem.setOverrideProperty(widgetId, null);
	}

}
