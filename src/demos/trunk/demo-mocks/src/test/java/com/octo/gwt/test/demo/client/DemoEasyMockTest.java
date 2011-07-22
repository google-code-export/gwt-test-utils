package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
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

public class DemoEasyMockTest extends GwtTestWithEasyMock {

  private RPCComposite composite;

  @Mock
  private MyServiceAsync service;

  @Before
  public void beforeDemoEasyMockTest() throws Exception {
    composite = new RPCComposite();
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @SuppressWarnings("unchecked")
  @Test
  public void rpcCall_Failure() {
    // Arrange
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));

    expectServiceAndCallbackOnFailure(new Exception("Mocked exception"));

    // replay all @Mock objects
    replay();

    assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    // verify all @Mock objects
    verify();
    assertEquals("Failure : Mocked exception", label.getText());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void rpcCall_Success() {
    // Arrange
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    service.createBean(EasyMock.eq("OCTO"), EasyMock.isA(AsyncCallback.class));

    FooBean expected = new FooBean();
    expected.setName("mocked");

    expectServiceAndCallbackOnSuccess(expected);

    // replay all @Mock objects
    replay();

    assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    // verify all @Mock objects
    verify();

    assertEquals("Bean \"mocked\" has been created", label.getText());
  }

}
