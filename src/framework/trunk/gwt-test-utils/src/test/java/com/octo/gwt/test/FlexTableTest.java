package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.events.Browser;

@SuppressWarnings("deprecation")
public class FlexTableTest extends GwtTestTest {

  private boolean clicked = false;

  @Test
  public void checkClickHandlerNestedWidget() {

    clicked = false;
    FlexTable t = new FlexTable();

    Button b = new Button("Wide Button");
    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clicked = !clicked;

      }
    });
    // add the button
    t.setWidget(0, 0, b);

    Assert.assertEquals(false, clicked);
    // simule the click
    Browser.click(t.getWidget(0, 0));

    Assert.assertEquals(true, clicked);
  }

  @Test
  public void checkClickListenerNestedWidget() {

    clicked = false;
    FlexTable t = new FlexTable();

    Button b = new Button("Wide Button");
    b.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        clicked = !clicked;

      }
    });
    // add the button
    t.setWidget(0, 0, b);

    Assert.assertEquals(false, clicked);
    // simule the click
    Browser.click(t.getWidget(0, 0));

    Assert.assertEquals(true, clicked);
  }

  @Test
  public void checkFlexTable() {

    // Tables have no explicit size -- they resize automatically on demand.
    FlexTable t = new FlexTable();

    // Put some text at the table's extremes. This forces the table to be
    // 3 by 3.
    t.setText(0, 0, "upper-left corner");
    t.setText(2, 2, "bottom-right corner");

    // Let's put a button in the middle...
    Button b = new Button("Wide Button");
    t.setWidget(1, 0, b);

    // ...and set it's column span so that it takes up the whole row.
    t.getFlexCellFormatter().setColSpan(1, 0, 3);

    Assert.assertEquals(3, t.getRowCount());
    Assert.assertEquals("bottom-right corner", t.getText(2, 2));
    Assert.assertEquals(b, t.getWidget(1, 0));
  }

  @Test
  public void checkHTML() {
    FlexTable t = new FlexTable();
    t.setHTML(1, 1, "<h1>test</h1>");
    Assert.assertEquals("<h1>test</h1>", t.getHTML(1, 1));
    Element e = t.getCellFormatter().getElement(1, 1);
    Assert.assertEquals(1, e.getChildCount());
    HeadingElement h1 = e.getChild(0).cast();
    Assert.assertEquals("H1", h1.getTagName());
    Assert.assertEquals("test", h1.getInnerText());
  }

  @Test
  public void checkText() {
    FlexTable t = new FlexTable();
    t.setText(1, 1, "text");
    Assert.assertEquals("text", t.getText(1, 1));
  }

  @Test
  public void checkTitle() {
    FlexTable t = new FlexTable();
    t.setTitle("title");
    Assert.assertEquals("title", t.getTitle());
  }

  @Test
  public void checkVisible() {
    FlexTable t = new FlexTable();
    Assert.assertEquals(true, t.isVisible());
    t.setVisible(false);
    Assert.assertEquals(false, t.isVisible());
  }

}
