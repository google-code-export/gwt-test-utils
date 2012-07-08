package com.googlecode.gwt.test.internal.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.dom.client.Style;

/**
 * 
 * Some {@link Style} utility methods. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class StyleUtils {

  private static final Pattern STYLE_PATTERN = Pattern.compile("(.+):(.+)");

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

  public static void overrideStyle(Style target, String newValue) {
    for (Map.Entry<String, String> entry : getStyleProperties(newValue).entrySet()) {
      target.setProperty(GwtStringUtils.camelize(entry.getKey()),
          entry.getValue());
    }
  }

  private StyleUtils() {

  }

}
