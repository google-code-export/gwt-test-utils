package com.octo.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.octo.gwt.test.WindowOperationsHandler;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Window.class)
public class WindowPatcher {

  @PatchMethod
  public static void alert(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.alert(msg);
    }
  }

  @PatchMethod
  public static boolean confirm(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      return handler.confirm(msg);
    }

    return false;
  }

  @PatchMethod
  public static String getTitle() {
    return Document.get().getTitle();
  }

  @PatchMethod
  public static void moveBy(int dx, int dy) {

  }

  @PatchMethod
  public static void moveTo(int x, int y) {

  }

  @PatchMethod
  public static void open(String url, String name, String features) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.open(url, name, features);
    }
  }

  @PatchMethod
  public static void print() {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      handler.print();
    }
  }

  @PatchMethod
  public static String prompt(String msg, String initialValue) {
    WindowOperationsHandler handler = GwtConfig.get().getWindowOperationsHandler();
    if (handler != null) {
      return handler.prompt(msg, initialValue);
    }

    return null;
  }

  @PatchMethod
  public static void resizeBy(int width, int height) {

  }

  @PatchMethod
  public static void resizeTo(int width, int height) {

  }

  @PatchMethod
  public static void scrollTo(int left, int top) {

  }

  @PatchMethod
  public static void setMargin(String size) {
    Document.get().getBody().getStyle().setProperty("margin", size);
  }

  @PatchMethod
  public static void setStatus(String status) {

  }

  @PatchMethod
  public static void setTitle(String title) {
    Document.get().setTitle(title);
  }

}
