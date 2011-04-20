package com.octo.gwt.test;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.internal.GwtReset;

/**
 * <p>
 * Base class for test classes which need to manipulate (directly or indirectly)
 * GWT components.
 * </p>
 * 
 * <p>
 * AbstractGwtTest provides the mechanism which allows the instantiation of GWT
 * components in the Java Virtual Machine.
 * </p>
 * 
 * <p>
 * Class loading parameters used to instantiate classes referenced in tests can
 * be configured using the META-INF\gwt-test-utils.properties file of your
 * application.
 * </p>
 * 
 */
@RunWith(GwtTestRunner.class)
public abstract class GwtTest {

	@Before
	public void setUpGwtTest() throws Exception {
		GwtConfig.setLocale(getLocale());
		GwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
		GwtConfig.setLogHandler(getLogHandler());
		GwtConfig.setHostPagePath(getHostPagePath());
		GwtConfig.setEnsureDebugId(ensureDebugId());
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

	/**
	 * Override this method if you want your test to allow the setup of debug
	 * id.
	 * 
	 * @return true if setting debug id should be enabled, false otherwise.
	 */
	protected boolean ensureDebugId() {
		return false;
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
		return GwtConfig.addGwtCreateHandler(gwtCreateHandler);
	}

	@Deprecated
	protected void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
		addGwtCreateHandler(gwtCreateHandler);
	}

}
