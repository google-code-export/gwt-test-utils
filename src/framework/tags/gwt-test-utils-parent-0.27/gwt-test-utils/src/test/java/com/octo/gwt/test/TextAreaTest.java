package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class TextAreaTest extends GwtTest {

  @Test
  public void checkGetCursorPos() {
    // Set up
    TextArea t = new TextArea();
    t.setText("myText");
    GwtReflectionUtils.setPrivateFieldValue(t, "attached", true);

    // Test
    t.setCursorPos(2);

    Assert.assertEquals(2, t.getCursorPos());
  }

  @Test
  public void checkGetSelectionLength() {
    // Set up
    TextArea t = new TextArea();
    t.setText("myText");
    GwtReflectionUtils.setPrivateFieldValue(t, "attached", true);

    // Test
    t.setSelectionRange(1, 3);

    Assert.assertEquals(3, t.getSelectionLength());
  }

  @Test
  public void checkName() {
    TextArea t = new TextArea();
    t.setName("name");
    Assert.assertEquals("name", t.getName());
  }

  @Test
  public void checkText() {
    TextArea t = new TextArea();
    t.setText("text");
    Assert.assertEquals("text", t.getText());
  }

  @Test
  public void checkTitle() {
    TextArea t = new TextArea();
    t.setTitle("title");
    Assert.assertEquals("title", t.getTitle());
  }

  @Test
  public void checkVisible() {
    TextArea t = new TextArea();
    Assert.assertEquals(true, t.isVisible());
    t.setVisible(false);
    Assert.assertEquals(false, t.isVisible());
  }

  @Test
  public void checkVisibleLines() {
    TextArea t = new TextArea();
    t.setVisibleLines(10);

    Assert.assertEquals(10, t.getVisibleLines());
  }

}
