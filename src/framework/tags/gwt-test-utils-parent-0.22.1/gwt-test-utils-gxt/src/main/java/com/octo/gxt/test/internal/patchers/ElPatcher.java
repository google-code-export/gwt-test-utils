package com.octo.gxt.test.internal.patchers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.extjs.gxt.ui.client.core.El;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.internal.utils.StyleUtils;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(El.class)
public class ElPatcher {

  private static Pattern UNIT_PATTERN = Pattern.compile("^(\\d+)[(px|em|%|en|ex|pt|in|cm|mm|pc)]{0,1}$");

  @PatchMethod
  public static String addUnits(String v, String defaultUnit) {
    if (v == null)
      return "";

    v = v.replaceAll(" ", "");

    if ("undefined".equals(v))
      return "";

    Matcher m = UNIT_PATTERN.matcher(v);
    if (m.matches()) {
      String unit = (m.groupCount() == 3) ? m.group(2)
          : (defaultUnit != null && !"".equals(defaultUnit)) ? defaultUnit
              : "px";

      return m.group(1) + unit;
    }

    return v;
  }

  @PatchMethod
  public static El applyStyles(El el, String styles) {
    Element elem = getWrappedElement(el);
    LinkedHashMap<String, String> styleProperties = StyleUtils.getStyleProperties(elem.getAttribute("style"));

    for (Map.Entry<String, String> entry : styleProperties.entrySet()) {
      elem.getStyle().setProperty(entry.getKey(), entry.getValue());
    }

    return el;
  }

  @PatchMethod
  public static void disableTextSelectInternal(
      com.google.gwt.user.client.Element e, boolean disable) {

  }

  @PatchMethod
  public static boolean isLeftorRight(El el, String s) {
    return s != null && (s.contains("Left") || s.contains("Right"));
  }

  @PatchMethod
  public static El removeStyleName(El el, String styleName) {
    Element elem = getWrappedElement(el);
    elem.removeClassName(styleName);

    return el;
  }

  @PatchMethod
  public static El repaint(El el) {
    return el;
  }

  private static Element getWrappedElement(El el) {
    return GwtReflectionUtils.getPrivateFieldValue(el, "dom");
  }

}
