package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Test;

import com.extjs.gxt.ui.client.widget.treegrid.TreeGridView;

// TODO: complete tests..
public class TreeGridViewTest extends GwtGxtTest {

  @Test
  public void checkAutoFill() {
    // Setup
    TreeGridView view = new TreeGridView();

    // Test
    view.setAutoFill(true);

    // Asserts
    Assert.assertTrue(view.isAutoFill());
  }

}
