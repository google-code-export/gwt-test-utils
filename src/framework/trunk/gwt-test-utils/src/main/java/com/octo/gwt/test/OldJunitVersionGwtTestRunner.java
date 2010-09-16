package com.octo.gwt.test;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;

/**
 * The JUnit runner to use with gwt-test-utils and JUnit 4.4 or older.
 * 
 */
public class OldJunitVersionGwtTestRunner extends JUnit4ClassRunner {

	public OldJunitVersionGwtTestRunner(Class<?> klass) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.getInstance().loadClass(klass.getCanonicalName()));
	}

}
