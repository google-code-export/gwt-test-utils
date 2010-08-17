package com.octo.gwt.test.internal.patcher;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(NumberFormat.class)
public class NumberFormatPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String toFixed(double d, int digits) {
		return String.valueOf(new BigDecimal(d).setScale(digits, BigDecimal.ROUND_DOWN).doubleValue()).toLowerCase();
	}

}
