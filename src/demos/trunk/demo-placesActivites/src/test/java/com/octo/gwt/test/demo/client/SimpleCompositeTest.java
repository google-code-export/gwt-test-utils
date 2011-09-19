package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class SimpleCompositeTest extends DemoSimpleTest {

  private SimpleComposite composite;

  @Before
  public void init() throws Exception {
    composite = new SimpleComposite();
  }

  @Test
  public void mouseMove_Picture() {
    // Arrange
    Image img = GwtReflectionUtils.getPrivateFieldValue(composite, "img");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    assertEquals("", label.getText());

    // Act
    Browser.mouseMove(img);

    // Assert
    assertEquals("mouse moved on picture !", label.getText());
  }

}
