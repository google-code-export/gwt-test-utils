package com.octo.gwt.test.csv;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.csv.AbstractGwtCsvTest;
import com.octo.gwt.test.csv.CsvMethod;
import com.octo.gwt.test.csv.GwtCsvTestRunner;
import com.octo.gwt.test.csv.data.MyBeautifulApp;
import com.octo.gwt.test.csv.data.MyRemoteService;
import com.octo.gwt.test.csv.data.MyService;
import com.octo.gwt.test.csv.data.MyStringStore;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;

@RunWith(GwtCsvTestRunner.class)
public abstract class MyGwtShell extends AbstractGwtCsvTest {

	private MyBeautifulApp app;

	private RemoteServiceCreateHandler handlerImpl;

	@Before
	public void setUp() throws Exception {
		handlerImpl = new RemoteServiceCreateHandler() {

			@Override
			public Object findService(Class<?> remoteServiceClass, String remoteServiceRelativePath) {
				if (remoteServiceClass == MyRemoteService.class && "myService".equals(remoteServiceRelativePath)) {
					return new MyService();
				}
				return null;
			}

		};

		addGwtCreateHandler(handlerImpl);

		MyStringStore.appender = "";
	}

	@Override
	protected NodeObjectFinder getNodeObjectFinder(String prefix) {
		if ("app".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(app, node);
				}
			};
		} else if ("appender".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return MyStringStore.appender;
				}
			};

		}
		return super.getNodeObjectFinder(prefix);
	}

	@CsvMethod
	public void initApp() {
		app = new MyBeautifulApp();
		app.onModuleLoad();
	}

	@CsvMethod
	public void append(String s) {
		MyStringStore.appender += s;
	}

}
