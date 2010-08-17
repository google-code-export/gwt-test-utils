package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test.internal.utils.ElementUtils;
import com.octo.gwt.test.patcher.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(ListBox.class)
public class ListBoxPatcher extends AutomaticPropertyContainerPatcher {

	@PatchMethod
	public static SelectElement getSelectElement(ListBox listbox) {
		return ElementUtils.castToDomElement(listbox.getElement());
	}

}
