package com.octo.gwt.test.internal.patchers.dom;

import javassist.CtClass;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.AutomaticPropertyContainerPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.patchers.PatchMethod.Type;

@PatchClass(InputElement.class)
public class InputElementPatcher extends AutomaticPropertyContainerPatcher {

  @PatchMethod
  public static void click(InputElement inputElement) {

  }

  @PatchMethod
  public static String getValue(InputElement inputElement) {
    PropertyContainer attributes = PropertyContainerUtils.getProperty(
        inputElement, ElementPatcher.PROPERTY_MAP_FIELD);
    String value = (String) attributes.get("value");
    return (value != null) ? value : "";
  }

  @PatchMethod
  public static void select(InputElement inputElement) {

  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String useMap() {
    return PropertyContainerUtils.getCodeGetProperty("this", "UserMap",
        CtClass.booleanType);
  }

}
