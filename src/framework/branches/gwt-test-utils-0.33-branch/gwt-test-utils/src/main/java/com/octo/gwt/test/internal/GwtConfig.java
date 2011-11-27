package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletConfig;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.UIObject.DebugIdImplEnabled;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.GwtModuleRunner;
import com.octo.gwt.test.WindowOperationsHandler;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.uibinder.UiBinderWidgetFactory;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser.BrowserErrorHandler;

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

  /**
   * Setup a GWT module to be run. <strong>This method must be run only once, at
   * the very beginning of the GWT module emulation.</strong>
   * 
   * @param gwtModuleRunner The configuration of the module to be run.
   */
  public static void setup(GwtModuleRunner gwtModuleRunner) {
    INSTANCE.setupInstance(gwtModuleRunner);
  }

  private String checkedModuleName;

  private final DebugIdImpl disabledInstance = new DebugIdImpl();

  private final DebugIdImpl enabledInstance = new DebugIdImplEnabled();

  private GwtModuleRunner gwtModuleRunner;

  private Locale locale;

  private final List<UiBinderWidgetFactory> uiBinderWidgetFactories = new ArrayList<UiBinderWidgetFactory>();

  private GwtConfig() {

  }

  public void afterTest() throws Throwable {
    gwtModuleRunner = null;
    uiBinderWidgetFactories.clear();
  }

  public BrowserErrorHandler getBrowserErrorHandler() {
    return gwtModuleRunner.getBrowserErrorHandler();
  }

  public String getHostPagePath() {
    return gwtModuleRunner.getHostPagePath();
  }

  public Locale getLocale() {
    return locale;
  }

  public GwtLogHandler getLogHandler() {
    return gwtModuleRunner.getLogHandler();
  }

  public String getModuleName() {
    return checkedModuleName;
  }

  public ServletConfig getServletConfig() {
    return gwtModuleRunner.getServletConfig();
  }

  public List<UiBinderWidgetFactory> getUiBinderWidgetFactories() {
    return uiBinderWidgetFactories;
  }

  public WindowOperationsHandler getWindowOperationsHandler() {
    return gwtModuleRunner.getWindowOperationsHandler();
  }

  /**
   * For internal testing purpose
   * 
   * @param locale
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  private String getCheckedModuleName() {
    String moduleName = gwtModuleRunner.getModuleName();
    if (moduleName == null || "".equals(moduleName.trim())) {
      throw new GwtTestConfigurationException(
          "The tested module name returned by "
              + gwtModuleRunner.getClass().getName()
              + ".getModuleName() should not be null or empty");
    }

    String moduleAlias = ModuleData.get().getModuleAlias(moduleName);
    if (moduleAlias == null) {
      throw new GwtTestConfigurationException(
          "The tested module '"
              + moduleName
              + "' has not been found. Did you forget to declare a 'module-file' property in your 'META-INF/gwt-test-utils.properties' configuration file ?");
    }

    return moduleAlias;
  }

  private void setupDebugIdImpl(GwtModuleRunner gwtModuleRunner) {
    DebugIdImpl debugIdImplToUse = gwtModuleRunner.ensureDebugId()
        ? enabledInstance : disabledInstance;

    GwtReflectionUtils.setStaticField(UIObject.class, "debugIdImpl",
        debugIdImplToUse);
  }

  private void setupInstance(GwtModuleRunner gwtModuleRunner) {
    if (this.gwtModuleRunner != null) {
      throw new GwtTestException(
          "Because of the single-threaded nature of the GWT environment, gwt-test-utils tests can not be run in multiple thread at the same time");
    }

    this.gwtModuleRunner = gwtModuleRunner;
    this.checkedModuleName = getCheckedModuleName();

    setLocale(gwtModuleRunner.getLocale());
    setupDebugIdImpl(gwtModuleRunner);

    AfterTestCallbackManager.get().registerCallback(this);
  }

}
