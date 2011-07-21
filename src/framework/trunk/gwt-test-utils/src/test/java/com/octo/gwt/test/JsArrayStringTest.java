package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.JsArrayString;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;

public class JsArrayStringTest extends GwtTestTest {

  private JsArrayString jsArrayString;

  @Before
  public void beforeJsArrayStringTest() {
    // Arrange
    jsArrayString = JavaScriptObjects.newObject(JsArrayString.class);
    assertEquals(0, jsArrayString.length());
    assertNull(jsArrayString.get(100));

    // Act
    jsArrayString.set(4, "test");

    // Assert
    assertEquals(5, jsArrayString.length());
    assertNull(jsArrayString.get(3));
  }

  @Test
  public void join() {
    // Act
    String join = jsArrayString.join();

    // Assert
    assertEquals(",,,,test", join);
  }

  @Test
  public void join_AfterResize() {
    // Arrange
    jsArrayString.setLength(3);

    // Act
    String join = jsArrayString.join();

    // Assert
    assertEquals(",,", join);
    assertEquals(3, jsArrayString.length());
  }

  @Test
  public void push() {
    // Act
    jsArrayString.push("pushed");

    // Assert
    assertEquals(",,,,test,pushed", jsArrayString.join());
    assertEquals(6, jsArrayString.length());
    assertEquals("pushed", jsArrayString.get(jsArrayString.length() - 1));
  }

  @Test
  public void shift() {
    // Arrange
    jsArrayString.set(0, "toshift");

    // Act
    String shift = jsArrayString.shift();

    // Assert
    assertEquals("toshift", shift);
    assertEquals(4, jsArrayString.length());
    assertEquals(",,,test", jsArrayString.join());
  }

  @Test
  public void unshift() {
    // Act
    jsArrayString.unshift("tounshift");

    // Assert
    assertEquals(6, jsArrayString.length());
    assertEquals("tounshift,,,,,test", jsArrayString.join());
  }
}
