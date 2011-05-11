package com.octo.gwt.test.internal.patchers.dom;

import java.util.ArrayList;
import java.util.List;

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
import com.octo.gwt.test.internal.utils.EventUtils;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.EventBuilder;

@PatchClass(classes = {"com.google.gwt.dom.client.DOMImpl"})
public class DOMImplPatcher extends AutomaticPatcher {

  @PatchMethod
  public static ButtonElement createButtonElement(Object domImpl, Document doc,
      String type) {
    ButtonElement e = (ButtonElement) doc.createElement("button");
    PropertyContainer properties = JavaScriptObjects.getObject(e,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(JsoProperties.ELEM_TYPE, type);
    return e;
  }

  @PatchMethod
  public static InputElement createCheckInputElement(Object domImpl,
      Document doc) {
    InputElement e = createInputElement(doc, "checkbox", null);
    e.setValue("on");

    return e;
  }

  @PatchMethod
  public static Element createElement(Object domImpl, Document doc, String tag) {
    return JavaScriptObjects.newElement(tag);
  }

  @PatchMethod
  public static NativeEvent createHtmlEvent(Object domImpl, Document doc,
      String type, boolean canBubble, boolean cancelable) {
    return EventBuilder.create(Event.getTypeInt(type)).build();
  }

  public static InputElement createInputElement(Document doc, String type,
      String name) {
    InputElement e = doc.createElement("input").cast();

    PropertyContainer properties = JavaScriptObjects.getObject(e,
        JsoProperties.ELEM_PROPERTIES);

    properties.put(JsoProperties.ELEM_TYPE, type);

    if (name != null) {
      properties.put(JsoProperties.ELEM_NAME, name);
    }

    return e;
  }

  @PatchMethod
  public static InputElement createInputElement(Object domImpl, Document doc,
      String type) {
    return createInputElement(doc, type, null);
  }

  @PatchMethod
  public static InputElement createInputRadioElement(Object domImpl,
      Document doc, String name) {
    return DOMImplPatcher.createInputElement(doc, "RADIO", name);
  }

  @PatchMethod
  public static void cssClearOpacity(Object domImpl, Style style) {
    style.setProperty("opacity", "");
  }

  @PatchMethod
  public static void cssSetOpacity(Object domImpl, Style style, double value) {
    double modulo = value % 1;
    String stringValue = (modulo == 0) ? String.valueOf((int) value)
        : String.valueOf(value);
    style.setProperty("opacity", stringValue);
  }

  @PatchMethod
  public static boolean eventGetAltKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_ALT);
  }

  @PatchMethod
  public static int eventGetButton(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getInteger(evt, JsoProperties.EVENT_BUTTON);
  }

  @PatchMethod
  public static int eventGetCharCode(Object domImpl, NativeEvent evt) {
    return evt.getKeyCode();
  }

  @PatchMethod
  public static boolean eventGetCtrlKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_CTRL);
  }

  @PatchMethod
  public static int eventGetKeyCode(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getInteger(evt, JsoProperties.EVENT_KEYCODE);
  }

  @PatchMethod
  public static boolean eventGetMetaKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_META);
  }

  @PatchMethod
  public static boolean eventGetShiftKey(Object domImpl, NativeEvent evt) {
    return JavaScriptObjects.getBoolean(evt, JsoProperties.EVENT_KEY_SHIFT);
  }

  @PatchMethod
  public static EventTarget eventGetTarget(Object domImpl,
      NativeEvent nativeEvent) {
    return JavaScriptObjects.getObject(nativeEvent, JsoProperties.EVENT_TARGET);
  }

  @PatchMethod
  public static String eventGetType(Object domImpl, NativeEvent nativeEvent) {
    int eventType = JavaScriptObjects.getInteger(nativeEvent,
        JsoProperties.EVENT_TYPE);
    return EventUtils.getEventTypeString(eventType);
  }

  @PatchMethod
  public static void eventPreventDefault(Object domImpl, NativeEvent evt) {
    JavaScriptObjects.setProperty(evt, JsoProperties.EVENT_PREVENTDEFAULT, true);
  }

  @PatchMethod
  public static void eventStopPropagation(Object domImpl, NativeEvent evt) {

  }

  @PatchMethod
  public static int getAbsoluteLeft(Object domImpl, Element elem) {
    return 0;
  }

  @PatchMethod
  public static int getAbsoluteTop(Object domImpl, Element elem) {
    return 0;
  }

  @PatchMethod
  public static String getAttribute(Object domImpl, Element elem, String name) {
    name = JsoProperties.get().getPropertyName(name);
    PropertyContainer properties = JavaScriptObjects.getObject(elem,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(name);
  }

  @PatchMethod
  public static int getBodyOffsetLeft(Object domImpl, Document doc) {
    return 0;
  }

  @PatchMethod
  public static int getBodyOffsetTop(Object domImpl, Document doc) {
    return 0;
  }

  @PatchMethod
  public static Element getFirstChildElement(Object domImpl, Element elem) {
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
  public static String getInnerHTML(Object domImpl, Element elem) {
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
  public static String getInnerText(Object domImpl, Element elem) {
    Text textNode = getTextNode(elem);
    if (textNode != null) {
      return textNode.getData();
    } else {
      return "";
    }
  }

  @PatchMethod
  public static Element getNextSiblingElement(Object domImpl, Element elem) {
    Node parent = elem.getParentNode();
    if (parent == null)
      return null;

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
  public static Element getParentElement(Object domImpl, Node elem) {
    Node parent = elem.getParentNode();

    if (parent == null || !(parent instanceof Element))
      return null;

    return parent.cast();
  }

  @PatchMethod
  public static int getScrollLeft(Object domImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.SCROLL_LEFT);
  }

  @PatchMethod
  public static int getTabIndex(Object domImpl, Element elem) {
    return JavaScriptObjects.getInteger(elem, JsoProperties.TAB_INDEX);
  }

  @PatchMethod
  public static String getTagName(Object domImpl, Element elem) {
    if (elem == null)
      return null;

    String tagName = JavaScriptObjects.getObject(elem, JsoProperties.TAG_NAME);

    return (tagName != null) ? tagName
        : (String) GwtReflectionUtils.getStaticFieldValue(elem.getClass(),
            "TAG");
  }

  @PatchMethod
  public static boolean hasAttribute(Object domImpl, Element elem, String name) {
    PropertyContainer properties = JavaScriptObjects.getObject(elem,
        JsoProperties.ELEM_PROPERTIES);

    return properties.contains(name);
  }

  @PatchMethod
  public static String imgGetSrc(Object domImpl, Element img) {
    PropertyContainer properties = JavaScriptObjects.getObject(img,
        JsoProperties.ELEM_PROPERTIES);
    return properties.getString(JsoProperties.ELEM_IMG_SRC);
  }

  @PatchMethod
  public static void imgSetSrc(Object domImpl, Element img, String src) {
    PropertyContainer properties = JavaScriptObjects.getObject(img,
        JsoProperties.ELEM_PROPERTIES);
    properties.put(JsoProperties.ELEM_IMG_SRC, src);
  }

  @PatchMethod
  public static boolean isOrHasChild(Object domImpl, Node parent, Node child) {
    if (parent.equals(child)) {
      return true;
    } else if (child.getParentElement() != null
        && child.getParentElement().equals(parent)) {
      return true;
    }
    return false;
  }

  @PatchMethod
  public static void scrollIntoView(Object domImpl, Element elem) {

  }

  @PatchMethod
  public static void selectAdd(Object domImpl, SelectElement select,
      OptionElement option, OptionElement before) {
    if (before == null) {
      select.appendChild(option);
    } else {
      select.insertBefore(option, before);
    }

    refreshSelect(select);
  }

  @PatchMethod
  public static void selectClear(Object domImpl, SelectElement select) {
    List<Node> innerList = JavaScriptObjects.getObject(select.getChildNodes(),
        JsoProperties.NODE_LIST_INNER_LIST);

    innerList.clear();
    select.setSelectedIndex(-1);
  }

  @PatchMethod
  public static int selectGetLength(Object domImpl, SelectElement select) {
    return selectGetOptions(domImpl, select).getLength();
  }

  @PatchMethod
  public static NodeList<OptionElement> selectGetOptions(Object domImpl,
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

  // Abstract methods

  @PatchMethod
  public static void selectRemoveOption(Object domImpl, SelectElement select,
      int index) {
    NodeList<Node> childNodes = select.getChildNodes();
    List<Node> list = JavaScriptObjects.getObject(childNodes,
        JsoProperties.NODE_LIST_INNER_LIST);
    list.remove(index);

    refreshSelect(select);
  }

  @PatchMethod
  public static void setInnerText(Object domImpl, Element elem, String text) {
    Text textNode = getTextNode(elem);
    if (textNode != null) {
      textNode.setData(text);
    } else {
      textNode = JavaScriptObjects.newText(text);
      elem.appendChild(textNode);
    }
  }

  @PatchMethod
  public static void setScrollLeft(Object domImpl, Element elem, int left) {
    JavaScriptObjects.setProperty(elem, JsoProperties.SCROLL_LEFT, left);
  }

  @PatchMethod
  public static String toString(Object domImpl, Element elem) {
    return elem.toString();
  }

  private static NodeList<Node> getChildNodeList(Node node) {
    return JavaScriptObjects.getObject(node, JsoProperties.NODE_LIST_FIELD);
  }

  private static Text getTextNode(Element elem) {
    NodeList<Node> list = elem.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.getItem(i);
      if (Node.TEXT_NODE == node.getNodeType()) {
        return node.cast();
      }
    }

    return null;
  }

  private static void refreshSelect(SelectElement select) {
    SelectElementPatcher.refreshSelect(select);
  }

}
