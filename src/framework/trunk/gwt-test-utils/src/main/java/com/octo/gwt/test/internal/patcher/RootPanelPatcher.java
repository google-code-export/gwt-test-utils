package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(RootPanel.class)
public class RootPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static com.google.gwt.user.client.Element getBodyElement() {
		return Document.get().getBody().cast();
	}

}
