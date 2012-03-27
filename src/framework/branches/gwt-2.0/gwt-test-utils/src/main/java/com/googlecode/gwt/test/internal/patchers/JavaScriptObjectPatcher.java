package com.googlecode.gwt.test.internal.patchers;

import java.util.LinkedHashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.googlecode.gwt.test.internal.utils.GwtStringUtils;
import com.googlecode.gwt.test.internal.utils.JavaScriptObjects;
import com.googlecode.gwt.test.internal.utils.JsoProperties;
import com.googlecode.gwt.test.internal.utils.PropertyContainer;
import com.googlecode.gwt.test.patchers.InitMethod;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(JavaScriptObject.class)
class JavaScriptObjectPatcher {

  @PatchMethod
  static JavaScriptObject createArray() {
    return JavaScriptObjects.newObject(JsArrayString.class);
  }

  @PatchMethod
  static JavaScriptObject createFunction() {
    return JavaScriptObjects.newObject(JavaScriptObject.class);
  }

  @PatchMethod
  static JavaScriptObject createObject() {
    return JavaScriptObjects.newObject(JavaScriptObject.class);
  }

  @InitMethod
  static void initClass(CtClass c) throws CannotCompileException {
    // add field "protected PropertyContainer JSO_PROPERTIES;"
    CtField propertiesField = CtField.make("protected "
        + PropertyContainer.class.getName() + " "
        + JavaScriptObjects.PROPERTIES + ";", c);
    c.addField(propertiesField);

    // BECAUSE OF MOCKED element, we need to check if PropertyContainer is null
    // when calling JavaScriptObjects.getProperties(e)
    // CtConstructor defaultConstructor = JavassistUtils.findConstructor(c);
    // defaultConstructor.insertAfter(JavaScriptObjects.PROPERTIES + " = "
    // + PropertyContainer.class.getName()
    // + ".newInstance(new java.util.HashMap());");
  }

  @PatchMethod
  static String toString(JavaScriptObject jso) {
    // TODO : remove this code when overlay will be OK
    if (Text.class.isInstance(jso)) {
      Text text = jso.cast();
      return "'" + text.getData() + "'";
    } else if (Document.class.isInstance(jso)) {
      return "[object HTMLDocument]";
    } else if (Style.class.isInstance(jso)) {
      return styleToString((Style) jso);
    } else if (Element.class.isInstance(jso)) {
      return elementToString((Element) jso);
    } else if (NodeList.class.isInstance(jso)) {
      return JavaScriptObjects.getObject(jso,
          JsoProperties.NODE_LIST_INNER_LIST).toString();
    } else {
      return jso.getClass().getSimpleName();
    }
  }

  private static String elementToString(Element elem) {
    String tagName = elem.getTagName().toLowerCase();

    // handle the particular case of <br> element
    if ("br".equals(tagName)) {
      return "<br>" + elem.getInnerText();
    }

    StringBuilder sb = new StringBuilder();
    sb.append("<").append(tagName).append(" ");

    PropertyContainer attrs = JavaScriptObjects.getObject(elem,
        JsoProperties.ELEM_PROPERTIES);
    for (Map.Entry<String, Object> entry : attrs.entrySet()) {
      // special treatment for "disabled" property, which should be a empty
      // string attribute if the DOM element is disabled
      if ("disabled".equals(entry.getKey())) {
        Boolean disabled = (Boolean) entry.getValue();
        if (disabled.booleanValue()) {
          sb.append(entry.getKey()).append("=\"\" ");
        }
      } else if ("className".equals(entry.getKey())) {
        // special treatment for "className", which is mapped with DOM standard
        // property "class"
        sb.append("class=\"").append(entry.getValue()).append("\" ");
      } else if ("style".equals(entry.getKey())) {
        String style = elem.getStyle().toString();
        if (!"".equals(style)) {
          sb.append("style=\"").append(elem.getStyle().toString()).append("\" ");
        }
      } else {
        sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append(
            "\" ");
      }
    }
    // remove the last space character
    sb.replace(sb.length() - 1, sb.length(), "");

    sb.append(">").append(elem.getInnerHTML());
    sb.append("</").append(tagName).append(">");
    return sb.toString();
  }

  private static String styleToString(Style style) {
    LinkedHashMap<String, String> styleProperties = JavaScriptObjects.getObject(
        style, JsoProperties.STYLE_PROPERTIES);

    StringBuilder sb = new StringBuilder();

    for (Map.Entry<String, String> entry : styleProperties.entrySet()) {
      String cssPropertyValue = entry.getValue().trim();

      if (!"".equals(cssPropertyValue)) {
        String cssProperyName = GwtStringUtils.hyphenize(entry.getKey());
        sb.append(cssProperyName).append(": ").append(cssPropertyValue).append(
            "; ");
      }
    }

    return sb.toString();

  }

}
