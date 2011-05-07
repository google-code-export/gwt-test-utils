package com.octo.gwt.test.demo.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class SimpleCompositeTest extends DemoSimpleTest {

  private SimpleComposite composite;

  @Test
  public void checkMouseMoveOnPicture() {
    // Setup
    Image img = GwtReflectionUtils.getPrivateFieldValue(composite, "img");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");

    Assert.assertEquals("", label.getText());

    // Test
    Browser.mouseMove(img);

    // Assert
    Assert.assertEquals("mouse moved on picture !", label.getText());
  }

  @Before
  public void init() throws Exception {
    composite = new SimpleComposite();
  }

}
