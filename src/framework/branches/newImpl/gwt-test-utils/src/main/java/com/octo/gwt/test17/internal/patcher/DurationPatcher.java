package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

public class DurationPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "currentTimeMillis")) {
			return callMethod("currentTimeMillis");
		}

		return null;
	}

	public static double currentTimeMillis() {
		return System.currentTimeMillis();
	}

}
