package com.octo.gwt.test.internal;

import java.util.Locale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.WindowOperationsHandler;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Internal configuration of gwt-test-utils. <strong>For internal use
 * only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class GwtConfig implements AfterTestCallback {

  private static final GwtConfig INSTANCE = new GwtConfig();

  public static GwtConfig get() {
    return INSTANCE;
  }

  private boolean ensureDebugId;
  private String hostPagePath;
  private Locale locale;
  private GwtLogHandler logHandler;
  private String moduleName;
  private WindowOperationsHandler windowOperationsHandler;

  private GwtConfig() {
    AfterTestCallbackManager.get().registerCallback(this);

  }

  public void afterTest() throws Throwable {
    locale = null;
    logHandler = null;
    hostPagePath = null;
    moduleName = null;
    ensureDebugId = false;
  }

  public boolean ensureDebugId() {
    return ensureDebugId;
  }

  public String getHostPagePath() {
    return hostPagePath;
  }

  public Locale getLocale() {
    return locale;
  }

  public GwtLogHandler getLogHandler() {
    return logHandler;
  }

  public String getModuleName() {
    return moduleName;
  }

  public WindowOperationsHandler getWindowOperationsHandler() {
    return windowOperationsHandler;
  }

  public void setEnsureDebugId(boolean ensureDebugId) {
    this.ensureDebugId = ensureDebugId;
    // refresh the static instance in UIObject
    GwtReflectionUtils.setStaticField(UIObject.class, "debugIdImpl",
        GWT.create(DebugIdImpl.class));
  }

  public void setHostPagePath(String hostPagePath) {
    this.hostPagePath = hostPagePath;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setLogHandler(GwtLogHandler logHandler) {
    this.logHandler = logHandler;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public void setWindowOperationsHandler(
      WindowOperationsHandler windowOperationsHandler) {
    this.windowOperationsHandler = windowOperationsHandler;
  }

}
