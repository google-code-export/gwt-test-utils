package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(InputElement.class)
public class InputElementPatcher extends OverlayPatcher {

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

}
