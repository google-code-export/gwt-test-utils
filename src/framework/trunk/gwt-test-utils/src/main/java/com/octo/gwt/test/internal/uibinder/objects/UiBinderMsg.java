package com.octo.gwt.test.internal.uibinder.objects;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class UiBinderMsg implements UiBinderTag {

  private final UiBinderTag parent;
  private final StringBuilder sb;

  public UiBinderMsg(UiBinderTag parent) {
    this.parent = parent;
    sb = new StringBuilder();
  }

  public void addElement(Element element) {
    sb.append("<").append(element.getTagName());
    // TODO : append attributes
    sb.append(">").append(element.getInnerText()).append("</").append(
        element.getTagName()).append(">");
  }

  public void addWidget(Widget widget) {
    parent.addWidget(widget);

  }

  public void appendText(String data) {
    sb.append(data);
  }

  public Object getWrapped() {
    return sb.toString();
  }
}
