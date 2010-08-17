package com.octo.gwt.test;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.internal.PatchGwtReset;

public abstract class AbstractGwtConfigurableTest {

	@Before
	public void setUpAbstractGwtIntegrationShell() throws Exception {
		PatchGwtConfig.setLocale(getLocale());
		PatchGwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
		PatchGwtConfig.setLogHandler(getLogHandler());
	}

	@After
	public void tearDownAbstractGwtIntegrationShell() throws Exception {
		resetPatchGwt();
	}

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

	protected void resetPatchGwt() throws Exception {
		// reinit GWT
		PatchGwtReset.reset();
	}

	protected boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		return PatchGwtConfig.addGwtCreateHandler(gwtCreateHandler);
	}

	@Deprecated
	protected void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		addGwtCreateHandler(gwtCreateHandler);
	}

}
