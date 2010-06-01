package com.octo.gwt.test;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.utils.WidgetUtils;


public abstract class AbstractGwtTest extends WidgetUtils {
	
	@Before
	public void setUpAbstractGwtTest() throws Exception {
		PatchGwtConfig.setLocale(getLocale());
		PatchGwtConfig.setGwtCreateHandler(getGwtCreateHandler());
		PatchGwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
		PatchGwtConfig.setLogHandler(getLogHandler());
		initPatchGwt();
	}

	@After
	public void tearDownAbstractGwtTest() throws Exception {
		resetPatchGwt();
	}

	protected void initPatchGwt() throws Exception {
		//initialisation du framework de mock GWT
		PatchGWT.init();
	}

	protected void resetPatchGwt() throws Exception {
		// reinit GWT
		PatchGwtReset.reset();
	}

	protected GwtCreateHandler getGwtCreateHandler() {
		//this method can be overrided
		return null;
	}
	
	protected String getCurrentTestedModuleFile() {
		//this method can be overrided
		return null;
	}
	
	protected GwtLogHandler getLogHandler() {
		//this method can be overrided
		return null;
	}
	
	protected Locale getLocale() {
		//this method can be overrided
		return null;
	}
	
	

}
