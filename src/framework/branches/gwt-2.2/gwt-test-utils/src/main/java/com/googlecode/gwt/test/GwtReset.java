package com.googlecode.gwt.test;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

/**
 * Class in charge of reseting all necessary GWT internal objects after the
 * execution of a unit test. <strong>For internal use only.</strong>
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
class GwtReset {

  private static final GwtReset INSTANCE = new GwtReset();

  static GwtReset get() {
    return INSTANCE;
  }

  private static void getStaticAndCallClear(Class<?> clazz, String fieldName) {
    GwtReflectionUtils.callPrivateMethod(
        GwtReflectionUtils.getStaticFieldValue(clazz, fieldName), "clear");
  }

  private GwtReset() {
  }

  void reset() throws Exception {
    getStaticAndCallClear(Timer.class, "timers");
    getStaticAndCallClear(RootPanel.class, "rootPanels");
    GwtReflectionUtils.setStaticField(RootLayoutPanel.class, "singleton", null);
    getStaticAndCallClear(RootPanel.class, "widgetsToDetach");

    Object commandExecutor = GwtReflectionUtils.getStaticFieldValue(
        Class.forName("com.google.gwt.user.client.DeferredCommand"),
        "commandExecutor");
    GwtReflectionUtils.callPrivateMethod(
        GwtReflectionUtils.getPrivateFieldValue(commandExecutor, "commands"),
        "clear");

    GwtReflectionUtils.setStaticField(NumberFormat.class,
        "cachedDecimalFormat", null);
    GwtReflectionUtils.setStaticField(NumberFormat.class,
        "cachedScientificFormat", null);
    GwtReflectionUtils.setStaticField(NumberFormat.class,
        "cachedPercentFormat", null);
    GwtReflectionUtils.setStaticField(NumberFormat.class,
        "cachedCurrencyFormat", null);

    GwtReflectionUtils.setPrivateFieldValue(LocaleInfo.getCurrentLocale(),
        "dateTimeConstants", null);
    GwtReflectionUtils.setPrivateFieldValue(LocaleInfo.getCurrentLocale(),
        "dateTimeFormatInfo", null);
    GwtReflectionUtils.setPrivateFieldValue(LocaleInfo.getCurrentLocale(),
        "numberConstants", null);
    getStaticAndCallClear(DateTimeFormat.class, "cache");

    GwtReflectionUtils.setStaticField(Window.class, "handlers", null);
    GwtReflectionUtils.setStaticField(Event.class, "handlers", null);

  }
}
