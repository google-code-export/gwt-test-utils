package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class StackPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static int findDividerIndex(StackPanel panel, com.google.gwt.user.client.Element child) {
		Element domChild = ElementUtils.castToDomElement(child);
		WidgetCollection children = GwtTestReflectionUtils.getPrivateFieldValue(panel, "children");

		for (int i = 0; i < children.size(); i++) {
			Element wElem = ElementUtils.castToDomElement(children.get(i).getElement());
			if (domChild.equals(wElem)) {
				return i;
			}
		}

		return -1;
	}

}
