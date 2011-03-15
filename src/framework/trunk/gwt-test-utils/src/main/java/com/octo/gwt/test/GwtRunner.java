package com.octo.gwt.test;

import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * <p>
 * The JUnit Runner allowing to run tests classes which reference, directly or
 * indirectly, GWT components.
 * </p>
 * 
 * <p>
 * Since it wraps a {@link BlockJUnit4ClassRunner}, It is designed to work with
 * JUnit 4.5 or higher. If you are using JUnit 4.4 or any previous version, you
 * should specify your test classes to be runned with
 * {@link OldJunitVersionGwtRunner} with the @RunWith JUnit annotation.
 * </p>
 * 
 * @author Gael Lazzari
 */
public class GwtRunner extends GwtRunnerBase {

	private static final String classRunnerName = "org.junit.runners.BlockJUnit4ClassRunner";

	public GwtRunner(Class<?> clazz) throws Exception {
		super(clazz);
	}

	@Override
	protected String getClassRunnerClassName() {
		return classRunnerName;
	}

}
