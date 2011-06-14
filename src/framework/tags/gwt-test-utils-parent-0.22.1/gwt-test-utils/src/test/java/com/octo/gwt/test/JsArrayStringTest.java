package com.octo.gwt.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.JsArrayString;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;

public class JsArrayStringTest extends GwtTestTest {

  private JsArrayString jsArrayString;

  @Test
  public void checkJoin() {
    // Act
    String join = jsArrayString.join();

    // Assert
    Assert.assertEquals(",,,,test", join);
  }

  @Test
  public void checkJoinAfterResize() {
    // Arrange
    jsArrayString.setLength(3);

    // Act
    String join = jsArrayString.join();

    // Assert
    Assert.assertEquals(",,", join);
    Assert.assertEquals(3, jsArrayString.length());
  }

  @Test
  public void checkPush() {
    // Act
    jsArrayString.push("pushed");

    // Assert
    Assert.assertEquals(",,,,test,pushed", jsArrayString.join());
    Assert.assertEquals(6, jsArrayString.length());
    Assert.assertEquals("pushed", jsArrayString.get(jsArrayString.length() - 1));
  }

  @Test
  public void checkShift() {
    // Arrange
    jsArrayString.set(0, "toshift");

    // Act
    String shift = jsArrayString.shift();

    // Assert
    Assert.assertEquals("toshift", shift);
    Assert.assertEquals(4, jsArrayString.length());
    Assert.assertEquals(",,,test", jsArrayString.join());
  }

  @Test
  public void checkUnshift() {
    // Act
    jsArrayString.unshift("tounshift");

    // Assert
    Assert.assertEquals(6, jsArrayString.length());
    Assert.assertEquals("tounshift,,,,,test", jsArrayString.join());
  }

  @Before
  public void setupJsArrayStringTest() {
    // Arrange
    jsArrayString = JavaScriptObjects.newObject(JsArrayString.class);
    Assert.assertEquals(0, jsArrayString.length());
    Assert.assertNull(jsArrayString.get(100));

    // Act
    jsArrayString.set(4, "test");

    // Assert
    Assert.assertEquals(5, jsArrayString.length());
    Assert.assertNull(jsArrayString.get(3));
  }
}
