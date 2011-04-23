package com.octo.gwt.test.internal.uibinder.objects;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public interface UiBinderTag {

  public void addElement(Element element);

  public void addWidget(Widget widget);

  public void appendText(String text);

  public Object getWrapped();
}
