package com.octo.gwt.test.integ.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.integ.tools.DirectoryTestReader;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

public class StandardJUnit4CsvRunner extends BlockJUnit4ClassRunner {

	private DirectoryTestReader reader;

	public StandardJUnit4CsvRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.getInstance().loadClass(clazz.getCanonicalName()));
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		if (reader == null) {
			reader = new DirectoryTestReader(getTestClass().getJavaClass());
		}
		List<FrameworkMethod> frameworkMethods = new ArrayList<FrameworkMethod>();
		for (Method m : reader.getTestMethods()) {
			frameworkMethods.add(new FrameworkMethod(m));
		}
		return frameworkMethods;
	}

	@Override
	protected Object createTest() throws Exception {
		Object testInstance = reader.createObject();
		Method m = GwtTestReflectionUtils.findMethod(testInstance.getClass(), "setReader", null);
		m.invoke(testInstance, reader);
		return testInstance;
	}

}
