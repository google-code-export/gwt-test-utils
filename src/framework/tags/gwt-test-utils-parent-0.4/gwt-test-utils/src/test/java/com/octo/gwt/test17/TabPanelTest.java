package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class TabPanelTest extends AbstractGWTTest {

	int selectedTabIndex = -1;

	@Test
	public void checkTitle() {
		TabPanel tp = new TabPanel();
		tp.setTitle("title");
		Assert.assertEquals("title", tp.getTitle());
	}

	@Test
	public void checkVisible() {
		TabPanel tp = new TabPanel();
		Assert.assertEquals(true, tp.isVisible());
		tp.setVisible(false);
		Assert.assertEquals(false, tp.isVisible());
	}

	@Test
	public void checkTabPanel() {
		TabPanel tp = createTabPanel();

		Widget w = tp.getWidget(1);

		Assert.assertTrue(w instanceof HTML);
		HTML html = (HTML) w;

		Assert.assertEquals("Bar", html.getHTML());
	}

	@Test
	public void checkWidgetIndex() {
		TabPanel tp = new TabPanel();
		Widget widget0 = new HTML("Foo");
		tp.add(widget0, "foo");
		Widget widget1 = new HTML("Bar");
		tp.add(widget1, "bar");

		Assert.assertEquals(1, tp.getWidgetIndex(widget1));
	}

	@Test
	public void checkDeck() {
		TabPanel tp = createTabPanel();

		// Show the 'bar' tab initially.
		tp.selectTab(2);

		Assert.assertEquals(2, tp.getDeckPanel().getVisibleWidget());
	}

	@Test
	public void checkSelection() {
		TabPanel tp = createTabPanel();

		tp.addSelectionHandler(new SelectionHandler<Integer>() {

			public void onSelection(SelectionEvent<Integer> event) {
				selectedTabIndex = event.getSelectedItem();
			}
		});

		Assert.assertEquals(-1, selectedTabIndex);

		tp.selectTab(1);
		Assert.assertEquals(1, selectedTabIndex);
	}

	private TabPanel createTabPanel() {
		TabPanel tp = new TabPanel();
		tp.add(new HTML("Foo"), "foo");
		tp.add(new HTML("Bar"), "bar");
		tp.add(new HTML("Baz"), "baz");

		return tp;
	}

}
