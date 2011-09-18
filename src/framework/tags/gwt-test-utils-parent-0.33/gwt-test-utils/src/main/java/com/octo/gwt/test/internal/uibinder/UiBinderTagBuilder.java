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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@SuppressWarnings("unchecked")
public class UiBinderTagBuilder<T> {

  public static final <T> UiBinderTagBuilder<T> create(
      Class<T> rootComponentClass, Object owner) {
    return new UiBinderTagBuilder<T>(rootComponentClass, owner);
  }

  private UiBinderTag currentTag;
  private final Object owner;
  private final UiResourceManager resourceManager;

  private Object rootComponent;

  private final Class<T> rootComponentClass;

  private UiBinderTagBuilder(Class<T> rootComponentClass, Object owner) {
    this.rootComponentClass = rootComponentClass;
    this.owner = owner;
    this.resourceManager = UiResourceManager.newInstance(owner);
  }

  public UiBinderTagBuilder<T> appendText(String text) {
    if (!"".equals(text.trim())) {
      currentTag.appendText(text);
    }
    return this;
  }

  public T build() {
    if (rootComponent == null) {
      if (currentTag == null) {
        throw new GwtTestUiBinderException(
            owner.getClass().getName()
                + " does not declare a root widget in its corresponding .ui.xml file");
      } else {
        throw new GwtTestUiBinderException(
            "Cannot build UiBinder component while the parsing of '"
                + owner.getClass().getSimpleName() + ".ui.xml' is not finished");
      }

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

  public UiBinderTagBuilder<T> endTag(String nameSpaceURI, String localName) {

    // ignore <UiBinder> tag
    if (UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    Object currentObject = currentTag.getWrapped();
    UiBinderTag parentTag = currentTag.getParentTag();
    currentTag = parentTag;

    if (UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
      // ignore <data>, <image> and <style> stags
      return this;
    } else if (UiBinderUtils.isMsgTag(nameSpaceURI, localName)) {
      // special <msg> case
      parentTag.appendText((String) currentObject);
      return this;
    }

    if (parentTag == null) {
      // parsing is finished, this must be the root component
      if (rootComponent != null) {
        throw new GwtTestUiBinderException(
            "UiBinder template '"
                + owner.getClass().getName()
                + "' should declare only one root widget in its corresponding .ui.xml file");
      } else {
        rootComponent = currentObject;
      }
    } else {
      // add to its parent
      if (IsWidget.class.isInstance(currentObject)) {
        parentTag.addWidget((IsWidget) currentObject);
      } else {
        parentTag.addElement((Element) currentObject);
      }
    }

    return this;
  }

  public UiBinderTagBuilder<T> startTag(String nameSpaceURI, String localName,
      Attributes attributes) {

    if (UiBinderUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    // register the current UiBinderTag in the stack
    currentTag = createUiBinderTag(nameSpaceURI, localName, attributes,
        currentTag);

    return this;
  }

  private <U> U createSpecificWidget(Class<U> clazz, Attributes attributes) {

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

  private UiBinderTag createUiBinderTag(String nameSpaceURI, String localName,
      Attributes attributes, UiBinderTag parentTag) {

    localName = localName.replaceAll("\\.", "\\$");

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

      if (IsWidget.class.isAssignableFrom(clazz)) {
        // create or get the instance
        IsWidget isWidget = getInstance((Class<? extends IsWidget>) clazz,
            attributes);

        return new UiBinderWidget<IsWidget>(isWidget, attributes, parentTag,
            owner, resourceManager);
      } else {
        throw new GwtTestUiBinderException("Not managed type in file '"
            + owner.getClass().getSimpleName() + ".ui.xml"
            + "', only implementation of '" + IsWidget.class.getName()
            + "' are allowed");
      }
    } else if (UiBinderUtils.isResourceTag(nameSpaceURI, localName)) {
      return resourceManager.registerResource(localName, attributes, parentTag);
    } else if (UiBinderUtils.isMsgTag(nameSpaceURI, localName)) {
      return new UiBinderMsg(currentTag);
    } else {
      return new UiBinderElement(nameSpaceURI, localName, attributes,
          parentTag, owner);
    }
  }

  private <U> U getInstance(Class<U> clazz, Attributes attributes) {
    U instance = getProvidedUiField(clazz);

    if (instance == null) {
      // delegate to specific widgets instanciator
      instance = createSpecificWidget(clazz, attributes);
    }

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

  private <U> U getObjectFromUiFactory(Class<U> clazz) {
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

  private String[] getUiConstructorArgs(Class<?> clazz, Attributes attributes,
      boolean inclueSetters) {
    List<String> argsList = new ArrayList<String>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      if (!UiBinderUtils.isUiFieldAttribute(attributes.getURI(i), attrName)) {

        if (inclueSetters || !isBeanProperty(clazz, attrName)) {
          argsList.add(attributes.getValue(i));
        }
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

}
