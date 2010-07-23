package com.octo.gwt.test;

import java.util.Locale;

import com.octo.gwt.test.internal.patcher.GwtPatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.utils.LoadPropertiesHelper;
import com.octo.gwt.test.utils.LoadPropertiesHelper.SequenceReplacement;

public class PatchGwtConfig {

	private static Locale locale;

	public static void setLocale(Locale locale) {
		PatchGwtConfig.locale = locale;
	}

	public static Locale getLocale() {
		return locale;
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

	public static void setCurrentTestedModuleFile(String currentTestedModuleFile) {
		ImplPatcher.currentTestedModuleFile = currentTestedModuleFile;
	}

	public static void replaceSequenceInProperties(String regex, String to) {
		LoadPropertiesHelper.sequenceReplacementList.add(new SequenceReplacement(regex, to));
	}

	public static void reset() {
		locale = null;
	}

}
