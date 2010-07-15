package com.octo.gwt.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import com.octo.gwt.test.utils.WidgetUtils;

public class WidgetUtilsTest extends AbstractGwtTest {

	@Test
	public void checkListBoxIndex() {
		// Setup
		ListBox lb = new ListBox();
		lb.addItem("item0");
		lb.addItem("item1");
		lb.addItem("item2");

		// Test & Assert
		Assert.assertEquals(0, WidgetUtils.getIndexInListBox(lb, "item0"));
		Assert.assertEquals(1, WidgetUtils.getIndexInListBox(lb, "item1"));
		Assert.assertEquals(2, WidgetUtils.getIndexInListBox(lb, "item2"));
		Assert.assertEquals(-1, WidgetUtils.getIndexInListBox(lb, "item3"));
	}

	@Test
	public void checkMenuBarItems() {
		// Setup
		MenuBar bar = new MenuBar();

		Command cmd = new Command() {
			public void execute() {
			}

		};

		MenuItem item0 = bar.addItem("item0", cmd);
		MenuItem item1 = bar.addItem("item1", cmd);

		// Test
		List<MenuItem> items = WidgetUtils.getMenuItems(bar);

		// Assert
		Assert.assertEquals(2, items.size());
		Assert.assertEquals(item0, items.get(0));
		Assert.assertEquals(item1, items.get(1));
	}

	@Test
	public void checkNewWidgetIsVisible() {
		// Setup
		Button b = new Button();

		// Test
		Boolean isVisible = WidgetUtils.isWidgetVisible(b);

		// Assert
		Assert.assertTrue(isVisible);
	}

	@Test
	public void checkNewWidgetIsNotVisibleWhenParentIsNotVisible() {
		// Setup
		SimplePanel sp = new SimplePanel();
		sp.setVisible(false);
		Button b = new Button();
		sp.add(b);

		// Test
		Boolean isVisible = WidgetUtils.isWidgetVisible(b);

		// Assert
		Assert.assertFalse(isVisible);
	}

}
