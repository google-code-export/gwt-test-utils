package com.octo.gxt.test.internal.patchers;

import com.extjs.gxt.ui.client.core.XDOM;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(XDOM.class)
class XDOMPatcher {

  @PatchMethod
  static Element getBody() {
    return Document.get().getBody().cast();
  }

  @PatchMethod
  static int getBodyScrollLeft() {
    return 0;
  }

  @PatchMethod
  static int getBodyScrollTop() {
    return 0;
  }

  @PatchMethod
  static String getComputedStyle(Element e, String style) {
    return "";
  }

  @PatchMethod
  static Element getDocument() {
    return Document.get().getDocumentElement().cast();
  }

  @PatchMethod
  static int getDocumentHeight() {
    return Document.get().getDocumentElement().getClientHeight();
  }

  @PatchMethod
  static int getDocumentWidth() {
    return Document.get().getDocumentElement().getClientWidth();
  }

  @PatchMethod
  static int getScrollBarWidthInternal() {
    return 0;
  }

  @PatchMethod
  static int getViewportHeight() {
    return Document.get().getClientHeight();
  }

  @PatchMethod
  static int getViewportWidth() {
    return Document.get().getClientWidth();
  }
}
