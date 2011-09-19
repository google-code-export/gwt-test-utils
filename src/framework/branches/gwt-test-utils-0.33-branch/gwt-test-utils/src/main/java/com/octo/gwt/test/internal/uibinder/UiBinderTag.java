package com.octo.gwt.test.internal.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

interface UiBinderTag {

  public void addElement(Element element);

  public void addWidget(IsWidget widget);

  public void appendText(String text);

  public UiBinderTag getParentTag();

  public Object getWrapped();

}
