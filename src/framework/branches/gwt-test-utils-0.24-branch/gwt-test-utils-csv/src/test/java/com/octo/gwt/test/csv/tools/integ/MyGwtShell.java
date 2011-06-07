package com.octo.gwt.test.csv.tools.integ;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.csv.CsvMethod;
import com.octo.gwt.test.csv.GwtCsvTest;
import com.octo.gwt.test.csv.StandardJUnit4CsvRunner;
import com.octo.gwt.test.csv.runner.CsvRunner;
import com.octo.gwt.test.csv.runner.Node;
import com.octo.gwt.test.csv.tools.NodeObjectFinder;
import com.octo.gwt.test.integration.DefaultGwtRpcExceptionHandler;
import com.octo.gwt.test.integration.RemoteServiceCreateHandler;

@RunWith(StandardJUnit4CsvRunner.class)
public abstract class MyGwtShell extends GwtCsvTest {

	private MyBeautifulApp app;

	private RemoteServiceCreateHandler handlerImpl;

	@Override
	public String getModuleName() {
		return null;
	}

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

	@CsvMethod
	public void enableNewExceptionHandler() {

		handlerImpl.setExceptionHandler(new DefaultGwtRpcExceptionHandler() {

			@Override
			public void handle(Throwable t, AsyncCallback<?> callback) {
				Assert.assertEquals(NullPointerException.class, t.getClass());
				MyStringStore.appender = "toto";
				super.handle(t, callback);
			}

		});
	}

}
