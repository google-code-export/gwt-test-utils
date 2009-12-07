package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;

public class InputElementPatcher extends AbstractPatcher {

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		String name = m.getName();
		if ("blur".equals(name) || "click".equals(name) || "focus".equals(name) || "select".equals(name)) {
			PatchUtils.replaceImplementation(m, null);
		} else if ("useMap".equals(name)) {
			PatchUtils.replaceImplementation(m, "{ " + PropertyHolder.callGet("UseMap", m.getReturnType()) + " }");
		} else {
			return false;
		}

		return true;
	}
}
