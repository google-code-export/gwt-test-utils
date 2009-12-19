package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.dom.PropertyHolder;

public class FocusImplPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "blur|focus")) {
			return "";
		} else if (match(m, "createFocusable")) {
			return callMethod("createFocusable");
		} else if (match(m, "getTabIndex")) {
			return callMethod("getTabIndex", "$1");
		} else if (match(m, "setTabIndex")) {
			return callMethod("setTabIndex", "$1, $2");
		}

		return null;
	}

	public static Element createFocusable() {
		DivElement div = Document.get().createDivElement();
		return ElementUtils.castToUserElement(div);
	}

	public static int getTabIndex(Element elem) {
		Integer tabIndex = (Integer) PropertyHolder.get(elem).get("TabIndex");
		if (tabIndex == null)
			return 0;
		else
			return tabIndex;
	}

	public static void setTabIndex(Element elem, int index) {
		PropertyHolder.get(elem).put("TabIndex", index);
	}

}
