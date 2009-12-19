package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test17.ElementUtils;

public class ListBoxPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getSelectElement")) {
			return callMethod("getSelectElement", "this");
		}

		return null;
	}

	public static SelectElement getSelectElement(ListBox listbox) {
		return ElementUtils.castToDomElement(listbox.getElement());
	}

}
