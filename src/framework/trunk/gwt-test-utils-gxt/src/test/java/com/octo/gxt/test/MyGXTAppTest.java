package com.octo.gxt.test;

import org.junit.Assert;
import org.junit.Test;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.AbstractGwtTest;

public class MyGXTAppTest extends AbstractGwtTest {

	@Override
	protected String getModuleName() {
		return "fake";
	}

	@Test
	public void checkOnModuleLoad() {
		// Setup
		MyGXTApp app = new MyGXTApp();

		// Test
		app.onModuleLoad();

		// Assert
		ContentPanel cp = (ContentPanel) RootPanel.get().getWidget(0);
		Assert.assertEquals(10, cp.getPosition(true).x);
		Assert.assertEquals(10, cp.getPosition(true).y);

		Assert.assertEquals(250, cp.getSize().width);
		//Assert.assertEquals(140, cp.getSize().height);
		Assert.assertTrue(cp.getCollapsible());
		Assert.assertTrue(cp.getFrame());
		Assert.assertEquals("backgroundColor: white;", cp.getBodyStyle());
		Assert.assertEquals(3, cp.getHeader().getToolCount());

		ToolButton tool0 = (ToolButton) cp.getHeader().getTool(0);
		Assert.assertEquals("x-nodrag x-tool-gear x-tool x-component", tool0.getStyleName());

		ToolButton tool1 = (ToolButton) cp.getHeader().getTool(1);
		Assert.assertEquals("x-nodrag x-tool-close x-tool x-component", tool1.getStyleName());

		ToolButton tool2 = (ToolButton) cp.getHeader().getTool(2);
		Assert.assertEquals("x-nodrag x-tool-toggle x-tool x-component", tool2.getStyleName());
	}

}
