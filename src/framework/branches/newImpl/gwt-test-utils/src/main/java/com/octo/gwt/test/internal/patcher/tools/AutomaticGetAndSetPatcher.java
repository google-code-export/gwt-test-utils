package com.octo.gwt.test.internal.patcher.tools;

import java.lang.reflect.Modifier;

import javassist.CtMethod;

import com.octo.gwt.test.utils.PatchUtils;

public class AutomaticGetAndSetPatcher extends AutomaticPatcher {

	public String getNewBody(CtMethod m) throws Exception {
		String superNewBody = super.getNewBody(m);
		if (superNewBody != null) {
			return superNewBody;
		}
		if (Modifier.isNative(m.getModifiers())) {
			String fieldName = PatchUtils.getPropertyName(m);
			if (fieldName != null) {
				if (m.getName().startsWith("set")) {
					return SubClassedHelper.getCodeSetProperty("this", fieldName, "$1", false);
				} else {
					return "return " + SubClassedHelper.getCodeGetProperty("this", fieldName, m.getReturnType());
				}
			}
		}
		return null;
	}

}
