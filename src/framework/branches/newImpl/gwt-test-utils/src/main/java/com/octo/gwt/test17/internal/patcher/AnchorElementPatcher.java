package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;

public class AnchorElementPatcher extends AbstractPatcher {

	public boolean patchMethod(CtMethod m) throws Exception {
		if (m.getName().equals("blur") || m.getName().equals("focus")) {
			PatchUtils.replaceImplementation(m, null);
		} else {
			return false;
		}

		return true;
	}

}
