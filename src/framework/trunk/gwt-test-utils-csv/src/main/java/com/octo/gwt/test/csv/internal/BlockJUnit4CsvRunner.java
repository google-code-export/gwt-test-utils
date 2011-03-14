package com.octo.gwt.test.csv.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.csv.tools.DirectoryTestReader;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class BlockJUnit4CsvRunner extends BlockJUnit4ClassRunner {

	private DirectoryTestReader reader;

	public BlockJUnit4CsvRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtTestClassLoader.get().loadClass(clazz.getName()));
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
		Method m = GwtReflectionUtils.findMethod(testInstance.getClass(), "setReader");
		m.invoke(testInstance, reader);
		return testInstance;
	}

}
