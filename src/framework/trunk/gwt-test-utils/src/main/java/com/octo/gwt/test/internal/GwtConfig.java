package com.octo.gwt.test.internal;

import java.util.Locale;

import com.octo.gwt.test.GwtLogHandler;

public class GwtConfig {

  private static final GwtConfig INSTANCE = new GwtConfig();

  public static GwtConfig get() {
    return INSTANCE;
  }

  private String hostPagePath;
  private Locale locale;
  private GwtLogHandler logHandler;
  private String moduleName;

  private GwtConfig() {

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

  public void reset() {
    locale = null;
    logHandler = null;
    hostPagePath = null;
    moduleName = null;
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

}
