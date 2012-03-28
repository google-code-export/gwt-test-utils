package com.googlecode.gwt.test.internal.patchers.dom;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.googlecode.gwt.test.internal.utils.GwtStringUtils;
import com.googlecode.gwt.test.internal.utils.StyleUtils;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(Style.class)
class StylePatcher {

  // map initialized with default style values
  private static final Map<String, String> DEFAULT_STYLE_VALUES = new HashMap<String, String>() {

    private static final long serialVersionUID = 1L;

    {
      put("whiteSpace", "nowrap");
    }
  };
  private static final String STYLE_BORDER_BOTTOM_WIDTH = "border-bottom-width";
  private static final String STYLE_BORDER_LEFT_WIDTH = "border-left-width";
  private static final String STYLE_BORDER_RIGHT_WIDTH = "border-right-width";

  private static final String STYLE_BORDER_TOP_WIDTH = "border-top-width";

  @PatchMethod
  static void clearBorderWidth(Style style) {
    StyleUtils.getProperties(style).remove(STYLE_BORDER_BOTTOM_WIDTH);
    StyleUtils.getProperties(style).remove(STYLE_BORDER_LEFT_WIDTH);
    StyleUtils.getProperties(style).remove(STYLE_BORDER_RIGHT_WIDTH);
    StyleUtils.getProperties(style).remove(STYLE_BORDER_TOP_WIDTH);
  }

  @PatchMethod
  static void clearFloat(Style style) {
    StyleUtils.getProperties(style).remove("float");
  }

  @PatchMethod
  static String getBorderWidth(Style style) {
    return getPropertyImpl(style, STYLE_BORDER_TOP_WIDTH);
  }

  @PatchMethod
  static String getPropertyImpl(Style style, String propertyName) {
    String value = StyleUtils.getProperties(style).get(propertyName);

    if (value == null) {
      String defaultValue = DEFAULT_STYLE_VALUES.get(propertyName);
      value = defaultValue != null ? defaultValue : "";
    }

    return value;
  }

  @PatchMethod
  static void setBorderWidth(Style style, double value, Unit unit) {
    double modulo = value % 1;
    String completeValue = modulo == 0 ? Integer.toString((int) value)
        + unit.getType() : Double.toString(value) + unit.getType();
    setPropertyImpl(style, STYLE_BORDER_BOTTOM_WIDTH, completeValue);
    setPropertyImpl(style, STYLE_BORDER_LEFT_WIDTH, completeValue);
    setPropertyImpl(style, STYLE_BORDER_RIGHT_WIDTH, completeValue);
    setPropertyImpl(style, STYLE_BORDER_TOP_WIDTH, completeValue);
  }

  @PatchMethod
  static void setFloat(Style style, Float value) {
    setPropertyImpl(style, "float", value.getCssName());
  }

  @PatchMethod
  static void setPropertyImpl(Style style, String propertyName,
      String propertyValue) {
    // treat case when propertyValue = "250.0px" => "250px" instead
    propertyValue = GwtStringUtils.treatDoubleValue(propertyValue);

    LinkedHashMap<String, String> styleProperties = StyleUtils.getProperties(style);

    // ensure the style will be added at the end of the LinkedHashMap
    styleProperties.remove(propertyName);
    styleProperties.put(propertyName, propertyValue);
  }

}
