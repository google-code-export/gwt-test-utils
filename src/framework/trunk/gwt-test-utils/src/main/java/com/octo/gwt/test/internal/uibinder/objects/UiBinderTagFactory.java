package com.octo.gwt.test.internal.uibinder.objects;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@SuppressWarnings("unchecked")
public class UiBinderTagFactory {

  private final Object owner;

  public UiBinderTagFactory(Object owner) {
    this.owner = owner;
  }

  public UiBinderTag createUiBinderTag(Class<?> rootComponentClass) {
    if (Element.class.isAssignableFrom(rootComponentClass)) {
      String tagName = GwtReflectionUtils.getStaticFieldValue(
          rootComponentClass, "TAG");
      return new UiBinderElement(tagName, new AttributesImpl(), owner);
    } else if (HTMLPanel.class == rootComponentClass) {
      return new UiBinderHTMLPanel();
    } else if (Widget.class.isAssignableFrom(rootComponentClass)) {
      return new UiBinderWidget<Widget>((Class<Widget>) rootComponentClass);
    } else {
      throw new GwtTestConfigurationException("Not managed type in file '"
          + owner.getClass().getSimpleName() + ".ui.xml"
          + "', only subclass of '" + Widget.class.getName() + "' or of '"
          + Element.class.getName() + "' are managed");
    }
  }

  public UiBinderTag createUiBinderTag(String nameSpaceURI,
      String localName, Attributes attributes) {

    int i = nameSpaceURI.lastIndexOf(':');
    if (i > 0) {

      String packageName = nameSpaceURI.substring(i + 1);

      String className = packageName + "." + localName;
      try {
        Class<?> clazz = Class.forName(className);

        if (HTMLPanel.class == clazz) {
          return new UiBinderHTMLPanel();
        } else if (Widget.class.isAssignableFrom(clazz)) {
          return new UiBinderWidget<Widget>((Class<Widget>) clazz);
        } else {
          throw new GwtTestConfigurationException("Not managed type in file '"
              + owner.getClass().getSimpleName() + ".ui.xml"
              + "', only subclass of '" + Widget.class.getName()
              + "' are managed");
        }

      } catch (ClassNotFoundException e) {
        // specific element, to be handle by the parent widget
        return new UiBinderElement(localName, attributes, owner);
      }
    } else {
      return new UiBinderElement(localName, attributes, owner);
    }
  }

}
