package com.octo.gwt.test.internal.patchers.dom;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.StyleUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Style.class)
public class StylePatcher extends OverlayPatcher {

  @PatchMethod
  public static void clearBorderWidth(Style style) {
    JavaScriptObjects.getJsoProperties(style).remove("border-top-width");
    JavaScriptObjects.getJsoProperties(style).remove("border-right-width");
    JavaScriptObjects.getJsoProperties(style).remove("border-bottom-width");
    JavaScriptObjects.getJsoProperties(style).remove("border-left-width");
  }

  @PatchMethod
  public static void clearFloat(Style style) {
    setPropertyImpl(style, "float", "");
  }

  @PatchMethod
  public static String getBorderWidth(Style style) {
    return getPropertyImpl(style, "border-top-width");
  }

  @PatchMethod
  public static String getPropertyImpl(Style style, String propertyName) {
    return JavaScriptObjects.getJsoProperties(style).getString(propertyName);
  }

  @PatchMethod
  public static void setBorderWidth(Style style, double value, Unit unit) {
    PropertyContainer pc = JavaScriptObjects.getJsoProperties(style);
    double modulo = value % 1;
    String completeValue = (modulo == 0) ? Integer.toString((int) value)
        + unit.getType() : Double.toString(value) + unit.getType();
    pc.put("border-top-width", completeValue);
    pc.put("border-right-width", completeValue);
    pc.put("border-bottom-width", completeValue);
    pc.put("border-left-width", completeValue);
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

    JavaScriptObjects.getJsoProperties(style).put(propertyName, propertyValue);

    Element owner = StyleUtils.getOwnerElement(style);
    String styleAttribute = owner.getAttribute("style");

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
    owner.setAttribute("style", styleValue);
  }

}
