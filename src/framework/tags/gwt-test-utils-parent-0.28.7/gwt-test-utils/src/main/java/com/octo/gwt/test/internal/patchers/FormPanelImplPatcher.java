package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(FormPanelImpl.class)
class FormPanelImplPatcher {

  @PatchMethod
  static String getContents(FormPanelImpl panelImpl, Element iframe) {
    return iframe.getInnerHTML();
  }

  @PatchMethod
  static String getEncoding(FormPanelImpl panelImpl, Element form) {
    return JavaScriptObjects.getString(form, "enctype");
  }

  @PatchMethod
  static void hookEvents(FormPanelImpl panelImpl, Element iframe, Element form,
      FormPanelImplHost listener) {

  }

  @PatchMethod
  static void setEncoding(FormPanelImpl panelImpl, Element form, String encoding) {
    JavaScriptObjects.setProperty(form, "enctype", encoding);
    JavaScriptObjects.setProperty(form, "encoding", encoding);
  }

  @PatchMethod
  static void unhookEvents(FormPanelImpl panelImpl, Element iframe, Element form) {

  }

}
