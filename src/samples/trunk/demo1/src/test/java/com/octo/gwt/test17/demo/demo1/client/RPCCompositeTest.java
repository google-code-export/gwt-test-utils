package com.octo.gwt.test17.demo.demo1.client;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test17.Mock;
import com.octo.gwt.test17.ReflectionUtils;
import com.octo.gwt.test17.demo.demo1.beans.FooBean;
import com.octo.gwt.test17.test.AbstractGWTEasyMockTest;

public class RPCCompositeTest extends AbstractGWTEasyMockTest {

	@Mock
	private FirstServiceAsync service;
	
	private RPCComposite composite;
	
	@Before
	public void init() throws Exception {
		composite = new RPCComposite();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void checkRPCCallSuccess() {
		//Setup
		Button button = ReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = ReflectionUtils.getPrivateFieldValue(composite, "label");
		
		service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));
		
		FooBean excepted = new FooBean();
		excepted.setName("mocked");
		
		expectServiceAndCallbackOnSuccess(excepted);
		replay();
		// Test
		Assert.assertEquals(null, label.getText());
		
		// click to call server back
		click(button);
		
		// Assert
		verify();
		Assert.assertEquals("Bean \"mocked\" has been created", label.getText());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void checkRPCCallFailure() {
		//Setup
		Button button = ReflectionUtils.getPrivateFieldValue(composite, "button");
		Label label = ReflectionUtils.getPrivateFieldValue(composite, "label");
		
		service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));
		
		expectServiceAndCallbackOnFailure(new Exception("Mocked exception"));
		replay();
		// Test
		Assert.assertEquals(null, label.getText());
		
		// click to call server back
		click(button);
		
		// Assert
		verify();
		Assert.assertEquals("Failure : Mocked exception", label.getText());
	}

}
