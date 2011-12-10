package com.octo.gwt.test.uibinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.resources.MyClientBundle;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class UiBinderWithWidgetsTest extends GwtTestTest {

  @Before
  public void before() {
    registerUiConstructor(UiConstructorLabel.class, "uiConstructorLabel");
  }

  @Test
  public void click_UiHandler() {
    // Arrange
    UiBinderWithWidgets w = new UiBinderWithWidgets("gael", "eric");

    // Pre-Assert
    assertEquals(1, w.listBox.getVisibleItemCount());

    // Act
    Browser.click(w.button);

    // Assert
    assertEquals(2, w.listBox.getVisibleItemCount());
  }

  @Test
  public void uiBinderWidget() {
    // Arrange
    UiBinderWithWidgets w = new UiBinderWithWidgets("gael", "eric");

    // Act
    RootPanel.get().add(w);

    // Assert
    assertEquals(1, w.listBox.getVisibleItemCount());

    HTMLPanel wrappedPanel = GwtReflectionUtils.callPrivateMethod(w,
        "getWidget");
    assertEquals(w.listBox, wrappedPanel.getWidget(0));

    assertEquals("Test radio 1", w.radioButton1.getText());
    assertEquals("MyRadioGroup", w.radioButton1.getName());
    assertTrue(w.radioButton1.getValue());

    assertEquals("Test radio 2", w.radioButton2.getText());
    assertEquals("MyRadioGroup", w.radioButton2.getName());
    assertFalse(w.radioButton2.getValue());

    assertEquals(MyClientBundle.INSTANCE.testImageResource().getURL(),
        w.image.getUrl());
    assertEquals("http://slazzer.com/image.jpg", w.imageWithUrl.getUrl());

    assertEquals("my provided label", w.providedLabel.getText());
    assertEquals("custom text setup in ui.xml", w.providedLabel.getCustomText());
    assertEquals("my provided string", w.providedLabel.providedString);
    assertEquals("disabled", w.providedLabel.getStyleName());

    assertEquals("my UiFactory label", w.uiFactoryLabel.getText());
    assertEquals("custom text setup in ui.xml",
        w.uiFactoryLabel.getCustomText());
    assertEquals("gael", w.uiFactoryLabel.uiFactoryString);

    assertEquals("my UiConstructor label", w.uiConstructorLabel.getText());
    assertEquals("custom text setup in ui.xml",
        w.uiConstructorLabel.getCustomText());
    assertEquals("uiConstructor property",
        w.uiConstructorLabel.uiConstructorLabel);

    Label label = (Label) wrappedPanel.getWidget(1);
    assertNotNull(label);
    assertEquals("Keep your ducks", label.getText());

    SpanElement spanElement = Document.get().getElementById("mySpan").cast();
    assertEquals("some span for testing", spanElement.getInnerText());
    assertEquals("pretty", spanElement.getClassName());

    assertEquals("We <b>strongly</b> urge you to reconsider.",
        w.msgLabel.getText());

    assertEquals("9'00", w.msgInnerWidget.getText());
    assertEquals(w.msgInnerWidget, wrappedPanel.getWidget(4));

    assertEquals(HasHorizontalAlignment.ALIGN_LEFT,
        w.verticalPanel.getHorizontalAlignment());
    assertEquals(HasVerticalAlignment.ALIGN_MIDDLE,
        w.verticalPanel.getVerticalAlignment());

    // Assertion on specific style
    assertEquals("style", w.style.getName());
    assertEquals("disabled", w.style.disabled());
    assertEquals(
        ".redBox {background-color: pink;border: 1px solid red;}.enabled {color: black;}.disabled {color: gray;}",
        w.style.getText());

    // Assertion on inner style
    assertEquals("gwt-PushButton testStyle pretty gwt-PushButton-up",
        w.pushButton.getStyleName());

    // Assertion on inner image
    assertEquals("img", w.img.getName());
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/uibinder/img.jpg",
        w.img.getURL());

    // Assertion on inner data
    assertEquals("data", w.data.getName());
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/uibinder/data.test",
        w.data.getUrl());

  }
}
