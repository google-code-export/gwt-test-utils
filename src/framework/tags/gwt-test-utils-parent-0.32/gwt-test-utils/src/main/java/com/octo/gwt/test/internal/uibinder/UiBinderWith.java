package com.octo.gwt.test.internal.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handle <ui:with> tag
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderWith implements UiBinderTag {

  private final Object with;

  public UiBinderWith(Object with) {
    this.with = with;
  }

  public void addElement(Element element) {
    // nothing to do
  }

  public void addWidget(Widget widget) {
    // nothing to do
  }

  public void appendText(String text) {
    // nothing to do
  }

  public UiBinderTag getParentTag() {
    // nothing to do
    return null;
  }

  public Object getWrapped() {
    return with;
  }

}
