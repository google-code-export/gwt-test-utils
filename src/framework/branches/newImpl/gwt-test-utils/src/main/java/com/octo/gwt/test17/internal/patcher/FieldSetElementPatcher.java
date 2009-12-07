package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

public class FieldSetElementPatcher extends AbstractPatcher {

	public boolean patchMethod(CtMethod m) throws Exception {
		if ("getForm".equals(m.getName())) {

		}
		return false;
	}

}
