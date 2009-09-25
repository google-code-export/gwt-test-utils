package com.octo.gwt.test17;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class FlexTabPanel extends AbstractGWTTest {

	@Test
	public void checkFlexTab() {

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

}
