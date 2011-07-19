package com.octo.gwt.test;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.internal.AfterTestCallbackManager;
import com.octo.gwt.test.internal.GwtClassLoader;
import com.octo.gwt.test.internal.GwtConfig;

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
public abstract class GwtTest extends GwtModuleRunnerAdapter {

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

  @Before
  public final void setUpGwtTest() throws Exception {
    GwtConfig.setup(this);
  }

  @After
  public final void tearDownGwtTest() throws Exception {

    List<Throwable> throwables = AfterTestCallbackManager.get().triggerCallbacks();

    if (throwables.size() > 0) {
      throw new GwtTestException(
          throwables.size()
              + " exception(s) thrown during JUnit @After callback. First one is thrown :",
          throwables.get(0));
    }

  }

}
