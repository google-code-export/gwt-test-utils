package com.octo.gwt.test.uibinder;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.resources.ResourcePrototypeProxyBuilder;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Base class for resource tags : <ui:style />, <ui:image /> and <ui:data />
 * 
 * @author Gael Lazzari
 * 
 */
abstract class UiBinderResourceTag implements UiBinderTag {

  private final String alias;
  private final ResourcePrototypeProxyBuilder builder;
  private final Object owner;
  private final UiBinderTag parentTag;
  private final Map<String, Object> resources;
  private Object wrappedObject;

  UiBinderResourceTag(ResourcePrototypeProxyBuilder builder, String alias,
      UiBinderTag parentTag, Object owner, Map<String, Object> resources) {
    this.builder = builder;
    this.owner = owner;
    this.parentTag = parentTag;
    this.alias = alias;
    this.resources = resources;
  }

  public void addElement(Element element) {
    // adapter method
  }

  public void addWidget(IsWidget widget) {
    // adapter method
  }

  public void appendText(String text) {
    // adapter method
  }

  public UiBinderTag getParentTag() {
    return parentTag;
  }

  public Object getWrapped() {
    if (wrappedObject == null) {
      // delegate the creation to subclasses
      wrappedObject = buildObject(builder);

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

  protected abstract Object buildObject(ResourcePrototypeProxyBuilder builder);

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
