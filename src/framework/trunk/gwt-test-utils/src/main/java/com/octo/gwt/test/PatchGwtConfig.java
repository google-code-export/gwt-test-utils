package com.octo.gwt.test;

import java.util.Locale;

import com.octo.gwt.test.internal.patcher.GwtPatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.PatchGwtUtils.SequenceReplacement;

public class PatchGwtConfig {

	private static Locale locale;

	public static void setLocale(Locale locale) {
		PatchGwtConfig.locale = locale;
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setLogHandler(GwtLogHandler gwtLogHandler) {
		GwtPatcher.gwtLogHandler = gwtLogHandler;
	}

	public static boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		return GwtPatcher.addGwtCreateHandler(gwtCreateHandler);
	}

	public static void setCurrentTestedModuleFile(String currentTestedModuleFile) {
		ImplPatcher.currentTestedModuleFile = currentTestedModuleFile;
	}

	public static void replaceSequenceInProperties(String regex, String to) {
		PatchGwtUtils.sequenceReplacementList.add(new SequenceReplacement(regex, to));
	}

	public static void reset() {
		locale = null;
	}

}
