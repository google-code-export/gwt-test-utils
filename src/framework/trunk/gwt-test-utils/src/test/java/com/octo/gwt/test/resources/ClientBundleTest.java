package com.octo.gwt.test.resources;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.AbstractGWTTest;

public class ClientBundleTest extends AbstractGWTTest {
	
	@Test
	public void textResourceTxt() {
		// Setup
		TextResource textResource = MyClientBundle.INSTANCE.textResourceTxt();
		String expectedText = "Hello gwt-test-utils !\r\nThis is a test with a simple text file";
		
		// Test
		String name = textResource.getName();
		String text = textResource.getText();
		
		// Assert
		Assert.assertEquals("textResourceTxt", name);
		Assert.assertEquals(expectedText, text);
	}
	
	@Test
	public void textResourceXml() {
		// Setup
		TextResource textResource = MyClientBundle.INSTANCE.textResourceXml();
		String expectedText = "<gwt-test-utils>\r\n\t<test>this is a test</test>\r\n</gwt-test-utils>";
		
		// Test
		String name = textResource.getName();
		String text = textResource.getText();
		
		// Assert
		Assert.assertEquals("textResourceXml", name);
		Assert.assertEquals(expectedText, text);
	}

}
