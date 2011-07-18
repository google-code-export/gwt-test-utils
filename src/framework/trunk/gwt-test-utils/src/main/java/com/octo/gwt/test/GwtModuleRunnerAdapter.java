package com.octo.gwt.test;

import java.io.File;
import java.util.Locale;

import javax.servlet.ServletConfig;

import org.junit.Assert;

import com.octo.gwt.test.exceptions.GwtTestPatchException;
import com.octo.gwt.test.internal.handlers.GwtCreateHandlerManager;
import com.octo.gwt.test.utils.events.Browser.BrowserErrorHandler;

public abstract class GwtModuleRunnerAdapter implements GwtModuleRunner {

  private static final String DEFAULT_WAR_DIR = "war/";
  private static final String MAVEN_DEFAULT_RES_DIR = "src/main/resources/";
  private static final String MAVEN_DEFAULT_WEB_DIR = "src/main/webapp/";

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.GwtModuleRunner#addGwtCreateHandler(com.octo.gwt.test
   * .GwtCreateHandler)
   */
  public boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#ensureDebugId()
   */
  public boolean ensureDebugId() {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getBrowserErrorHandler()
   */
  public BrowserErrorHandler getBrowserErrorHandler() {
    return new BrowserErrorHandler() {

      public void onError(String errorMessage) {
        // remove pending tasks, no need to execute
        FinallyCommandTrigger.clearPendingCommands();

        Assert.fail(errorMessage);
      }
    };
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getHostPagePath()
   */
  public String getHostPagePath() {
    return getHostPagePath(getModuleName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getLocale()
   */
  public Locale getLocale() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getLogHandler()
   */
  public GwtLogHandler getLogHandler() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getModuleName()
   */
  public abstract String getModuleName();

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getLogHandler()
   */
  public ServletConfig getServletConfig() {
    throw new GwtTestPatchException(
        "No ServletConfig specified. You should override "
            + GwtModuleRunner.class.getSimpleName()
            + ".getServletConfig() to provide your own ServletConfig mocked instance");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.GwtModuleRunner#getWindowOperationsHandler()
   */
  public WindowOperationsHandler getWindowOperationsHandler() {
    return null;
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

    return null;
  }

}
