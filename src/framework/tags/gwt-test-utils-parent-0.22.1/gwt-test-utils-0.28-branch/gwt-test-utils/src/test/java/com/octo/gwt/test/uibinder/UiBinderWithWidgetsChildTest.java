package com.octo.gwt.test.uibinder;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.events.Browser;

public class UiBinderWithWidgetsChildTest extends GwtTestTest {

  @Test
  public void checkUiBinderWidget() {
    // Arrange
    UiBinderWithWidgetsChild w = new UiBinderWithWidgetsChild("gael", "eric");

    // Act
    RootPanel.get().add(w);

    // Assert
    Assert.assertEquals(1, w.listBox.getVisibleItemCount());

    Assert.assertEquals("my provided label", w.providedLabel.getText());
    Assert.assertEquals("custom text setup in ui.xml",
        w.providedLabel.getCustomText());
    Assert.assertEquals("my provided string", w.providedLabel.providedString);
    Assert.assertEquals("disabled", w.providedLabel.getStyleName());

    Assert.assertEquals("my UiFactory label", w.uiFactoryLabel.getText());
    Assert.assertEquals("custom text setup in ui.xml",
        w.uiFactoryLabel.getCustomText());
    Assert.assertEquals("gael", w.uiFactoryLabel.uiFactoryString);

    Assert.assertEquals("my UiConstructor label",
        w.uiConstructorLabel.getText());
    Assert.assertEquals("custom text setup in ui.xml",
        w.uiConstructorLabel.getCustomText());
    Assert.assertEquals("uiConstructor property",
        w.uiConstructorLabel.uiConstructorLabel);

    // Assertion on inner style
    Assert.assertEquals("style", w.style.getName());
    Assert.assertEquals(
        ".redBox {background-color: pink;border: 1px solid red;}.enabled {color: black;}.disabled {color: gray;}",
        w.style.getText());

    // override by child assertion
    Assert.assertEquals("override by child", w.pushButton.getText());

  }

  @Test
  public void checkUiHandlerClick() {
    // Arrange
    UiBinderWithWidgetsChild w = new UiBinderWithWidgetsChild("gael", "eric");

    // Pre-Assert
    Assert.assertEquals(1, w.listBox.getVisibleItemCount());

    // Act
    Browser.click(w.button);

    // Assert
    Assert.assertEquals(2, w.listBox.getVisibleItemCount());
  }

}
