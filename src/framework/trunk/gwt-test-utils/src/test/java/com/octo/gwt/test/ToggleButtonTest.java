package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ToggleButton;
import com.octo.gwt.test.utils.events.Browser;

public class ToggleButtonTest extends AbstractGwtTest {

	private boolean clicked;

	@Test
	public void checkClick() {
		// Setup
		final ToggleButton toggleButton = new ToggleButton("Up", "Down");
		clicked = false;

		toggleButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clicked = true;
			}
		});

		// Pre-Assert
		Assert.assertFalse("ToggleButton should not be toggled by default", toggleButton.isDown());

		// Test
		Browser.click(toggleButton);

		// Assert
		Assert.assertTrue("ToggleButton onClick was not triggered", clicked);
		Assert.assertTrue("ToggleButton should be toggled after being clicked once", toggleButton.isDown());

		// Test 2
		Browser.click(toggleButton);
		Assert.assertFalse("ToggleButton should not be toggled after being clicked twice", toggleButton.isDown());
	}

}
