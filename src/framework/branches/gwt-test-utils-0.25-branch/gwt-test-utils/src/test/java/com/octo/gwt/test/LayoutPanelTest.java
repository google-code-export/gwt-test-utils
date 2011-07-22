package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dom.client.Element;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class LayoutPanelTest extends GwtTestTest {

  private boolean onAnimationComplete;

  private LayoutPanel panel;

  @Test
  public void add() {
    // Arrange
    Button b = new Button();
    assertFalse(b.isAttached());

    // Act
    panel.add(b);

    // Assert
    assertEquals(1, panel.getWidgetCount());
    assertEquals(b, panel.getWidget(0));
    assertTrue(b.isAttached());
  }

  @Test
  public void animate() {
    // Arrange
    AnimationCallback callback = new AnimationCallback() {

      public void onAnimationComplete() {
        onAnimationComplete = true;
      }

      public void onLayout(Layer layer, double progress) {
        // never called in gwt-test-utils
      }
    };

    // Act
    panel.animate(4, callback);

    // Assert
    assertTrue(onAnimationComplete);
  }

  @Before
  public void beforeLayoutPanel() {
    onAnimationComplete = false;

    panel = new LayoutPanel();
    assertFalse(panel.isAttached());
    RootPanel.get().add(panel);
    assertTrue(panel.isAttached());
    assertEquals(0, panel.getWidgetCount());
  }

  @Test
  public void getWidgetContainerElement() {
    // Arrange
    FlowPanel fp1 = new FlowPanel();
    panel.add(fp1);
    Element fp1Element = fp1.getElement();

    // Act
    Element fp1Container = panel.getWidgetContainerElement(fp1);

    // Assert
    assertEquals(fp1Element, fp1Container.getFirstChildElement());
  }

}
