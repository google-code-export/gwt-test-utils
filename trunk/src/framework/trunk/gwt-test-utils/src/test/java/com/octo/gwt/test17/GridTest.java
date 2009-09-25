package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class GridTest extends AbstractGWTTest {
	
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

}
