package com.octo.gwt.test;

import org.junit.internal.runners.JUnit4ClassRunner;

/**
 * The JUnit runner to use with gwt-test-utils and JUnit 4.4 or older. It wraps
 * a {@link JUnit4ClassRunner} instance, which is a JUnit deprecated class.
 * 
 * @see JUnit4ClassRunner
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("deprecation")
public class OldJunitVersionGwtRunner extends GwtRunnerBase {

	private static final String classRunnerName = "org.junit.internal.runners.JUnit4ClassRunner";

	public OldJunitVersionGwtRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
