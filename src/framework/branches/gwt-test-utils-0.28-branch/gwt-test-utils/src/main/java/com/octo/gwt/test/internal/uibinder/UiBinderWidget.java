package com.octo.gwt.test.internal.uibinder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderWidget<T extends Widget> implements UiBinderTag {

  private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("^\\{(\\w+)\\.{1}([^\\}]*)}$");

  private final Map<String, Object> attributesMap;

  private final T wrapped;

  UiBinderWidget(T wrapped, Attributes attributes, Object owner,
      UiResourceManager resourceManager) {
    this.wrapped = wrapped;
    this.attributesMap = new HashMap<String, Object>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i).trim();
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiFieldAttribute(attrUri, attrName)) {
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, this.wrapped);
      } else if (attrName.contains("tyleName")) {
        attributesMap.put(attrName,
            UiBinderUtils.getEffectiveClassName(attrValue));
      } else {
        Matcher m = ATTRIBUTE_PATTERN.matcher(attrValue);
        if (m.matches()) {
          String resourceAlias = m.group(1);
          Object resource = resourceManager.getUiResource(resourceAlias);
          if (resource == null) {
            throw new GwtTestUiBinderException("Error in file '"
                + owner.getClass().getSimpleName()
                + ".ui.xml' : no resource declared for alias '" + resourceAlias
                + "'");
          }

          // handle "alias.myCssResource.myClass"
          String[] properties = m.group(2).trim().split("\\.");
          try {
            for (String property : properties) {
              resource = GwtReflectionUtils.callPrivateMethod(resource,
                  property);
            }
          } catch (Exception e) {
            if (GwtTestException.class.isInstance(e)) {
              throw (GwtTestException) e;
            } else {
              throw new GwtTestUiBinderException(
                  "Error while calling property '"
                      + m.group(2)
                      + "' on object of type '"
                      + resourceManager.getUiResource(resourceAlias).getClass().getName()
                      + "'", e);
            }
          }
          attributesMap.put(attrName, resource);

        } else {
          attributesMap.put(attrName, attrValue);
        }
      }
    }
  }

  public final void addElement(Element element) {
    appendElement(this.wrapped, element);
  }

  protected void addWidget(T wrapped, Widget widget) {
    if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(widget);
    }
  }

  public final void addWidget(Widget widget) {
    addWidget(this.wrapped, widget);
  }

  protected void appendElement(T wrapped, Element child) {
    wrapped.getElement().appendChild(child);
  }

  public final void appendText(String data) {
    if (!"".equals(data.trim())) {
      appendText(this.wrapped, data);
    }
  }

  protected void appendText(T wrapped, String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data);
      wrapped.getElement().appendChild(text);
    }
  }

  public Object getWrapped() {
    UiBinderUtils.populateWidget(this.wrapped, attributesMap);
    return wrapped;
  }

}
