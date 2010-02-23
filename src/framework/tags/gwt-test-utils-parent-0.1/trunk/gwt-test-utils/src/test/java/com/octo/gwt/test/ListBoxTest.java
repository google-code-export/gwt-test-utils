package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.ListBox;
import com.octo.gwt.test.AbstractGWTTest;

public class ListBoxTest extends AbstractGWTTest {

	@Test
	public void checkName() {
		ListBox listBox = new ListBox(false);
		listBox.setName("name");
		Assert.assertEquals("name", listBox.getName());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkIsMultipleSelect() {
		ListBox listBox = new ListBox(false);
		Assert.assertEquals(false, listBox.isMultipleSelect());

		listBox.setMultipleSelect(true);
		Assert.assertEquals(true, listBox.isMultipleSelect());
	}

	@Test
	public void checkTabIndex() {
		ListBox listBox = new ListBox(false);
		listBox.setTabIndex(2);
		Assert.assertEquals(2, listBox.getTabIndex());
	}

	@Test
	public void checkTitle() {
		ListBox listBox = new ListBox(false);
		listBox.setTitle("title");
		Assert.assertEquals("title", listBox.getTitle());
	}

	@Test
	public void checkVisible() {
		ListBox listBox = new ListBox(false);
		Assert.assertEquals(true, listBox.isVisible());
		listBox.setVisible(false);
		Assert.assertEquals(false, listBox.isVisible());
	}

	@Test
	public void checkListBox() {
		ListBox listBox = getListBox();

		Assert.assertEquals(2, listBox.getVisibleItemCount());
		Assert.assertEquals(2, listBox.getItemCount());
		Assert.assertEquals("item 0", listBox.getItemText(0));
		Assert.assertEquals("item 1", listBox.getItemText(1));
	}

	@Test
	public void checkSelected() {
		ListBox listBox = getListBox();

		listBox.setSelectedIndex(1);
		Assert.assertEquals("item 1", listBox.getItemText(listBox.getSelectedIndex()));
	}

	private ListBox getListBox() {
		ListBox listBox = new ListBox(false);
		listBox.setVisibleItemCount(2);
		listBox.addItem("item 0");
		listBox.addItem("item 1");

		return listBox;
	}

}
