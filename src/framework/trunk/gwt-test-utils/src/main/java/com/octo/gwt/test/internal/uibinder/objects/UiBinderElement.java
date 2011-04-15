package com.octo.gwt.test.internal.uibinder.objects;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.uibinder.UiBinderUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderElement implements UiBinderTag {

  private final Element wrapped;

  public UiBinderElement(String tagName, Attributes attributes, Object owner) {
    wrapped = JavaScriptObjects.newElement(tagName);

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i);
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiBinderField(attrUri, attrName)) {
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, wrapped);
      } else {
        wrapped.setAttribute(attrName, attrValue);
      }
    }
  }

  public void addTag(UiBinderTag tag) {
    Object wrappedChild = tag.complete();
    if (Widget.class.isInstance(wrappedChild)) {
      addWidget((Widget) wrappedChild);
    } else {
      appendElement((Element) wrappedChild);
    }
  }

  public void appendText(String data) {
    Text text = JavaScriptObjects.newText(data);
    wrapped.appendChild(text);
  }

  public Object complete() {
    return wrapped;
  }

  protected void addWidget(Widget widget) {
    wrapped.appendChild(widget.getElement());
  }

  protected void appendElement(Element child) {
    wrapped.appendChild(child);
  }

}
