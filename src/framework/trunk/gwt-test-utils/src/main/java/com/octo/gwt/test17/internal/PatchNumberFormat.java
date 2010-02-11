package com.octo.gwt.test17.internal;

import java.math.BigDecimal;

public class PatchNumberFormat {
	
	public static String toFixed(double d, int digits) {
		return String.valueOf(new BigDecimal(d).setScale(digits, BigDecimal.ROUND_DOWN).doubleValue()).toLowerCase();
	}

}
