package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.googlecode.gwt.test.utils.events.Browser;
import com.octo.gwt.test.demo.beans.FooBean;

public class DemoMockitoTest extends GwtTestWithMockito {

  private RPCComposite composite;

  @Mock
  private MyServiceAsync service;

  @Before
  public void beforeDemoMockitoTest() throws Exception {
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

    // Stub remote service invocation and failure notification
    doFailureCallback(new Exception("Mocked exception")).when(service).createBean(
        eq("OCTO"), any(AsyncCallback.class));

    assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    // verify service invocation
    verify(service).createBean(eq("OCTO"), any(AsyncCallback.class));
    assertEquals("Failure : Mocked exception", label.getText());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void rpcCall_Success() {
    // Arrange
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    FooBean expected = new FooBean();
    expected.setName("mocked");

    // Stub remote service invocation and success notification
    doSuccessCallback(expected).when(service).createBean(eq("OCTO"),
        any(AsyncCallback.class));

    assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    // verify service invocation
    verify(service).createBean(eq("OCTO"), any(AsyncCallback.class));

    assertEquals("Bean \"mocked\" has been created", label.getText());
  }

}
