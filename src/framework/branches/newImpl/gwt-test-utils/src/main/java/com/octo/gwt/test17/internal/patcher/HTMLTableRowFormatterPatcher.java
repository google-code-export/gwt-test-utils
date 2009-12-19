package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;

public class HTMLTableRowFormatterPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getRow")) {
			return callMethod("getRow", "$1, $2");
		}

		return null;
	}

	public static Element getRow(Element elem, int row) {
		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(row));
	}

}
