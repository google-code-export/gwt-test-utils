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

public class RPCCompositeWithEasyMockTest extends GwtTestWithEasyMock {

  private RPCComposite composite;

  @Mock
  private MyServiceAsync service;

  @SuppressWarnings("unchecked")
  @Test
  public void checkRPCCallFailure() {
    // Setup
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

  @SuppressWarnings("unchecked")
  @Test
  public void checkRPCCallSuccess() {
    // Setup
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));

    FooBean expected = new FooBean();
    expected.setName("mocked");

    expectServiceAndCallbackOnSuccess(expected);

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

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @Before
  public void init() throws Exception {
    composite = new RPCComposite();
  }

}
