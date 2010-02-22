package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.PopupPanel;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class PopupPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getStyleElement(PopupPanel panel) {
		return ElementUtils.castToUserElement(panel.getElement());
	}

}
