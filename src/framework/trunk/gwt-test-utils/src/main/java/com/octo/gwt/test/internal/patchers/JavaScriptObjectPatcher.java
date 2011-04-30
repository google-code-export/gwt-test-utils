package com.octo.gwt.test.internal.patchers;

import java.util.Map;

import javassist.CtClass;
import javassist.CtField;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Text;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.internal.utils.PropertyContainer;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JavaScriptObject.class)
public class JavaScriptObjectPatcher extends AutomaticPatcher {

  @PatchMethod
  public static JavaScriptObject createArray() {
    return JavaScriptObjects.newObject(JsArrayString.class);
  }

  @PatchMethod
  public static String toString(JavaScriptObject jso) {
    // TODO : remove this code when overlay will be OK
    if (Text.class.isInstance(jso)) {
      Text text = jso.cast();
      return "'" + text.getData() + "'";
    } else if (Document.class.isInstance(jso)) {
      return "[object HTMLDocument]";
    } else if (Element.class.isInstance(jso)) {
      Element e = jso.cast();
      return elementToString(e);
    } else if (NodeList.class.isInstance(jso)) {
      return JavaScriptObjects.getObject(jso,
          JsoProperties.NODE_LIST_INNER_LIST).toString();
    } else {
      return jso.getClass().getSimpleName();
    }
  }

  private static String elementToString(Element elem) {
    StringBuilder sb = new StringBuilder();
    String tagName = elem.getTagName().toLowerCase();
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

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);
    // add field "protected PropertyContainer JSO_PROPERTIES;"
    CtField propertiesField = CtField.make(
        "protected final " + PropertyContainer.class.getName() + " "
            + JavaScriptObjects.PROPERTIES + " = "
            + PropertyContainer.class.getName()
            + ".newInstance(this, new java.util.HashMap());", c);
    c.addField(propertiesField);
  }

}
