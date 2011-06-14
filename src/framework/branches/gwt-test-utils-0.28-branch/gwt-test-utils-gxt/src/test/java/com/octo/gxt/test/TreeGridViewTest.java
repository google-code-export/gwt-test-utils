package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Test;

import com.extjs.gxt.ui.client.widget.treegrid.TreeGridView;

// TODO: complete tests..
public class TreeGridViewTest extends GwtGxtTest {

  @Test
  public void checkAutoFill() {
    // Arrange
    TreeGridView view = new TreeGridView();

    // Act
    view.setAutoFill(true);

    // Assert
    Assert.assertTrue(view.isAutoFill());
  }

}
