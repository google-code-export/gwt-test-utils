package com.octo.gwt.test.resources.override;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.GwtTestTest;

public class MyOverridedClientBundleTest extends GwtTestTest {

  @Test
  public void checkChildNoOverride() {
    // Arrange
    DataResource testDataResource = MyOverridedClientBundle.INSTANCE.testDataResource();

    // Act
    String name = testDataResource.getName();
    String url = testDataResource.getUrl();

    // Assert
    Assert.assertEquals("testDataResource", name);
    Assert.assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/resources/textResourceXml.xml",
        url);
  }

  @Test
  public void checkChildOverrideWithAnnotation() {
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
    Assert.assertEquals("testImageResource", name);
    Assert.assertEquals(
        "http://127.0.0.1:8888/gwt_test_utils_module/com/octo/gwt/test/resources/override/override_testImageResource.gif",
        url);
    Assert.assertEquals(0, heigh);
    Assert.assertEquals(0, left);
    Assert.assertEquals(0, width);
    Assert.assertEquals(0, top);
  }

  @Test
  public void checkChildOverrideWithoutAnnotation() {
    // Arrange
    TextResource textResource = MyOverridedClientBundle.INSTANCE.textResourceTxt();
    String expectedText = "Overrided text resource !";

    // Act
    String name = textResource.getName();
    String text = textResource.getText();

    // Assert
    Assert.assertEquals("textResourceTxt", name);
    Assert.assertEquals(expectedText, text);
  }

}
