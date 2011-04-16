package com.octo.gwt.test.internal.uibinder.objects;

import org.xml.sax.Attributes;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;

@SuppressWarnings("unchecked")
public class UiBinderTagFactory {

  private final Object owner;

  public UiBinderTagFactory(Object owner) {
    this.owner = owner;
  }

  public UiBinderTag createUiBinderTag(String nameSpaceURI, String localName,
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

}
