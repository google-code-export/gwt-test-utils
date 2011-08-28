package com.octo.gwt.test.internal.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handle <ui:msg> tag.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderMsg implements UiBinderTag {

  private final UiBinderTag parentTag;
  private final StringBuilder sb;

  public UiBinderMsg(UiBinderTag parent) {
    this.parentTag = parent;
    sb = new StringBuilder();
  }

  public void addElement(Element element) {
    sb.append("<").append(element.getTagName());
    // TODO : append attributes
    sb.append(">").append(element.getInnerText()).append("</").append(
        element.getTagName()).append(">");
  }

  public void addWidget(Widget widget) {
    parentTag.addWidget(widget);

  }

  public void appendText(String data) {
    sb.append(data);
  }

  public UiBinderTag getParentTag() {
    return parentTag;
  }

  public Object getWrapped() {
    return sb.toString();
  }
}
