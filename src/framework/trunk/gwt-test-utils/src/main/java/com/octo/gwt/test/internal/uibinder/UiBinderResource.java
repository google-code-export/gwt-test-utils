package com.octo.gwt.test.internal.uibinder;

import java.lang.reflect.Field;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

class UiBinderResource implements UiBinderTag {

  private final Object owner;
  private final Object wrapped;

  UiBinderResource() {
    this.wrapped = null;
    this.owner = null;
  }

  UiBinderResource(Object wrapped, String alias, Object owner) {
    this.wrapped = wrapped;
    this.owner = owner;

    Field resourceUiField = getUniqueUiField(alias);
    if (resourceUiField != null) {
      try {
        resourceUiField.set(owner, wrapped);
      } catch (Exception e) {
        throw new ReflectionException(e);
      }
    }
  }

  public void addElement(Element element) {
    // nothing to do
  }

  public void addWidget(Widget widget) {
    // nothing to do
  }

  public void appendText(String text) {
    // TODO Auto-generated method stub
  }

  public Object getWrapped() {
    return this.wrapped;
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
