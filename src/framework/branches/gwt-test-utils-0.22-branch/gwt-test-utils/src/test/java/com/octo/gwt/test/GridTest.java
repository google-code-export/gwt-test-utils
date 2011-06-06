package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class GridTest extends AbstractGwtTest {

  private boolean clicked = false;

  @Test
  public void checkText() {
    Grid g = new Grid(1, 1);
    g.setText(0, 0, "text");
    Assert.assertEquals("text", g.getText(0, 0));
  }

  @Test
  public void checkTitle() {
    Grid g = new Grid(1, 1);
    g.setTitle("title");
    Assert.assertEquals("title", g.getTitle());
  }

  @Test
  public void checkTableListner() {
    clicked = false;
    Grid g = new Grid(1, 1);
    Button b = new Button("Does nothing, but could");
    g.setWidget(0, 0, b);
    g.addTableListener(new TableListener() {

      public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
        clicked = !clicked;
      }

    });

    click(g, 0, 0);

    Assert.assertTrue("TableListener should have been notified", clicked);

  }

  @Test
  public void checkClickHander() {
    clicked = false;
    final Grid g = new Grid(1, 1);
    final Button b = new Button("Does nothing, but could");
    g.setWidget(0, 0, b);
    g.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clicked = !clicked;
        Assert.assertEquals(b, ((Grid) event.getSource()).getWidget(0, 0));

      }
    });

    click(g, 0, 0);

    Assert.assertTrue("TableListener should have been notified", clicked);

  }

  @Test
  public void checkHTML() {
    Grid g = new Grid(1, 1);
    g.setHTML(0, 0, "<h1>test</h1>");
    Assert.assertEquals("<h1>test</h1>", g.getHTML(0, 0));
    Element e = g.getCellFormatter().getElement(0, 0);
    Assert.assertEquals(1, e.getChildCount());
    Element h1 = (Element) e.getChild(0);
    Assert.assertEquals("H1", h1.getTagName());
    Assert.assertEquals("test", h1.getInnerText());
  }

  @Test
  public void checkVisible() {
    Grid g = new Grid(1, 1);
    Assert.assertEquals(true, g.isVisible());
    g.setVisible(false);
    Assert.assertEquals(false, g.isVisible());
  }

  @Test
  public void checkGrid() {
    // Grids must be sized explicitly, though they can be resized later.
    Grid g = new Grid(5, 5);

    // Put some values in the grid cells.
    for (int row = 0; row < 5; ++row) {
      for (int col = 0; col < 5; ++col)
        g.setText(row, col, "" + row + ", " + col);
    }

    Assert.assertEquals("3, 2", g.getText(3, 2));

    Button b = new Button("Does nothing, but could");
    g.setWidget(2, 2, b);

    Assert.assertEquals(b, g.getWidget(2, 2));
  }

  @Test
  public void checkRemoveFromGrid() {
    // Grids must be sized explicitly, though they can be resized later.
    Grid g = new Grid(1, 1);

    Button b = new Button("Does nothing, but could");
    g.setWidget(0, 0, b);

    Assert.assertTrue("The button has not been removed from grid", g.remove(b));
  }

  @Test
  public void checkAddStyleName() {
    // Grids must be sized explicitly, though they can be resized later.
    Grid g = new Grid(1, 1);

    g.getRowFormatter().addStyleName(0, "style");

    Assert.assertEquals("style", g.getRowFormatter().getStyleName(0));
  }

  @Test
  public void checkClickListenerNestedWidget() {
    // Grids must be sized explicitly, though they can be resized later.
    Grid g = new Grid(1, 1);

    Button b = new Button();

    b.addClickListener(new ClickListener() {

      public void onClick(Widget sender) {
        clicked = !clicked;

      }
    });
    // add the button
    g.setWidget(0, 0, b);

    Assert.assertEquals(false, clicked);
    // simule the click
    click(g.getWidget(0, 0));

    Assert.assertEquals(true, clicked);
  }

  @Test
  public void checkClickHandlerNestedWidget() {
    // Grids must be sized explicitly, though they can be resized later.
    Grid g = new Grid(1, 1);

    Button b = new Button();

    b.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        clicked = !clicked;

      }
    });
    // add the button
    g.setWidget(0, 0, b);

    Assert.assertEquals(false, clicked);
    // simule the click
    click(g.getWidget(0, 0));

    Assert.assertEquals(true, clicked);
  }

}
