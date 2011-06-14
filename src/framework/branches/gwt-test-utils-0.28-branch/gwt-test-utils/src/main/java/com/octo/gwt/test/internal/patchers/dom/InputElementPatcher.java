package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(InputElement.class)
class InputElementPatcher {

  @PatchMethod
  static void click(InputElement inputElement) {

  }

  @PatchMethod
  static String getName(InputElement inputElement) {
    PropertyContainer properties = JavaScriptObjects.getObject(inputElement,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.ELEM_NAME);
  }

  @PatchMethod
  static String getType(InputElement inputElement) {
    PropertyContainer properties = JavaScriptObjects.getObject(inputElement,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.ELEM_TYPE);
  }

  @PatchMethod
  static String getValue(InputElement inputElement) {
    PropertyContainer properties = JavaScriptObjects.getObject(inputElement,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.INPUT_ELEM_VALUE);
  }

  @PatchMethod
  static void select(InputElement inputElement) {

  }

  @PatchMethod
  static void setName(InputElement inputElement, String name) {
    PropertyContainer properties = JavaScriptObjects.getObject(inputElement,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(JsoProperties.ELEM_NAME, name);
  }

}
