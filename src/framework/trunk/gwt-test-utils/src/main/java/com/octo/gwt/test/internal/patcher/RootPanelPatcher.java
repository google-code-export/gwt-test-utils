package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(RootPanel.class)
public class RootPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static com.google.gwt.user.client.Element getBodyElement() {
		return ElementUtils.castToUserElement(Document.get().getBody());
	}

}
