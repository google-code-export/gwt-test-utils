package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.Node;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(classes = {"com.google.gwt.xml.client.impl.NodeImpl"})
public class NodeImplPatcher {

  @PatchMethod
  public static String toString(Node nodeImpl) {
    JavaScriptObject jso = GwtReflectionUtils.getPrivateFieldValue(nodeImpl,
        "jsObject");
    return jso.toString();
  }

}
