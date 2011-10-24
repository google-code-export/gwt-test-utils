package com.octo.gwt.test.uibinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Class responsible for Object instanciatations. It handles provided
 * {@link UiField}, {@link UiFactory} fields and {@link UiConstructor} fields.
 * 
 * @author Gael Lazzari
 * 
 */
@SuppressWarnings("unchecked")
class UiBinderInstanciator {

  static <U> U getInstance(Class<U> clazz, Attributes attributes, Object owner) {
    U instance = getProvidedUiField(clazz, owner);

    if (instance == null) {
      // delegate to specific widgets instanciator
      instance = createSpecificWidget(clazz, attributes, owner);
    }

    GwtTestConfigurationException createHandlerException = null;

    if (instance == null) {
      try {
        // try to create it with any custom GwtCreateHandler or with
        // DefaultGwtCreateHandler (if there is an empty constructor)
        instance = (U) GWT.create(clazz);
      } catch (GwtTestConfigurationException e) {
        createHandlerException = e;
        // just keep trying with @UiFactory or @UiConstructor
      }
    }

    if (instance == null) {
      instance = getObjectFromUiFactory(clazz, owner);
    }

    if (instance == null) {
      instance = getObjectFromUiConstructor(clazz, attributes);
    }

    if (instance == null) {
      if (IsWidget.class.isAssignableFrom(clazz)) {
        throw new GwtTestUiBinderException(
            clazz.getName()
                + " has no default (zero args) constructor. To fix this, you can define a @UiFactory method on the UiBinder's owner, or annotate a constructor of "
                + clazz.getSimpleName() + " with @UiConstructor.");
      } else {
        throw createHandlerException;
      }
    }

    return instance;
  }

  // TODO : not a flexible design...
  private static <U> U createSpecificWidget(Class<U> clazz,
      Attributes attributes, Object owner) {

    if (clazz == RadioButton.class) {
      String name = attributes.getValue("name");
      if (name == null) {
        throw new GwtTestUiBinderException(
            "Error during the instanciation of a RadioButton declared in "
                + owner.getClass().getSimpleName()
                + ".ui.xml' : missing required attribute 'name'");
      }
      return (U) new RadioButton(name);

    } else if (clazz == NamedFrame.class) {
      String name = attributes.getValue("name");
      if (name == null) {
        throw new GwtTestUiBinderException(
            "Error during the instanciation of a NamedFrame declared in "
                + owner.getClass().getSimpleName()
                + ".ui.xml' : missing required attribute 'name'");
      }
      return (U) new NamedFrame(name);

    }

    return null;
  }

  private static <U> U getObjectFromUiConstructor(Class<U> clazz,
      Attributes attributes) {
    for (Constructor<?> cons : clazz.getDeclaredConstructors()) {
      if (cons.getAnnotation(UiConstructor.class) != null) {
        Constructor<U> uiConstructor = (Constructor<U>) cons;
        String[] args = getUiConstructorArgs(clazz, attributes, false);

        if (args.length != uiConstructor.getParameterTypes().length) {
          args = getUiConstructorArgs(clazz, attributes, true);
        }

        if (args.length != uiConstructor.getParameterTypes().length) {
          throw new GwtTestUiBinderException(
              "Cannot invoke @UiConstructor '"
                  + uiConstructor.toGenericString()
                  + "' : gwt-test-utils is not able to determine which XML attributes are intented to be passed as parameters of this constructor");
        }

        try {
          return GwtReflectionUtils.instantiateClass(uiConstructor,
              (Object[]) args);
        } catch (Exception e) {
          StringBuilder sb = new StringBuilder();
          sb.append("Error while calling @UiConstructor 'new ").append(
              clazz.getSimpleName()).append("(");

          for (String arg : args) {
            sb.append("\"" + arg + "\"");
            sb.append(", ");
          }

          if (args.length > 0) {
            sb.delete(sb.length() - 2, sb.length() - 1);
          }

          sb.append(");'");

          throw new GwtTestUiBinderException(sb.toString(), e);
        }
      }
    }

    return null;
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

  private static <U> U getProvidedUiField(Class<U> clazz, Object owner) {
    Map<Field, UiField> map = GwtReflectionUtils.getAnnotatedField(
        owner.getClass(), UiField.class);

    for (Map.Entry<Field, UiField> entry : map.entrySet()) {
      if (entry.getKey().getType() == clazz && entry.getValue().provided()) {
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

  private static String[] getUiConstructorArgs(Class<?> clazz,
      Attributes attributes, boolean inclueSetters) {
    List<String> argsList = new ArrayList<String>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      if (!UiBinderXmlUtils.isUiFieldAttribute(attributes.getURI(i), attrName)) {

        if (inclueSetters || !isBeanProperty(clazz, attrName)) {
          argsList.add(attributes.getValue(i));
        }
      }
    }

    return argsList.toArray(new String[0]);
  }

  private static boolean isBeanProperty(Class<?> clazz, String attrName) {
    String setter = "set" + Character.toUpperCase(attrName.charAt(0))
        + attrName.substring(1);
    for (Method m : clazz.getMethods()) {
      if (setter.equals(m.getName()) && m.getParameterTypes().length == 1) {
        return true;
      }
    }
    return false;
  }

}
