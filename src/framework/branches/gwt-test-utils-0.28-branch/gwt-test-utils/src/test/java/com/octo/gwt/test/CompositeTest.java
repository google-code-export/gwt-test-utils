package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.utils.events.Browser;

public class CompositeTest extends GwtTestTest {

  private class MyComposite extends Composite {
    MyComposite(Label label) {
      initWidget(label);
    }
  }

  private Composite composite;
  private int compositeCount;
  private Label label;

  private int labelCount;

  @Before
  public void beforeCompositeTest() {
    label = new Label("wrapped label");
    composite = new MyComposite(label);
    RootPanel.get().add(composite);

    labelCount = 0;
    compositeCount = 0;
  }

  // @Test
  public void click_Wrapped() {
    // Arrange
    label.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        labelCount++;

        assertEquals(label, event.getSource());
        assertEquals(label.getElement(), event.getRelativeElement());

        // composite handler should be trigger first
        assertEquals(labelCount + 1, compositeCount);
      }
    });

    composite.addDomHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        compositeCount++;

        assertEquals(composite, event.getSource());
        assertEquals(label.getElement(), event.getRelativeElement());
      }

    }, ClickEvent.getType());

    // Act
    Browser.click(label);

    // Assert
    assertEquals(1, labelCount);
    assertEquals(1, compositeCount);
  }

  // @Test
  public void click_Wrapper() {
    // Arrange
    label.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        labelCount++;

        assertEquals(label, event.getSource());
        assertEquals(label.getElement(), event.getRelativeElement());
      }
    });

    composite.addDomHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        compositeCount++;

        assertEquals(composite, event.getSource());
        assertEquals(label.getElement(), event.getRelativeElement());

        // composite handler should be trigger first
        assertEquals(labelCount + 1, compositeCount);
      }

    }, ClickEvent.getType());

    // Act
    Browser.click(composite);

    // Assert
    assertEquals(1, labelCount);
    assertEquals(1, compositeCount);
  }

  @Test
  public void emptyTest() {

  }

}
