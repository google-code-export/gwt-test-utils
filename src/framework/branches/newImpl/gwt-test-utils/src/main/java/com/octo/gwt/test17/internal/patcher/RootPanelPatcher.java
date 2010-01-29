package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class RootPanelPatcher extends AutomaticPatcher {

	@PatchMethod
	public static com.google.gwt.user.client.Element getBodyElement() {
		return ElementUtils.castToUserElement(Document.get().getBody());
	}

}
