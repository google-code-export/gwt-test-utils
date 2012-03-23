package com.octo.gwt.test.internal.patchers.dom;

import java.util.List;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.internal.utils.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(NodeList.class)
class NodeListPatcher {

  @PatchMethod
  static <T extends Node> T getItem(NodeList<T> nodeList, int index) {
    if (nodeList.getLength() <= index) {
      return null;
    } else {
      List<T> innerList = JavaScriptObjects.getObject(nodeList,
          JsoProperties.NODE_LIST_INNER_LIST);
      return innerList.get(index);
    }
  }

  @PatchMethod
  static <T extends Node> int getLength(NodeList<T> nodeList) {
    List<T> innerList = JavaScriptObjects.getObject(nodeList,
        JsoProperties.NODE_LIST_INNER_LIST);
    return innerList.size();
  }

}
