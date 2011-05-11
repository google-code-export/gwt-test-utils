package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;

// TODO: complete tests..
public class ContentPanelTest extends GwtGxtTest {

  private ContentPanel panel;

  @Test
  public void checkParent() {
    // Setup
    Button b = new Button();

    // Test 1
    panel.add(b);

    // Assert 1
    Assert.assertEquals(panel, b.getParent());

    // Test 2
    panel.removeAll();

    // Asserts 2
    Assert.assertEquals(0, panel.getItemCount());
    Assert.assertNull(b.getParent());
  }

  @Before
  public void setupContentPanel() {
    panel = new ContentPanel();
  }

}
