package com.octo.gwt.test.internal.uibinder.objects;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.uibinder.UiBinderUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderWidget<T extends Widget> implements UiBinderTag {

  private final Map<String, String> attributesMap;
  private final Object owner;
  private final T wrapped;

  public UiBinderWidget(Class<T> clazz, Attributes attributes, Object owner) {
    this.wrapped = instanciate(clazz);
    this.owner = owner;
    this.attributesMap = new HashMap<String, String>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i);
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiBinderField(attrUri, attrName)) {
        GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, wrapped);
      } else {
        attributesMap.put(attrName, attrValue);
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
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data);
      wrapped.getElement().appendChild(text);
    }
  }

  public Object complete() {

    try {
      BeanUtils.populate(wrapped, attributesMap);
    } catch (Exception e) {
      throw new ReflectionException("Error while setting properties for '"
          + wrapped.getClass().getSimpleName() + "' in '"
          + owner.getClass().getSimpleName() + ".ui.xml'", e);
    }

    return wrapped;
  }

  protected void addWidget(Widget widget) {
    if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(widget);
    }
  }

  protected void appendElement(Element child) {
    wrapped.getElement().appendChild(child);
  }

  protected T instanciate(Class<T> clazz) {
    return GwtReflectionUtils.instantiateClass(clazz);
  }

}
