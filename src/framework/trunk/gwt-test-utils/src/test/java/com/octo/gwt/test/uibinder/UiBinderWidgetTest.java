package com.octo.gwt.test.uibinder;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtTestTest;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class UiBinderWidgetTest extends GwtTestTest {

  @Test
  public void checkUiBinderWidget() {
    // Setup
    UiBinderWidget helloWorld = new UiBinderWidget("gael", "eric");

    // Test
    RootPanel.get().add(helloWorld);

    // Asserts
    ListBox listBox = GwtReflectionUtils.getPrivateFieldValue(helloWorld,
        "listBox");
    Assert.assertEquals(1, listBox.getVisibleItemCount());

  }
}
