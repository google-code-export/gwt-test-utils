package com.octo.gwt.test.internal.uibinder;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderElement implements UiBinderTag {

  private final UiBinderTag parentTag;
  private final Element wrapped;

  UiBinderElement(String nsURI, String tagName, Attributes attributes,
      UiBinderTag parentTag, Object owner) {
    this.wrapped = JavaScriptObjects.newElement(tagName);
    this.parentTag = parentTag;

    JavaScriptObjects.setProperty(wrapped, JsoProperties.XML_NAMESPACE, nsURI);

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i).trim();
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiFieldAttribute(attrUri, attrName)) {
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, this.wrapped);
      } else if ("class".equalsIgnoreCase(attrName)) {
        this.wrapped.setAttribute("class",
            UiBinderUtils.getEffectiveStyleName(attrValue));
      } else {
        this.wrapped.setAttribute(attrName, attrValue);
      }
    }
  }

  public final void addElement(Element element) {
    appendElement(wrapped, element);

  }

  public void addWidget(Widget widget) {
    addWidget(this.wrapped, widget);

  }

  public final void appendText(String data) {
    if (!"".equals(data.trim())) {
      appendText(this.wrapped, data);
    }
  }

  public UiBinderTag getParentTag() {
    return parentTag;
  }

  public Object getWrapped() {
    return this.wrapped;
  }

  protected void addWidget(Element wrapped, Widget widget) {
    List<Widget> childWidgets = JavaScriptObjects.getObject(wrapped,
        JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

    if (childWidgets == null) {
      childWidgets = new ArrayList<Widget>();
      JavaScriptObjects.setProperty(wrapped,
          JsoProperties.UIBINDER_CHILD_WIDGETS_LIST, childWidgets);
    }

    childWidgets.add(widget);
    appendElement(wrapped, widget.getElement());
  }

  protected void appendElement(Element wrapped, Element child) {
    wrapped.appendChild(child);
  }

  protected void appendText(Element wrapped, String data) {
    Text text = JavaScriptObjects.newText(data);
    wrapped.appendChild(text);
  }

}
