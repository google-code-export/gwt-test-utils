package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;

public class FocusImplOldPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "createBlurHandler|createFocusHandler|createMouseHandler|focus|blur")) {
			return "";
		} else if (match(m, "createFocusable")) {
			return PatchUtils.callMethod(FocusImplPatcher.class, "createFocusable", null);
		}

		return null;
	}

}
