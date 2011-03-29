package com.octo.gwt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class TextBoxTest extends GwtTest {

  private class KeyPressEventData {

    public char charCode;
    public int keyCode;
  }

  @Test
  public void checkGetCursorPos() {
    // Set up
    TextBox t = new TextBox();
    t.setText("myText");
    GwtReflectionUtils.setPrivateFieldValue(t, "attached", true);

    // Test
    t.setCursorPos(2);

    Assert.assertEquals(2, t.getCursorPos());
  }

  @Test
  public void checkMaxLength() {
    TextBox t = new TextBox();
    t.setMaxLength(10);

    Assert.assertEquals(10, t.getMaxLength());
  }

  @Test
  public void checkName() {
    TextBox t = new TextBox();
    t.setName("name");
    Assert.assertEquals("name", t.getName());
  }

  @Test
  public void checkPressKey() {
    // Setup
    final List<KeyPressEventData> events = new ArrayList<KeyPressEventData>();
    TextBox tb = new TextBox();

    tb.addKeyPressHandler(new KeyPressHandler() {

      public void onKeyPress(KeyPressEvent event) {
        KeyPressEventData data = new KeyPressEventData();
        data.keyCode = event.getNativeEvent().getKeyCode();
        data.charCode = event.getCharCode();
        events.add(data);
      }
    });

    // Test
    Browser.fillText(tb, "gael");

    // Assert
    Assert.assertEquals("gael", tb.getValue());
    Assert.assertEquals(4, events.size());
    Assert.assertEquals('g', events.get(0).charCode);
    Assert.assertEquals(103, events.get(0).keyCode);
    Assert.assertEquals('a', events.get(1).charCode);
    Assert.assertEquals(97, events.get(1).keyCode);
    Assert.assertEquals('e', events.get(2).charCode);
    Assert.assertEquals(101, events.get(2).keyCode);
    Assert.assertEquals('l', events.get(3).charCode);
    Assert.assertEquals(108, events.get(3).keyCode);
  }

  @Test
  public void checkText() {
    TextBox t = new TextBox();
    t.setText("text");
    Assert.assertEquals("text", t.getText());
  }

  @Test
  public void checkTitle() {
    TextBox t = new TextBox();
    t.setTitle("title");
    Assert.assertEquals("title", t.getTitle());
  }

  @Test
  public void checkValue() {
    TextBox t = new TextBox();
    Assert.assertEquals("", t.getValue());
  }

  @Test
  public void checkVisible() {
    TextBox t = new TextBox();
    Assert.assertEquals(true, t.isVisible());
    t.setVisible(false);
    Assert.assertEquals(false, t.isVisible());
  }
}
