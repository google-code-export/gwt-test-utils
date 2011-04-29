package com.octo.gwt.test.internal.patchers.dom;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.StyleUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Style.class)
public class StylePatcher extends OverlayPatcher {

  @PatchMethod
  public static void clearBorderWidth(Style style) {
    JavaScriptObjects.remove(style, JsoProperties.STYLE_BORDER_BOTTOM_WIDTH);
    JavaScriptObjects.remove(style, JsoProperties.STYLE_BORDER_LEFT_WIDTH);
    JavaScriptObjects.remove(style, JsoProperties.STYLE_BORDER_RIGHT_WIDTH);
    JavaScriptObjects.remove(style, JsoProperties.STYLE_BORDER_TOP_WIDTH);
  }

  @PatchMethod
  public static void clearFloat(Style style) {
    setPropertyImpl(style, "float", "");
  }

  @PatchMethod
  public static String getBorderWidth(Style style) {
    return getPropertyImpl(style, JsoProperties.STYLE_BORDER_TOP_WIDTH);
  }

  @PatchMethod
  public static String getPropertyImpl(Style style, String propertyName) {
    return JavaScriptObjects.getString(style, propertyName);
  }

  @PatchMethod
  public static void setBorderWidth(Style style, double value, Unit unit) {
    double modulo = value % 1;
    String completeValue = (modulo == 0) ? Integer.toString((int) value)
        + unit.getType() : Double.toString(value) + unit.getType();
    JavaScriptObjects.setProperty(style,
        JsoProperties.STYLE_BORDER_BOTTOM_WIDTH, completeValue);
    JavaScriptObjects.setProperty(style, JsoProperties.STYLE_BORDER_LEFT_WIDTH,
        completeValue);
    JavaScriptObjects.setProperty(style,
        JsoProperties.STYLE_BORDER_RIGHT_WIDTH, completeValue);
    JavaScriptObjects.setProperty(style, JsoProperties.STYLE_BORDER_TOP_WIDTH,
        completeValue);
  }

  @PatchMethod
  public static void setFloat(Style style, Float value) {
    setPropertyImpl(style, "float", value.getCssName());
  }

  @PatchMethod
  public static void setPropertyImpl(Style style, String propertyName,
      String propertyValue) {
    // treat case when propertyValue = "250.0px" => "250px" instead
    propertyValue = GwtStringUtils.treatDoubleValue(propertyValue);

    JavaScriptObjects.setProperty(style, propertyName, propertyValue);

    Element owner = StyleUtils.getOwnerElement(style);
    String styleAttribute = owner.getAttribute(JsoProperties.ELEM_STYLE);

    LinkedHashMap<String, String> styleProperties = StyleUtils.getStyleProperties(styleAttribute);

    String cssProperyName = GwtStringUtils.hyphenize(propertyName);

    if (styleProperties.containsKey(cssProperyName)) {
      styleProperties.remove(cssProperyName);
    }

    if (propertyValue != null && propertyValue.trim().length() > 0) {
      styleProperties.put(cssProperyName, propertyValue.trim());
    }

    StringBuilder sb = new StringBuilder();

    for (Map.Entry<String, String> entry : styleProperties.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(
          "; ");
    }

    String styleValue = (sb.length() > 0) ? sb.toString().substring(0,
        sb.length() - 1) : "";
    owner.setAttribute(JsoProperties.ELEM_STYLE, styleValue);
  }

}
