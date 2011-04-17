package com.octo.gwt.test.internal.uibinder.objects;

import org.xml.sax.Attributes;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.uibinder.UiBinderUtils;
import com.octo.gwt.test.utils.FastStack;

@SuppressWarnings("unchecked")
public class UiBinderComponentBuilder<T> {

  public static final <T> UiBinderComponentBuilder<T> create(
      Class<T> rootComponentClass, Object owner) {
    return new UiBinderComponentBuilder<T>(rootComponentClass, owner);
  }

  private final Object owner;

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
      throw new GwtTestPatchException(
          "Cannot build UiBinder component while the parsing of '"
              + owner.getClass().getSimpleName() + ".ui.xml' is not finished");

    } else if (!rootComponentClass.isInstance(rootComponent)) {
      throw new GwtTestConfigurationException(
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
    if (!shouldIgnoreTag(nameSpaceURI, localName)) {
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
      if (UiBinderUtils.isUiBinderResource(nameSpaceURI, localName)) {
        // TODO: handle
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
        throw new GwtTestConfigurationException("Cannot find class '"
            + className + "' declared in file '"
            + owner.getClass().getSimpleName() + ".ui.xml");
      }

      if (HTMLPanel.class == clazz) {
        return new UiBinderHTMLPanel(attributes, owner);
      } else if (Widget.class.isAssignableFrom(clazz)) {
        return new UiBinderWidget<Widget>((Class<Widget>) clazz, attributes,
            owner);
      } else {
        throw new GwtTestConfigurationException("Not managed type in file '"
            + owner.getClass().getSimpleName() + ".ui.xml"
            + "', only subclass of '" + Widget.class.getName()
            + "' are managed");
      }
    } else {
      return new UiBinderElement(nameSpaceURI, localName, attributes, owner);
    }
  }

  private boolean shouldIgnoreTag(String nameSpaceURI, String localName) {
    return "style".equals(localName)
        || UiBinderUtils.isUiBinderTag(nameSpaceURI, localName);
  }

}
