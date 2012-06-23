package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.gwt.test.spring.GwtSpringTest;
import com.googlecode.gwt.test.spring.GwtTestContextLoader;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.googlecode.gwt.test.utils.events.Browser;
import com.octo.gwt.test.demo.server.FooBeanFactory;
import com.octo.gwt.test.demo.server.FooBeanFactorySimple;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"}, loader = GwtTestContextLoader.class)
public class DemoSpringTest extends GwtSpringTest {

  private RPCComposite composite;

  @Autowired
  private FooBeanFactory fooBeanFactory;

  @Before
  public void beforeDemoSpringTest() throws Exception {
    composite = new RPCComposite();

    // Assert this test has been injected object it needs
    assertNotNull(fooBeanFactory);
    assertEquals(FooBeanFactorySimple.class, fooBeanFactory.getClass());
  }

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

}
