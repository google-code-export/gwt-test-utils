package com.octo.gwt.test.internal.patchers.dom;

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
import com.octo.gwt.test.internal.overrides.OverrideEvent;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.internal.utils.TagAware;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.EventBuilder;
import com.octo.gwt.test.utils.events.EventUtils;

@PatchClass(classes = {"com.google.gwt.dom.client.DOMImpl"})
public class DOMImplPatcher extends AutomaticPatcher {

  private static final String ABSOLUTE_LEFT = "AbsoluteLeft";
  private static final String ABSOLUTE_TOP = "AbsoluteTop";
  private static final String INNER_HTML = "InnerHTML";
  private static final String NODE_LIST_FIELD = "ChildNodes";
  private static final String SCROLL_LEFT = "ScrollLeft";
  private static final String SRC = "Src";
  private static final String TAB_INDEX = "TabIndex";
  private static final String TAG_NAME = "TagName";

  @PatchMethod
  public static ButtonElement createButtonElement(Object domImpl, Document doc,
      String type) {
    ButtonElement e = (ButtonElement) doc.createElement("button");
    PropertyContainerUtils.setProperty(e, "Type", type);
    return e;
  }

  @PatchMethod
  public static Element createElement(Object domImpl, Document doc, String tag) {
    return NodeFactory.createElement(tag);
  }

  @PatchMethod
  public static NativeEvent createHtmlEvent(Object domImpl, Document doc,
      String type, boolean canBubble, boolean cancelable) {
    return EventBuilder.create(Event.getTypeInt(type)).build();
  }

  public static InputElement createInputElement(Document doc, String type,
      String name) {
    InputElement e = (InputElement) doc.createElement("input");
    PropertyContainerUtils.setProperty(e, "Type", type);

    if (name != null) {
      PropertyContainerUtils.setProperty(e, "Name", name);
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
    OverrideEvent event = evt.cast();
    return event.isOverrideAltKey();
  }

  @PatchMethod
  public static int eventGetButton(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    return event.getOverrideButton();
  }

  @PatchMethod
  public static int eventGetCharCode(Object domImpl, NativeEvent evt) {
    return evt.getKeyCode();
  }

  @PatchMethod
  public static boolean eventGetCtrlKey(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    return event.isOverrideCtrlKey();
  }

  @PatchMethod
  public static int eventGetKeyCode(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    return event.getOverrideKeyCode();
  }

  @PatchMethod
  public static boolean eventGetMetaKey(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    return event.isOverrideMetaKey();
  }

  @PatchMethod
  public static boolean eventGetShiftKey(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    return event.isOverrideShiftKey();
  }

  @PatchMethod
  public static EventTarget eventGetTarget(Object domImpl,
      NativeEvent nativeEvent) {
    return null;
  }

  @PatchMethod
  public static String eventGetType(Object domImpl, NativeEvent nativeEvent) {
    return EventUtils.getEventTypeString(nativeEvent);
  }

  @PatchMethod
  public static void eventPreventDefault(Object domImpl, NativeEvent evt) {
    OverrideEvent event = evt.cast();
    event.setPreventDefault(true);
  }

  @PatchMethod
  public static void eventStopPropagation(Object domImpl, NativeEvent evt) {

  }

  @PatchMethod
  public static int getAbsoluteLeft(Object domImpl, Element elem) {
    return PropertyContainerUtils.getPropertyInteger(elem, ABSOLUTE_LEFT);
  }

  @PatchMethod
  public static int getAbsoluteTop(Object domImpl, Element elem) {
    return PropertyContainerUtils.getPropertyInteger(elem, ABSOLUTE_TOP);
  }

  @PatchMethod
  public static String getAttribute(Object domImpl, Element elem, String name) {
    PropertyContainer propertyContainer = PropertyContainerUtils.getProperty(
        elem, ElementPatcher.PROPERTY_MAP_FIELD);
    String attribute = (String) propertyContainer.get(name);

    return attribute == null ? "" : attribute;
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
    return PropertyContainerUtils.getPropertyString(elem, INNER_HTML);
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

    OverrideNodeList<Node> list = getChildNodeList(parent);

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
    return PropertyContainerUtils.getPropertyInteger(elem, SCROLL_LEFT);
  }

  @PatchMethod
  public static int getTabIndex(Object domImpl, Element elem) {
    return PropertyContainerUtils.getPropertyInteger(elem, TAB_INDEX);
  }

  @PatchMethod
  public static String getTagName(Object domImpl, Element elem) {
    if (elem == null)
      return null;

    if (elem instanceof TagAware) {
      return ((TagAware) elem).getTag();
    }

    String tagName = PropertyContainerUtils.getProperty(elem, TAG_NAME);
    return (tagName != null) ? tagName
        : (String) GwtReflectionUtils.getStaticFieldValue(elem.getClass(),
            "TAG");
  }

  @PatchMethod
  public static String imgGetSrc(Object domImpl, Element img) {
    return PropertyContainerUtils.getProperty(img, SRC);
  }

  @PatchMethod
  public static void imgSetSrc(Object domImpl, Element img, String src) {
    PropertyContainerUtils.setProperty(img, SRC, src);
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
  }

  @PatchMethod
  public static void selectClear(Object domImpl, SelectElement select) {
    OverrideNodeList<Node> childNodes = (OverrideNodeList<Node>) select.getChildNodes();
    childNodes.getList().clear();
    select.setSelectedIndex(-1);
  }

  // Abstract methods

  @PatchMethod
  public static int selectGetLength(Object domImpl, SelectElement select) {
    return selectGetOptions(domImpl, select).getLength();
  }

  @PatchMethod
  public static NodeList<OptionElement> selectGetOptions(Object domImpl,
      SelectElement select) {
    OverrideNodeList<OptionElement> list = new OverrideNodeList<OptionElement>();

    for (int i = 0; i < select.getChildNodes().getLength(); i++) {
      Element e = (Element) select.getChildNodes().getItem(i).cast();
      if (e instanceof OptionElement) {
        list.getList().add((OptionElement) e);
      }
    }

    return list;
  }

  @PatchMethod
  public static void selectRemoveOption(Object domImpl, SelectElement select,
      int index) {
    OverrideNodeList<Node> childNodes = (OverrideNodeList<Node>) select.getChildNodes();
    childNodes.getList().remove(index);
  }

  @PatchMethod
  public static void setInnerText(Object domImpl, Element elem, String text) {
    Text textNode = getTextNode(elem);
    if (textNode != null) {
      textNode.setData(text);
    } else {
      textNode = NodeFactory.createTextNode(text);
      elem.appendChild(textNode);
    }
  }

  @PatchMethod
  public static void setScrollLeft(Object domImpl, Element elem, int left) {
    PropertyContainerUtils.setProperty(elem, SCROLL_LEFT, left);
  }

  private static OverrideNodeList<Node> getChildNodeList(Node node) {
    return PropertyContainerUtils.getProperty(node, NODE_LIST_FIELD);
  }

  private static Text getTextNode(Element elem) {
    NodeList<Node> list = elem.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.getItem(i);
      if (Text.class.isInstance(node)) {
        return (Text) node;
      }
    }

    return null;
  }

}
