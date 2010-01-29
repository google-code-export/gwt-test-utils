package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.i18n.client.impl.CurrencyData;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class CurrencyDataPatcher extends AutomaticPatcher {

	@PatchMethod
	public static String getCurrencyCode(CurrencyData currencyData) {
		return CurrencyListPatcher.getCurrencyCode(currencyData);
	}
	
	@PatchMethod
	public static String getCurrencySymbol(CurrencyData currencyData) {
		return CurrencyListPatcher.getCurrencySymbol(currencyData);
	}
	
	@PatchMethod
	public static int getFlagsAndPrecision(CurrencyData currencyData) {
		return CurrencyListPatcher.getFlagsAndPrecision(currencyData);
	}
	
}
