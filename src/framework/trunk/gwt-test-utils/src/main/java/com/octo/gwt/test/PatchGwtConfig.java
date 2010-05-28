package com.octo.gwt.test;

import java.util.Locale;

import com.octo.gwt.test.internal.patcher.GwtPatcher;
import com.octo.gwt.test.utils.PatchUtils;

public class PatchGwtConfig {

	public static void setLocale(Locale locale) {
		PatchUtils.locale = locale;
	}

	public static void addCreateClass(Class<?> classLiteral, Object object) {
		GwtPatcher.classes.put(classLiteral, object);
	}

	public static void setLogHandler(GwtLogHandler gwtLogHandler) {
		GwtPatcher.gwtLogHandler = gwtLogHandler;
	}

	public static void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		GwtPatcher.gwtCreateHandler = gwtCreateHandler;
	}

}
