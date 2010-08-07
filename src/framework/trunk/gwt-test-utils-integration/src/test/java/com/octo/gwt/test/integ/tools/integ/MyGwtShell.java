package com.octo.gwt.test.integ.tools.integ;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.integ.CsvMethod;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;
import com.octo.gwt.test.integ.handler.DefaultGwtRpcExceptionHandler;
import com.octo.gwt.test.integ.handler.GwtCreateRemoteServiceHandler;
import com.octo.gwt.test.integ.junit.StandardJUnit4CsvRunner;
import com.octo.gwt.test.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test.integ.tools.NodeObjectFinder;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.ISerializeCallback;

@RunWith(StandardJUnit4CsvRunner.class)
public abstract class MyGwtShell extends AbstractGwtIntegrationShell {

	private MyBeautifulApp app;

	private GwtCreateRemoteServiceHandler handlerImpl;

	@Before
	public void setUp() throws Exception {
		handlerImpl = new GwtCreateRemoteServiceHandler() {

			@Override
			public Object findService(Class<?> remoteServiceClazz) {
				if (remoteServiceClazz == MyRemoteService.class) {
					return new MyService();
				}
				return null;
			}

		};
		handlerImpl.addBackToGwtCallbacks(MyCustomObject.class, new ISerializeCallback() {

			public Object callback(Object object) throws Exception {
				GwtTestReflectionUtils.setPrivateFieldValue(object, "myField", "titi");
				return object;
			}
		});
		handlerImpl.addFromGwtCallbacks(Class.forName(MyCustomObject.class.getCanonicalName(), true, GwtTestClassLoader.getInstance().getParent()),
				new ISerializeCallback() {

					public Object callback(Object object) throws Exception {
						GwtTestReflectionUtils.setPrivateFieldValue(object, "myField", "toto");
						return object;
					}
				});

		addGwtCreateHandler(handlerImpl);

		MyStringStore.appender = "";

		mapNodeObjectFinder("app", new NodeObjectFinder() {

			public Object find(CsvRunner csvRunner, Node node) {
				return csvRunner.getValue(app, node);
			}
		});

		mapNodeObjectFinder("appender", new NodeObjectFinder() {

			public Object find(CsvRunner csvRunner, Node node) {
				return MyStringStore.appender;
			}

		});
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
