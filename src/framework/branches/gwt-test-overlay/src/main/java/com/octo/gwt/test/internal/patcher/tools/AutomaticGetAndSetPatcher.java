package com.octo.gwt.test.internal.patcher.tools;

import java.lang.reflect.Modifier;

import javassist.CtMethod;

import com.octo.gwt.test.utils.PatchGwtUtils;

public class AutomaticGetAndSetPatcher extends AutomaticPatcher {

	public String getNewBody(CtMethod m) throws Exception {
		String superNewBody = super.getNewBody(m);
		if (superNewBody != null) {
			return superNewBody;
		}
		if (Modifier.isNative(m.getModifiers())) {
			String fieldName = PatchGwtUtils.getPropertyName(m);
			// manage case when current method is a getter or setter
			if (fieldName != null) {
				if (m.getName().startsWith("set")) {
					return PropertyContainerAwareHelper.getCodeSetProperty("this", fieldName, "$1");
				} else {
					return "return " + PropertyContainerAwareHelper.getCodeGetProperty("this", fieldName, m.getReturnType());
				}
			}
		}
		return null;
	}

}
