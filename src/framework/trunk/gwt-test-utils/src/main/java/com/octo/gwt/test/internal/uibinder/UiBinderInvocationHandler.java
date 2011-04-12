package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("unchecked")
public class UiBinderInvocationHandler implements InvocationHandler {

  private final Class<?> proxiedClass;

  UiBinderInvocationHandler(Class<?> proxiedClass) {
    this.proxiedClass = proxiedClass;
  }

  public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
    if (method.getName().equals("createAndBindUi")) {
      return createAndBindUi(args[0]);
    } else {
      throw new ReflectionException("Not managed method for UiBinder : "
          + method.getName());
    }
  }

  private Object createAndBindUi(Object owner) {
    Class<?> rootElementClass = getRootElementClass();

    Object result;

    if (JavaScriptObject.class.isAssignableFrom(rootElementClass)) {
      result = instanciateJSO((Class<? extends JavaScriptObject>) rootElementClass);
    } else {
      result = GwtReflectionUtils.instantiateClass(rootElementClass);
    }

    fillObjectsWithXMLData(result, owner);
    return result;
  }

/**
       * This method is in charge of the instanciation of DOM object / GWT
       * widget and their binding with @UiField in the owner
       * 
       * @param result The resulting object, to be returned by {@link 
       * @param owner
       */
  private void fillObjectsWithXMLData(Object result, Object owner) {
    GwtUiBinderParser parser = new GwtUiBinderParser();
    parser.fillObjects(result, owner);
  }

  /**
   * Get the actual class of the <U> parameter.
   * 
   * @return The class of the root element or widget generated from UiBinder.
   */
  private Class<?> getRootElementClass() {
    for (Type type : proxiedClass.getGenericInterfaces()) {

      if (type instanceof ParameterizedType) {
        ParameterizedType pType = (ParameterizedType) type;

        return (Class<?>) pType.getActualTypeArguments()[0];
      }
    }

    throw new GwtTestConfigurationException("The UiBinder subinterface '"
        + proxiedClass.getName()
        + "' is not parameterized. Please add its generic types.");
  }

  private <T extends JavaScriptObject> T instanciateJSO(Class<T> nodeClass) {
    if (Element.class.isAssignableFrom(nodeClass)) {
      String tagName = GwtReflectionUtils.getStaticFieldValue(nodeClass, "TAG");
      return (T) JavaScriptObjects.newElement(tagName);
    } else {
      return JavaScriptObjects.newObject(nodeClass);
    }
  }

}
