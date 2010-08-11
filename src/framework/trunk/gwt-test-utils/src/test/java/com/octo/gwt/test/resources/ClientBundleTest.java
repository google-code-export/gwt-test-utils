package com.octo.gwt.test.resources;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
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
		String testStyleWithHover = testCssResource.testStyleWithHover();
		String testStyleOnSpecificElement = testCssResource.testStyleOnSpecificElement();
		String testStyleOnSpecificStyle = testCssResource.testStyleOnSpecificStyle();
		String constantValue = testCssResource.testConstant();

		// Assert
		Assert.assertEquals("testCssResource", name);
		Assert.assertEquals("testStyle", testStyle);
		Assert.assertEquals("testStyleWithHover", testStyleWithHover);
		Assert.assertEquals("testStyleOnSpecificElement", testStyleOnSpecificElement);
		Assert.assertEquals("testStyleOnSpecificStyle", testStyleOnSpecificStyle);
		Assert.assertEquals("constant-value", constantValue);
	}

	@Test
	public void checkCssResourceEnsureInjected() {
		// Setup
		TestCssResource testCssResource = MyClientBundle.INSTANCE.testCssResource();

		// Tests & Assert
		Assert.assertTrue(testCssResource.ensureInjected());
		Assert.assertFalse(testCssResource.ensureInjected());
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

	@Test
	public void checkImageResource() {
		// Setup
		ImageResource testImageResource = MyClientBundle.INSTANCE.testImageResource();

		// Test
		String name = testImageResource.getName();
		String url = testImageResource.getURL();
		int heigh = testImageResource.getHeight();
		int left = testImageResource.getLeft();
		int width = testImageResource.getWidth();
		int top = testImageResource.getTop();

		// Assert
		Assert.assertEquals("testImageResource", name);
		Assert.assertEquals("http://localhost:8888/gwt_test_utils_module/com/octo/gwt/test/resources/testImageResource.gif", url);
		Assert.assertEquals(0, heigh);
		Assert.assertEquals(0, left);
		Assert.assertEquals(0, width);
		Assert.assertEquals(0, top);
	}

	@Test
	public void checkImageResourceShouldThrowExceptionWhenMultipleMatchingResourceFile() {
		// Setup
		String expectedMessage = "Too many resource files found for method MyClientBundle.doubleShouldThrowException()";
		try {
			// Test
			MyClientBundle.INSTANCE.doubleShouldThrowException();
			Assert.fail("An exception should have been thrown since there are multiple matching file for the tested ClientBundle method");
		} catch (Exception e) {
			// Assert
			Assert.assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Test
	public void checkChildNoOverride() {
		// Setup
		DataResource testDataResource = MyChildClientBundle.INSTANCE.testDataResource();

		// Test
		String name = testDataResource.getName();
		String url = testDataResource.getUrl();

		// Assert
		Assert.assertEquals("testDataResource", name);
		Assert.assertEquals("http://localhost:8888/gwt_test_utils_module/com/octo/gwt/test/resources/textResourceXml.xml", url);
	}

	@Test
	public void checkChildOverride() {
		// Setup
		ImageResource testImageResource = MyChildClientBundle.INSTANCE.testImageResource();

		// Test
		String name = testImageResource.getName();
		String url = testImageResource.getURL();
		int heigh = testImageResource.getHeight();
		int left = testImageResource.getLeft();
		int width = testImageResource.getWidth();
		int top = testImageResource.getTop();

		// Assert
		Assert.assertEquals("testImageResource", name);
		Assert.assertEquals("http://localhost:8888/gwt_test_utils_module/com/octo/gwt/test/resources/override_testImageResource.gif", url);
		Assert.assertEquals(0, heigh);
		Assert.assertEquals(0, left);
		Assert.assertEquals(0, width);
		Assert.assertEquals(0, top);
	}

}
