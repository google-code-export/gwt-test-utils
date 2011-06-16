package com.octo.gwt.test;

import java.io.File;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.Window;
import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.GwtClassLoader;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.GwtReset;
import com.octo.gwt.test.internal.ModuleData;
import com.octo.gwt.test.internal.handlers.GwtCreateHandlerManager;

/**
 * <p>
 * Base class for test classes which need to manipulate (directly or indirectly)
 * GWT components.
 * </p>
 * 
 * <p>
 * It provides the mechanism which allows the instantiation of GWT components in
 * the Java Virtual Machine, by beeing launched with the {@link GwtRunner} JUnit
 * Runner.
 * </p>
 * 
 * <p>
 * Class loading parameters used to instantiate classes referenced in tests can
 * be configured using the META-INF\gwt-test-utils.properties file of your
 * application.
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
@RunWith(GwtRunner.class)
public abstract class GwtTest {

  private static final String DEFAULT_WAR_DIR = "war/";
  private static final String MAVEN_DEFAULT_RES_DIR = "src/main/resources/";
  private static final String MAVEN_DEFAULT_WEB_DIR = "src/main/webapp/";

  /**
   * Bind the GwtClassLoader to the current thread
   */
  @BeforeClass
  public static final void bindClassLoader() {
    Thread.currentThread().setContextClassLoader(GwtClassLoader.get());
  }

  /**
   * Unbind the static classloader instance from the current thread by binding
   * the system classloader instead.
   */
  @AfterClass
  public static final void unbindClassLoader() {
    Thread.currentThread().setContextClassLoader(
        GwtClassLoader.get().getParent());
  }

  /**
   * <p>
   * Specifies a module to use when running this test case. Subclasses must
   * return the name of a module that will cause the source for that subclass to
   * be included.
   * </p>
   * 
   * <p>
   * If the return module name is not present in the gwt-test-utils
   * configuration file (e.g. "META-INF/gwt-test-utils.properties"), an
   * exception will be thrown.
   * </p>
   * 
   * @return the fully qualified name of a module. <strong>It cannot be null or
   *         empty</strong>.
   * 
   * 
   */
  public abstract String getModuleName();

  @Before
  public final void setUpGwtTest() throws Exception {
    GwtConfig.get().setLocale(getLocale());
    GwtConfig.get().setLogHandler(getLogHandler());
    GwtConfig.get().setWindowOperationsHandler(getWindowOperationsHandler());
    GwtConfig.get().setEnsureDebugId(ensureDebugId());

    String moduleName = getCheckedModuleName();
    GwtConfig.get().setModuleName(moduleName);

    GwtConfig.get().setHostPagePath(getHostPagePath(getModuleName()));
  }

  @After
  public final void tearDownGwtTest() throws Exception {
    resetPatchGwt();
  }

  protected boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  /**
   * Override this method if you want your test to allow the setup of debug id.
   * 
   * @return true if setting debug id should be enabled, false otherwise.
   */
  protected boolean ensureDebugId() {
    return false;
  }

  /**
   * Return the relative path in the project of the HTML file which is used by
   * the corresponding GWT module.
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

  protected Locale getLocale() {
    // this method can be overrided by subclass
    return null;
  }

  protected GwtLogHandler getLogHandler() {
    // this method can be overrided by subclass
    return null;
  }

  /**
   * Override this method to provide a custom {@link WindowOperationsHandler}.
   * 
   * <p>
   * This default implementation return null.
   * </p>
   * .
   * 
   * @return A custom Window to which static method calls from {@link Window}
   *         are delegate.
   */
  protected WindowOperationsHandler getWindowOperationsHandler() {
    return null;
  }

  protected void resetPatchGwt() throws Exception {
    // reinit GWT
    GwtReset.reset();
  }

  private String getCheckedModuleName() {
    String moduleName = getModuleName();
    if (moduleName == null || "".equals(moduleName.trim())) {
      throw new GwtTestConfigurationException(
          "The tested module name returned by " + this.getClass().getName()
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

}
