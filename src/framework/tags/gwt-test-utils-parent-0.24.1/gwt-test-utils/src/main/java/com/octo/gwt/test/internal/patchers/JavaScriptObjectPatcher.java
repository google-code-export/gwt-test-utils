package com.octo.gwt.test.internal.patchers;

import javassist.CtClass;
import javassist.CtField;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
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
    StringBuilder sb = new StringBuilder();
    sb.append(jso.getClass().getSimpleName()).append("[");
    // TODO : remove this code when overlay will be OK
    if (NodeList.class.isInstance(jso)) {
      sb.append(JavaScriptObjects.getJsoProperties(jso).getObject(
          JsoProperties.NODE_LIST_INNER_LIST));
    } else if (Element.class.isInstance(jso)) {
      Element e = jso.cast();
      sb.append(e.getTagName());
    } else if (Text.class.isInstance(jso)) {
      Text text = jso.cast();
      sb.append(text.getData());
    } else {
      sb.append(JavaScriptObjects.getJsoProperties(jso).size());
    }
    sb.append("]");

    return sb.toString();
  }

  @Override
  public void initClass(CtClass c) throws Exception {
    super.initClass(c);

    // add field "protected PropertyContainer __PROPERTIES__;"
    CtField propertiesField = CtField.make(
        "protected final " + PropertyContainer.class.getName() + " "
            + JavaScriptObjects.PROPERTIES + " = new "
            + PropertyContainer.class.getName() + "(this);", c);
    c.addField(propertiesField);
  }

}
