package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class TreeTest extends AbstractGwtTest {

	private Tree bar;
	private TreeItem parent;
	private TreeItem item0;
	private TreeItem item1;
	private TreeItem item2;

	private TreeItem clickedTreeItem;

	@Override
	public String getCurrentTestedModuleFile() {
		return "test-config.gwt.xml";
	}

	@Before
	public void setupTree() {
		// Create a tree with a few items in it.
		parent = new TreeItem("parent");
		item0 = parent.addItem("item0");
		item1 = parent.addItem("item1");

		// Add a CheckBox to the tree
		item2 = new TreeItem(new CheckBox("item2"));
		parent.addItem(item2);

		bar = new Tree();
		bar.addItem(parent);

		// Add it to the root panel.
		RootPanel.get().add(bar);

		Assert.assertTrue(bar.isVisible());

		clickedTreeItem = null;

	}

	@Test
	public void checkTitle() {
		bar.setTitle("title");
		Assert.assertEquals("title", bar.getTitle());
	}

	@Test
	public void checkVisible() {
		Assert.assertEquals(true, bar.isVisible());
		bar.setVisible(false);
		Assert.assertEquals(false, bar.isVisible());
	}

	@Test
	public void checkAnimationEnabled() {
		bar.setAnimationEnabled(true);

		Assert.assertEquals(true, bar.isAnimationEnabled());
	}

	@Test
	public void checkAddItem() {

		bar.addItem("parent2");

		Assert.assertEquals(2, bar.getItemCount());
		Assert.assertEquals("parent2", bar.getItem(1).getHTML());
	}

	@Test
	public void checkRemoveItem() {
		bar.removeItem(parent);

		Assert.assertEquals(0, bar.getItemCount());
	}

	@Test
	public void checkAddSubItem() {

		bar.getItem(0).addItem("item3");

		Assert.assertEquals(4, bar.getItem(0).getChildCount());
		Assert.assertEquals(item0, bar.getItem(0).getChild(0));
		Assert.assertEquals(item1, bar.getItem(0).getChild(1));
		Assert.assertEquals(item2, bar.getItem(0).getChild(2));
		Assert.assertEquals("item3", bar.getItem(0).getChild(3).getHTML());
	}

	@Test
	public void checkRemoveSubItem() {

		bar.getItem(0).removeItem(item0);

		Assert.assertEquals(2, bar.getItem(0).getChildCount());
		Assert.assertEquals(item1, bar.getItem(0).getChild(0));
		Assert.assertEquals(item2, bar.getItem(0).getChild(1));
	}

	@Test
	public void checkSelection() {
		// Setup
		bar.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				clickedTreeItem = event.getSelectedItem();
			}
		});

		// Test
		SelectionEvent.fire(bar, item0);

		// Assert
		Assert.assertEquals(item0, clickedTreeItem);
	}

}
