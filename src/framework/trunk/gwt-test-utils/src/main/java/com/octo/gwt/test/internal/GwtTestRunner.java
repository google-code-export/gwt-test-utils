package com.octo.gwt.test.internal;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.octo.gwt.test.GwtTestClassLoader;


public class GwtTestRunner extends BlockJUnit4ClassRunner {

	public GwtTestRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.getInstance().loadClass(clazz.getCanonicalName()));
	}

}
