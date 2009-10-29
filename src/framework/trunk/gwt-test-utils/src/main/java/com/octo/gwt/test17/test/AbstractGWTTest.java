package com.octo.gwt.test17.test;

import org.junit.Before;

import com.octo.gwt.test17.PatchGWT;
import com.octo.gwt.test17.WidgetUtils;

public abstract class AbstractGWTTest extends WidgetUtils {

	@Before
	public void setUpAbstractGwtTest() throws Exception {
		initPatchGwt();
	}
	
	protected void initPatchGwt() throws Exception {
		//initialisation du framework de mock GWT
		PatchGWT.init();
		// reinit GWT
		PatchGWT.reset();
	}

}
