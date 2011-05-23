package com.octo.gwt.test.uibinder;

import org.junit.Assert;
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

  @Test
  public void checkUiBinderWidget() {
    // Setup
    UiBinderWithWidgets w = new UiBinderWithWidgets("gael", "eric");

    // Test
    RootPanel.get().add(w);

    // Asserts
    Assert.assertEquals(1, w.listBox.getVisibleItemCount());

    HTMLPanel wrappedPanel = GwtReflectionUtils.callPrivateMethod(w,
        "getWidget");
    Assert.assertEquals(w.listBox, wrappedPanel.getWidget(0));

    Assert.assertEquals(MyClientBundle.INSTANCE.testImageResource().getURL(),
        w.image.getUrl());

    Assert.assertEquals("my provided label", w.providedLabel.getText());
    Assert.assertEquals("custom text setup in ui.xml",
        w.providedLabel.getCustomText());
    Assert.assertEquals("my provided string", w.providedLabel.providedString);

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

    Label label = (Label) wrappedPanel.getWidget(1);
    Assert.assertNotNull(label);
    Assert.assertEquals("Keep your ducks", label.getText());

    SpanElement spanElement = Document.get().getElementById("mySpan").cast();
    Assert.assertEquals("some span for testing", spanElement.getInnerText());
    Assert.assertEquals("pretty", spanElement.getClassName());

    Assert.assertEquals("We <b>strongly</b> urge you to reconsider.",
        w.msgLabel.getText());

    Assert.assertEquals("9'00", w.msgInnerWidget.getText());
    Assert.assertEquals(w.msgInnerWidget, wrappedPanel.getWidget(3));

    Assert.assertEquals(HasHorizontalAlignment.ALIGN_LEFT,
        w.verticalPanel.getHorizontalAlignment());
    Assert.assertEquals(HasVerticalAlignment.ALIGN_MIDDLE,
        w.verticalPanel.getVerticalAlignment());
  }

  @Test
  public void checkUiHandlerClick() {
    // Setup
    UiBinderWithWidgets w = new UiBinderWithWidgets("gael", "eric");

    // Pre-Assert
    Assert.assertEquals(1, w.listBox.getVisibleItemCount());

    // Test
    Browser.click(w.button);

    // Asserts
    Assert.assertEquals(2, w.listBox.getVisibleItemCount());
  }
}
