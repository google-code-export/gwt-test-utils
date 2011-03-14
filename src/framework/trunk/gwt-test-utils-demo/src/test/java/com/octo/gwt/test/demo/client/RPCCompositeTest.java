package com.octo.gwt.test.demo.client;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.GwtTestWithEasyMock;
import com.octo.gwt.test.Mock;
import com.octo.gwt.test.demo.beans.FooBean;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class RPCCompositeTest extends GwtTestWithEasyMock {

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
		//Setup
		Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

		service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));

		FooBean excepted = new FooBean();
		excepted.setName("mocked");

		expectServiceAndCallbackOnSuccess(excepted);

		// replay all @Mock objects
		replay();

		Assert.assertEquals("", label.getText());

		// Test
		Browser.click(button);

		// Assert
		// verify all @Mock objects
		verify();

		Assert.assertEquals("Bean \"mocked\" has been created", label.getText());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkRPCCallFailure() {
		//Setup
		Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

		service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));

		expectServiceAndCallbackOnFailure(new Exception("Mocked exception"));

		// replay all @Mock objects
		replay();

		Assert.assertEquals("", label.getText());

		// Test
		Browser.click(button);

		// Assert
		// verify all @Mock objects
		verify();
		Assert.assertEquals("Failure : Mocked exception", label.getText());
	}

}
