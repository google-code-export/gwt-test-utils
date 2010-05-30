package com.octo.gwt.test;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class GwtTestRunner extends BlockJUnit4ClassRunner {

	public GwtTestRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(init(clazz));
	}
	
	private static Class<?> init(Class<?> clazz) throws ClassNotFoundException {
		Thread.currentThread().setContextClassLoader(GwtTestClassLoader.getInstance());
		return GwtTestClassLoader.getInstance().loadClass(clazz.getCanonicalName());
	}

}
