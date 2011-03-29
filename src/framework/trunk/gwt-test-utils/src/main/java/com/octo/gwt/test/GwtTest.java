package com.octo.gwt.test;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.octo.gwt.test.internal.GwtConfig;
import com.octo.gwt.test.internal.GwtCreateHandlerManager;
import com.octo.gwt.test.internal.GwtReset;

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

  @Before
  public void setUpGwtTest() throws Exception {
    GwtConfig.get().setLocale(getLocale());
    GwtConfig.get().setLogHandler(getLogHandler());
    GwtConfig.get().setHostPagePath(getHostPagePath());

    String moduleName = getModuleName();
    if (moduleName == null) {
      if (getCurrentTestedModuleFile() != null) {
        moduleName = getCurrentTestedModuleFile().substring(0,
            getCurrentTestedModuleFile().toLowerCase().indexOf(".gwt.xml")).replaceAll(
            "/", ".");
      }
    }

    GwtConfig.get().setModuleName(moduleName);
  }

  @After
  public void tearDownGwtTest() throws Exception {
    resetPatchGwt();
  }

  protected boolean addGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    return GwtCreateHandlerManager.get().addGwtCreateHandler(gwtCreateHandler);
  }

  @Deprecated
  protected String getCurrentTestedModuleFile() {
    // this method can be overrided by subclass
    return null;
  }

  protected String getHostPagePath() {
    // this method can be overrided by subclass
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

  protected String getModuleName() {
    // this method can be overrided by subclass
    return null;
  }

  protected void resetPatchGwt() throws Exception {
    // reinit GWT
    GwtReset.reset();
  }

  @Deprecated
  protected void setGwtCreateHandler(GwtCreateHandler gwtCreateHandler) {
    addGwtCreateHandler(gwtCreateHandler);
  }

}
