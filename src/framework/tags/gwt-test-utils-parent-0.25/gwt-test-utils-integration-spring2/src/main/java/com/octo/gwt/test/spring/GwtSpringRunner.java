package com.octo.gwt.test.spring;

import com.octo.gwt.test.GwtRunnerBase;

public class GwtSpringRunner extends GwtRunnerBase {

	private static final String classRunnerName = "org.springframework.test.context.junit4.SpringJUnit4ClassRunner";

	public GwtSpringRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}