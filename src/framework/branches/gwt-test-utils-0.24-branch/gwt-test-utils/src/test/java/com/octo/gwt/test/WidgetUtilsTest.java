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
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestBox.SuggestionDisplay;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.WidgetUtils;

public class WidgetUtilsTest extends GwtTestTest {

	@Test
	public void checkAssertListBoxDataDoNotMatchDifferentElement() {
		// Setup
		ListBox lb = new ListBox();
		lb.addItem("item0");
		lb.addItem("item1");
		lb.addItem("iTem2");

		String[] content = new String[] { "item0", "item1", "item2" };

		// Test & Assert
		Assert.assertFalse(WidgetUtils.assertListBoxDataMatch(lb, content));
	}

	@Test
	public void checkAssertListBoxDataDoNotMatchMissingElement() {
		// Setup
		ListBox lb = new ListBox();
		lb.addItem("item0");
		lb.addItem("item1");

		String[] content = new String[] { "item0", "item1", "item2" };

		// Test & Assert
		Assert.assertFalse(WidgetUtils.assertListBoxDataMatch(lb, content));
	}

	@Test
	public void checkAssertListBoxDataMatch() {
		// Setup
		ListBox lb = new ListBox();
		lb.addItem("item0");
		lb.addItem("item1");
		lb.addItem("item2");

		String[] content = new String[] { "item0", "item1", "item2" };

		// Test & Assert
		Assert.assertTrue(WidgetUtils.assertListBoxDataMatch(lb, content));
	}

	@Test
	public void checkGetListBoxContentToString() {
		// Setup
		ListBox lb = new ListBox();
		lb.addItem("item0");
		lb.addItem("item1");
		lb.addItem("item2");

		String expected = "item0 | item1 | item2 |";

		// Test
		String actual = WidgetUtils.getListBoxContentToString(lb);

		Assert.assertEquals(expected, actual);
	}

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
	public void checkSuggestBoxItems() {
		// Setup
		SuggestBox box = new SuggestBox();
		SuggestionDisplay display = GwtReflectionUtils.getPrivateFieldValue(
				box, "display");
		MenuBar bar = GwtReflectionUtils.getPrivateFieldValue(display,
				"suggestionMenu");

		Command cmd = new Command() {
			public void execute() {
			}

		};

		MenuItem item0 = bar.addItem("item0", cmd);
		MenuItem item1 = bar.addItem("item1", cmd);

		// Test
		List<MenuItem> items = WidgetUtils.getMenuItems(box);

		// Assert
		Assert.assertEquals(2, items.size());
		Assert.assertEquals(item0, items.get(0));
		Assert.assertEquals(item1, items.get(1));
	}

	@Test
	public void checNewWidgetIsNotVisibleWhenParentIsNotVisible() {
		MenuBar bar = new MenuBar();
		bar.setVisible(false);
		MenuItem item0 = bar.addItem("test0", (Command) null);
		item0.setVisible(true);

		// Test
		Boolean isVisible = WidgetUtils.isWidgetVisible(item0);

		// Assert
		Assert.assertFalse(isVisible);
	}

}
