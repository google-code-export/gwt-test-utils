package com.octo.gwt.test.uibinder;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handles <ui:msg /> tags.
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

  public void addWidget(Widget isWidget) {
    parentTag.addWidget(isWidget);

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
