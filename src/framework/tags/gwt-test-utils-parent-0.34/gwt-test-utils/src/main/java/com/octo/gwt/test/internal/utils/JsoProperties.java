package com.octo.gwt.test.internal.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * List of {@link JavaScriptObject} properties. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class JsoProperties {

  public static final String DOCUMENT_ELEMENT = "documentElement";

  public static final String ELEM_BINDED_UIOBJECT = "ELEM_BINDED_UIOBJECT";
  public static final String ELEM_EVENTLISTENER = "ELEM_EVENTLISTENER";
  public static final String ELEM_PROPERTIES = "ELEM_PROPERTIES";
  public static final String ELEMENT_CLIENT_HEIGHT = "ELEMENT_CLIENT_HEIGHT";
  public static final String ELEMENT_CLIENT_WIDTH = "ELEMENT_CLIENT_WIDTH";

  // EVENT PROPERTIES
  public static final String EVENT_BUTTON = "EVENT_button";
  public static final String EVENT_IS_STOPPED = "EVENT_isStopped";

  public static final String EVENT_KEY_ALT = "EVENT_altKey";
  public static final String EVENT_KEY_CTRL = "EVENT_ctrlKey";
  public static final String EVENT_KEY_META = "EVENT_metaKey";
  public static final String EVENT_KEY_SHIFT = "EVENT_shiftKey";
  public static final String EVENT_KEYCODE = "EVENT_keyCode";
  public static final String EVENT_PREVENTDEFAULT = "EVENT_preventDefault";
  public static final String EVENT_RELATEDTARGET = "EVENT_relatedTarget";
  public static final String EVENT_TARGET = "EVENT_target";
  public static final String EVENT_TYPE = "EVENT_type";
  public static final String JSARRAY_WRAPPED_LIST = "JSARRAY_WRAPPED_LIST";
  // MOUSE EVENT PROPERTIES
  public static final String MOUSEEVENT_CLIENTX = "EVENT_clientX";

  public static final String MOUSEEVENT_CLIENTY = "EVENT_clientY";

  public static final String MOUSEEVENT_SCREENX = "EVENT_screenX";
  public static final String MOUSEEVENT_SCREENY = "EVENT_screenY";
  public static final String NODE_LIST_FIELD = "childNodes";
  public static final String NODE_LIST_INNER_LIST = "NODE_LIST_INNER_LIST";

  public static final String NODE_NAME = "nodeName";
  public static final String NODE_NAMESPACE_URI = "namespaceURI";
  public static final String NODE_TYPE_FIELD = "nodeType";
  public static final String PARENT_NODE_FIELD = "parentNode";

  // POTENTIAL ELEMENT PROPERTIES
  public static final String POTENTIALELEMENT_TAG = "POTENTIALELEMENT_TAG";
  public static final String POTENTIALELEMENT_UIOBJECT = "POTENTIALELEMENT_UIOBJECT";
  public static final String POTENTIALELEMENT_WRAPPED_ELEMENT = "POTENTIALELEMENT_WRAPPED_ELEMENT";

  public static final String SCROLL_LEFT = "scrollLeft";

  public static final String SELECTED_INDEX_FIELD = "selectedIndex";
  public static final String SELECTED_SIZE = "SELECTED_SIZE";
  public static final String SELECTION_END = "SELECTION_END";

  public static final String SELECTION_START = "SELECTION_START";
  public static final String STYLE_BORDER_BOTTOM_WIDTH = "border-bottom-width";
  public static final String STYLE_BORDER_LEFT_WIDTH = "border-left-width";
  public static final String STYLE_BORDER_RIGHT_WIDTH = "border-right-width";
  public static final String STYLE_BORDER_TOP_WIDTH = "border-top-width";
  public static final String STYLE_OBJECT_FIELD = "STYLE_OBJECT";
  public static final String STYLE_TARGET_ELEMENT = "STYLE_TARGET_ELEMENT";
  public static final String STYLE_WHITESPACE_PROPERTY = "whiteSpace";

  public static final String TAB_INDEX = "tabIndex";
  public static final String TAG_NAME = "tagName";

  // for TableElement
  public static final String TCAPTION = "TCAPTION";
  public static final String TFOOT = "tFoot";
  public static final String THEAD = "tHead";

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
    propertyNames = new HashMap<String, String>();
    propertyNames.put("id", "id");
    propertyNames.put("name", "name");
    propertyNames.put("class", "className");
    propertyNames.put("title", "title");
    propertyNames.put("type", "type");
    propertyNames.put("value", "value");
    propertyNames.put("src", "src");
    propertyNames.put("lang", "lang");
  }

  public String getStandardDOMPropertyName(String propertyNameCaseInsensitive) {
    return propertyNames.get(propertyNameCaseInsensitive.toLowerCase());
  }

  public boolean isStandardDOMProperty(String propertyName) {
    return propertyNames.values().contains(propertyName)
        && (!"class".equals(propertyName));
  }

}
