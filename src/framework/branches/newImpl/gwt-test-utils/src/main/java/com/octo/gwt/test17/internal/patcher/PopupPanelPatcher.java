package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class PopupPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getStyleElement(PopupPanel panel) {
		return ElementUtils.castToUserElement(panel.getElement());
	}

}
