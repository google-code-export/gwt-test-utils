package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class MenuBarTest extends AbstractGWTTest {

	Command cmd = new Command() {

		public void execute() {
			called = true;
		}

	};

	private boolean called = false;

	@Test
	public void checkTitle() {
		MenuBar bar = new MenuBar();
		bar.setTitle("title");
		Assert.assertEquals("title", bar.getTitle());
	}

	@Test
	public void checkVisible() {
		MenuBar bar = new MenuBar();
		Assert.assertEquals(true, bar.isVisible());
		bar.setVisible(false);
		Assert.assertEquals(false, bar.isVisible());
	}

	@Test
	public void checkAutoOpen() {
		MenuBar bar = new MenuBar();
		bar.setAutoOpen(false);

		Assert.assertEquals(false, bar.getAutoOpen());
	}

	@Test
	public void checkAnimationEnabled() {
		MenuBar bar = new MenuBar();
		bar.setAnimationEnabled(true);

		Assert.assertEquals(true, bar.isAnimationEnabled());
	}

	@Test
	public void checkAddItem() {
		MenuBar bar = new MenuBar();

		MenuItem item0 = bar.addItem("test0", cmd);
		MenuItem item1 = bar.addItem("test1", cmd);

		Assert.assertEquals(0, bar.getItemIndex(item0));
		Assert.assertEquals(1, bar.getItemIndex(item1));
	}

	@Test
	public void checkRemoveItem() {
		MenuBar bar = new MenuBar();

		MenuItem item0 = bar.addItem("test0", cmd);
		MenuItem item1 = bar.addItem("test1", cmd);

		bar.removeItem(item0);

		Assert.assertEquals(0, bar.getItemIndex(item1));
	}

	@Test
	public void checkSeparator() {
		MenuBar bar = new MenuBar();
		bar.addItem("test0", cmd);
		MenuItemSeparator separator = bar.addSeparator();
		bar.addItem("test1", cmd);

		Assert.assertEquals(1, bar.getSeparatorIndex(separator));
	}

	@Test
	public void checkBarClicked() {
		MenuBar bar = new MenuBar();
		MenuItem item = bar.addItem("item", cmd);

		Assert.assertEquals(false, called);

		click(bar, item);

		Assert.assertEquals(true, called);
	}

}
