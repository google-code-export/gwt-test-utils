package com.octo.gwt.test17.internal.patcher;

import java.math.BigDecimal;

import javassist.CtMethod;

public class NumberFormatPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "toFixed")) {
			return callMethod("toFixed", "$1, $2");
		}

		return null;
	}

	public static String toFixed(double d, int digits) {
		return String.valueOf(new BigDecimal(d).setScale(digits, BigDecimal.ROUND_DOWN).doubleValue());
	}

}
