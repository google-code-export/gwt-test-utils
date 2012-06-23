package com.octo.gwt.test.demo.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.googlecode.gwt.test.utils.events.Browser;

public class SimpleCompositeTest2 extends DemoSimpleTest {

  private SimpleComposite2 composite2;

  @Test
  public void click_button() {
    // Arrange
    TextBox textBox = GwtReflectionUtils.getPrivateFieldValue(composite2,
        "textBox");
    Button button = GwtReflectionUtils.getPrivateFieldValue(composite2,
        "button");
    Label label = GwtReflectionUtils.getPrivateFieldValue(composite2, "label");
    ListBox listBox = GwtReflectionUtils.getPrivateFieldValue(composite2,
        "listBox");
    listBox.setSelectedIndex(1);

    // fill the textBox
    textBox.setText("Gael");
    Browser.change(textBox);

    assertEquals("this label will be updated", label.getText());
    assertEquals("Good morning",
        listBox.getItemText(listBox.getSelectedIndex()));

    // Act
    Browser.click(button);

    // Assert
    assertEquals("Good morning Gael", label.getText());
  }

  @Before
  public void init() throws Exception {
    composite2 = new SimpleComposite2();
  }

}
