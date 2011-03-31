package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test.utils.events.Browser;

public class CheckBoxTest extends GwtTest {

  @Test
  public void checkCheckBoxClick() {
    CheckBox cb = new CheckBox();
    cb.setValue(false);
    cb.setFocus(true);

    Assert.assertEquals(false, cb.getValue());

    // we must use the parent click method to simule the click without
    // registering an handler
    Browser.click(cb);
    Assert.assertEquals(true, cb.getValue());
  }

  @Test
  public void checkChecked() {
    CheckBox cb = new CheckBox();
    Assert.assertEquals(false, cb.getValue());
    cb.setValue(true);
    Assert.assertEquals(true, cb.getValue());
  }

  @Test
  public void checkFormValue() {
    // Setup
    CheckBox cb = new CheckBox();
    Assert.assertEquals("", cb.getFormValue());
    cb.setFormValue("whatever");

    // Test
    String formValue = cb.getFormValue();

    // Assert
    Assert.assertEquals("whatever", formValue);

  }

  @Test
  public void checkHTML() {
    CheckBox cb = new CheckBox("<h1>foo</h1>", true);
    Assert.assertEquals("<h1>foo</h1>", cb.getHTML());
    cb.setHTML("<h1>test</h1>");
    Assert.assertEquals("<h1>test</h1>", cb.getHTML());
    // Assert the labelElem value
    Assert.assertEquals(1, cb.getElement().getChild(1).getChildCount());
    HeadingElement h1 = cb.getElement().getChild(1).getChild(0).cast();
    Assert.assertEquals("H1", h1.getTagName());
    Assert.assertEquals("test", h1.getInnerText());
  }

  @Test
  public void checkName() {
    CheckBox cb = new CheckBox();
    cb.setName("name");
    Assert.assertEquals("name", cb.getName());
  }

  @Test
  public void checkText() {
    CheckBox cb = new CheckBox("foo");
    Assert.assertEquals("foo", cb.getText());
    cb.setText("text");
    Assert.assertEquals("text", cb.getText());
  }

  @Test
  public void checkTitle() {
    CheckBox cb = new CheckBox();
    cb.setTitle("title");
    Assert.assertEquals("title", cb.getTitle());
  }

  @Test
  public void checkVisible() {
    CheckBox cb = new CheckBox();
    Assert.assertEquals(true, cb.isVisible());
    cb.setVisible(false);
    Assert.assertEquals(false, cb.isVisible());
  }

}
