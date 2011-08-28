package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DOM.class)
class DOMPatcher {

  /**
   * Hack for GWT 2.0.4
   * 
   * @param parent
   * @param child
   */
  // TODO: remove when starting to support PotentialElement
  @PatchMethod
  static Element getFirstChild(Element elem) {
    Node firstChild = elem.getFirstChildElement();
    if (firstChild != null) {
      return firstChild.cast();
    } else {
      return null;
    }
  }

  @PatchMethod
  static Element getNextSibling(Element elem) {
    return (Element) elem.getNextSibling();
  }

  @PatchMethod
  static Element getParent(Element elem) {
    com.google.gwt.dom.client.Element parentElem = elem.getParentElement();

    if (parentElem == null) {
      return null;
    }

    Element parent = parentElem.cast();
    return parent;

  }

}
