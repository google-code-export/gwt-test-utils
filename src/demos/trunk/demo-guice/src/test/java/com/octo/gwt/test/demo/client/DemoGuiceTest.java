package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.octo.gwt.test.demo.server.FooBeanFactory;
import com.octo.gwt.test.demo.server.FooBeanFactorySimple;
import com.octo.gwt.test.guice.GwtGuiceTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class DemoGuiceTest extends GwtGuiceTest {

  private RPCComposite composite;

  @Inject
  private FooBeanFactory fooBeanFactory;

  @Before
  public void beforeDemoGuiceTest() throws Exception {
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
