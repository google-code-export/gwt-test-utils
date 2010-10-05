package com.octo.gwt.test;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * <p>
 * GwtTestRunner is a JUnit test runner allowing to run tests classes which
 * reference, directly or indirectly, GWT components.
 * </p>
 * 
 * <p>
 * To achieve this, it uses a specific class loader ({@link GwtTestClassLoader}
 * ), which aim is to provide JVM-compliant versions of classes referenced in
 * those test classes. To obtain JVM-compliant code, the class loader rely on a
 * set of class patchers (implementing {@link IPatcher}) which can be configured
 * using the META-INF\gwt-test-utils.properties file of your application.
 * </p>
 */
public class GwtTestRunner extends BlockJUnit4ClassRunner {

	public GwtTestRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.getInstance().loadClass(clazz.getCanonicalName()));
	}

}
