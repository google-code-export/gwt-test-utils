package com.octo.gwt.test.internal.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

interface UiBinderTag {

  public void addElement(Element element);

  public void addWidget(Widget widget);

  public void appendText(String text);

  public UiBinderTag getParentTag();

  public Object getWrapped();

}
