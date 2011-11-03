package com.octo.gwt.test.uibinder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * 
 * Handles <ui:import> tags.
 * 
 * @author Gael Lazzari
 * 
 */
class UiBinderImportTag implements UiBinderTag {

  private final UiBinderTag parentTag;

  UiBinderImportTag(Attributes attributes, UiBinderTag parentTag,
      Map<String, Object> resources, Object owner) {
    this.parentTag = parentTag;
    // collects single and multiple imports in UiBinderResourceManager inner map
    collectObjectToImport(attributes, resources, owner);
  }

  public void addElement(Element element) {
    // nothing to do
  }

  public void addWidget(Widget widget) {
    // nothing to do
  }

  public void appendText(String text) {
    // nothing to do
  }

  public Object endTag() {
    // the result will not be used by UiBinderTagBuilder
    return null;
  }

  public UiBinderTag getParentTag() {
    return parentTag;
  }

  private void collectMultipleImports(String importValue,
      Map<String, Object> resources, Object owner) {

    try {
      String className = importValue.substring(0, importValue.lastIndexOf('.'));

      Class<?> clazz = GwtReflectionUtils.getClass(className);

      // this code handles classes and enums fine
      for (Field field : GwtReflectionUtils.getFields(clazz)) {
        if (Modifier.isStatic(field.getModifiers())
            && !Modifier.isPrivate(field.getModifiers())
            && !Modifier.isProtected(field.getModifiers())) {
          // register static field value in UiResourcesManager inner map
          Object value = GwtReflectionUtils.getStaticFieldValue(clazz,
              field.getName());
          resources.put(field.getName(), value);
        }
      }

    } catch (Exception e) {
      throw new GwtTestUiBinderException(
          "Error while trying to import multiple ui fields '" + importValue
              + "'", e);
    }
  }

  private Map<String, Object> collectObjectToImport(Attributes attributes,
      Map<String, Object> resources, Object owner) {
    Map<String, Object> result = new HashMap<String, Object>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i).trim();

      // ignore attributes other than <ui:field>
      if (!UiBinderXmlUtils.FIELD_ATTR_NAME.equals(attrName)) {
        continue;
      }

      // ignore empty attributes
      if (attrValue.length() == 0) {
        continue;
      }

      int token = attrValue.lastIndexOf('.');
      if (token > -1 && token < attrValue.length() - 1
          && attrValue.substring(token).equals(".*")) {
        // case of multiple import
        collectMultipleImports(attrValue, resources, owner);

      } else {
        // case of single import
        collectSingleImport(attrValue, resources, owner);
      }
    }

    return result;
  }

  private void collectSingleImport(String importValue,
      Map<String, Object> resources, Object owner) {
    try {
      int token = importValue.lastIndexOf('.');
      String className = importValue.substring(0, token);
      Class<?> clazz = GwtReflectionUtils.getClass(className);
      String fieldName = importValue.substring(token + 1);

      Object objectToImport = GwtReflectionUtils.getStaticFieldValue(clazz,
          fieldName);

      // register static field value in UiResourcesManager inner map
      resources.put(fieldName, objectToImport);

    } catch (Exception e) {
      throw new GwtTestUiBinderException(
          "Error while trying to import ui field '" + importValue + "'", e);
    }
  }
}
