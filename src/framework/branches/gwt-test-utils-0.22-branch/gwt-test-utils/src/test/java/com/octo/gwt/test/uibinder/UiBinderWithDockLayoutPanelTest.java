package com.octo.gwt.test.uibinder;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.octo.gwt.test.GwtTestTest;

public class UiBinderWithDockLayoutPanelTest extends GwtTestTest {

  @Test
  public void checkUiBinderWithDocLayoutPanel() {
    // Arrange
    UiBinderWithDockLayoutPanel panel = new UiBinderWithDockLayoutPanel();

    // Act
    RootLayoutPanel.get().add(panel);

    // Assert
    Assert.assertEquals("North", panel.northLabel.getText());
    Assert.assertEquals("Center", panel.centerLabel.getText());
    Assert.assertEquals("East", panel.eastLabel.getText());
    Assert.assertEquals("South", panel.southLabel.getText());
    Assert.assertEquals("Center", panel.centerLabel.getText());
    Assert.assertEquals(
        "<ul><li id=\"li-west0\">west0</li><li id=\"li-west1\">west1</li></ul>",
        panel.westHTML.getHTML());

    LIElement li0 = panel.westHTML.getElement().getFirstChildElement().getChild(
        0).cast();
    LIElement li1 = panel.westHTML.getElement().getFirstChildElement().getChild(
        1).cast();
    Assert.assertEquals("west0", li0.getInnerText());
    Assert.assertEquals("west1", li1.getInnerText());

    Assert.assertEquals(li0, Document.get().getElementById("li-west0"));
    Assert.assertEquals(li1, Document.get().getElementById("li-west1"));
  }

}
