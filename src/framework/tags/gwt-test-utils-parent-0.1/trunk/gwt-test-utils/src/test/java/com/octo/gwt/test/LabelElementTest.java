package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.AbstractGWTTest;

public class LabelElementTest extends AbstractGWTTest {
	
	@Test
	public void checkAs() {
		// Set up
		Label label = new Label();
		
		// Test
		LabelElement e = LabelElement.as(label.getElement());
		
		// Assert
		Assert.assertEquals("div", e.getTagName());
	}
	
	@Test
	public void checkHtmlFor() {		
		// Set up
		Label label = new Label();
		LabelElement e = LabelElement.as(label.getElement());
		
		// Test
		e.setHtmlFor("htmlFor");
		
		// Assert
		Assert.assertEquals("htmlFor", e.getHtmlFor());
	}
	
	

}
