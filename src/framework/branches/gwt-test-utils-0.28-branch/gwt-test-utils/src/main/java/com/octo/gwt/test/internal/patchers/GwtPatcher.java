package com.octo.gwt.test.internal.patchers;

import com.google.gwt.core.client.GWT;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.Mock;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.handlers.GwtCreateHandlerManager;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(GWT.class)
class GwtPatcher {

  @PatchMethod
  static Object create(Class<?> classLiteral) {
    for (GwtCreateHandler gwtCreateHandler : GwtCreateHandlerManager.get().getGwtCreateHandlers()) {
      try {
        Object o = gwtCreateHandler.create(classLiteral);
        if (o != null) {
          return o;
        }
      } catch (Exception e) {
        if (GwtTestException.class.isInstance(e)) {
          throw (GwtTestException) e;
        } else {
          throw new GwtTestPatchException("Error while creation instance of '"
              + classLiteral.getName() + "' through '"
              + gwtCreateHandler.getClass().getName() + "' instance", e);
        }
      }
    }

    throw new GwtTestConfigurationException(
        "No declared "
            + GwtCreateHandler.class.getSimpleName()
            + " has been able to create an instance of '"
            + classLiteral.getName()
            + "'. You should add our own with 'GwtTest.addGwtCreateHandler(..)' method or declared your tested object with @"
            + Mock.class.getSimpleName());
  }

  @PatchMethod
  static String getVersion() {
    return "GWT by gwt-test-utils";
  }

  @PatchMethod
  static boolean isClient() {
    return true;
  }

  @PatchMethod
  static void log(String message, Throwable t) {
    GwtLogHandler logHandler = GwtConfig.get().getLogHandler();
    if (logHandler != null) {
      logHandler.log(message, t);
    }
  }

}
