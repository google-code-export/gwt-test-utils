package com.octo.gwt.test;

import org.junit.After;
import org.junit.Before;

import com.octo.gwt.test.utils.WidgetUtils;


public abstract class AbstractGwtTest extends WidgetUtils {

	public String getCurrentTestedModuleFile() {
		return null;
	}
	
	@Before
	public void setUpAbstractGwtTest() throws Exception {
		initPatchGwt();
		PatchGwtConfig.setGwtCreateHandler(getGwtCreateHandler());
		PatchGwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
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

}
