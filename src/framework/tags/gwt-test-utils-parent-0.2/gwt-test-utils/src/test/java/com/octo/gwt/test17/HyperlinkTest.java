package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class HyperlinkTest extends AbstractGWTTest {
	
	@Test
	public void checkHTML() {
		Hyperlink link = new Hyperlink();
		link.setHTML("link-HTML");
		
		Assert.assertEquals("link-HTML", link.getHTML());
	}
	
	@Test
	public void checkTextAndToken() {
		Hyperlink link = new Hyperlink("test-text", "test-history-token");
		
		Assert.assertEquals("test-text", link.getText());
		Assert.assertEquals("test-history-token", link.getTargetHistoryToken());
	}
	
	private Boolean bool = false;
	
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
