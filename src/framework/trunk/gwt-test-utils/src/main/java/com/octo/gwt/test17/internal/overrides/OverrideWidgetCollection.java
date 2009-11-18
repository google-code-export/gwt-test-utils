package com.octo.gwt.test17.internal.overrides;

import java.util.Iterator;

import net.sf.cglib.proxy.Enhancer;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideWidgetCollection extends WidgetCollection {

	private HasWidgets overrideParent;
	
	public OverrideWidgetCollection(HasWidgets parent) {
		super(parent);
		overrideParent = parent;
	}

	@Override
	public void insert(Widget w, int beforeIndex) {
		super.insert(w, beforeIndex);
		
		Iterator<Widget> it = iterator();
		int i=0;
		
		while (it.hasNext()) {
			Widget current = it.next();
			// check if mock
			if (!Enhancer.isEnhanced(current.getClass())) {
				UserElement e = (UserElement) current.getElement();
				e.setOverrideProperty("__index", String.valueOf(i++));
				e.setOverrideProperty("__owner", String.valueOf(overrideParent.hashCode()));
			}
		}
	}
}
