package com.octo.gwt.test17;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.octo.gwt.test17.WidgetUtils;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class WidgetUtilsTest extends AbstractGWTTest {
	
	@Test
	public void checkMenuBarItems() {
		MenuBar bar = new MenuBar();
		
		Command cmd = new Command() {
			public void execute() {
			}
			
		};
		
		MenuItem item0 = bar.addItem("item0", cmd);
		MenuItem item1 = bar.addItem("item1", cmd);
		
		List<MenuItem> items = WidgetUtils.getMenuItems(bar);
		
		Assert.assertEquals(2, items.size());
		Assert.assertEquals(item0, items.get(0));
		Assert.assertEquals(item1, items.get(1));
	}

}
