package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.octo.gwt.test.WindowOperationsHandler;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtDomUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

@PatchClass(Window.class)
class WindowPatcher {

  @PatchMethod
  static void alert(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.alert(msg);
    }
  }

  @PatchMethod
  static boolean confirm(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      return handler.confirm(msg);
    }

    return false;
  }

  @PatchMethod
  static String getTitle() {
    return Document.get().getTitle();
  }

  @PatchMethod
  static void moveBy(int dx, int dy) {

  }

  @PatchMethod
  static void moveTo(int x, int y) {

  }

  @PatchMethod
  static void open(String url, String name, String features) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.open(url, name, features);
    }
  }

  @PatchMethod
  static void print() {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.print();
    }
  }

  @PatchMethod
  static String prompt(String msg, String initialValue) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      return handler.prompt(msg, initialValue);
    }

    return null;
  }

  @PatchMethod
  static void resizeBy(int width, int height) {
    Element viewportElement = GwtReflectionUtils.callPrivateMethod(
        Document.get(), "getViewportElement");
    int currentWidth = Document.get().getClientWidth();
    GwtDomUtils.setClientWidth(viewportElement, currentWidth + width);

    int currentHeight = Document.get().getClientHeight();
    GwtDomUtils.setClientHeight(viewportElement, currentHeight + height);
  }

  @PatchMethod
  static void resizeTo(int width, int height) {
    Element viewportElement = GwtReflectionUtils.callPrivateMethod(
        Document.get(), "getViewportElement");

    GwtDomUtils.setClientWidth(viewportElement, width);
    GwtDomUtils.setClientHeight(viewportElement, height);
  }

  @PatchMethod
  static void scrollTo(int left, int top) {

  }

  @PatchMethod
  static void setMargin(String size) {
    Document.get().getBody().getStyle().setProperty("margin", size);
  }

  @PatchMethod
  static void setStatus(String status) {

  }

  @PatchMethod
  static void setTitle(String title) {
    Document.get().setTitle(title);
  }

}
