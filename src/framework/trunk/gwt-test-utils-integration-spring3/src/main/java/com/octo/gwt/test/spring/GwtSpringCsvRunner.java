package com.octo.gwt.test.spring;

import com.octo.gwt.test.GwtRunnerBase;

public class GwtSpringCsvRunner extends GwtRunnerBase {

	private static final String classRunnerName = "com.octo.gwt.test.spring.Spring3JUnit4ClassRunner";

	public GwtSpringCsvRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
