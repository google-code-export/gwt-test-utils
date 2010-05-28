package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.octo.gwt.test.AbstractGwtTest;

public class HyperlinkTest extends AbstractGwtTest {

	@Test
	public void checkTitle() {
		Hyperlink link = new Hyperlink();
		link.setTitle("title");
		Assert.assertEquals("title", link.getTitle());
	}

	@Test
	public void checkHTMLAndToken() {
		Hyperlink link = new Hyperlink("<h1>foo</h1>", true, "test-history-token");
		Assert.assertEquals("test-history-token", link.getTargetHistoryToken());
		Assert.assertEquals("<h1>foo</h1>", link.getHTML());
		link.setHTML("<h1>test</h1>");
		Assert.assertEquals("<h1>test</h1>", link.getHTML());
	}

	@Test
	public void checkHTML() {
		Hyperlink link = new Hyperlink();
		link.setHTML("link-HTML");

		Assert.assertEquals("link-HTML", link.getHTML());
	}

	@Test
	public void checkVisible() {
		Hyperlink link = new Hyperlink();
		Assert.assertEquals(true, link.isVisible());
		link.setVisible(false);
		Assert.assertEquals(false, link.isVisible());
	}

	@Test
	public void checkTextAndToken() {
		Hyperlink link = new Hyperlink("test-text", "test-history-token");

		Assert.assertEquals("test-text", link.getText());
		Assert.assertEquals("test-history-token", link.getTargetHistoryToken());
	}

	private Boolean bool = false;

	@SuppressWarnings("deprecation")
	@Test
	public void checkClick() {
		Hyperlink link = new Hyperlink();
		link.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				bool = true;
			}

		});

		//simule the mouse click
		Assert.assertEquals(false, bool);
		click(link);

		Assert.assertEquals(true, bool);
	}

}
