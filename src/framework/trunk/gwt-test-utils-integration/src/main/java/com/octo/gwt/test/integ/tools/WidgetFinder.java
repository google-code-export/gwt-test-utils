package com.octo.gwt.test.integ.tools;

import com.google.gwt.user.client.ui.Widget;

public abstract class WidgetFinder {
	
	private WidgetFinder next;
	
	public Widget getWidget(String identifier) {
		Widget w = findWidget(identifier);
		
		if (w != null) {
			return w;
		} else if (next != null) {
			return next.getWidget(identifier);
		} else {
			throw new RuntimeException("No widget identified by '" + identifier + "' has been found");
		}
	}
	
	public WidgetFinder setNext(WidgetFinder next) {
		this.next = next;
		return this;
	}
	
	protected abstract Widget findWidget(String identifier);

}
