package com.octo.gwt.test.internal.utils;

import java.util.LinkedHashSet;
import java.util.Set;

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

  public static final String DICTIONARY_ENTRIES = "DICTIONARY_ENTRIES";
  public static final String DOCUMENT_ELEMENT = "documentElement";

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
  public static final String NODE_OWNER_DOCUMENT = "NODE_OWNER_DOCUMENT";
  public static final String NODE_PARENT_NODE = "parentNode";
  public static final String NODE_TYPE_FIELD = "nodeType";

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

  private final Set<String> propertyNames;

  private JsoProperties() {
    propertyNames = new LinkedHashSet<String>();
    /*
     * 
     * Parse HTML standard attributes here :
     * http://www.w3.org/TR/html4/index/attributes.html
     * 
     * With this jQuery script :
     * 
     * <script language="Javascript"> var array = new Array();
     * $('td[title=Name]').each(function() { var text = $(this).text().trim();
     * if (jQuery.inArray(text, array) == -1) { array.push(text); } });
     * 
     * var java = '<p>'; var length = array.length;
     * 
     * for (var i=0; i < length; i++) { java += 'propertyNames.add("' + array[i]
     * + '");<br/>'; }
     * 
     * java += '</p>';
     * 
     * $('table').parent().html(java); </script>
     */
    propertyNames.add("abbr");
    propertyNames.add("accept-charset");
    propertyNames.add("accept");
    propertyNames.add("accesskey");
    propertyNames.add("action");
    propertyNames.add("align");
    propertyNames.add("alink");
    propertyNames.add("alt");
    propertyNames.add("archive");
    propertyNames.add("axis");
    propertyNames.add("background");
    propertyNames.add("bgcolor");
    propertyNames.add("border");
    propertyNames.add("cellpadding");
    propertyNames.add("cellspacing");
    propertyNames.add("char");
    propertyNames.add("charoff");
    propertyNames.add("charset");
    propertyNames.add("checked");
    propertyNames.add("cite");
    propertyNames.add("class");
    propertyNames.add("classid");
    propertyNames.add("clear");
    propertyNames.add("code");
    propertyNames.add("codebase");
    propertyNames.add("codetype");
    propertyNames.add("color");
    propertyNames.add("cols");
    propertyNames.add("colspan");
    propertyNames.add("compact");
    propertyNames.add("content");
    propertyNames.add("coords");
    propertyNames.add("data");
    propertyNames.add("datetime");
    propertyNames.add("declare");
    propertyNames.add("defer");
    propertyNames.add("dir");
    propertyNames.add("disabled");
    propertyNames.add("enctype");
    propertyNames.add("face");
    propertyNames.add("for");
    propertyNames.add("frame");
    propertyNames.add("frameborder");
    propertyNames.add("headers");
    propertyNames.add("height");
    propertyNames.add("href");
    propertyNames.add("hreflang");
    propertyNames.add("hspace");
    propertyNames.add("http-equiv");
    propertyNames.add("id");
    propertyNames.add("ismap");
    propertyNames.add("label");
    propertyNames.add("lang");
    propertyNames.add("language");
    propertyNames.add("link");
    propertyNames.add("longdesc");
    propertyNames.add("marginheight");
    propertyNames.add("marginwidth");
    propertyNames.add("maxlength");
    propertyNames.add("media");
    propertyNames.add("method");
    propertyNames.add("multiple");
    propertyNames.add("name");
    propertyNames.add("nohref");
    propertyNames.add("noresize");
    propertyNames.add("noshade");
    propertyNames.add("nowrap");
    propertyNames.add("object");
    propertyNames.add("onblur");
    propertyNames.add("onchange");
    propertyNames.add("onclick");
    propertyNames.add("ondblclick");
    propertyNames.add("onfocus");
    propertyNames.add("onkeydown");
    propertyNames.add("onkeypress");
    propertyNames.add("onkeyup");
    propertyNames.add("onload");
    propertyNames.add("onmousedown");
    propertyNames.add("onmousemove");
    propertyNames.add("onmouseout");
    propertyNames.add("onmouseover");
    propertyNames.add("onmouseup");
    propertyNames.add("onreset");
    propertyNames.add("onselect");
    propertyNames.add("onsubmit");
    propertyNames.add("onunload");
    propertyNames.add("profile");
    propertyNames.add("prompt");
    propertyNames.add("readonly");
    propertyNames.add("rel");
    propertyNames.add("rev");
    propertyNames.add("rows");
    propertyNames.add("rowspan");
    propertyNames.add("rules");
    propertyNames.add("scheme");
    propertyNames.add("scope");
    propertyNames.add("scrolling");
    propertyNames.add("selected");
    propertyNames.add("shape");
    propertyNames.add("size");
    propertyNames.add("span");
    propertyNames.add("src");
    propertyNames.add("standby");
    propertyNames.add("start");
    propertyNames.add("style");
    propertyNames.add("summary");
    propertyNames.add("tabindex");
    propertyNames.add("target");
    propertyNames.add("text");
    propertyNames.add("title");
    propertyNames.add("type");
    propertyNames.add("usemap");
    propertyNames.add("valign");
    propertyNames.add("value");
    propertyNames.add("valuetype");
    propertyNames.add("version");
    propertyNames.add("vlink");
    propertyNames.add("vspace");
    propertyNames.add("width");
  }

  public String getDOMPropertyName(String propertyNameCaseInsensitive) {
    propertyNameCaseInsensitive = propertyNameCaseInsensitive.toLowerCase();
    if ("class".equals(propertyNameCaseInsensitive)) {
      return "className";
    }

    return propertyNameCaseInsensitive;

  }

  public boolean isStandardDOMProperty(String propertyName) {
    return "className".equals(propertyName) || !"class".equals(propertyName)
        && propertyNames.contains(propertyName);
  }

}
