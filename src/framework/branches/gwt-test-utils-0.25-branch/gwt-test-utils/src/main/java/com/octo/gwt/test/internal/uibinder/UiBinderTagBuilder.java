package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.internal.utils.FastStack;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@SuppressWarnings("unchecked")
public class UiBinderTagBuilder<T> {

  public static final <T> UiBinderTagBuilder<T> create(
      Class<T> rootComponentClass, Object owner) {
    return new UiBinderTagBuilder<T>(rootComponentClass, owner);
  }

  private final Object owner;
  private final UiResourceManager resourceManager;
  private Object rootComponent;
  private final Class<T> rootComponentClass;
  private final FastStack<UiBinderTag> tags = new FastStack<UiBinderTag>();

  private UiBinderTagBuilder(Class<T> rootComponentClass, Object owner) {
    this.rootComponentClass = rootComponentClass;
    this.owner = owner;
    this.resourceManager = UiResourceManager.newInstance(owner);
  }

  public UiBinderTagBuilder<T> appendText(String text) {
    if (tags.size() > 0) {
      tags.get(tags.size() - 1).appendText(text);
    }

    return this;
  }

  public T build() {
    if (rootComponent == null) {
      throw new GwtTestUiBinderException(
          "Cannot build UiBinder component while the parsing of '"
              + owner.getClass().getSimpleName() + ".ui.xml' is not finished");

    } else if (!rootComponentClass.isInstance(rootComponent)) {
      throw new GwtTestUiBinderException(
          "Error in '"
              + owner.getClass().getSimpleName()
              + ".ui.xml' configuration : root component is expected to be an instance of '"
              + rootComponentClass.getName()
              + "' but is actually an instance of '"
              + rootComponent.getClass().getName() + "'");
    }

    return (T) rootComponent;
  }

  private UiBinderTag createUiBinderTag(String nameSpaceURI, String localName,
      Attributes attributes) {

    int i = nameSpaceURI.lastIndexOf(':');
    if (i > 0 && Character.isUpperCase(localName.charAt(0))) {
      // the element should represent a Widget Class
      String className = nameSpaceURI.substring(i + 1) + "." + localName;

      Class<?> clazz = null;
      try {
        clazz = Class.forName(className);
      } catch (ClassNotFoundException e) {
        throw new GwtTestUiBinderException("Cannot find class '" + className
            + "' declared in file '" + owner.getClass().getSimpleName()
            + ".ui.xml");
      }

      if (Widget.class.isAssignableFrom(clazz)) {
        // create or get the instance
        Widget widget = getInstance((Class<? extends Widget>) clazz, attributes);

        return new UiBinderWidget<Widget>(widget, attributes, owner,
            resourceManager);
      } else {
        throw new GwtTestUiBinderException("Not managed type in file '"
            + owner.getClass().getSimpleName() + ".ui.xml"
            + "', only subclass of '" + Widget.class.getName()
            + "' are managed");
      }
    } else if (UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
      return resourceManager.registerResource(localName, attributes);
    } else if (UiBinderUtils.isMsgTag(nameSpaceURI, localName)) {
      return new UiBinderMsg(getPrevious());
    } else {
      return new UiBinderElement(nameSpaceURI, localName, attributes, owner);
    }
  }

  public UiBinderTagBuilder<T> endTag(String nameSpaceURI, String localName) {

    // ignore <UiBinder> tag
    if (UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    Object currentObject = tags.pop().getWrapped();

    // ignore <data>, <image> and <style> stags
    if (UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
      return this;
    }

    if (tags.size() == 0) {
      // parsing is finished, this is the root component
      rootComponent = currentObject;
    } else {
      // add to its parent
      UiBinderTag parentTag = getPrevious();

      if (Widget.class.isInstance(currentObject)) {
        parentTag.addWidget((Widget) currentObject);
      } else if (Element.class.isInstance(currentObject)) {
        parentTag.addElement((Element) currentObject);
      } else if (String.class == currentObject.getClass()) {
        // <msg> case
        parentTag.appendText((String) currentObject);
      } else {
        // just ignore it, it must be a resource tag
      }
    }

    return this;
  }

  private <U> U getInstance(Class<U> clazz, Attributes attributes) {
    U instance = getProvidedUiField(clazz);

    if (instance == null) {
      try {
        // try to create it with any custom GwtCreateHandler or with
        // DefaultGwtCreateHandler (if there is an empty constructor)
        instance = (U) GWT.create(clazz);
      } catch (GwtTestConfigurationException e) {
        // just keep trying with @UiFactory or @UiConstructor
      }
    }

    if (instance == null) {
      instance = getObjectFromUiFactory(clazz);
    }

    if (instance == null) {
      instance = getObjectFromUiConstructor(clazz, attributes);
    }

    if (instance == null) {
      throw new GwtTestUiBinderException(
          clazz.getName()
              + " has no default (zero args) constructor. To fix this, you can define a @UiFactory method on the UiBinder's owner, or annotate a constructor of "
              + clazz.getSimpleName() + " with @UiConstructor.");
    }

    return instance;
  }

  private <U> U getObjectFromUiConstructor(Class<U> clazz, Attributes attributes) {
    for (Constructor<?> cons : clazz.getDeclaredConstructors()) {
      if (cons.getAnnotation(UiConstructor.class) != null) {
        Constructor<U> uiConstructor = (Constructor<U>) cons;
        String[] args = getUiConstructorArgs(clazz, attributes);
        try {
          return GwtReflectionUtils.instantiateClass(uiConstructor,
              (Object[]) args);
        } catch (Exception e) {
          StringBuilder sb = new StringBuilder();
          sb.append("Error while executing instruction 'new ").append(
              clazz.getSimpleName()).append("(");
          for (String arg : args) {
            sb.append("\"" + arg + "\"");
            sb.append(", ");
          }
          sb.replace(sb.length() - 2, sb.length() - 1, ");'");

          throw new GwtTestUiBinderException(sb.toString(), e);
        }
      }
    }

    return null;
  }

  private <U> U getObjectFromUiFactory(Class<U> clazz) {
    Map<Method, UiFactory> map = GwtReflectionUtils.getAnnotatedMethod(
        owner.getClass(), UiFactory.class);

    for (Method factoryMethod : map.keySet()) {
      if (factoryMethod.getReturnType() == clazz) {
        return (U) GwtReflectionUtils.callPrivateMethod(owner, factoryMethod);
      }
    }

    return null;
  }

  private UiBinderTag getPrevious() {
    return tags.get(tags.size() - 1);
  }

  private <U> U getProvidedUiField(Class<U> clazz) {
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

  private String[] getUiConstructorArgs(Class<?> clazz, Attributes attributes) {
    List<String> argsList = new ArrayList<String>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      if (!UiBinderUtils.isUiFieldAttribute(attributes.getURI(i), attrName)
          && !isBeanProperty(clazz, attrName)) {
        argsList.add(attributes.getValue(i));
      }
    }

    return argsList.toArray(new String[0]);
  }

  private boolean isBeanProperty(Class<?> clazz, String attrName) {
    String setter = "set" + Character.toUpperCase(attrName.charAt(0))
        + attrName.substring(1);
    for (Method m : clazz.getMethods()) {
      if (setter.equals(m.getName()) && m.getParameterTypes().length == 1) {
        return true;
      }
    }
    return false;
  }

  public UiBinderTagBuilder<T> startTag(String nameSpaceURI, String localName,
      Attributes attributes) {

    if (UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    // register the current UiBinderTag in the stack
    tags.push(createUiBinderTag(nameSpaceURI, localName, attributes));

    return this;
  }

}
