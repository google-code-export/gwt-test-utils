package com.octo.gwt.test17.integ.tools.integ;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test17.PatchGWT;
import com.octo.gwt.test17.integ.csvrunner.CsvRunner;
import com.octo.gwt.test17.integ.csvrunner.Node;
import com.octo.gwt.test17.integ.junit.StandardJUnit4CsvRunner;
import com.octo.gwt.test17.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test17.integ.tools.PrefixProcessor;

@RunWith(StandardJUnit4CsvRunner.class)
public abstract class MyGwtShell extends AbstractGwtIntegrationShell {
	
	public static String appender = "";
	
	private MyBeautifulApp app;
	
	@Before
	public void setUp() throws Exception {
		PatchGWT.init();
		PatchGWT.reset();
	}
	
	public void initApp() {
		app = new MyBeautifulApp();
		app.onModuleLoad();
	}

	public void append(String s) {
		appender += s;
	}

	@Override
	public PrefixProcessor findPrefixProcessor(String prefix) {
		if ("app".equals(prefix)) {
			return new PrefixProcessor() {
				
				public Object process(CsvRunner csvRunner, Node next) {
					return csvRunner.getValue(app, next);
				}
				
			};
		}
		return super.findPrefixProcessor(prefix);
	}
	
}
