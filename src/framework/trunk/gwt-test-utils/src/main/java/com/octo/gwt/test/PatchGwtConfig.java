package com.octo.gwt.test;

import java.util.List;
import java.util.Locale;

import com.octo.gwt.test.internal.ModuleData;

public class PatchGwtConfig {

	private static PatchGwtConfig INSTANCE;

	public static PatchGwtConfig get() {
		if (INSTANCE == null) {
			INSTANCE = new PatchGwtConfig();
		}

		return INSTANCE;
	}

	private Locale locale;
	private GwtLogHandler logHandler;
	private String hostPagePath;
	private String moduleName;

	private PatchGwtConfig() {

	}

	public void reset() {
		locale = null;
		logHandler = null;
		hostPagePath = null;
		moduleName = null;
	}

	public void setLogHandler(GwtLogHandler logHandler) {
		this.logHandler = logHandler;
	}

	public GwtLogHandler getLogHandler() {
		return logHandler;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setHostPagePath(String hostPagePath) {
		this.hostPagePath = hostPagePath;
	}

	public String getHostPagePath() {
		return hostPagePath;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		if (moduleName != null)
			return moduleName;

		List<String> moduleNames = ModuleData.get().getModuleNames();

		switch (moduleNames.size()) {
		case 0:
			return null;
		case 1:
			return moduleNames.get(0);
		default:
			throw new RuntimeException(
					"You have declared more than one 'module-file' in your META-INF/gwt-test-utils.properties files. Please override the "
							+ AbstractGwtConfigurableTest.class.getSimpleName() + ".getModuleName() method to tell which module is currently tested");
		}

	}

}
