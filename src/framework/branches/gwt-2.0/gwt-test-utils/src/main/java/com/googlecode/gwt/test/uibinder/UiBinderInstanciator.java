package com.googlecode.gwt.test.uibinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.test.exceptions.GwtTestUiBinderException;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

/**
 * Class responsible for Object instantiations. It handles provided
 * {@link UiField}, {@link UiFactory} fields and {@link UiConstructor} fields.
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("unchecked")
class UiBinderInstanciator {

  static <U> U getInstance(Class<U> clazz, Map<String, Object> attributes,
      Object owner) {

    String uiFieldValue = (String) attributes.get("ui:field");
    U instance = getProvidedUiField(clazz, owner, uiFieldValue);

    if (instance == null) {
      instance = getObjectFromUiFactory(clazz, owner);
    }

    if (instance == null) {
      instance = getObjectFromUiConstructor(clazz, attributes);
    }

    if (instance == null && !UIObject.class.isAssignableFrom(clazz)
        && !Widget.class.isAssignableFrom(clazz)) {
      instance = GWT.<U> create(clazz);
    }

    return instance;
  }

  private static List<Object> extractArgs(String[] argNames,
      Map<String, Object> attributes) {

    List<Object> args = new ArrayList<Object>();
    for (String argName : argNames) {
      Object arg = attributes.get(argName);
      if (arg == null) {
        // the widget .ui.xml declaration does not match with this
        // @UiConstructor
        return null;
      }

      args.add(arg);
    }

    return args;
  }

  private static <U> U getObjectFromUiConstructor(Class<U> clazz,
      Map<String, Object> attributes) {

    List<String[]> registeredUiConstructors = GwtConfig.get().getUiConstructors(
        clazz);

    boolean hasUiConstructor = false;

    for (Constructor<?> cons : clazz.getDeclaredConstructors()) {
      if (cons.getAnnotation(UiConstructor.class) != null) {

        hasUiConstructor = true;
        if (registeredUiConstructors == null) {
          throw new GwtTestUiBinderException(
              "gwt-test-utils has found a @UiConstructor in '"
                  + clazz.getName()
                  + "' which isn't registered. You should register it by calling the 'registerUiConstructor' with the approriate parameters in your test class");
        }

        Constructor<U> uiConstructor = (Constructor<U>) cons;

        for (String[] argNames : registeredUiConstructors) {
          List<Object> potentialArgs = extractArgs(argNames, attributes);
          if (potentialArgs != null && matchs(uiConstructor, potentialArgs)) {
            return instanciate(uiConstructor, potentialArgs);
          }
        }

      }
    }

    if (!hasUiConstructor) {
      return null;
    } else {
      throw new GwtTestUiBinderException(
          "gwt-test-utils has found at least one @UiConstructor in '"
              + clazz.getName()
              + "' which isn't registered well. You should register it by calling the 'registerUiConstructor' with the approriate parameters in your test class");

    }
  }

  private static <U> U getObjectFromUiFactory(Class<U> clazz, Object owner) {
    Map<Method, UiFactory> map = GwtReflectionUtils.getAnnotatedMethod(
        owner.getClass(), UiFactory.class);

    List<Method> compatibleFactories = new ArrayList<Method>();
    for (Method factoryMethod : map.keySet()) {
      if (clazz.isAssignableFrom(factoryMethod.getReturnType())) {
        compatibleFactories.add(factoryMethod);
      }
    }

    switch (compatibleFactories.size()) {
      case 0:
        return null;
      case 1:
        return (U) GwtReflectionUtils.callPrivateMethod(owner,
            compatibleFactories.get(0));
      default:
        throw new GwtTestUiBinderException("Duplicate factory in class '"
            + owner.getClass().getName() + " for type '" + clazz.getName()
            + "'");
    }
  }

  private static <U> U getProvidedUiField(Class<U> clazz, Object owner,
      String uiFieldValue) {
    Map<Field, UiField> map = GwtReflectionUtils.getAnnotatedField(
        owner.getClass(), UiField.class);

    for (Map.Entry<Field, UiField> entry : map.entrySet()) {
      if (!entry.getValue().provided()) {
        // not a provided uiField
        continue;
      } else if (entry.getKey().getName().equals(uiFieldValue)
          || (uiFieldValue == null && entry.getKey().getType() == clazz)) {
        Object providedObject = GwtReflectionUtils.getPrivateFieldValue(owner,
            entry.getKey());
        if (providedObject == null) {
          throw new GwtTestUiBinderException(
              "The UiField(provided=true) '"
                  + entry.getKey().getDeclaringClass().getSimpleName()
                  + "."
                  + entry.getKey().getName()
                  + "' has not been initialized before calling 'UiBinder.createAndBind(..)' method");
        }

        return (U) providedObject;
      }
    }

    return null;
  }

  private static <U> U instanciate(Constructor<U> cons, List<Object> args) {
    try {
      return GwtReflectionUtils.instantiateClass(cons, args.toArray());
    } catch (Exception e) {
      StringBuilder sb = new StringBuilder();
      sb.append("Error while calling @UiConstructor 'new ").append(
          cons.getDeclaringClass().getSimpleName()).append("(");

      for (Object arg : args) {
        sb.append("\"" + arg.toString() + "\"");
        sb.append(", ");
      }

      if (args.size() > 0) {
        sb.delete(sb.length() - 2, sb.length() - 1);
      }

      sb.append(");'");

      throw new GwtTestUiBinderException(sb.toString(), e);
    }
  }

  private static boolean matchs(Constructor<?> cons, List<Object> args) {
    Class<?>[] paramTypes = cons.getParameterTypes();
    if (paramTypes.length != args.size()) {
      return false;
    }

    for (int i = 0; i < args.size(); i++) {
      if (!paramTypes[i].isInstance(args.get(i))) {
        return false;
      }
    }

    return true;
  }

}
