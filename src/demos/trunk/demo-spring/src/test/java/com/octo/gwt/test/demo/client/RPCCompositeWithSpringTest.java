package com.octo.gwt.test.demo.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.spring.GwtSpringTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RPCCompositeWithSpringTest extends GwtSpringTest {

  private RPCComposite composite;

  @Test
  public void checkClick() {
    // Arrange
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");
    Assert.assertEquals("", label.getText());

    // Act
    Browser.click(button);

    // Assert
    Assert.assertEquals("Bean \"OCTO\" has been created", label.getText());
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
