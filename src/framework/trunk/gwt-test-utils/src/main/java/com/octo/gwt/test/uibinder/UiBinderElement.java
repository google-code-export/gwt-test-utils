package com.octo.gwt.test.uibinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Handles all HTML element tags declared in a .ui.xml file.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderElement implements UiBinderTag {

  private final UiBinderTag parentTag;
  private final Element wrapped;

  UiBinderElement(String nsURI, String tagName, Map<String, Object> attributes,
      UiBinderTag parentTag, Object owner) {
    this.wrapped = JavaScriptObjects.newElement(tagName, Document.get());
    this.parentTag = parentTag;

    JavaScriptObjects.setProperty(wrapped, JsoProperties.XML_NAMESPACE, nsURI);

    for (Map.Entry<String, Object> entry : attributes.entrySet()) {
      String attrName = entry.getKey();
      String attrValue = entry.getValue().toString().trim();

      if ("ui:field".equals(attrName)) {
        try {
          GwtReflectionUtils.setPrivateFieldValue(owner, attrValue,
              this.wrapped);
        } catch (ReflectionException e) {
          // ui:field has no corresponding @UiField declared : just ignore it
        }
      } else if ("class".equalsIgnoreCase(attrName)) {
        this.wrapped.setAttribute("class",
            UiBinderXmlUtils.getEffectiveStyleName(attrValue));
      } else {
        this.wrapped.setAttribute(attrName, attrValue);
      }
    }
  }

  public final void addElement(Element element) {
    appendElement(wrapped, element);

  }

  public void addWidget(IsWidget widget) {
    addWidget(this.wrapped, widget);

  }

  public final void appendText(String data) {
    if (!"".equals(data.trim())) {
      appendText(this.wrapped, data);
    }
  }

  public Object endTag() {
    return this.wrapped;
  }

  public UiBinderTag getParentTag() {
    return parentTag;
  }

  protected void addWidget(Element wrapped, IsWidget isWidget) {
    List<IsWidget> childWidgets = JavaScriptObjects.getObject(wrapped,
        JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

    if (childWidgets == null) {
      childWidgets = new ArrayList<IsWidget>();
      JavaScriptObjects.setProperty(wrapped,
          JsoProperties.UIBINDER_CHILD_WIDGETS_LIST, childWidgets);
    }

    childWidgets.add(isWidget);
    appendElement(wrapped, isWidget.asWidget().getElement());
  }

  protected void appendElement(Element wrapped, Element child) {
    wrapped.appendChild(child);
  }

  protected void appendText(Element wrapped, String data) {
    Text text = JavaScriptObjects.newText(data, wrapped.getOwnerDocument());
    wrapped.appendChild(text);
  }

}
