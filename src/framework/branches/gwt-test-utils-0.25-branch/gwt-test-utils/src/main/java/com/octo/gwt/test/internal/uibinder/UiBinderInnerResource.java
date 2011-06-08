package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.utils.resources.ResourcePrototypeProxyBuilder;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderInnerResource implements UiBinderTag {

  private final String alias;
  private final ResourcePrototypeProxyBuilder builder;
  private final Object owner;
  private final Map<String, Object> resources;
  private final StringBuilder text;
  private Object wrappedObject;

  UiBinderInnerResource(ResourcePrototypeProxyBuilder builder, String alias,
      Object owner, Map<String, Object> resources) {
    this.builder = builder;
    this.owner = owner;
    this.alias = alias;
    this.resources = resources;
    this.text = new StringBuilder();
  }

  public void addElement(Element element) {
    // nothing to do
  }

  public void addWidget(Widget widget) {
    // nothing to do
  }

  public void appendText(String text) {
    this.text.append(text.trim());
  }

  public Object getWrapped() {
    if (wrappedObject == null) {
      wrappedObject = builder.text(text.toString()).build();

      // set the corresponding @UiField
      Field resourceUiField = getUniqueUiField(alias);
      if (resourceUiField != null) {
        try {
          resourceUiField.set(owner, wrappedObject);
        } catch (Exception e) {
          throw new ReflectionException(e);
        }
      }

      // register the built resource to the resourceManager inner map
      resources.put(alias, wrappedObject);

    }

    return wrappedObject;
  }

  private Field getUniqueUiField(String alias) {
    Set<Field> resourceFields = GwtReflectionUtils.getFields(owner.getClass());
    if (resourceFields.size() == 0) {
      return null;
    }

    Field result = null;

    for (Field f : resourceFields) {
      if (alias.equals(f.getName()) && f.isAnnotationPresent(UiField.class)) {
        if (result != null) {
          throw new GwtTestUiBinderException("There are more than one '"
              + f.getName() + "' @UiField in class '"
              + owner.getClass().getName() + "' or its superclass");
        }

        result = f;
      }
    }

    return result;
  }
}
