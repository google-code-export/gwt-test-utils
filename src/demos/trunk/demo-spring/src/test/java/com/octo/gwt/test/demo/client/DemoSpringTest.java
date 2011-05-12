package com.octo.gwt.test.demo.client;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import com.octo.gwt.test.GwtTest;
import com.octo.gwt.test.spring.GwtSpringRunner;
import com.octo.gwt.test.spring.SpringServiceGwtCreateHandler;

@RunWith(GwtSpringRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class DemoSpringTest extends GwtTest implements
    ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @Before
  public void initDemoSpringTest() throws Exception {
    // add a new RemoteServiceHandler which will call Spring context
    addGwtCreateHandler(new SpringServiceGwtCreateHandler(applicationContext) {

      @Override
      protected Object findInSpringContext(
          ApplicationContext applicationContext, Class<?> remoteServiceClass,
          String remoteServiceRelativePath) {
        if ("rpc/myService".equals(remoteServiceRelativePath)) {
          return applicationContext.getBean("myService");
        }
        return null;
      }
    });
  }

  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }

}
