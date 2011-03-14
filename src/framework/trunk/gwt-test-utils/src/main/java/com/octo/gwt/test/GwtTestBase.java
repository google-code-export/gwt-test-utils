package com.octo.gwt.test;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.GwtReset;

public abstract class GwtTestBase {

	@Before
	public void setUpAbstractGwtIntegrationShell() throws Exception {
		GwtConfig.get().setLocale(getLocale());
		GwtConfig.get().setLogHandler(getLogHandler());
		GwtConfig.get().setHostPagePath(getHostPagePath());

		String moduleName = getModuleName();
		if (moduleName == null) {
			if (getCurrentTestedModuleFile() != null) {
				moduleName = getCurrentTestedModuleFile().substring(0, getCurrentTestedModuleFile().toLowerCase().indexOf(".gwt.xml")).replaceAll(
						"/", ".");
			}
		}

		GwtConfig.get().setModuleName(moduleName);
	}

	@After
	public void tearDownAbstractGwtIntegrationShell() throws Exception {
		resetPatchGwt();
	}

	protected String getModuleName() {
		// this method can be overrided by subclass
		return null;
	}

	@Deprecated
	protected String getCurrentTestedModuleFile() {
		// this method can be overrided by subclass
		return null;
	}

	protected GwtLogHandler getLogHandler() {
		// this method can be overrided by subclass
		return null;
	}

	protected Locale getLocale() {
		// this method can be overrided by subclass
		return null;
	}

	protected String getHostPagePath() {
		// this method can be overrided by subclass
		return null;
	}

	protected void resetPatchGwt() throws Exception {
		// reinit GWT
		GwtReset.reset();
	}

	protected boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		return GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
	}

	@Deprecated
	protected void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		addGwtCreateHandler(gwtCreateHandler);
	}

}
