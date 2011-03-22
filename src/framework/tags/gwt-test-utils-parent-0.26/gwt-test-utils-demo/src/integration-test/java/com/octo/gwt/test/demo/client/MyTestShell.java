package com.octo.gwt.test.demo.client;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.csv.CsvDirectory;
import com.octo.gwt.test.csv.CsvMacros;
import com.octo.gwt.test.csv.CsvMethod;
import com.octo.gwt.test.csv.GwtCsvRunner;
import com.octo.gwt.test.csv.GwtCsvTest;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.demo.server.MyServiceImpl;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;

@CsvDirectory(value = "functional-tests", extension = ".csv")
@CsvMacros(value = "functional-tests", pattern = "^macro.*\\.csv$")
@RunWith(GwtCsvRunner.class)
public class MyTestShell extends GwtCsvTest {

	private Application application = new Application();

	@Before
	public void setUpMyTestShell() throws Exception {
		addGwtCreateHandler(createGwtCreateHandler());
	}

	@Override
	protected String getHostPagePath() {
		return "com/octo/gwt/test/demo/public/Application.html";
	}

	@CsvMethod
	public void initApp() {
		application.onModuleLoad();
	}

	private GwtCreateHandler createGwtCreateHandler() {
		return new RemoteServiceCreateHandler() {

			@Override
			public Object findService(Class<?> remoteServiceClass, String remoteServiceRelativePath) {
				if (remoteServiceClass == MyService.class && "myService".equals(remoteServiceRelativePath)) {
					return new MyServiceImpl();
				}
				return null;
			}
		};
	}

	@Override
	protected NodeObjectFinder getNodeObjectFinder(String prefix) {
		if ("myApp".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(application, node);
				}

			};
		} else if ("simpleComposite".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(RootPanel.get("main").getWidget(0), node);
				}
			};
		} else if ("simpleComposite2".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(RootPanel.get("main").getWidget(1), node);
				}
			};
		} else if ("rpcComposite".equals(prefix)) {
			return new NodeObjectFinder() {

				public Object find(CsvRunner csvRunner, Node node) {
					return csvRunner.getNodeValue(RootPanel.get("main").getWidget(2), node);
				}
			};
		}

		return super.getNodeObjectFinder(prefix);
	}
}
