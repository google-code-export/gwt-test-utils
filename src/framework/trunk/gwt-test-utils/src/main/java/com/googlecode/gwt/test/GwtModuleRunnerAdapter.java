package com.googlecode.gwt.test;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.gwt.test.exceptions.GwtTestConfigurationException;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;
import com.googlecode.gwt.test.internal.AfterTestCallback;
import com.googlecode.gwt.test.internal.AfterTestCallbackManager;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.internal.handlers.GwtCreateHandlerManager;
import com.googlecode.gwt.test.internal.i18n.DictionaryUtils;
import com.googlecode.gwt.test.uibinder.UiObjectTagFactory;
import com.googlecode.gwt.test.utils.events.Browser.BrowserErrorHandler;

public abstract class GwtModuleRunnerAdapter implements GwtModuleRunner,
    AfterTestCallback {

  private final class BrowserErrorHandlerDelegate implements
      BrowserErrorHandler {

    private BrowserErrorHandler customHandler;
    private BrowserErrorHandler defaultHandler;

    public void onError(String errorMessage) {
      // remove pending tasks, no need to execute
      FinallyCommandTrigger.clearPendingCommands();

      if (customHandler != null) {
        customHandler.onError(errorMessage);
      } else {
        ensureDefault().onError(errorMessage);
      }
    }

    private BrowserErrorHandler ensureDefault() {
      if (defaultHandler == null) {
        defaultHandler = GwtModuleRunnerAdapter.this.getDefaultBrowserErrorHandler();
        if (defaultHandler == null) {
          throw new GwtTestConfigurationException(
              "You have to provide a non null instance of "
                  + BrowserErrorHandler.class.getSimpleName()
                  + " when overriding "
                  + GwtModuleRunnerAdapter.class.getName()
                  + ".getDefaultBrowserErrorHandler()");
        }
      }
      return defaultHandler;
    }

  }

  private static final String DEFAULT_WAR_DIR = "war/";
  private static final String MAVEN_DEFAULT_RES_DIR = "src/main/resources/";
  private static final String MAVEN_DEFAULT_WEB_DIR = "src/main/webapp/";

  private final BrowserErrorHandlerDelegate browserErrorHandlerDelegate;
  private boolean canDispatchDomEventOnDetachedWidget;
  private Locale locale;
  private GwtLogHandler logHandler;
  private ServletConfig servletConfig;
  private WindowOperationsHandler windowOperationsHandler;

  public GwtModuleRunnerAdapter() {
    browserErrorHandlerDelegate = new BrowserErrorHandlerDelegate();
    AfterTestCallbackManager.get().registerRemoveableCallback(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#addDictionaryEntries(java.lang.
   * String, java.util.Map)
   */
  public final void addDictionaryEntries(String dictionaryName,
      Map<String, String> entries) {

    Dictionary dictionary = Dictionary.getDictionary(dictionaryName);
    DictionaryUtils.addEntries(dictionary, entries);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#addGwtCreateHandler(com.googlecode
   * .gwt.test .GwtCreateHandler)
   */
  public final void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#addUiObjectTagFactory(com.googlecode
   * .gwt .test.uibinder.UiObjectTagFactory)
   */
  public final void addUiObjectTagFactory(
      UiObjectTagFactory<? extends IsWidget> factory) {
    GwtConfig.get().getUiObjectTagFactories().add(factory);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.internal.AfterTestCallback#afterTest()
   */
  public final void afterTest() throws Throwable {
    this.locale = null;
    this.servletConfig = null;
    this.windowOperationsHandler = null;
    this.browserErrorHandlerDelegate.customHandler = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#canDispatchDomEventOnDetachedWidget
   * ()
   */
  public final boolean canDispatchDomEventOnDetachedWidget() {
    return canDispatchDomEventOnDetachedWidget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#ensureDebugId()
   */
  public boolean ensureDebugId() {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getBrowserErrorHandler()
   */
  public final BrowserErrorHandler getBrowserErrorHandler() {
    return browserErrorHandlerDelegate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getHostPagePath()
   */
  public final String getHostPagePath() {
    return getHostPagePath(getModuleName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getLocale()
   */
  public final Locale getLocale() {
    return locale;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getLogHandler()
   */
  public final GwtLogHandler getLogHandler() {
    return logHandler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getModuleName()
   */
  public abstract String getModuleName();

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getLogHandler()
   */
  public ServletConfig getServletConfig() {
    if (servletConfig == null) {
      throw new GwtTestPatchException(
          "No ServletConfig specified. You have to set your own ServetConfig mocked instance with the protected 'setServletConfig' method available in your test class");
    }

    return servletConfig;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getWindowOperationsHandler()
   */
  public final WindowOperationsHandler getWindowOperationsHandler() {
    return windowOperationsHandler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#registerUiConstructor(java.lang
   * .Class, java.lang.String[])
   */
  public final void registerUiConstructor(Class<? extends IsWidget> clazz,
      String... argNames) {
    GwtConfig.get().registerUiConstructor(clazz, argNames);
  }

  protected abstract BrowserErrorHandler getDefaultBrowserErrorHandler();

  /**
   * Specifies the relative path in the project of the HTML file which is used
   * by the corresponding GWT module.
   * 
   * @param moduleFullQualifiedName The full qualifed name of the corresponding
   *          GWT module.
   * @return The relative path of the HTML file used, or null if there is no
   *         HTML file
   */
  protected String getHostPagePath(String moduleFullQualifiedName) {
    // try with gwt default structure
    String fileSimpleName = moduleFullQualifiedName.substring(moduleFullQualifiedName.lastIndexOf('.') + 1)
        + ".html";

    String fileRelativePath = DEFAULT_WAR_DIR + fileSimpleName;
    if (new File(fileRelativePath).exists()) {
      return fileRelativePath;
    }

    // try with the new maven archetype default path
    fileRelativePath = MAVEN_DEFAULT_WEB_DIR + fileSimpleName;
    if (new File(fileRelativePath).exists()) {
      return fileRelativePath;
    }

    // try with the old maven archetype default path
    String packagePath = moduleFullQualifiedName.substring(0,
        moduleFullQualifiedName.lastIndexOf('.') + 1).replaceAll("\\.", "/");

    fileRelativePath = MAVEN_DEFAULT_RES_DIR + packagePath + "public/"
        + fileSimpleName;
    if (new File(fileRelativePath).exists()) {
      return fileRelativePath;
    }

    // no HTML hostpage found
    return null;
  }

  protected final void setBrowserErrorHandler(
      BrowserErrorHandler browserErrorHandler) {
    this.browserErrorHandlerDelegate.customHandler = browserErrorHandler;
  }

  protected final void setCanDispatchDomEventOnDetachedWidget(
      boolean canDispatchDomEventOnDetachedWidget) {
    this.canDispatchDomEventOnDetachedWidget = canDispatchDomEventOnDetachedWidget;
  }

  protected final void setLocale(Locale locale) {
    this.locale = locale;
  }

  protected final void setLogHandler(GwtLogHandler logHandler) {
    this.logHandler = logHandler;
  }

  protected final void setServletConfig(ServletConfig servletConfig) {
    this.servletConfig = servletConfig;
  }

  protected final void setWindowOperationsHandler(
      WindowOperationsHandler windowOperationsHandler) {
    this.windowOperationsHandler = windowOperationsHandler;
  }

}
