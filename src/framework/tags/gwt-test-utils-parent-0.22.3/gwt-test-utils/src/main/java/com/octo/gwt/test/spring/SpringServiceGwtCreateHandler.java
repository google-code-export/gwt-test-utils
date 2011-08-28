package com.octo.gwt.test.spring;

import org.springframework.context.ApplicationContext;

import com.google.gwt.user.client.rpc.RemoteService;
import com.octo.gwt.test.GwtCreateHandler;
import com.octo.gwt.test.server.RemoteServiceCreateHandler;

/**
 * {@link GwtCreateHandler} to retrieve {@link RemoteService} beans in a Spring
 * context.
 * 
 * @author Gael Lazzari
 * 
 */
public abstract class SpringServiceGwtCreateHandler extends
    RemoteServiceCreateHandler {

  private final ApplicationContext applicationContext;

  public SpringServiceGwtCreateHandler(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  protected abstract Object findInSpringContext(
      ApplicationContext applicationContext, Class<?> remoteServiceClass,
      String remoteServiceRelativePath);

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.integration.RemoteServiceCreateHandler#findService(java
   * .lang.Class, java.lang.String)
   */
  @Override
  protected Object findService(Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {
    return findInSpringContext(applicationContext, remoteServiceClass,
        remoteServiceRelativePath);
  }

}
