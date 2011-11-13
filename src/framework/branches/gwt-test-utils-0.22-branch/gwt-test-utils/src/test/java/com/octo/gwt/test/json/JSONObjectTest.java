package com.octo.gwt.test.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.octo.gwt.test.GwtTestTest;

public class JSONObjectTest extends GwtTestTest {

  @Test
  public void empty() {

  }

  // @Test
  public void parseStrict() {
    // Arrange
    String json = "{\"string\": \"json string\", \"int\": 3}";

    // Act
    JSONObject o = JSONParser.parse(json).isObject();

    // Assert
    JSONString string = (JSONString) o.get("string");
    JSONNumber number = (JSONNumber) o.get("int");

    assertEquals("jsons string", string.stringValue());
    assertEquals(3.0, number.doubleValue(), 0);
    // JsonToken.START_OBJECT

  }
}
