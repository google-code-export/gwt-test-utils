package com.octo.gwt.test.spring;

import com.octo.gwt.test.AbstractGwtTestRunner;

public class GwtSpring3TestRunner extends AbstractGwtTestRunner {

	private static final String classRunnerName = "org.springframework.test.context.junit4.SpringJUnit4ClassRunner";

	public GwtSpring3TestRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
