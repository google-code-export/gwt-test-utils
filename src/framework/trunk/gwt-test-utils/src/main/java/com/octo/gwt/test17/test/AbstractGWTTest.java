package com.octo.gwt.test17.test;

import org.junit.Before;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.PatchGWT;
import com.octo.gwt.test17.overrides.OverrideEvent;

public abstract class AbstractGWTTest {

	@Before
	public void setUp() throws Exception {
		//initialisation du framework de mock GWT
		PatchGWT.init();
		// reinit GWT
		PatchGWT.reset();
	}
	
	protected void click(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}
	
	protected void click(MenuBar parent, MenuItem clickedItem) {
		parent.onBrowserEvent(new OverrideEvent(Event.ONCLICK, clickedItem.getElement()));
	}

}
