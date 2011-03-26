package com.octo.gwt.test.demo.client;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.GwtTestWithMockito;
import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class RPCCompositeWithMockitoTest extends GwtTestWithMockito {

	@Mock
	private MyServiceAsync service;

	private RPCComposite composite;

	@Before
	public void init() throws Exception {
		composite = new RPCComposite();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkRPCCallSuccess() {
		// Setup
		Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

		FooBean expected = new FooBean();
		expected.setName("mocked");
		
		// Stub remote service invocation and success notification
		doSuccessCallback(expected).when(service).createBean(eq("OCTO"), any(AsyncCallback.class));

		Assert.assertEquals("", label.getText());

		// Test
		Browser.click(button);

		// Assert
		// verify service invocation
		verify(service).createBean(eq("OCTO"), any(AsyncCallback.class));

		Assert.assertEquals("Bean \"mocked\" has been created", label.getText());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkRPCCallFailure() {
		// Setup
		Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

		// Stub remote service invocation and failure notification
		doFailureCallback(new Exception("Mocked exception")).when(service).createBean(eq("OCTO"), any(AsyncCallback.class));

		Assert.assertEquals("", label.getText());

		// Test
		Browser.click(button);

		// Assert
		// verify service invocation
		verify(service).createBean(eq("OCTO"), any(AsyncCallback.class));
		Assert.assertEquals("Failure : Mocked exception", label.getText());
	}

}
