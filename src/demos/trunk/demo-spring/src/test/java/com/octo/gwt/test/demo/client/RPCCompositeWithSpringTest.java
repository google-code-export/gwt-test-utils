package com.octo.gwt.test.demo.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class RPCCompositeWithSpringTest extends DemoSpringTest {

  private RPCComposite composite;

  @Test
  public void checkClick() {
    // Setup
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite, "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite, "label");
    Assert.assertEquals("", label.getText());

    // Test
    Browser.click(button);

    // Assert
    Assert.assertEquals("Bean \"OCTO\" has been created", label.getText());
  }

  @Before
  public void init() throws Exception {
    composite = new RPCComposite();
  }
}
