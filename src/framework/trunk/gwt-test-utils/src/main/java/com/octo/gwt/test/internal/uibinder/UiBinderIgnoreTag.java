package com.octo.gwt.test.internal.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

class UiBinderIgnoreTag implements UiBinderTag {

  private final Object wrapped;

  public UiBinderIgnoreTag(Object wrapped) {
    this.wrapped = wrapped;
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
    return wrapped;
  }

}
