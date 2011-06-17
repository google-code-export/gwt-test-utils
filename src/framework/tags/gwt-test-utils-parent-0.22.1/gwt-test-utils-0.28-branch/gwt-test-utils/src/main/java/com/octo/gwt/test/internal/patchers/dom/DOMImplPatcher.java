package com.octo.gwt.test.internal.patchers.dom;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test.internal.utils.EventUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.EventBuilder;

@PatchClass(target = "com.google.gwt.dom.client.DOMImpl")
class DOMImplPatcher {

  @PatchMethod
  static ButtonElement createButtonElement(Object domImpl, Document doc,
      String type) {
    ButtonElement e = (ButtonElement) doc.createElement("button");
    PropertyContainer properties = JavaScriptObjects.getObject(e,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(JsoProperties.ELEM_TYPE, type);
    return e;
  }

  @PatchMethod
  static Element createElement(Object domImpl, Document doc, String tag) {
    return JavaScriptObjects.newElement(tag);
  }

  @PatchMethod
  static NativeEvent createHtmlEvent(Object domImpl, Document doc, String type,
      boolean canBubble, boolean cancelable) {
    return EventBuilder.create(Event.getTypeInt(type)).build();
  }

  static InputElement createInputElement(Document doc, String type, String name) {
    InputElement e = (InputElement) doc.createElement("input");

    PropertyContainer properties = JavaScriptObjects.getObject(e,
        JsoProperties.ELEM_PROPERTIES);

    properties.put(JsoProperties.ELEM_TYPE, type);

    if (name != null) {
      properties.put(JsoProperties.ELEM_NAME, name);
    }

    return e;
  }

  @PatchMethod
  static InputElement createInputElement(Object domImpl, Document doc,
      String type) {
    return createInputElement(doc, type, null);
  }

  @PatchMethod
  static InputElement createInputRadioElement(Object domImpl, Document doc,
      String name) {
    return DOMImplPatcher.createInputElement(doc, "RADIO", name);
  }

  @PatchMethod
  static void cssClearOpacity(Object domImpl, Style style) {
    style.setProperty("opacity", "");
  }

  @PatchMethod
  static void cssSetOpacity(Object domImpl, Style style, double value) {
    double modulo = value % 1;
    String stringValue = (modulo == 0) ? String.valueOf((int) value)
        : String.valueOf(value);
    style.setProperty("opacity", stringValue);
  }

  @PatchMethod
  static boolean eventGetAltKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_ALT);
  }

  @PatchMethod
  static int eventGetButton(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getInteger(evt, JsoProperties.EVENT_BUTTON);
  }

  @PatchMethod
  static int eventGetCharCode(Object domImpl, NativeEvent evt) {
    return evt.getKeyCode();
  }

  @PatchMethod
  static boolean eventGetCtrlKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_CTRL);
  }

  @PatchMethod
  static int eventGetKeyCode(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getInteger(evt, JsoProperties.EVENT_KEYCODE);
  }

  @PatchMethod
  static boolean eventGetMetaKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_META);
  }

  @PatchMethod
  static EventTarget eventGetRelatedTarget(Object domImpl,
      NativeEvent nativeEvent) {

    JavaScriptObject relatedTargetJSO = JavaScriptObjects.getObject(
        nativeEvent, JsoProperties.EVENT_RELATEDTARGET);

    if (relatedTargetJSO == null) {
      return null;
    }

    return relatedTargetJSO.cast();
  }

  @PatchMethod
  static boolean eventGetShiftKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_SHIFT);
  }

  @PatchMethod
  static EventTarget eventGetTarget(Object domImpl, NativeEvent nativeEvent) {
    UIObject target = JavaScriptObjects.getObject(nativeEvent,
        JsoProperties.EVENT_TARGET);
    return target.getElement().cast();
  }

  @PatchMethod
  static String eventGetType(Object domImpl, NativeEvent nativeEvent) {
    int eventType = JavaScriptObjects.getInteger(nativeEvent,
        JsoProperties.EVENT_TYPE);
    return EventUtils.getEventTypeString(eventType);
  }

  @PatchMethod
  static void eventPreventDefault(Object domImpl, NativeEvent evt) {
    JavaScriptObjects.setProperty(evt, JsoProperties.EVENT_PREVENTDEFAULT, true);
  }

  @PatchMethod
  static void eventStopPropagation(Object domImpl, NativeEvent evt) {

  }

  @PatchMethod
  static int getAbsoluteLeft(Object domImpl, Element elem) {
    return 0;
  }

  @PatchMethod
  static int getAbsoluteTop(Object domImpl, Element elem) {
    return 0;
  }

  @PatchMethod
  static String getAttribute(Object domImpl, Element elem, String name) {
    name = JsoProperties.get().getPropertyName(name);
    PropertyContainer properties = JavaScriptObjects.getObject(elem,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(name);
  }

  @PatchMethod
  static int getBodyOffsetLeft(Object domImpl, Document doc) {
    return 0;
  }

  @PatchMethod
  static int getBodyOffsetTop(Object domImpl, Document doc) {
    return 0;
  }

  @PatchMethod
  static Element getFirstChildElement(Object domImpl, Element elem) {
    NodeList<Node> nodeList = elem.getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.getItem(i);
      if (node instanceof Element) {
        return node.cast();
      }
    }

    return null;
  }

  @PatchMethod
  static String getInnerHTML(Object domImpl, Element elem) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < elem.getChildNodes().getLength(); i++) {
      Node current = elem.getChildNodes().getItem(i);
      if (current.getNodeType() == Node.TEXT_NODE) {
        Text text = current.cast();
        sb.append(text.getData());
      } else {
        sb.append(current.toString());
      }
    }

    return sb.toString();
  }

  @PatchMethod
  static String getInnerText(Object domImpl, Element elem) {
    StringBuilder sb = new StringBuilder("");

    appendInnerTextRecursive(elem, sb);

    return sb.toString();
  }

  @PatchMethod
  static Element getNextSiblingElement(Object domImpl, Element elem) {
    Node parent = elem.getParentNode();
    if (parent == null) {
      return null;
    }

    NodeList<Node> list = getChildNodeList(parent);

    for (int i = 0; i < list.getLength(); i++) {
      Node current = list.getItem(i);
      if (current.equals(elem) && i < list.getLength() - 1) {
        while (i < list.getLength() - 1) {
          i++;
          if (list.getItem(i) instanceof Element) {
            return list.getItem(i).cast();
          }
        }
      }
    }

    return null;
  }

  @PatchMethod
  static Element getParentElement(Object domImpl, Node elem) {
    Node parent = elem.getParentNode();

    if (parent == null || !(parent instanceof Element)) {
      return null;
    }

    return parent.cast();
  }

  @PatchMethod
  static int getScrollLeft(Object domImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.SCROLL_LEFT);
  }

  @PatchMethod
  static int getTabIndex(Object domImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.TAB_INDEX);
  }

  @PatchMethod
  static String getTagName(Object domImpl, Element elem) {
    if (elem == null) {
      return null;
    }

    String tagName = JavaScriptObjects.getObject(elem, JsoProperties.TAG_NAME);

    return (tagName != null) ? tagName
        : (String) GwtReflectionUtils.getStaticFieldValue(elem.getClass(),
            "TAG");
  }

  @PatchMethod
  static String imgGetSrc(Object domImpl, Element img) {
    PropertyContainer properties = JavaScriptObjects.getObject(img,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.ELEM_IMG_SRC);
  }

  @PatchMethod
  static void imgSetSrc(Object domImpl, Element img, String src) {
    PropertyContainer properties = JavaScriptObjects.getObject(img,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(JsoProperties.ELEM_IMG_SRC, src);
  }

  @PatchMethod
  static boolean isOrHasChild(Object domImpl, Node parent, Node child) {
    if (parent.equals(child)) {
      return true;
    } else if (child.getParentElement() != null
        && child.getParentElement().equals(parent)) {
      return true;
    }
    return false;
  }

  @PatchMethod
  static void scrollIntoView(Object domImpl, Element elem) {

  }

  @PatchMethod
  static void selectAdd(Object domImpl, SelectElement select,
      OptionElement option, OptionElement before) {
    if (before == null) {
      select.appendChild(option);
    } else {
      select.insertBefore(option, before);
    }

    refreshSelect(select);
  }

  @PatchMethod
  static void selectClear(Object domImpl, SelectElement select) {
    clearChildNodes(select);
    select.setSelectedIndex(-1);
  }

  @PatchMethod
  static int selectGetLength(Object domImpl, SelectElement select) {
    return selectGetOptions(domImpl, select).getLength();
  }

  @PatchMethod
  static NodeList<OptionElement> selectGetOptions(Object domImpl,
      SelectElement select) {
    List<OptionElement> innerList = new ArrayList<OptionElement>();
    for (int i = 0; i < select.getChildNodes().getLength(); i++) {
      Element e = select.getChildNodes().getItem(i).cast();
      if ("option".equals(e.getTagName())) {
        OptionElement option = e.cast();
        innerList.add(option);
      }
    }

    return JavaScriptObjects.newNodeList(innerList);
  }

  @PatchMethod
  static void selectRemoveOption(Object domImpl, SelectElement select, int index) {
    NodeList<Node> childNodes = select.getChildNodes();
    List<Node> list = JavaScriptObjects.getObject(childNodes,
        JsoProperties.NODE_LIST_INNER_LIST);
    list.remove(index);

    refreshSelect(select);
  }

  @PatchMethod
  static void setInnerText(Object domImpl, Element elem, String text) {
    clearChildNodes(elem);
    elem.appendChild(JavaScriptObjects.newText(text));
  }

  @PatchMethod
  static void setScrollLeft(Object domImpl, Element elem, int left) {
    JavaScriptObjects.setProperty(elem, JsoProperties.SCROLL_LEFT, left);
  }

  @PatchMethod
  static String toString(Object domImpl, Element elem) {
    return elem.toString();
  }

  private static void appendInnerTextRecursive(Element elem, StringBuilder sb) {
    NodeList<Node> list = elem.getChildNodes();

    for (int i = 0; i < elem.getChildNodes().getLength(); i++) {
      Node node = list.getItem(i);
      switch (node.getNodeType()) {
        case Node.TEXT_NODE:
          Text text = node.cast();
          sb.append(text.getData());
          break;
        case Node.ELEMENT_NODE:
          Element childNode = node.cast();
          appendInnerTextRecursive(childNode, sb);
          break;
      }
    }
  }

  private static void clearChildNodes(Element elem) {
    List<Node> innerList = JavaScriptObjects.getObject(elem.getChildNodes(),
        JsoProperties.NODE_LIST_INNER_LIST);

    innerList.clear();
  }

  private static NodeList<Node> getChildNodeList(Node node) {
    return JavaScriptObjects.getObject(node, JsoProperties.NODE_LIST_FIELD);
  }

  private static void refreshSelect(SelectElement select) {
    SelectElementPatcher.refreshSelect(select);
  }

}
