package com.octo.gwt.test.internal.patchers.dom;

import java.util.LinkedHashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.octo.gwt.test.internal.GwtClassPool;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.internal.utils.StyleUtils;
import com.octo.gwt.test.patchers.OverlayPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Style.class)
public class StylePatcher extends OverlayPatcher {

  @PatchMethod
  public static void clearBorderWidth(Style style) {
    PropertyContainer pc = PropertyContainerUtils.cast(style).getProperties();
    pc.put("border-top-width", "");
    pc.put("border-right-width", "");
    pc.put("border-bottom-width", "");
    pc.put("border-left-width", "");
  }

  @PatchMethod
  public static void clearFloat(Style style) {
    setPropertyImpl(style, "float", "");
  }

  @PatchMethod
  public static String getBorderWidth(Style style) {
    String borderWidth = PropertyContainerUtils.getProperty(style,
        "border-top-width");
    return (borderWidth != null) ? borderWidth : "";
  }

  @PatchMethod
  public static String getPropertyImpl(Style style, String propertyName) {
    String propertyValue = PropertyContainerUtils.getProperty(style,
        propertyName);
    return (propertyValue != null) ? propertyValue : "";
  }

  @PatchMethod
  public static void setBorderWidth(Style style, double value, Unit unit) {
    PropertyContainer pc = PropertyContainerUtils.cast(style).getProperties();
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

    PropertyContainerUtils.setProperty(style, propertyName, propertyValue);

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

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    CtField targetElementField = new CtField(
        GwtClassPool.getCtClass(Element.class),
        DOMProperties.STYLE_TARGET_ELEMENT, c);
    c.addField(targetElementField);

    CtConstructor constructor = new CtConstructor(
        new CtClass[]{GwtClassPool.getCtClass(Element.class)}, c);
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("this." + DOMProperties.STYLE_TARGET_ELEMENT + " = $1;");
    sb.append(PropertyContainerUtils.getCodeSetProperty("this", "whiteSpace",
        "\"nowrap\"") + ";");
    sb.append("}");
    constructor.setBody(sb.toString());
    c.addConstructor(constructor);
  }

}
