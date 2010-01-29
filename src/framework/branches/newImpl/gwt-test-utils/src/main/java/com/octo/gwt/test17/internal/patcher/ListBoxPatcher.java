package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class ListBoxPatcher extends AutomaticSubclasser {

	@PatchMethod
	public static SelectElement getSelectElement(ListBox listbox) {
		return ElementUtils.castToDomElement(listbox.getElement());
	}

}
