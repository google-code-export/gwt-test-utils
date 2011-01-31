package com.octo.gwt.test.spring;

import com.octo.gwt.test.AbstractGwtTestRunner;

public class GwtSpring2TestRunner extends AbstractGwtTestRunner {

	private static final String classRunnerName = "org.springframework.test.context.junit4.SpringJUnit4ClassRunner";

	public GwtSpring2TestRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}