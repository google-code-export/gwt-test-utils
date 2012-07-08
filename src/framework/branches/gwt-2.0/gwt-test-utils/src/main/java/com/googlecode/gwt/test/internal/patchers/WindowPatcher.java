package com.googlecode.gwt.test.internal.patchers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.googlecode.gwt.test.WindowOperationsHandler;
import com.googlecode.gwt.test.exceptions.GwtTestConfigurationException;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(Window.class)
class WindowPatcher {

  @PatchMethod
  static void alert(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getModuleRunner().getWindowOperationsHandler();
    if (handler != null) {
      handler.alert(msg);
    }
  }

  @PatchMethod
  static boolean confirm(String msg) {
    WindowOperationsHandler handler = GwtConfig.get().getModuleRunner().getWindowOperationsHandler();
    if (handler == null) {
      throw new GwtTestConfigurationException(
          "A call to Window.confirm(msg) was triggered, but no "
              + WindowOperationsHandler.class.getSimpleName()
              + " has been registered. You need to setup your own with the protected 'setWindowOperationsHandler' method available in your test class");

    }

    return handler.confirm(msg);
  }

  @PatchMethod
  static String getTitle() {
    return Document.get().getTitle();
  }

  @PatchMethod
  static void open(String url, String name, String features) {
    WindowOperationsHandler handler = GwtConfig.get().getModuleRunner().getWindowOperationsHandler();
    if (handler != null) {
      handler.open(url, name, features);
    }
  }

  @PatchMethod
  static void print() {
    WindowOperationsHandler handler = GwtConfig.get().getModuleRunner().getWindowOperationsHandler();
    if (handler != null) {
      handler.print();
    }
  }

  @PatchMethod
  static String prompt(String msg, String initialValue) {
    WindowOperationsHandler handler = GwtConfig.get().getModuleRunner().getWindowOperationsHandler();
    if (handler != null) {
      return handler.prompt(msg, initialValue);
    }

    return null;
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
