package com.octo.gwt.test.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Base interface for a XML tag in a .ui.xml file.
 * 
 * @author Gael Lazzari
 * 
 */
public interface UiTag {

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
  void addWidget(IsWidget widget);

  /**
   * Append text to this UiBinder tag.
   * 
   * @param text The text to append
   */
  void appendText(String text);

  /**
   * Callback method called when the UiBinder tag is closed, so implementation
   * could apply some custom configuration if necessary.
   * 
   * @return The UiBinder tag's wrapped object (Widget, Resource, DOM
   *         element...)
   */
  Object endTag();

  /**
   * Get the parent UiBinder tag.
   * 
   * @return The parent UiBinder tag
   */
  UiTag getParentTag();

}
