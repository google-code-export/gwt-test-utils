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
    JavaScriptObjects.setProperty(e, widgetId, null);
    e.setPropertyString(widgetId, null);
  }

  @PatchMethod
  public static int getIndex(Element e) {
    String index = JavaScriptObjects.getObject(e, widgetId);
    return index == null ? -1 : Integer.parseInt(index);
  }

  @PatchMethod
  public static void setIndex(Element e, int index) {
    JavaScriptObjects.setProperty(e, widgetId, Integer.toString(index));
  }

}
