package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideSelectElement;

public class PatchListBox {

	public static SelectElement getSelectElement(Object o) {
		if (o instanceof ListBox) {
			ListBox list = (ListBox) o;
			UserElement e = UserElement.overrideCast(list.getElement());
			Element elem = e.getOther();
			if (elem instanceof SelectElement) {
				SelectElement select = (SelectElement) elem;
				return select;
			}
		}
		throw new UnsupportedOperationException("Unable to cast to SelectElement " + o.getClass().getCanonicalName());
	}

	public static void clearListBox(Object o) {
		if (o instanceof ListBox) {
			ListBox list = (ListBox) o;
			UserElement e = UserElement.overrideCast(list.getElement());
			Element elem = e.getOther();
			if (elem instanceof SelectElement) {
				OverrideSelectElement s = OverrideSelectElement.overrideCast(elem);
				s.overrideClear();
			}
		}
	}
}
