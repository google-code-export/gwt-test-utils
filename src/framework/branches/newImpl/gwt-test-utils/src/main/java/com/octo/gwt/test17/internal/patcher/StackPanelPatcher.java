package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class StackPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static int findDividerIndex(StackPanel panel, Element child) {
		child = ElementUtils.castToDomElement(child);
		WidgetCollection children = ReflectionUtils.getPrivateFieldValue(panel, "children");

		for (int i = 0; i < children.size(); i++) {
			Element wElem = ElementUtils.castToDomElement(children.get(i).getElement());
			if (child.equals(wElem)) {
				return i;
			}
		}

		return -1;
	}

}
