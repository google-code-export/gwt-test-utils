package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.test.AbstractGWTTest;

@SuppressWarnings("deprecation")
public class GridTest extends AbstractGWTTest {

	private boolean clicked = false;

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
		//add the button
		g.setWidget(0, 0, b);

		Assert.assertEquals(false, clicked);
		//simule the click
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
		//add the button
		g.setWidget(0, 0, b);

		Assert.assertEquals(false, clicked);
		//simule the click
		click(g.getWidget(0, 0));

		Assert.assertEquals(true, clicked);	
	}

}
