package com.octo.gwt.test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.exceptions.GwtTestConfigurationException;
import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.GwtReset;
import com.octo.gwt.test.internal.ModuleData;

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
   */
  public abstract String getModuleName();

  @Before
  public void setUpGwtTest() throws Exception {
    GwtConfig.get().setLocale(getLocale());
    GwtConfig.get().setLogHandler(getLogHandler());

    String moduleName = getCheckedModuleName();
    GwtConfig.get().setModuleName(moduleName);

    GwtConfig.get().setHostPagePath(getHostPagePath(getModuleName()));
  }

  @After
  public void tearDownGwtTest() throws Exception {
    resetPatchGwt();
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

  protected boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  protected String getHostPagePath(String moduleFullQualifiedName) {
    // TODO: refactor this scary method :-o
    String fileSimpleName = moduleFullQualifiedName.substring(moduleFullQualifiedName.lastIndexOf('.') + 1)
        + ".html";

    URL classesURL = GwtTest.class.getClassLoader().getResource(".");
    File classDir = null;
    try {
      classDir = new File(classesURL.toURI());
    } catch (URISyntaxException e) {
      throw new GwtTestConfigurationException(
          "Error while trying to load HTML host page for module '"
              + moduleFullQualifiedName + "'", e);
    }

    // try at the root of the test classpath
    File file = new File(classDir, fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }

    String fileRelativePath = moduleFullQualifiedName.substring(0,
        moduleFullQualifiedName.lastIndexOf('.') + 1).replaceAll("\\.", "/");

    // try in package folder at the root of the test classpath
    file = new File(classDir, fileRelativePath + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }

    // try old maven archetype fashion (with a index.html which redirect to a
    // html file in
    // a "public" folder in src/main/resources
    file = new File(classDir, fileRelativePath + "/public/" + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }

    // try in the "war" folder at the root of the projet
    File root = classDir.equals("target") ? classDir.getParentFile()
        : classDir.getParentFile().getParentFile();

    file = new File(root, "war/" + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }
    // try in "war folder, with package name
    file = new File(root, "war/" + fileRelativePath + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }

    // try in src/main/webapp
    file = new File(root, "src/main/webapp/" + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
    }
    // try in "src/main/webapp", with package name
    file = new File(root, "src/main/webapp/" + fileRelativePath
        + fileSimpleName);
    if (file.exists()) {
      return file.getAbsolutePath();
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

  protected void resetPatchGwt() throws Exception {
    // reinit GWT
    GwtReset.reset();
  }

}
