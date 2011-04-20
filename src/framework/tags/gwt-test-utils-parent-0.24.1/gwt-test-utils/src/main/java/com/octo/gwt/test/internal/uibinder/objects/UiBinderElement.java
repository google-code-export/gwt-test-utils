package com.octo.gwt.test.internal.uibinder.objects;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.uibinder.UiBinderUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderElement implements UiBinderTag {

  private final Element wrapped;

  public UiBinderElement(String nsURI, String tagName, Attributes attributes,
      Object owner) {
    this.wrapped = JavaScriptObjects.newElement(tagName);

    JavaScriptObjects.getJsoProperties(wrapped).put(
        JsoProperties.XML_NAMESPACE, nsURI);

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i).trim();
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiFieldAttribute(attrUri, attrName)) {
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, this.wrapped);
      } else if ("class".equalsIgnoreCase(attrName)) {
        this.wrapped.setAttribute("class",
            UiBinderUtils.getEffectiveClassName(attrValue));
      } else {
        this.wrapped.setAttribute(attrName, attrValue);
      }
    }
  }

  public final void addTag(UiBinderTag tag) {
    Object wrappedChild = tag.complete();
    if (Widget.class.isInstance(wrappedChild)) {
      addWidget(this.wrapped, (Widget) wrappedChild);
    } else {
      appendElement(this.wrapped, (Element) wrappedChild);
    }
  }

  public final void appendText(String data) {
    appendText(this.wrapped, data);
  }

  public Object complete() {
    return this.wrapped;
  }

  protected void addWidget(Element wrapped, Widget widget) {
    List<Widget> childWidgets = JavaScriptObjects.getJsoProperties(wrapped).getObject(
        JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

    if (childWidgets == null) {
      childWidgets = new ArrayList<Widget>();
      JavaScriptObjects.getJsoProperties(wrapped).put(
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
