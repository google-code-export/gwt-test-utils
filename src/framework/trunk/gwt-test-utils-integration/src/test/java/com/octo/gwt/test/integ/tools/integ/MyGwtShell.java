package com.octo.gwt.test.integ.tools.integ;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.GwtTestClassLoader;
import com.octo.gwt.test.PatchGwtConfig;
import com.octo.gwt.test.integ.csvrunner.CsvRunner;
import com.octo.gwt.test.integ.csvrunner.Node;
import com.octo.gwt.test.integ.handler.DefaultGwtRpcExceptionHandler;
import com.octo.gwt.test.integ.handler.GwtCreateHandlerImpl;
import com.octo.gwt.test.integ.junit.StandardJUnit4CsvRunner;
import com.octo.gwt.test.integ.tools.AbstractGwtIntegrationShell;
import com.octo.gwt.test.integ.tools.PrefixProcessor;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.ISerializeCallback;

@RunWith(StandardJUnit4CsvRunner.class)
public abstract class MyGwtShell extends AbstractGwtIntegrationShell {

	private MyBeautifulApp app;
	
	private GwtCreateHandlerImpl handlerImpl;

	@Before
	public void setUp() throws Exception {
		handlerImpl = new GwtCreateHandlerImpl() {

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
		handlerImpl.addFromGwtCallbacks(Class.forName(MyCustomObject.class.getCanonicalName(), true, GwtTestClassLoader.getInstance().getParent()), new ISerializeCallback() {
			
			public Object callback(Object object) throws Exception {
				GwtTestReflectionUtils.setPrivateFieldValue(object, "myField", "toto");
				return object;
			}
		});
		PatchGwtConfig.setGwtCreateHandler(handlerImpl);
		MyStringStore.appender = "";
	}

	public void initApp() {
		app = new MyBeautifulApp();
		app.onModuleLoad();
	}

	public void append(String s) {
		MyStringStore.appender += s;
	}

	@Override
	public PrefixProcessor findPrefixProcessor(String prefix) {
		if ("app".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return csvRunner.getValue(failOnError, app, next);
				}

			};
		}
		if ("appender".equals(prefix)) {
			return new PrefixProcessor() {

				public Object process(CsvRunner csvRunner, Node next, boolean failOnError) {
					return MyStringStore.appender;
				}

			};
		}
		return super.findPrefixProcessor(prefix);
	}
	
	public void enableNewExceptionHandler() {
	
		handlerImpl.setExceptionHandler(new DefaultGwtRpcExceptionHandler() {
	
			@Override
			public void handle(InvocationTargetException invocationTargetException, AsyncCallback<?> callback) {
				Assert.assertEquals(NullPointerException.class, invocationTargetException.getCause().getClass());
				MyStringStore.appender = "toto";
				super.handle(invocationTargetException, callback);
			}
	
		});
	}

}
