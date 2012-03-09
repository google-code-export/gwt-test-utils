package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.UIObject.DebugIdImplEnabled;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.GwtLogHandler;
import com.octo.gwt.test.GwtModuleRunner;
import com.octo.gwt.test.WindowOperationsHandler;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.uibinder.UiObjectTagFactory;
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

  private String checkedModuleName;
  private final DebugIdImpl disabledInstance = new DebugIdImpl();
  private final DebugIdImpl enabledInstance = new DebugIdImplEnabled();
  private GwtModuleRunner gwtModuleRunner;
  private final Map<Class<?>, List<String[]>> uiConstructorsMap = new HashMap<Class<?>, List<String[]>>();

  private final List<UiObjectTagFactory<?>> uiObjectTagFactories = new ArrayList<UiObjectTagFactory<?>>();

  private GwtConfig() {
    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() throws Throwable {
    gwtModuleRunner = null;
    uiObjectTagFactories.clear();
    uiConstructorsMap.clear();
  }

  public boolean canDispatchDomEventOnDetachedWidget() {
    return gwtModuleRunner.canDispatchDomEventOnDetachedWidget();
  }

  public BrowserErrorHandler getBrowserErrorHandler() {
    return gwtModuleRunner.getBrowserErrorHandler();
  }

  public String getHostPagePath() {
    return gwtModuleRunner.getHostPagePath();
  }

  public Locale getLocale() {
    return gwtModuleRunner.getLocale();
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

  public List<String[]> getUiConstructors(Class<?> clazz) {
    return uiConstructorsMap.get(clazz);
  }

  public List<UiObjectTagFactory<?>> getUiObjectTagFactories() {
    return uiObjectTagFactories;
  }

  public WindowOperationsHandler getWindowOperationsHandler() {
    return gwtModuleRunner.getWindowOperationsHandler();
  }

  public void registerUiConstructor(
      Class<? extends Widget> classWithUiConstructor, String... argNames) {
    List<String[]> uiConstructors = uiConstructorsMap.get(classWithUiConstructor);
    if (uiConstructors == null) {
      uiConstructors = new ArrayList<String[]>();
      uiConstructorsMap.put(classWithUiConstructor, uiConstructors);
    }

    uiConstructors.add(argNames);
  }

  /**
   * Setup a GWT module to be run. <strong>This method must be run only once, at
   * the very beginning of the GWT module emulation.</strong>
   * 
   * @param gwtModuleRunner The configuration of the module to be run.
   */
  public void setup(GwtModuleRunner gwtModuleRunner) {
    if (this.gwtModuleRunner != null) {
      throw new GwtTestException(
          "Because of the single-threaded nature of the GWT environment, gwt-test-utils tests can not be run in multiple thread at the same time");
    }

    this.gwtModuleRunner = gwtModuleRunner;
    this.checkedModuleName = getCheckedModuleName();

    setupDebugIdImpl(gwtModuleRunner);

    registerUiConstructor(NamedFrame.class, "name");
    registerUiConstructor(RadioButton.class, "name");
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

}
