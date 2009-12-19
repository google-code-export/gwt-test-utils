package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.InputElement;

public class InputElementPatcher extends AbstractElementPatcher<InputElement> {

	public InputElementPatcher() {
		super(InputElement.class);
	}

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "blur|click|focus|select")) {
			return "";
		} else if (match(m, "useMap")) {
			try {
				return "{ " + PropertyHolder.callGet("UseMap", m.getReturnType()) + " }";
			} catch (Exception e) {
				throw new RuntimeException("Error while patching InputElement.useMap()", e);
			}
		} else {
			return super.getNewBody(m);
		}
	}
}
