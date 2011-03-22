package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.octo.gwt.test.utils.events.Browser;

public class PushButtonTest extends GwtTest {

	private boolean clicked;

	@Test
	public void checkClick() {

		// Setup
		clicked = false;

		final PushButton b = new PushButton("Up", "Down");

		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				clicked = true;
			}
		});

		// Pre-Assert
		Assert.assertEquals("Up", b.getText());

		// Test
		Browser.click(b);

		// Assert
		Assert.assertTrue("PushedButton onClick was not triggered", clicked);
		Assert.assertEquals("Up", b.getText());
	}

}
