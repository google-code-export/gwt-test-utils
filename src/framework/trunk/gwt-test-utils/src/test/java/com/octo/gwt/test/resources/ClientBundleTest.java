package com.octo.gwt.test.resources;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.AbstractGwtTest;

public class ClientBundleTest extends AbstractGwtTest {
	
	@Override
	public String getCurrentTestedModuleFile() {
		return "test-config.gwt.xml";
	}
	
	@Test
	public void checkTextResourceTxt() {
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
	public void checkTResourceXml() {
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
	
	@Test
	public void checkCssResource() {
		// Setup
		TestCssResource testCssResource = MyClientBundle.INSTANCE.testCssResource();
	
		// Test
		String name = testCssResource.getName();
		String testStyle = testCssResource.testStyle();
		String constantValue = testCssResource.testConstant();
		
		// Assert
		Assert.assertEquals("testCssResource", name);
		Assert.assertEquals("testStyle", testStyle);
		Assert.assertEquals("constant-value", constantValue);
	}
	
	@Test
	public void checkDataResource() {
		// Setup
		DataResource testDataResource = MyClientBundle.INSTANCE.testDataResource();
	
		// Test
		String name = testDataResource.getName();
		String url = testDataResource.getUrl();
		
		// Assert
		Assert.assertEquals("testDataResource", name);
		Assert.assertEquals("http://localhost:8888/gwt_test_utils_module/com/octo/gwt/test/resources/textResourceXml.xml", url);
	}

}
