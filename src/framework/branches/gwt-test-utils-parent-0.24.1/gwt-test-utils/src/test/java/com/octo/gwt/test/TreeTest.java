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

public class TreeTest extends GwtTest {

	private Tree tree;
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

		tree = new Tree();
		tree.addItem(parent);

		// Add it to the root panel.
		RootPanel.get().add(tree);

		Assert.assertTrue(tree.isVisible());

		clickedTreeItem = null;

	}

	@Test
	public void checkTitle() {
		tree.setTitle("title");
		Assert.assertEquals("title", tree.getTitle());
	}

	@Test
	public void checkVisible() {
		Assert.assertEquals(true, tree.isVisible());
		tree.setVisible(false);
		Assert.assertEquals(false, tree.isVisible());
	}

	@Test
	public void checkAnimationEnabled() {
		tree.setAnimationEnabled(true);

		Assert.assertEquals(true, tree.isAnimationEnabled());
	}

	@Test
	public void checkAddItem() {

		tree.addItem("parent2");

		Assert.assertEquals(2, tree.getItemCount());
		Assert.assertEquals("parent2", tree.getItem(1).getHTML());
	}

	@Test
	public void checkRemoveItem() {
		tree.removeItem(parent);

		Assert.assertEquals(0, tree.getItemCount());
	}

	@Test
	public void checkAddSubItem() {

		tree.getItem(0).addItem("item3");

		Assert.assertEquals(4, tree.getItem(0).getChildCount());
		Assert.assertEquals(item0, tree.getItem(0).getChild(0));
		Assert.assertEquals(item1, tree.getItem(0).getChild(1));
		Assert.assertEquals(item2, tree.getItem(0).getChild(2));
		Assert.assertEquals("item3", tree.getItem(0).getChild(3).getHTML());
	}

	@Test
	public void checkRemoveSubItem() {

		tree.getItem(0).removeItem(item0);

		Assert.assertEquals(2, tree.getItem(0).getChildCount());
		Assert.assertEquals(item1, tree.getItem(0).getChild(0));
		Assert.assertEquals(item2, tree.getItem(0).getChild(1));
	}

	@Test
	public void checkSelected() {
		// Setup
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				clickedTreeItem = event.getSelectedItem();
			}
		});

		// Test
		tree.setSelectedItem(item1);
		TreeItem selected = tree.getSelectedItem();

		// Assert
		Assert.assertEquals(item1, clickedTreeItem);
		Assert.assertEquals(item1, selected);
	}

	@Test
	public void checkSelectedOnFocusWidget() {
		// Setup
		tree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			public void onSelection(SelectionEvent<TreeItem> event) {
				clickedTreeItem = event.getSelectedItem();
			}
		});

		// Test on item2 which wrap a Checkbox
		tree.setSelectedItem(item2);
		TreeItem selected = tree.getSelectedItem();

		// Assert
		Assert.assertEquals(item2, clickedTreeItem);
		Assert.assertEquals(item2, selected);
	}

}
