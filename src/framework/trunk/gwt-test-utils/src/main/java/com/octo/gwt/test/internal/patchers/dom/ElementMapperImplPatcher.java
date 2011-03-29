package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(ElementMapperImpl.class)
public class ElementMapperImplPatcher extends AutomaticPatcher {

  private static final String widgetId = "__uiObjectID";

  @PatchMethod
  public static void clearIndex(Element e) {
    e.setPropertyString(widgetId, null);
  }

  @PatchMethod
  public static int getIndex(Element e) {
    String index = e.getPropertyString(widgetId);
    return index == null ? -1 : Integer.parseInt(index);
  }

  @PatchMethod
  public static void setIndex(Element e, int index) {
    e.setPropertyString(widgetId, Integer.toString(index));
  }

}
