package com.octo.gwt.test.integ.spring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.octo.gwt.test.csv.tools.DirectoryTestReader;
import com.octo.gwt.test.internal.GwtClassLoader;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class GwtSpringCsvRunner extends SpringJUnit4ClassRunner {

	private DirectoryTestReader reader;

	public GwtSpringCsvRunner(Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(GwtClassLoader.getInstance().loadClass(clazz.getCanonicalName()));
	}

	protected List<FrameworkMethod> computeTestMethods() {
		if (reader == null) {
			reader = new DirectoryTestReader(getTestClass().getJavaClass());
		}
		List<FrameworkMethod> list = new ArrayList<FrameworkMethod>();
		for (Method csvMethod : reader.getTestMethods()) {
			list.add(new FrameworkMethod(csvMethod));
		}

		return list;
	}

	@Override
	protected Object createTest() throws Exception {
		Object testInstance = reader.createObject();
		Method m = GwtReflectionUtils.findMethod(testInstance.getClass(), "setReader");
		m.invoke(testInstance, reader);
		getTestContextManager().prepareTestInstance(testInstance);
		return testInstance;
	}

}
