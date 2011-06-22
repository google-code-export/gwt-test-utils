package com.octo.gwt.test.spring;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test.GwtTest;

/**
 * <p>
 * Base class for tests that needs to be run with <code>spring-test</code>
 * module.
 * </p>
 * <p>
 * Subclasses <strong>must be annotated</strong> with the spring
 * {@link ContextConfiguration} annotation to configure the spring files
 * location to use to build the test context.
 * </p>
 * <p>
 * You can autowire any spring bean in subclasses. For {@link RemoteService}
 * beans, implement the
 * {@link GwtSpringTest#findRpcServiceInSpringContext(ApplicationContext, Class, String)}
 * method to get deferred binding working in test mode.
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
@RunWith(GwtSpringRunner.class)
public abstract class GwtSpringTest extends GwtTest implements
    ApplicationContextAware {

  private ApplicationContext applicationContext;

  /**
   * Test initialization method.
   */
  @Before
  public void initGwtSpringTest() {
    // add a new RemoteServiceHandler which will call Spring context
    addGwtCreateHandler(new SpringServiceGwtCreateHandler(applicationContext) {

      @Override
      protected Object findInSpringContext(
          ApplicationContext applicationContext, Class<?> remoteServiceClass,
          String remoteServiceRelativePath) {
        return findRpcServiceInSpringContext(applicationContext,
            remoteServiceClass, remoteServiceRelativePath);
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.context.ApplicationContextAware#setApplicationContext
   * (org.springframework.context.ApplicationContext)
   */
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * Implement this method to retrieve {@link RemoteService} instance declared
   * in a Spring context.
   * 
   * @param applicationContext The spring context.
   * @param remoteServiceClass The remote service interface of the spring bean
   *          to retrieve.
   * @param remoteServiceRelativePath The remote service relative path of the
   *          spring bean to retrieve.
   * @return The corresponding spring bean, or null if no bean has been found
   *         for this type and path.
   */
  protected abstract Object findRpcServiceInSpringContext(
      ApplicationContext applicationContext, Class<?> remoteServiceClass,
      String remoteServiceRelativePath);

  /**
   * Get the Spring context which as been injected in the test class.
   * 
   * @return The injected Spring context.
   * 
   * @see ApplicationContextAware
   */
  protected ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
