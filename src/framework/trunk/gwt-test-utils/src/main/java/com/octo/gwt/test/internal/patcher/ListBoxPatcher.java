package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test.internal.patcher.tools.AutomaticSubclasser;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.utils.ElementUtils;

@PatchClass(ListBox.class)
public class ListBoxPatcher extends AutomaticSubclasser {

	@PatchMethod
	public static SelectElement getSelectElement(ListBox listbox) {
		return ElementUtils.castToDomElement(listbox.getElement());
	}

}
