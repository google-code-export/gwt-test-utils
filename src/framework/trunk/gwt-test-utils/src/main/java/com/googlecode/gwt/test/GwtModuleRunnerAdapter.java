package com.googlecode.gwt.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.ui.IsWidget;
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

  private static final String DEFAULT_WAR_DIR = "war/";
  private static final Logger LOGGER = LoggerFactory.getLogger(GwtConfig.class);
  private static final String MAVEN_DEFAULT_RES_DIR = "src/main/resources/";
  private static final String MAVEN_DEFAULT_WEB_DIR = "src/main/webapp/";

  private boolean canDispatchDomEventOnDetachedWidget;
  private Locale locale;
  private GwtLogHandler logHandler;
  private ServletConfig servletConfig;
  private WindowOperationsHandler windowOperationsHandler;

  public GwtModuleRunnerAdapter() {
    AfterTestCallbackManager.get().registerCallback(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#addDictionaryEntries(java.lang.String,
   * java.util.Map)
   */
  public void addDictionaryEntries(String dictionaryName,
      Map<String, String> entries) {

    Dictionary dictionary = Dictionary.getDictionary(dictionaryName);
    DictionaryUtils.addEntries(dictionary, entries);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#addGwtCreateHandler(com.googlecode.gwt.test
   * .GwtCreateHandler)
   */
  public void addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#addUiObjectTagFactory(com.googlecode.gwt
   * .test.uibinder.UiObjectTagFactory)
   */
  public void addUiObjectTagFactory(
      UiObjectTagFactory<? extends IsWidget> factory) {
    GwtConfig.get().getUiObjectTagFactories().add(factory);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.internal.AfterTestCallback#afterTest()
   */
  public void afterTest() throws Throwable {
    this.locale = null;
    this.locale = null;
    this.servletConfig = null;
    this.windowOperationsHandler = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#canDispatchDomEventOnDetachedWidget()
   */
  public boolean canDispatchDomEventOnDetachedWidget() {
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
  public BrowserErrorHandler getBrowserErrorHandler() {
    return new BrowserErrorHandler() {

      public void onError(String errorMessage) {
        // remove pending tasks, no need to execute
        FinallyCommandTrigger.clearPendingCommands();

        fail(errorMessage);
      }
    };
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getHostPagePath()
   */
  public String getHostPagePath() {
    return getHostPagePath(getModuleName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getLocale()
   */
  public Locale getLocale() {
    return locale;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.googlecode.gwt.test.GwtModuleRunner#getLogHandler()
   */
  public GwtLogHandler getLogHandler() {
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
  public WindowOperationsHandler getWindowOperationsHandler() {
    return windowOperationsHandler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunner#registerUiConstructor(java.lang.Class,
   * java.lang.String[])
   */
  public void registerUiConstructor(Class<? extends IsWidget> clazz,
      String... argNames) {
    GwtConfig.get().registerUiConstructor(clazz, argNames);
  }

  /**
   * Specifies the relative path in the project of the HTML file which is used
   * by the corresponding GWT module.
   * 
   * @param moduleFullQualifiedName The full qualifed name of the corresponding
   *          GWT module.
   * @return The relative path of the HTML file used.
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

    LOGGER.warn("Cannot find the actual HTML host page for module '"
        + getModuleName() + "'. You should override "
        + GwtModuleRunner.class.getName()
        + ".getHostPagePath() method to specify it.");

    return null;
  }

  protected void setCanDispatchDomEventOnDetachedWidget(
      boolean canDispatchDomEventOnDetachedWidget) {
    this.canDispatchDomEventOnDetachedWidget = canDispatchDomEventOnDetachedWidget;
  }

  protected void setLocale(Locale locale) {
    this.locale = locale;
  }

  protected void setLogHandler(GwtLogHandler logHandler) {
    this.logHandler = logHandler;
  }

  protected void setServletConfig(ServletConfig servletConfig) {
    this.servletConfig = servletConfig;
  }

  protected void setWindowOperationsHandler(
      WindowOperationsHandler windowOperationsHandler) {
    this.windowOperationsHandler = windowOperationsHandler;
  }

}
