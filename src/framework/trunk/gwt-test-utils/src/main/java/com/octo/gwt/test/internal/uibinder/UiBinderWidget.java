package com.octo.gwt.test.internal.uibinder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderWidget<T extends Widget> implements UiBinderTag {

  private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("^\\{(\\w+)\\.{1}([^\\}]*)}$");

  private final Map<String, Object> attributesMap;
  private final Object owner;
  private final T wrapped;

  public UiBinderWidget(T wrapped, Attributes attributes, Object owner,
      Map<String, Object> resources) {
    this.wrapped = wrapped;
    this.owner = owner;
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
          Object resource = resources.get(resourceAlias);
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
                  "Error while calling property '" + m.group(2)
                      + "' on object of type '"
                      + resources.get(resourceAlias).getClass().getName() + "'",
                  e);
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
    appendElement(wrapped, element);
  }

  public final void addWidget(Widget widget) {
    addWidget(wrapped, widget);
  }

  public final void appendText(String data) {
    appendText(wrapped, data);
  }

  public Object getWrapped() {

    try {
      BeanUtils.populate(this.wrapped, attributesMap);
    } catch (Exception e) {
      throw new ReflectionException("Error while setting properties for '"
          + this.wrapped.getClass().getSimpleName() + "' in '"
          + owner.getClass().getSimpleName() + ".ui.xml'", e);
    }

    return wrapped;
  }

  protected void addWidget(T wrapped, Widget widget) {
    if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(widget);
    }
  }

  protected void appendElement(T wrapped, Element child) {
    wrapped.getElement().appendChild(child);
  }

  protected void appendText(T wrapped, String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data);
      wrapped.getElement().appendChild(text);
    }
  }

}
