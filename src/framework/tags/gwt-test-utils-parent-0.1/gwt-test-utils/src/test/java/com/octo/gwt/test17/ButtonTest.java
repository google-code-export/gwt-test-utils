package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.overrides.OverrideEvent;
import com.octo.gwt.test17.test.AbstractGWTTest;

@SuppressWarnings("deprecation")
public class ButtonTest extends AbstractGWTTest {

	@Test
	public void checkText() {
		Button b = new Button();
		b.setText("toto");

		Assert.assertEquals("toto", b.getText());
	}

	@Test
	public void checkHTML() {
		Button b = new Button("test");
		Assert.assertEquals("test", b.getHTML());

		b.setHTML("test-html");

		Assert.assertEquals("test-html", b.getHTML());
	}

	@Test
	public void checkEnable() {
		Button b = new Button();
		Assert.assertEquals(true, b.isEnabled());

		b.setEnabled(false);

		Assert.assertEquals(false, b.isEnabled());
	}

	@Test
	public void checkVisible() {
		Button b = new Button();
		Assert.assertEquals(true, b.isVisible());

		b.setVisible(false);

		Assert.assertEquals(false, b.isVisible());
	}

	@Test
	public void checkStyleName() {
		Button b = new Button();
		Assert.assertEquals("gwt-Button", b.getStyleName());

		b.setStyleName("test-button-style");

		Assert.assertEquals("test-button-style", b.getStyleName());
	}
	
	@Test
	public void checkStylePrimaryName() {
		Button b = new Button();

		b.setStylePrimaryName("test-button-styleP");

		Assert.assertEquals("test-button-styleP", b.getStylePrimaryName());
	}

	@Test
	public void checkClickWithHander() {
		final Button b = new Button();

		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				b.setHTML("clicked");

			}
		});

		Assert.assertEquals(null, b.getHTML());

		// simulate click
		b.onBrowserEvent(new OverrideEvent(Event.ONCLICK));

		Assert.assertEquals("clicked", b.getHTML());
	}

	@Test
	public void checkClickWithListener() {
		final Button b = new Button();

		b.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				b.setHTML("clicked");

			}
		});

		Assert.assertEquals(null, b.getHTML());

		// simulate click
		click(b);

		Assert.assertEquals("clicked", b.getHTML());
	}

}
