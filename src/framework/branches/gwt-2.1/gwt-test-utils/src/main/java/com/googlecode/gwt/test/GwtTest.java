package com.googlecode.gwt.test;

import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.internal.AfterTestCallbackManager;
import com.googlecode.gwt.test.internal.GwtClassLoader;
import com.googlecode.gwt.test.internal.GwtConfig;
import com.googlecode.gwt.test.utils.events.Browser.BrowserErrorHandler;

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
public abstract class GwtTest extends GwtModuleRunnerAdapter implements Test {

  private static final BrowserErrorHandler JUNIT_BROWSER_ERROR_HANDLER = new BrowserErrorHandler() {

    public void onError(String errorMessage) {
      fail(errorMessage);
    }
  };

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

  private final Test test;

  public GwtTest() {
    this.test = createJUnit4TestAdapter();
    this.setCanDispatchDomEventOnDetachedWidget(true);
  }

  public int countTestCases() {
    return test.countTestCases();
  }

  /**
   * Runs a test and collects its result in a TestResult instance.
   */
  public void run(TestResult result) {
    test.run(result);
  }

  @Before
  public final void setUpGwtTest() throws Exception {
    GwtConfig.get().setup(this);
  }

  @After
  public final void tearDownGwtTest() throws Exception {

    GwtReset.get().reset();

    List<Throwable> throwables = AfterTestCallbackManager.get().triggerCallbacks();

    if (throwables.size() > 0) {
      throw new GwtTestException(
          throwables.size()
              + " exception(s) thrown during JUnit @After callback. First one is thrown :",
          throwables.get(0));
    }

  }

  /**
   * Create a test instance compatible with JUnit 3 {@link Test} so that the
   * current <code>GwtTest</code> can be added to a {@link TestSuite}.
   * 
   * @return A JUnit Test adapter for this test.
   */
  protected Test createJUnit4TestAdapter() {
    return new JUnit4TestAdapter(this.getClass());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.googlecode.gwt.test.GwtModuleRunnerAdapter#getDefaultBrowserErrorHandler
   * ()
   */
  @Override
  protected BrowserErrorHandler getDefaultBrowserErrorHandler() {
    return JUNIT_BROWSER_ERROR_HANDLER;
  }

}
