package com.octo.gwt.test;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;

/**
 * The JUnit runner to use with gwt-test-utils and JUnit 4.4 or older.
 * 
 * @see JUnit4ClassRunner
 * 
 */
@SuppressWarnings("deprecation")
public class OldJunitVersionGwtRunner extends JUnit4ClassRunner {

	public OldJunitVersionGwtRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.get().loadClass(clazz.getName()));
	}

}
