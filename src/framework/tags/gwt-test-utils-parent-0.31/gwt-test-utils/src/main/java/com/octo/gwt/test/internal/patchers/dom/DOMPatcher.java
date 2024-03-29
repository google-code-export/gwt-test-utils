package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DOM.class)
class DOMPatcher {

  @PatchMethod
  static Element getFirstChild(Element elem) {
    Node firstChild = elem.getFirstChildElement();
    if (firstChild != null) {
      return firstChild.cast();
    } else {
      return null;
    }
  }
}
