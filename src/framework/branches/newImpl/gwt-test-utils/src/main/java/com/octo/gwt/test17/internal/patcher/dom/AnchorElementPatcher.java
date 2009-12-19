package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.AnchorElement;

public class AnchorElementPatcher extends AbstractElementPatcher<AnchorElement> {

	public AnchorElementPatcher() {
		super(AnchorElement.class);
	}

	public String getNewBody(CtMethod m) {
		if (match(m, "blur|focus")) {
			return "";
		} else {
			return super.getNewBody(m);
		}
	}

}
