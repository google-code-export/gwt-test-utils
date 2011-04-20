package com.octo.gwt.test.internal.mock;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test.GwtCreateHandler;

public class MockCreateHandler implements GwtCreateHandler {

  private final Map<Class<?>, Object> mockObjects;

  public MockCreateHandler(Map<Class<?>, Object> mockObjects) {
    this.mockObjects = mockObjects;
  }

  public Object create(Class<?> classLiteral) throws Exception {
    if (RemoteService.class.isAssignableFrom(classLiteral)) {
      String asyncName = classLiteral.getCanonicalName() + "Async";
      classLiteral = Class.forName(asyncName);
    }
    return mockObjects.get(classLiteral);
  }

}
