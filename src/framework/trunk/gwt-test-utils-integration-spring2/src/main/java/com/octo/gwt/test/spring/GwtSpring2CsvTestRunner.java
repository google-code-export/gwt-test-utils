package com.octo.gwt.test.spring;

import com.octo.gwt.test.AbstractGwtTestRunner;

public class GwtSpring2CsvTestRunner extends AbstractGwtTestRunner {

	private static final String classRunnerName = "com.octo.gwt.test.spring.Spring2JUnit4ClassRunner";

	public GwtSpring2CsvTestRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
