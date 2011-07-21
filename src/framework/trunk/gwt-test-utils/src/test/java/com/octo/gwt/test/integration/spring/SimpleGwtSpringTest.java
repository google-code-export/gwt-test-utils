package com.octo.gwt.test.integration.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.integration.MyService;
import com.octo.gwt.test.integration.MyServiceAsync;
import com.octo.gwt.test.integration.client.MyObject;
import com.octo.gwt.test.spring.GwtSpringTest;
import com.octo.gwt.test.spring.GwtTestContextLoader;

@ContextConfiguration(locations = {"classpath:com/octo/gwt/test/integration/spring/applicationContext-test.xml"}, loader = GwtTestContextLoader.class)
public class SimpleGwtSpringTest extends GwtSpringTest {

  private boolean failure;
  private boolean success;

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.GwtTestUtils";
  }

  @Test
  public void rpcCall() {
    // Arrange
    failure = false;
    success = false;
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

  @Override
  protected Object findRpcServiceInSpringContext(
      ApplicationContext applicationContext, Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    if ("myService".equals(remoteServiceRelativePath)) {
      return applicationContext.getBean("myService");
    }

    return null;
  }

  @Override
  protected String getHostPagePath(String moduleFullQualifiedName) {
    return null;
  }

}
