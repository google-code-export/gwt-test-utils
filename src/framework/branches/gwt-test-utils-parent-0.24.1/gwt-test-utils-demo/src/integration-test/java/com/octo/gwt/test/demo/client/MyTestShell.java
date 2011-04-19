package com.octo.gwt.test.demo.client;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.demo.server.MyServiceImpl;
import com.octo.gwt.test.integ.CsvDirectory;
import com.octo.gwt.test.integ.CsvMacros;
import com.octo.gwt.test.integ.CsvMethod;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;
import com.octo.gwt.test.integ.handler.RemoteServiceCreateHandler;
import com.octo.gwt.test.integ.junit.StandardJUnit4CsvRunner;
import com.octo.gwt.test.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test.integ.tools.NodeObjectFinder;

@CsvDirectory(value = "functional-tests", extension = ".csv")
@CsvMacros(value = "functional-tests", pattern = "^macro.*\\.csv$")
@RunWith(StandardJUnit4CsvRunner.class)
public class MyTestShell extends AbstractGwtIntegrationShell {

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
