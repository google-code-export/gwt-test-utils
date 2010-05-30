package com.octo.gwt.test.integ.tools.integ;

import org.junit.runners.model.InitializationError;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.integ.junit.StandardJUnit4CsvRunner;

public class LocalRunner extends StandardJUnit4CsvRunner {

	static {
		GwtTestClassLoader.getInstance().delegateLoadingOf(MyStringStore.class.getCanonicalName());
	}
	
	public LocalRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(clazz);
	}
	
}
