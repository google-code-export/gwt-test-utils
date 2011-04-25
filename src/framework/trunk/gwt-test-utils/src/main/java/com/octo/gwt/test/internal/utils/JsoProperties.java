package com.octo.gwt.test.internal.utils;

import java.util.Map;
import java.util.TreeMap;

public class JsoProperties {

  public static final String DOCUMENT_ELEMENT = "documentElement";

  public static final String DOM_NAME = "name";
  // EVENT PROPERTIES
  public static final String EVENT_BUTTON = "EVENT_button";
  public static final String EVENT_KEY_ALT = "EVENT_altKey";
  public static final String EVENT_KEY_CTRL = "EVENT_ctrlKey";
  public static final String EVENT_KEY_META = "EVENT_metaKey";
  public static final String EVENT_KEY_SHIFT = "EVENT_shiftKey";
  public static final String EVENT_KEYCODE = "EVENT_keyCode";
  public static final String EVENT_PREVENTDEFAULT = "EVENT_preventDefault";
  public static final String EVENT_TARGET = "EVENT_target";

  public static final String EVENT_TYPE = "EVENT_type";
  public static final String INNER_HTML = "INNER_HTML";
  public static final String NODE_LIST_FIELD = "childNodes";
  public static final String NODE_LIST_INNER_LIST = "NODE_LIST_INNER_LIST";
  public static final String NODE_NAME = "nodeName";
  public static final String NODE_NAMESPACE_URI = "namespaceURI";
  public static final String NODE_TYPE_FIELD = "nodeType";
  public static final String PARENT_NODE_FIELD = "parentNode";
  public static final String SCROLL_LEFT = "scrollLeft";
  public static final String SELECTED_INDEX_FIELD = "selectedIndex";
  public static final String SELECTED_SIZE = "SELECTED_SIZE";

  public static final String SELECTION_END = "SELECTION_END";
  public static final String SELECTION_START = "SELECTION_START";
  public static final String SRC = "src";
  public static final String STYLE_OBJECT_FIELD = "STYLE_OBJECT";
  public static final String STYLE_TARGET_ELEMENT = "STYLE_TARGET_ELEMENT";
  public static final String STYLE_WHITESPACE_PROPERTY = "whiteSpace";
  public static final String TAB_INDEX = "tabIndex";
  public static final String TAG_NAME = "tagName";
  public static final String TYPE = "type";

  public static final String UIBINDER_CHILD_WIDGETS_LIST = "UIBINDER_CHILD_WIDGETS_LIST";

  public static final String XML_ATTR_JSO = "XML_ATTR_JSO";
  public static final String XML_ATTR_NAME = "XML_ATTR_NAME";
  public static final String XML_ATTR_SET = "XML_ATTR_SET";
  public static final String XML_ATTR_VALUE = "XML_ATTR_VALUE";
  public static final String XML_NAMESPACE = "XML_NAMESPACE";

  private static final JsoProperties INSTANCE = new JsoProperties();

  public static final JsoProperties get() {
    return INSTANCE;
  }

  private final Map<String, String> propertyNames;

  private JsoProperties() {
    propertyNames = new TreeMap<String, String>();
  }

  public void addJsoProperty(String propertyName) {
    propertyNames.put(propertyName.toLowerCase(), propertyName);
  }

  public String getPropertyName(String propertyNameCaseInsensitive) {
    String propertyName = propertyNames.get(propertyNameCaseInsensitive.toLowerCase());

    return (propertyName != null || propertyNameCaseInsensitive.contains("_"))
        ? propertyName : propertyNameCaseInsensitive;
  }

}
