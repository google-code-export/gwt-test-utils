package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.ElementUtils;

public class RootPanelPatch extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getBodyElement")) {
			return callMethod("getBodyElement");
		}

		return null;
	}

	public static com.google.gwt.user.client.Element getBodyElement() {
		return ElementUtils.castToUserElement(Document.get().getBody());
	}

}
