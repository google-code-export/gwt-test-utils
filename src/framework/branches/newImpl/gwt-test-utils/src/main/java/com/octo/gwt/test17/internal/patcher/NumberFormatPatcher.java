package com.octo.gwt.test17.internal.patcher;

import java.math.BigDecimal;

import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class NumberFormatPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String toFixed(double d, int digits) {
		return String.valueOf(
				new BigDecimal(d).setScale(digits, BigDecimal.ROUND_DOWN)
						.doubleValue()).toLowerCase();
	}

}
