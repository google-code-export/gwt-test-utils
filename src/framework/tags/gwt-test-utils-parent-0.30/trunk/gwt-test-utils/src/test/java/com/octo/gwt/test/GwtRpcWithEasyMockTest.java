package com.octo.gwt.test;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GwtRpcWithEasyMockTest extends GwtTestWithEasyMock {

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

  @Mock
  private MyRemoteServiceAsync mockedService;

  @SuppressWarnings("unchecked")
  @Test
  public void checkGwtRpcFailure() {
    // Arrange

    // mock future remote call
    mockedService.myMethod(EasyMock.eq("myParamValue"),
        EasyMock.isA(AsyncCallback.class));
    expectServiceAndCallbackOnFailure(new Exception());

    replay();

    // Act
    MyGwtClass gwtClass = new MyGwtClass();
    gwtClass.myValue = "toto";
    Assert.assertEquals("toto", gwtClass.myValue);
    gwtClass.run();

    // Assert
    verify();

    Assert.assertEquals("error", gwtClass.myValue);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void checkGwtRpcOk() {
    // Arrange

    // mock future remote call
    mockedService.myMethod(EasyMock.eq("myParamValue"),
        EasyMock.isA(AsyncCallback.class));
    expectServiceAndCallbackOnSuccess("returnValue");

    replay();

    // Act
    MyGwtClass gwtClass = new MyGwtClass();
    gwtClass.myValue = "toto";
    Assert.assertEquals("toto", gwtClass.myValue);
    gwtClass.run();

    // Assert
    verify();

    Assert.assertEquals("returnValue", gwtClass.myValue);
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.GwtTestUtils";
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return "test.html";
  }

}
