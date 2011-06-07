package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.patchers.PatchMethod.Type;

@PatchClass(DOM.class)
public class DOMPatcher extends AutomaticPatcher {

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createAnchor() {
    return "return createElement(\"a\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createButton() {
    return "return createElement(\"button\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createCaption() {
    return "return createElement(\"caption\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createCol() {
    return "return createElement(\"col\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createColGroup() {
    return "return createElement(\"colgroup\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createDiv() {
    return "return createElement(\"div\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createFieldSet() {
    return "return createElement(\"fieldset\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createForm() {
    return "return createElement(\"form\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createIFrame() {
    return "return createElement(\"iframe\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createImg() {
    return "return createElement(\"img\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createLabel() {
    return "return createElement(\"label\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createLegend() {
    return "return createElement(\"legend\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createOption() {
    return "return createElement(\"option\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createOptions() {
    return "return createElement(\"options\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createSpan() {
    return "return createElement(\"span\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTable() {
    return "return createElement(\"table\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTBody() {
    return "return createElement(\"tbody\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTD() {
    return "return createElement(\"td\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTextArea() {
    return "return createElement(\"textarea\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTFoot() {
    return "return createElement(\"tfoot\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTH() {
    return "return createElement(\"th\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTHead() {
    return "return createElement(\"thead\");";
  }

  @PatchMethod(type = Type.NEW_CODE_AS_STRING)
  public static String createTR() {
    return "return createElement(\"tr\");";
  }

  @PatchMethod
  public static Element getFirstChild(Element elem) {
    Node firstChild = elem.getFirstChildElement();
    if (firstChild != null) {
      return firstChild.cast();
    } else {
      return null;
    }
  }
}
