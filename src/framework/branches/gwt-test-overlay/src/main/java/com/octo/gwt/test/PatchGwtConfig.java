package com.octo.gwt.test;

import java.util.Locale;

import com.octo.gwt.test.internal.patcher.GWTPatcher;
import com.octo.gwt.test.internal.patcher.ImplPatcher;
import com.octo.gwt.test.utils.PatchGwtUtils;
import com.octo.gwt.test.utils.PatchGwtUtils.SequenceReplacement;

public class PatchGwtConfig {

	public static void setLocale(Locale locale) {
		PatchGwtUtils.locale = locale;
	}

	public static void addCreateClass(Class<?> classLiteral, Object object) {
		GWTPatcher.classes.put(classLiteral, object);
	}

	public static void setLogHandler(GwtLogHandler gwtLogHandler) {
		GWTPatcher.gwtLogHandler = gwtLogHandler;
	}

	public static void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		GWTPatcher.gwtCreateHandler = gwtCreateHandler;
	}
	
	public static void setCurrentTestedModuleFile(String currentTestedModuleFile) {
		ImplPatcher.currentTestedModuleFile = currentTestedModuleFile;
	}

	public static void replaceSequenceInProperties(String regex, String to) {
		PatchGwtUtils.sequenceReplacementList.add(new SequenceReplacement(regex, to));
	}

}
