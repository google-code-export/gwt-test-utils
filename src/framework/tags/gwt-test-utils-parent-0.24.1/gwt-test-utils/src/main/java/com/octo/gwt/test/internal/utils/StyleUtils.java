package com.octo.gwt.test.internal.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;

public class StyleUtils {

  private static final Pattern STYLE_PATTERN = Pattern.compile("(.+):(.+)");

  public static Element getOwnerElement(Style style) {
    return JavaScriptObjects.getJsoProperties(style).getObject(
        JsoProperties.STYLE_TARGET_ELEMENT);
  }

  public static LinkedHashMap<String, String> getStyleProperties(String style) {
    LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

    if (style == null || style.trim().length() == 0) {
      return result;
    }

    String[] styles = style.split("\\s*;\\s*");
    for (String property : styles) {
      Matcher m = STYLE_PATTERN.matcher(property);
      if (m.matches()) {
        result.put(m.group(1).trim(), m.group(2).trim());
      }
    }

    return result;
  }

  public static void setStyle(Style target, String value) {
    for (Map.Entry<String, String> entry : getStyleProperties(value).entrySet()) {
      target.setProperty(GwtStringUtils.camelize(entry.getKey()),
          entry.getValue());
    }
  }

  private StyleUtils() {

  }

}
