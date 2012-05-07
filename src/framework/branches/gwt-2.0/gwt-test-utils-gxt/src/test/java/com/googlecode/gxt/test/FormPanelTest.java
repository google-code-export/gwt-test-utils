package com.googlecode.gxt.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar.Resources;

public class FormPanelTest extends GwtGxtTest {

  @Test
  public void add() {
    // Arrange
    Resources resources = GWT.create(Resources.class);
    FormPanel panel = new FormPanel();

    // Act
    panel.add(new Image(resources.menuBarSubMenuIcon()));

    // Assert
    assertTrue(((Image) panel.getWidget(0)).getUrl().contains(
        "menuBarSubMenuIcon"));
  }
}
