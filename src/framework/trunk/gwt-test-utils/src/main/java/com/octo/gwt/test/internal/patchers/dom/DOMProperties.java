package com.octo.gwt.test.internal.patchers.dom;

import java.util.Map;
import java.util.TreeMap;

public class DOMProperties {

  public static final String ABSOLUTE_LEFT = "absoluteLeft";
  public static final String ABSOLUTE_TOP = "absoluteTop";
  public static final String CLASSNAME_FIELD = "className";
  public static final String DOCUMENT_ELEMENT = "documentElement";
  public static final String EVENT_BUTTON = "EVENT_button";

  // EVENT PROPERTIES
  public static final String EVENT_KEY_ALT = "EVENT_altKey";
  public static final String EVENT_KEY_CTRL = "EVENT_ctrlKey";
  public static final String EVENT_KEY_META = "EVENT_metaKey";
  public static final String EVENT_KEY_SHIFT = "EVENT_shiftKey";
  public static final String EVENT_KEYCODE = "EVENT_keyCode";
  public static final String EVENT_PREVENTDEFAULT = "EVENT_preventDefault";
  public static final String EVENT_TARGET = "EVENT_target";
  public static final String EVENT_TYPE = "EVENT_type";

  public static final String INNER_HTML = "innerHTML";
  public static final String NAME = "name";
  public static final String NODE_LIST_FIELD = "childNodes";
  public static final String NODE_LIST_INNER_LIST = "NODE_LIST_INNER_LIST";
  public static final String NODE_NAME = "nodeName";
  public static final String PARENT_NODE_FIELD = "parentNode";
  public static final String SCROLL_LEFT = "scrollLeft";
  public static final String SELECTED_INDEX_FIELD = "selectedIndex";

  public static final String SELECTION_END = "selectionEnd";
  public static final String SELECTION_START = "selectionStart";
  public static final String SRC = "src";
  public static final String STYLE_OBJECT_FIELD = "STYLE_OBJECT";
  public static final String STYLE_TARGET_ELEMENT = "STYLE_TARGET_ELEMENT";
  public static final String TAB_INDEX = "tabIndex";
  public static final String TAG_NAME = "tagName";
  public static final String TYPE = "type";

  private static final DOMProperties INSTANCE = new DOMProperties();

  public static final DOMProperties get() {
    return INSTANCE;
  }

  private final Map<String, String> propertyNames;

  private DOMProperties() {
    propertyNames = new TreeMap<String, String>();
  }

  public void addDOMProperty(String propertyName) {
    propertyNames.put(propertyName.toLowerCase(), propertyName);
  }

  public String getPropertyName(String propertyNameCaseInsensitive) {
    String propertyName = propertyNames.get(propertyNameCaseInsensitive.toLowerCase());

    return (propertyName != null || propertyNameCaseInsensitive.startsWith("_"))
        ? propertyName : propertyNameCaseInsensitive;
  }

}
