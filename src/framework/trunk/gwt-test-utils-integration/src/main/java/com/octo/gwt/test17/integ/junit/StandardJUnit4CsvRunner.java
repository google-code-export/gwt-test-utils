package com.octo.gwt.test17.integ.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.octo.gwt.test17.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test17.integ.tools.DirectoryTestReader;


public class StandardJUnit4CsvRunner extends BlockJUnit4ClassRunner {

	private DirectoryTestReader reader;
	
	public StandardJUnit4CsvRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		if (reader == null) {
			reader = new DirectoryTestReader(getTestClass().getJavaClass());
		}
		List<FrameworkMethod> frameworkMethods = new ArrayList<FrameworkMethod>();
		for(Method m : reader.getTestMethods()) {
			frameworkMethods.add(new FrameworkMethod(m));
		}
		return frameworkMethods;
	}
	
	
	@Override
	protected Object createTest() throws Exception {
		Object testInstance = reader.createObject();
		AbstractGwtIntegrationShell shell = (AbstractGwtIntegrationShell) testInstance;
		shell.setReader(reader);
		shell.setReader(reader);
		return shell;
	}

}
