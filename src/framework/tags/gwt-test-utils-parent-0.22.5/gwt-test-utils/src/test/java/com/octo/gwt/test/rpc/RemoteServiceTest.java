package com.octo.gwt.test.rpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.client.MyObject;

public class RemoteServiceTest extends GwtTestTest {

  private boolean failure;
  private boolean success;

  @Before
  public void beforeRemoteServiceTest() {
    failure = false;
    success = false;
  }

  @Test
  public void rpcCall_WithException() {
    // Arrange
    MyServiceAsync myService = GWT.create(MyService.class);

    // Act
    myService.someCallWithException(new AsyncCallback<Void>() {

      public void onFailure(Throwable caught) {

        assertEquals("Server side thrown exception !!", caught.getMessage());
        failure = true;
      }

      public void onSuccess(Void result) {
        success = true;
      }
    });

    // Assert
    assertTrue(
        "The service callback should have been call in a synchronised way",
        failure);
    assertFalse(success);
  }

  @Test
  public void rpcCall_WithSuccess() {
    // Arrange
    MyObject object = new MyObject("my field initialized during test setup");

    MyServiceAsync myService = GWT.create(MyService.class);

    // Act
    myService.update(object, new AsyncCallback<MyObject>() {

      public void onFailure(Throwable caught) {
        failure = true;
      }

      public void onSuccess(MyObject result) {
        // Assert 1
        assertEquals("updated field by server side code", result.getMyField());
        assertEquals("transient field", result.getMyTransientField());

        assertEquals(
            "A single child object should have been instanciate in server code",
            1, result.getMyChildObjects().size());
        assertEquals("this is a child !",
            result.getMyChildObjects().get(0).getMyChildField());
        assertEquals("child object transient field",
            result.getMyChildObjects().get(0).getMyChildTransientField());

        assertEquals("the field inherited from the parent has been updated !",
            result.getMyChildObjects().get(0).getMyField());
        assertEquals("transient field",
            result.getMyChildObjects().get(0).getMyTransientField());
        success = true;
      }
    });

    // Assert 2
    assertTrue(
        "The service callback should have been call in a synchronised way",
        success);
    assertFalse(failure);
  }

}
