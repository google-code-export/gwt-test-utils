package com.octo.gwt.test17.demo.demo2.client;

import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test17.GwtCreateHandler;
import com.octo.gwt.test17.demo.demo2.server.MyServiceImpl;
import com.octo.gwt.test17.integ.CsvDirectory;
import com.octo.gwt.test17.integ.CsvMacros;
import com.octo.gwt.test17.integ.csvrunner.CsvRunner;
import com.octo.gwt.test17.integ.csvrunner.Node;
import com.octo.gwt.test17.integ.handler.GwtCreateHandlerImpl;
import com.octo.gwt.test17.integ.junit.StandardJUnit4CsvRunner;
import com.octo.gwt.test17.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test17.integ.tools.PrefixProcessor;

@CsvDirectory("functional-tests")
@CsvMacros("initMacro")
@RunWith(StandardJUnit4CsvRunner.class)
public class MyTestShell extends AbstractGwtIntegrationShell {

	private MyApp myApp;

	public void initApp() {
		myApp = new MyApp();
		myApp.onModuleLoad();
	}

	@Override
	protected GwtCreateHandler getGwtCreateHandler() {
		return new GwtCreateHandlerImpl() {

			@Override
			public Object findService(Class<?> remoteServiceClazz) {
				if (remoteServiceClazz == MyService.class) {
					return new MyServiceImpl();
				}
				return null;
			}

		};
	}

	@Override
	public PrefixProcessor findPrefixProcessor(String prefix) {
		if ("myApp".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, myApp, next);
				}

			};
		} else if ("simpleComposite".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, RootPanel.get().getWidget(0), next);
				}
			};
		} else if ("simpleComposite2".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, RootPanel.get().getWidget(1), next);
				}
			};
		} else if ("rpcComposite".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, RootPanel.get().getWidget(2), next);
				}
			};
		}

		return super.findPrefixProcessor(prefix);
	}
}
