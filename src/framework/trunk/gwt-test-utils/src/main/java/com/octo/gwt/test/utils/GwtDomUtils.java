package com.octo.gwt.test.utils;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;

public class GwtDomUtils {

  public static void setClientWidth(Element element, int width) {
    JavaScriptObjects.setProperty(element, JsoProperties.ELEMENT_CLIENT_WIDTH,
        width);
  }

  public static void setClientHeight(Element element, int height) {
    JavaScriptObjects.setProperty(element, JsoProperties.ELEMENT_CLIENT_HEIGHT,
        height);
  }

}
