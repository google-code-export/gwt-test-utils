package com.octo.gwt.test.uibinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.octo.gwt.test.GwtCreateHandler;

/**
 * GwtCreateHandler in charge of creating UiBinder objects through deferred
 * binding, parsing .ui.xml files and binding to java {@link UiField}.
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class UiBinderCreateHandler implements GwtCreateHandler {

  private static final UiBinderCreateHandler INSTANCE = new UiBinderCreateHandler();

  public static UiBinderCreateHandler get() {
    return INSTANCE;
  }

  private UiBinderCreateHandler() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtCreateHandler#create(java.lang.Class)
   */
  public Object create(Class<?> classLiteral) throws Exception {
    if (UiBinder.class.isAssignableFrom(classLiteral)) {
      return createProxy(classLiteral);
    } else {
      return null;
    }
  }

  private Object createProxy(Class<?> uiBinderClass) {
    InvocationHandler ih = new UiBinderInvocationHandler(uiBinderClass);

    return Proxy.newProxyInstance(this.getClass().getClassLoader(),
        new Class<?>[]{uiBinderClass}, ih);
  }
}
