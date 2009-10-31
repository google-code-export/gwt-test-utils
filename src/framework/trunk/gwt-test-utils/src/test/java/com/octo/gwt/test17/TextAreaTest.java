package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.TextArea;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class TextAreaTest extends AbstractGWTTest {
	
	@Test
	public void checkText() {
		TextArea t = new TextArea();
		t.setText("text");
		
		Assert.assertEquals("text", t.getText());
	}
	
	@Test
	public void checkTitle() {
		TextArea t = new TextArea();
		t.setTitle("title");

		Assert.assertEquals("title", t.getTitle());
	}
	
	@Test
	public void checkVisibleLines() {
		TextArea t = new TextArea();
		t.setVisibleLines(10);
		
		Assert.assertEquals(10, t.getVisibleLines());
	}

}
