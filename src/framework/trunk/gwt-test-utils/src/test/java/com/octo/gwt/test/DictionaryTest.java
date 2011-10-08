package com.octo.gwt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.i18n.client.Dictionary;
import com.octo.gwt.test.i18n.DictionaryBuilder;

public class DictionaryTest extends GwtTestTest {

  @Test
  public void checkToString() {
    // Arrange
    addDictionary(createTestDictionary("toString"));

    // Act
    String toString = Dictionary.getDictionary("toString").toString();

    // Assert
    assertEquals("Dictionary toString", toString);
  }

  @Test
  public void get() {
    // Arrange
    addDictionary(createTestDictionary("get"));

    // Act
    String name = Dictionary.getDictionary("get").get("name");
    String description = Dictionary.getDictionary("get").get("description");
    String since = Dictionary.getDictionary("get").get("since");
    String by = Dictionary.getDictionary("get").get("by");

    // Assert
    assertEquals("gwt-test-utils", name);
    assertEquals("An awesome GWT testing tool ;-)", description);
    assertEquals("October 2K9", since);
    assertEquals("Bertrand & Gael", by);
  }

  @Test
  public void getDictionary() {
    // Arrange
    Dictionary expected = createTestDictionary("getDictionary");
    addDictionary(expected);

    // Act
    Dictionary dictionnary = Dictionary.getDictionary("getDictionary");

    // Assert
    assertEquals(expected, dictionnary);
  }

  @Test
  public void keySet() {
    // Arrange
    addDictionary(createTestDictionary("keySet"));

    // Act
    Set<String> keySet = Dictionary.getDictionary("keySet").keySet();

    // Assert
    assertEquals(4, keySet.size());
    assertTrue(keySet.contains("name"));
    assertTrue(keySet.contains("description"));
    assertTrue(keySet.contains("since"));
    assertTrue(keySet.contains("by"));
  }

  @Test
  public void values() {
    // Arrange
    addDictionary(createTestDictionary("values"));

    // Act
    Collection<String> values = Dictionary.getDictionary("values").values();

    // Assert
    assertEquals(4, values.size());
    assertTrue(values.contains("Bertrand & Gael"));
    assertTrue(values.contains("October 2K9"));
    assertTrue(values.contains("gwt-test-utils"));
    assertTrue(values.contains("An awesome GWT testing tool ;-)"));
  }

  private Dictionary createTestDictionary(String name) {

    Map<String, String> entries = new HashMap<String, String>();
    entries.put("name", "gwt-test-utils");
    entries.put("description", "An awesome GWT testing tool ;-)");

    DictionaryBuilder builder = DictionaryBuilder.create(name, entries);
    builder.addEntry("since", "October 2K9");
    builder.addEntry("by", "Bertrand & Gael");

    return builder.build();
  }
}
