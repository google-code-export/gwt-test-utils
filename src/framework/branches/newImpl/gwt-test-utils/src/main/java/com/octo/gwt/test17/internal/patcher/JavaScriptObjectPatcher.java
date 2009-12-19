package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

public class JavaScriptObjectPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "hashCode")) {
			return "super.hashCode()";
		}

		return null;
	}
}
