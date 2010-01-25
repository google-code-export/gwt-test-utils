package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class ListBoxPatcher extends AutomaticPatcher {

	@PatchMethod
	public static SelectElement getSelectElement(ListBox listbox) {
		return ElementUtils.castToDomElement(listbox.getElement());
	}

}
