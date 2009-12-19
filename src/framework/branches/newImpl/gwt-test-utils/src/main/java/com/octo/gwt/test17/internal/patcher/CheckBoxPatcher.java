package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;

public class CheckBoxPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "asOld")) {
			return callMethod("asOld", "$1");
		}

		return null;
	}

	public static Element asOld(com.google.gwt.dom.client.Element elem) {
		return ElementUtils.castToUserElement(elem);
	}

}
