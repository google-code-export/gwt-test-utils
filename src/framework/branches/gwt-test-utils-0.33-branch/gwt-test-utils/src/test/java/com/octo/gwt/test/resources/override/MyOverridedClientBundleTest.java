package com.octo.gwt.test.resources.override;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.GwtTestTest;

public class MyOverridedClientBundleTest extends GwtTestTest {

  @Test
  public void testDataResource_NoOverride() {
    // Arrange
    DataResource testDataResource = MyOverridedClientBundle.INSTANCE.testDataResource();

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
        "com.octo.gwt.test.internal.utils.resources.DataResourceCallback generated for 'com.octo.gwt.test.resources.override.MyOverridedClientBundle.testDataResource()'",
        toString);
    assertEquals(testDataResource,
        MyOverridedClientBundle.INSTANCE.testDataResource());
  }

  @Test
  public void testImageResource_OverrideWithAnnotation() {
    // Arrange
    ImageResource testImageResource = MyOverridedClientBundle.INSTANCE.testImageResource();

    // Act
    String name = testImageResource.getName();
    String url = testImageResource.getURL();
    int heigh = testImageResource.getHeight();
    int left = testImageResource.getLeft();
    int width = testImageResource.getWidth();
    int top = testImageResource.getTop();

    // Assert
    assertEquals("testImageResource", name);
    assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/resources/override/override_testImageResource.gif",
        url);
    assertEquals(0, heigh);
    assertEquals(0, left);
    assertEquals(0, width);
    assertEquals(0, top);
    assertEquals(testImageResource,
        MyOverridedClientBundle.INSTANCE.testImageResource());
  }

  @Test
  public void textResourceTxt_OverrideWithoutAnnotation() {
    // Arrange
    TextResource textResource = MyOverridedClientBundle.INSTANCE.textResourceTxt();
    String expectedText = "Overrided text resource !";

    // Act
    String name = textResource.getName();
    String text = textResource.getText();

    // Assert
    assertEquals("textResourceTxt", name);
    assertEquals(expectedText, text);
    assertEquals(textResource,
        MyOverridedClientBundle.INSTANCE.textResourceTxt());
  }

}
