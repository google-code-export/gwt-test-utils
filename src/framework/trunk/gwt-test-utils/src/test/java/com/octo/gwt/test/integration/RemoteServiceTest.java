package com.octo.gwt.test.integration;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.AbstractGwtTest;

public class RemoteServiceTest extends AbstractGwtTest {

	private boolean success;
	private boolean failure;

	@Before
	public void setupRemoteServiceTest() {
		addGwtCreateHandler(new RemoteServiceCreateHandler() {

			@Override
			protected Object findService(Class<?> remoteServiceClass, String remoteServiceRelativePath) {
				if (remoteServiceClass == MyService.class)
					return new MyServiceImpl();

				return null;
			}
		});

		success = false;
		failure = false;
	}

	@Test
	public void checkRemoteServiceCallWithSuccess() {
		// Setup
		MyObject object = new MyObject("my field initialized during test setup");

		MyServiceAsync myService = GWT.create(MyService.class);

		// test
		myService.update(object, new AsyncCallback<MyObject>() {

			public void onSuccess(MyObject result) {
				Assert.assertEquals("updated field by server side code", result.myField);
				Assert.assertEquals("transient field", result.myTransientField);

				Assert.assertEquals("A single child object should have been instanciate in server code", 1, result.myChildObjects.size());
				Assert.assertEquals("this is a child !", result.myChildObjects.get(0).myChildField);
				Assert.assertEquals("child object transient field", result.myChildObjects.get(0).myChildTransientField);
				success = true;
			}

			public void onFailure(Throwable caught) {
				failure = true;
			}
		});

		Assert.assertTrue("The service callback should have been call in a synchronised way", success);
		Assert.assertFalse(failure);
	}

	@Test
	public void checkRemoteServiceCallWithException() {
		// Setup
		MyServiceAsync myService = GWT.create(MyService.class);

		// test
		myService.someCallWithException(new AsyncCallback<Void>() {

			public void onFailure(Throwable caught) {

				Assert.assertEquals("Server side thrown exception !!", caught.getMessage());
				failure = true;
			}

			public void onSuccess(Void result) {
				success = true;
			}
		});

		Assert.assertTrue("The service callback should have been call in a synchronised way", failure);
		Assert.assertFalse(success);
	}
}