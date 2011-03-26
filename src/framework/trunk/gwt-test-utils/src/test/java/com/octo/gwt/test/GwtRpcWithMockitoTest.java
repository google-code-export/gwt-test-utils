package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtRpcWithMockitoTest extends GwtTestWithMockito {

	static class MyGwtClass {

		public String myValue;

		public void run() {
			MyRemoteServiceAsync service = GWT.create(MyRemoteService.class);

			service.myMethod("myParamValue", new AsyncCallback<String>() {

				public void onFailure(Throwable caught) {
					myValue = "error";
				}

				public void onSuccess(String result) {
					myValue = result;
				}

			});
		}
	}

	@org.mockito.Mock
	private MyRemoteServiceAsync mockedService;

	@SuppressWarnings("unchecked")
	@Test
	public void checkGwtRpcOk() {
		// Setup
		
		// mock future remote call
		doSuccessCallback("returnValue").when(mockedService).myMethod(eq("myParamValue"), any(AsyncCallback.class));

		// Test
		MyGwtClass gwtClass = new MyGwtClass();
		gwtClass.myValue = "toto";
		Assert.assertEquals("toto", gwtClass.myValue);
		gwtClass.run();

		// Assert
		verify(mockedService).myMethod(eq("myParamValue"), any(AsyncCallback.class));

		Assert.assertEquals("returnValue", gwtClass.myValue);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkGwtRpcFailure() {
		// Setup

		// mock future remote call
		doFailureCallback(new Exception()).when(mockedService).myMethod(eq("myParamValue"), any(AsyncCallback.class));

		// Test
		MyGwtClass gwtClass = new MyGwtClass();
		gwtClass.myValue = "toto";
		Assert.assertEquals("toto", gwtClass.myValue);
		gwtClass.run();

		// Assert
		Assert.assertEquals("error", gwtClass.myValue);
	}

}
