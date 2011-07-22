package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.spring.GwtSpringTest;
import com.octo.gwt.test.spring.GwtTestContextLoader;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"}, loader = GwtTestContextLoader.class)
public class DemoSpringTest extends GwtSpringTest {

  private RPCComposite composite;

  @Test
  public void click_button() {
    // Arrange
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");
    assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    assertEquals("Bean \"OCTO\" has been created", label.getText());
  }

  @Override
  public String getModuleName() {
    return "com.octo.gwt.test.demo.Application";
  }

  @Before
  public void init() throws Exception {
    composite = new RPCComposite();
  }

  @Override
  protected Object findRpcServiceInSpringContext(
      ApplicationContext applicationContext, Class<?> remoteServiceClass,
      String remoteServiceRelativePath) {

    if ("rpc/myService".equals(remoteServiceRelativePath)) {
      return applicationContext.getBean("myService");
    }
    return null;
  }
}
