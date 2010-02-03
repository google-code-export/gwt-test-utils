package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticElementSubclasser;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class SelectElementPatcher extends AutomaticElementSubclasser {

	@PatchMethod
	public static int getSize(SelectElement select) {
		int size = 0;

		for (int i = 0; i < select.getChildNodes().getLength(); i++) {
			Element e = ElementUtils.castToDomElement(select.getChildNodes().getItem(i));
			if (UIObject.isVisible(e)) {
				size++;
			}
		}

		return size;
	}

}
