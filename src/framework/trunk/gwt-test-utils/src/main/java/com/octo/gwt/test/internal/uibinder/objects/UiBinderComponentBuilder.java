package com.octo.gwt.test.internal.uibinder.objects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.internal.uibinder.UiBinderUtils;
import com.octo.gwt.test.utils.FastStack;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@SuppressWarnings("unchecked")
public class UiBinderComponentBuilder<T> {

  public static final <T> UiBinderComponentBuilder<T> create(
      Class<T> rootComponentClass, Object owner) {
    return new UiBinderComponentBuilder<T>(rootComponentClass, owner);
  }

  private final Object owner;
  private final Map<String, Object> resources = new HashMap<String, Object>();
  private Object rootComponent;
  private final Class<T> rootComponentClass;
  private final FastStack<UiBinderTag> tags = new FastStack<UiBinderTag>();

  private UiBinderComponentBuilder(Class<T> rootComponentClass, Object owner) {
    this.rootComponentClass = rootComponentClass;
    this.owner = owner;
  }

  public UiBinderComponentBuilder<T> appendText(char[] ch, int start, int end) {
    if (end > start) {
      tags.get(tags.size() - 1).appendText(new String(ch, start, end));
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

  public UiBinderComponentBuilder<T> endTag(String nameSpaceURI,
      String localName) {
    if (!shouldIgnoreTag(nameSpaceURI, localName)
        && !UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
      UiBinderTag tag = tags.pop();

      if (tags.size() == 0) {
        // parsing is finished, this is the root component
        rootComponent = tag.complete();
      } else {
        // add to its parent
        tags.get(tags.size() - 1).addTag(tag);
      }
    }

    return this;
  }

  public UiBinderComponentBuilder<T> startTag(String nameSpaceURI,
      String localName, Attributes attributes) {

    if (!shouldIgnoreTag(nameSpaceURI, localName)) {
      if (UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
        String alias = getResourceAlias(localName, attributes);

        if (!resources.containsKey(alias)) {
          String type = getResourceType(localName, attributes);
          resources.put(alias, this.getInstance(type, attributes));
        }
      } else {
        tags.push(createUiBinderTag(nameSpaceURI, localName, attributes));
      }
    }

    return this;
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

      // TODO: refactor..
      if (HTMLPanel.class == clazz) {
        HTMLPanel panel = new HTMLPanel("");
        return new UiBinderWidget<HTMLPanel>(panel, attributes, owner,
            resources);
      } else if (Widget.class.isAssignableFrom(clazz)) {
        Widget widget = getInstance((Class<Widget>) clazz, attributes);
        return new UiBinderWidget<Widget>(widget, attributes, owner, resources);
      } else {
        throw new GwtTestUiBinderException("Not managed type in file '"
            + owner.getClass().getSimpleName() + ".ui.xml"
            + "', only subclass of '" + Widget.class.getName()
            + "' are managed");
      }
    } else {
      return new UiBinderElement(nameSpaceURI, localName, attributes, owner);
    }
  }

  private <U> U getInstance(Class<U> clazz, Attributes attributes) {
    U instance = getProvidedUiField(clazz);

    if (instance == null) {
      instance = getObjectFromUiFactory(clazz);
    }

    if (instance == null) {
      instance = getObjectFromUiConstructor(clazz, attributes);
    }

    if (instance == null) {
      instance = (U) GWT.create(clazz);
    }

    return instance;
  }

  private <U> U getInstance(String type, Attributes attributes) {
    ;
    try {
      Class<U> clazz = (Class<U>) Class.forName(type);
      return getInstance(clazz, attributes);
    } catch (ClassNotFoundException e) {
      throw new GwtTestUiBinderException("Cannot find class '" + type
          + "' declared in file '" + owner.getClass().getSimpleName()
          + ".ui.xml'");
    }
  }

  private <U> U getObjectFromUiConstructor(Class<U> clazz, Attributes attributes) {
    for (Constructor<?> cons : clazz.getDeclaredConstructors()) {
      if (cons.getAnnotation(UiConstructor.class) != null) {
        Constructor<U> uiConstructor = (Constructor<U>) cons;
        String[] arguments = getUiConstructorArgs(attributes);
        try {
          return uiConstructor.newInstance((Object[]) arguments);
        } catch (Exception e) {
          StringBuilder sb = new StringBuilder();
          sb.append("Error while executing instruction 'new ").append(
              clazz.getSimpleName()).append("(");
          for (String argument : arguments) {
            sb.append(argument);
            sb.append(", ");
          }
          sb.replace(sb.length() - 3, sb.length() - 1, "')");

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

  private <U> U getProvidedUiField(Class<U> withObjectClass) {
    Map<Field, UiField> map = GwtReflectionUtils.getAnnotatedField(
        owner.getClass(), UiField.class);

    for (Map.Entry<Field, UiField> entry : map.entrySet()) {
      if (entry.getKey().getType() == withObjectClass
          && entry.getValue().provided()) {
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

  private String getResourceAlias(String localName, Attributes attributes) {
    String alias;
    alias = attributes.getValue("field");
    if (!"with".equals(localName) && alias == null) {
      alias = localName;
    }
    if (alias == null) {
      throw new GwtTestUiBinderException(
          "Cannot get the 'field' value for tag <" + localName + "> in "
              + owner.getClass().getSimpleName() + ".ui.xml");
    }

    return alias;
  }

  private String getResourceType(String localName, Attributes attributes) {
    String type = attributes.getValue("type");

    if (type == null) {
      throw new GwtTestUiBinderException("Error in '"
          + owner.getClass().getSimpleName() + ".ui.xml' : a <" + localName
          + "> tag does not contain the corresponding 'type' attribute");
    }

    return type;
  }

  private String[] getUiConstructorArgs(Attributes attributes) {
    List<String> argsList = new ArrayList<String>();

    for (int i = 0; i < attributes.getLength(); i++) {
      if (!UiBinderUtils.isUiFieldAttribute(attributes.getURI(i),
          attributes.getLocalName(i))) {
        argsList.add(attributes.getValue(i));
      }
    }

    return argsList.toArray(new String[0]);
  }

  private boolean shouldIgnoreTag(String nameSpaceURI, String localName) {
    return "style".equals(localName)
        || UiBinderUtils.isUiBinderTag(nameSpaceURI, localName);
  }

}
