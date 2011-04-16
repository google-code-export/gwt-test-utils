package com.octo.gwt.test.uibinder;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class UiBinderWithWidgetsTest extends GwtTestTest {

  @Test
  public void checkUiBinderWidget() {
    // Setup
    UiBinderWithWidgets helloWorld = new UiBinderWithWidgets("gael", "eric");

    // Test
    RootPanel.get().add(helloWorld);

    // Asserts
    ListBox listBox = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "listBox");
    Assert.assertEquals(1, listBox.getVisibleItemCount());

    HTMLPanel wrappedPanel = GwtReflectionUtils.callPrivateMethod(helloWorld,
        "getWidget");
    Assert.assertEquals(listBox, wrappedPanel.getWidget(0));

    Label label = (Label) wrappedPanel.getWidget(1);
    Assert.assertNotNull(label);
    // TODO: pass this assert
    // Assert.assertEquals("Keep your ducks", label.getText());

    SpanElement spanElement = Document.get().getElementById("mySpan").cast();
    // TODO: pass this assert
    // Assert.assertEquals("some span for testing", spanElement.getInnerText());
    Assert.assertEquals("mySpanClass", spanElement.getClassName());
  }

  @Test
  public void checkUiHandlerClick() {
    // Setup
    UiBinderWithWidgets helloWorld = new UiBinderWithWidgets("gael", "eric");
    ListBox listBox = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "listBox");
    Button button = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "button");

    // Pre-Assert
    Assert.assertEquals(1, listBox.getVisibleItemCount());

    // Test
    Browser.click(button);

    // Asserts
    // Assert.assertEquals(2, listBox.getVisibleItemCount());
  }
}
