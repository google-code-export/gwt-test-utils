package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test.internal.utils.EventUtils;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(DOMImpl.class)
public class DOMImplUserPatcher {

  @PatchMethod
  public static int eventGetTypeInt(DOMImpl domImpl, String type) {
    return EventUtils.getEventTypeInt(type);
  }

  @PatchMethod
  public static Element getChild(DOMImpl domImpl, Element userElem, int index) {
    if (index >= userElem.getChildNodes().getLength()) {
      return null;
    }

    return userElem.getChildNodes().getItem(index).cast();
  }

  @PatchMethod
  public static int getChildCount(Object domImpl, Element elem) {
    return elem.getChildCount();
  }

  // Abstract methods

  @PatchMethod
  public static int getChildIndex(DOMImpl domImpl, Element parent, Element child) {
    if (parent == null || child == null) {
      return -1;
    }

    for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
      if (child.equals(parent.getChildNodes().getItem(i))) {
        return i;
      }
    }

    return -1;
  }

  @PatchMethod
  public static int getEventsSunk(DOMImpl domImpl, Element elem) {
    return 1;
  }

  @PatchMethod
  public static void initEventSystem(DOMImpl domImpl) {
  }

  @PatchMethod
  public static void insertChild(DOMImpl domImpl, Element userParent,
      Element userChild, int index) {
    NodePatcher.insertAtIndex(userParent, userChild, index);
  }

  @PatchMethod
  public static void releaseCapture(Object domImpl, Element elem) {

  }

  @PatchMethod
  public static void setCapture(Object domImpl, Element elem) {

  }

  @PatchMethod
  public static void setEventListener(DOMImpl domImpl, Element elem,
      EventListener listener) {

  }

  @PatchMethod
  public static void sinkEvents(DOMImpl domImpl, Element elem, int eventBits) {

  }

}
