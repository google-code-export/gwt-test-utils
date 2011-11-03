package com.octo.gwt.test.uibinder;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;

@SuppressWarnings("unchecked")
class UiBinderTagBuilder<T> {

  static final <T> UiBinderTagBuilder<T> create(Class<T> rootComponentClass,
      Object owner) {
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

  UiBinderTagBuilder<T> appendText(String text) {
    if (!"".equals(text.trim())) {
      currentTag.appendText(text);
    }
    return this;
  }

  T build() {
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

  UiBinderTagBuilder<T> endTag(String nameSpaceURI, String localName) {

    // ignore <UiBinder> tag
    if (UiBinderXmlUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    Object currentObject = currentTag.endTag();
    UiBinderTag parentTag = currentTag.getParentTag();
    currentTag = parentTag;

    if (UiBinderXmlUtils.isResourceTag(nameSpaceURI, localName)
        || UiBinderXmlUtils.isImportTag(nameSpaceURI, localName)) {
      // ignore <ui:data>, <ui:image>, <ui:style> and <ui:import> tags
      return this;
    } else if (UiBinderXmlUtils.isMsgTag(nameSpaceURI, localName)) {
      // special <ui:msg> case
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
      if (Widget.class.isInstance(currentObject)) {
        parentTag.addWidget((Widget) currentObject);
      } else {
        parentTag.addElement((Element) currentObject);
      }
    }

    return this;
  }

  UiBinderTagBuilder<T> startTag(String nameSpaceURI, String localName,
      Attributes attributes) {

    if (UiBinderXmlUtils.isUiBinderTag(nameSpaceURI, localName)) {
      return this;
    }

    // register the current UiBinderTag in the stack
    currentTag = createUiBinderTag(nameSpaceURI, localName, attributes,
        currentTag);

    return this;
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

      if (Widget.class.isAssignableFrom(clazz)) {
        // create or get the instance
        Widget isWidget = UiBinderInstanciator.getInstance(
            (Class<? extends Widget>) clazz, attributes, resourceManager, owner);

        return DefaultUiBinderWidgetFactory.get().createUiBinderWidget(
            isWidget, attributes, parentTag, owner, resourceManager);

      } else {
        throw new GwtTestUiBinderException("Not managed type in file '"
            + owner.getClass().getSimpleName() + ".ui.xml"
            + "', only implementation of '" + Widget.class.getName()
            + "' are allowed");
      }
    } else if (UiBinderXmlUtils.isResourceTag(nameSpaceURI, localName)) {
      return resourceManager.registerResource(localName, attributes, parentTag,
          owner);
    } else if (UiBinderXmlUtils.isImportTag(nameSpaceURI, localName)) {
      return resourceManager.registerImport(attributes, parentTag, owner);
    } else if (UiBinderXmlUtils.isMsgTag(nameSpaceURI, localName)) {
      return new UiBinderMsg(currentTag);
    } else {
      return new UiBinderElement(nameSpaceURI, localName, attributes,
          parentTag, owner);
    }
  }
}
