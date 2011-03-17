package com.octo.gwt.test.integration;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.GwtTest;

public class RemoteServiceTest extends GwtTest {

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
				Assert.assertEquals("updated field by server side code", result.getMyField());
				Assert.assertEquals("transient field", result.getMyTransientField());

				Assert.assertEquals("A single child object should have been instanciate in server code", 1, result.getMyChildObjects().size());
				Assert.assertEquals("this is a child !", result.getMyChildObjects().get(0).getMyChildField());
				Assert.assertEquals("child object transient field", result.getMyChildObjects().get(0).getMyChildTransientField());

				Assert.assertEquals("the field inherited from the parent has been updated !", result.getMyChildObjects().get(0).getMyField());
				Assert.assertEquals("transient field", result.getMyChildObjects().get(0).getMyTransientField());
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
