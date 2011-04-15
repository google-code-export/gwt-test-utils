package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gwt.dev.Link;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.ReflectionException;

/**
 * 
 * @author Gael Lazzari
 * 
 */
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

  /**
   * This method is in charge of the instanciation of DOM object / GWT widget
   * and their binding with @UiField in the owner
   * 
   * @param owner The owner UiBinder subclass instance.
   * @return The root component, initially returned by {@link Link
   *         UiBinder#createAndBindUi(Object)}.
   */
  private Object createAndBindUi(Object owner) {
    Class<?> rootElementClass = getRootElementClass();
    GwtUiBinderParser parser = new GwtUiBinderParser();
    return parser.createUiComponenet(rootElementClass, owner);
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

}
