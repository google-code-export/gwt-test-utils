package com.octo.gwt.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.utils.WidgetUtils;

@RunWith(GwtTestRunner.class)
public abstract class AbstractGwtTest extends WidgetUtils {

	public String getCurrentTestedModuleFile() {
		return null;
	}
	
	@Before
	public void setUpAbstractGwtTest() throws Exception {
		PatchGwtConfig.setGwtCreateHandler(getGwtCreateHandler());
		PatchGwtConfig.setCurrentTestedModuleFile(getCurrentTestedModuleFile());
	}

	@After
	public void tearDownAbstractGwtTest() throws Exception {
		resetPatchGwt();
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
