package com.octo.gwt.test.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.Tree.Resources;
import com.octo.gwt.test.GwtTestTest;

public class ClientBundleTest extends GwtTestTest {

  @Test
  public void cssResource() {
    // Arrange
    TestCssResource testCssResource = MyClientBundle.INSTANCE.testCssResource();

    // Act
    String name = testCssResource.getName();
    String testStyle = testCssResource.testStyle();
    String testStyleWithHover = testCssResource.testStyleWithHover();
    String testStyleOnSpecificElement = testCssResource.testStyleOnSpecificElement();
    String testStyleOnSpecificStyle = testCssResource.testStyleOnSpecificStyle();
    String constantValue = testCssResource.testConstant();
    String toString = testCssResource.toString();

    // Assert
    assertEquals("testCssResource", name);
    assertEquals("testStyle", testStyle);
    assertEquals("testStyleWithHover", testStyleWithHover);
    assertEquals("testStyleOnSpecificElement", testStyleOnSpecificElement);
    assertEquals("testStyleOnSpecificStyle", testStyleOnSpecificStyle);
    assertEquals("constant-value", constantValue);
    assertEquals(
        "com.octo.gwt.test.internal.resources.CssResourceCallback generated for 'com.octo.gwt.test.resources.MyClientBundle.testCssResource()'",
        toString);
    assertEquals(testCssResource, MyClientBundle.INSTANCE.testCssResource());
  }

  @Test
  public void cssResourceEnsureInjected() {
    // Arrange
    TestCssResource testCssResource = MyClientBundle.INSTANCE.testCssResource();

    // Act & Assert
    assertTrue(testCssResource.ensureInjected());
    assertFalse(testCssResource.ensureInjected());
  }

  @Test
  public void dataResource() {
    // Arrange
    DataResource testDataResource = MyClientBundle.INSTANCE.testDataResource();

    // Act
    String name = testDataResource.getName();
    String url = testDataResource.getUrl();
    String toString = testDataResource.toString();

    // Assert
    assertEquals("testDataResource", name);
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/resources/textResourceXml.xml",
        url);
    assertEquals(
        "com.octo.gwt.test.internal.resources.DataResourceCallback generated for 'com.octo.gwt.test.resources.MyClientBundle.testDataResource()'",
        toString);
    assertEquals(testDataResource, MyClientBundle.INSTANCE.testDataResource());
  }

  @Test
  public void imageResource() {
    // Arrange
    ImageResource testImageResource = MyClientBundle.INSTANCE.testImageResource();

    // Act
    String name = testImageResource.getName();
    String url = testImageResource.getURL();
    int heigh = testImageResource.getHeight();
    int left = testImageResource.getLeft();
    int width = testImageResource.getWidth();
    int top = testImageResource.getTop();
    String toString = testImageResource.toString();

    // Assert
    assertEquals("testImageResource", name);
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/resources/testImageResource.gif",
        url);
    assertEquals(0, heigh);
    assertEquals(0, left);
    assertEquals(0, width);
    assertEquals(0, top);
    assertEquals(
        "com.octo.gwt.test.internal.resources.ImageResourceCallback generated for 'com.octo.gwt.test.resources.MyClientBundle.testImageResource()'",
        toString);
    assertEquals(testImageResource, MyClientBundle.INSTANCE.testImageResource());
  }

  @Test
  public void imageResource_FromGwtAPI() {
    // Arrange
    Resources treeResources = GWT.create(Resources.class);

    // Act
    String name = treeResources.treeOpen().getName();
    String url = treeResources.treeLeaf().getURL();

    // Assert
    assertEquals("treeOpen", name);
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/google/gwt/user/client/ui/treeLeaf.gif",
        url);
  }

  @Test
  public void imageResource_ShouldThrowExceptionWhenMultipleMatchingResourceFile() {
    // Arrange
    String expectedMessage = "Too many resource files found for method 'com.octo.gwt.test.resources.MyClientBundle.doubleShouldThrowException()'";
    try {
      // Act
      MyClientBundle.INSTANCE.doubleShouldThrowException();
      fail("An exception should have been thrown since there are multiple matching file for the tested ClientBundle method");
    } catch (Exception e) {
      // Assert
      assertEquals(expectedMessage, e.getMessage());
    }

  }

  @Test
  public void textResource_FromGwtAPI() {
    // Arrange
    com.google.gwt.user.client.impl.WindowImplIE.Resources treeResources = GWT.create(com.google.gwt.user.client.impl.WindowImplIE.Resources.class);

    // Act
    String name = treeResources.initWindowCloseHandler().getName();
    String text = treeResources.initWindowCloseHandler().getText();

    // Assert
    assertEquals("initWindowCloseHandler", name);
    assertTrue(text.startsWith("function __gwt_initWindowCloseHandler(beforeunload, unload) {"));
  }

  @Test
  public void textResource_Txt() {
    // Arrange
    TextResource textResource = MyClientBundle.INSTANCE.textResourceTxt();
    String expectedText = "Hello gwt-test-utils !\r\nThis is a test with a simple text file";

    // Act
    String name = textResource.getName();
    String text = textResource.getText();
    String toString = textResource.toString();

    // Assert
    assertEquals("textResourceTxt", name);
    assertEquals(expectedText, text);
    assertEquals(
        "com.octo.gwt.test.internal.resources.TextResourceCallback generated for 'com.octo.gwt.test.resources.MyClientBundle.textResourceTxt()'",
        toString);
    assertEquals(textResource, MyClientBundle.INSTANCE.textResourceTxt());
  }

  @Test
  public void textResource_Xml() {
    // Arrange
    TextResource textResource = MyClientBundle.INSTANCE.textResourceXml();
    String expectedText = "<gwt-test-utils>\r\n\t<test>this is a test</test>\r\n</gwt-test-utils>";

    // Act
    String name = textResource.getName();
    String text = textResource.getText();
    String toString = textResource.toString();

    // Assert
    assertEquals("textResourceXml", name);
    assertEquals(expectedText, text);
    assertEquals(
        "com.octo.gwt.test.internal.resources.TextResourceCallback generated for 'com.octo.gwt.test.resources.MyClientBundle.textResourceXml()'",
        toString);
    assertEquals(textResource, MyClientBundle.INSTANCE.textResourceXml());
  }

}
