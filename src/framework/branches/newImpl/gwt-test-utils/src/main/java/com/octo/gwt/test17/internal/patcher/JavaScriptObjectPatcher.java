package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

public class JavaScriptObjectPatcher extends AbstractPatcher {

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		if ("hashCode".equals(m.getName())) {
			replaceImplementation(m, "super.hashCode()");
		} else {
			return false;
		}

		return true;
	}

}
