package com.googlecode.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.UIObject.DebugIdImpl;
import com.google.gwt.user.client.ui.UIObject.DebugIdImplEnabled;
import com.googlecode.gwt.test.GwtModuleRunner;
import com.googlecode.gwt.test.exceptions.GwtTestConfigurationException;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.uibinder.UiObjectTagFactory;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

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

  private final List<UiObjectTagFactory<?>> uiObjectTagFactories = new ArrayList<UiObjectTagFactory<?>>();

  private GwtConfig() {
    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() throws Throwable {
    gwtModuleRunner = null;
    uiObjectTagFactories.clear();
  }

  public String getModuleName() {
    return checkedModuleName;
  }

  public GwtModuleRunner getModuleRunner() {
    return gwtModuleRunner;
  }

  public List<UiObjectTagFactory<?>> getUiObjectTagFactories() {
    return uiObjectTagFactories;
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

    setupDebugIdImpl(gwtModuleRunner.ensureDebugId());

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

  private void setupDebugIdImpl(boolean ensureDebugId) {
    DebugIdImpl debugIdImplToUse = ensureDebugId ? enabledInstance
        : disabledInstance;

    GwtReflectionUtils.setStaticField(UIObject.class, "debugIdImpl",
        debugIdImplToUse);
  }

}
