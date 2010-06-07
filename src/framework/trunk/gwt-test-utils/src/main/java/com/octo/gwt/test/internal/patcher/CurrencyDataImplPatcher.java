package com.octo.gwt.test.internal.patcher;

import com.google.gwt.i18n.client.CurrencyData;
import com.google.gwt.i18n.client.impl.CurrencyDataImpl;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(CurrencyDataImpl.class)
public class CurrencyDataImplPatcher extends AutomaticPatcher {

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
