package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.octo.gwt.test17.PatchUtils;

public class CurrencyDataPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getCurrencyCode")) {
			return PatchUtils.callMethod(CurrencyListPatcher.class, "getCurrencyCode", "this");
		} else if (match(m, "getCurrencySymbol")) {
			return PatchUtils.callMethod(CurrencyListPatcher.class, "getCurrencySymbol", "this");
		} else if (match(m, "getFlagsAndPrecision")) {
			return PatchUtils.callMethod(CurrencyListPatcher.class, "getFlagsAndPrecision", "this");
		}

		return null;
	}
}
