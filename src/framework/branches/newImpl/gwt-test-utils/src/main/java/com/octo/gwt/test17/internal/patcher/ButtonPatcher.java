package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.ElementWrapper;

public class ButtonPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "adjustType")) {
			return callMethod("adjustType", "$1");
		}

		return null;
	}

	public static void adjustType(Element element) {
		ElementWrapper wrapper = (ElementWrapper) element;
		((ButtonElement) wrapper.getWrappedElement()).setAttribute("type", "button");
	}

}
