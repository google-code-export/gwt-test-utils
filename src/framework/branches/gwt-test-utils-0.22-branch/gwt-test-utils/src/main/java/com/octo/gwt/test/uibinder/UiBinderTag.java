package com.octo.gwt.test.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base interface for a XML tag in a .ui.xml file.
 * 
 * @author Gael Lazzari
 * 
 */
public interface UiBinderTag {

  /**
   * Appends a DOM child element to this UiBinder tag.
   * 
   * @param element The element to append
   */
  void addElement(Element element);

  /**
   * Adds a child widget to this UiBinder tag.
   * 
   * @param widget The widget to add
   */
  void addWidget(Widget widget);

  /**
   * Append text to this UiBinder tag.
   * 
   * @param text The text to append
   */
  void appendText(String text);

  /**
   * Get the parent UiBinder tag.
   * 
   * @return The parent UiBinder tag
   */
  UiBinderTag getParentTag();

  /**
   * Get the Object (Widget, Resource, DOM element...) wrapped by the UiBinder
   * tag.
   * 
   * @return The wrapped object
   */
  Object getWrapped();

}
