package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ComplexPanelTest extends GwtTest {

  @Test
  public void checkAdd() {
    // Setup
    ComplexPanel panel = new FlowPanel();
    RootPanel.get().add(panel);
    Assert.assertTrue(panel.isAttached());
    Assert.assertEquals(0, panel.getWidgetCount());
    Button b1 = new Button();
    Assert.assertFalse(b1.isAttached());
    Assert.assertNull(b1.getParent());

    // Test
    panel.add(b1);

    // Assert
    Assert.assertTrue(b1.isAttached());
    Assert.assertEquals(panel, b1.getParent());
    Assert.assertEquals(1, panel.getWidgetCount());
    Assert.assertEquals(b1, panel.getWidget(0));
    Assert.assertEquals(0, panel.getWidgetIndex(b1));

  }

  @Test
  public void checkCount() {
    ComplexPanel panel = new FlowPanel();
    panel.add(new Button());
    panel.add(new Button());

    Assert.assertEquals(2, panel.getWidgetCount());
  }

  @Test
  public void checkRemove() {
    ComplexPanel panel = new FlowPanel();
    Button b = new Button();
    panel.add(b);

    panel.remove(b);
    Assert.assertEquals(0, panel.getWidgetCount());
  }

  @Test
  public void checkRemoveIndex() {
    ComplexPanel panel = new FlowPanel();

    Button b1 = new Button();
    panel.add(b1);

    Button b2 = new Button();
    panel.add(b2);

    panel.remove(1);

    Assert.assertEquals(1, panel.getWidgetCount());
    Assert.assertEquals(b1, panel.getWidget(0));
    Assert.assertEquals(panel, b1.getParent());
  }

  @Test
  public void checkTitle() {
    ComplexPanel panel = new FlowPanel();
    panel.setTitle("title");
    Assert.assertEquals("title", panel.getTitle());
  }

  @Test
  public void checkVisible() {
    ComplexPanel panel = new FlowPanel();
    Assert.assertEquals(true, panel.isVisible());
    panel.setVisible(false);
    Assert.assertEquals(false, panel.isVisible());
  }

}
