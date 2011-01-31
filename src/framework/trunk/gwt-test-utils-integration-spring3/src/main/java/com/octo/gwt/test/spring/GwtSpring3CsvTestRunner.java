package com.octo.gwt.test.spring;

import com.octo.gwt.test.AbstractGwtTestRunner;

public class GwtSpring3CsvTestRunner extends AbstractGwtTestRunner {

	private static final String classRunnerName = "com.octo.gwt.test.spring.Spring3JUnit4ClassRunner";

	public GwtSpring3CsvTestRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
