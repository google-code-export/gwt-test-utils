package com.octo.gwt.test.i18n;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Dictionary;
import com.octo.gwt.test.internal.i18n.DictionaryUtils;

/**
 * Builder which provides some helper methods to configure {@link Dictionary}
 * instances.
 * 
 * @author Gael Lazzari
 * 
 */
public class DictionaryBuilder {

  /**
   * Create a new builder for an empty {@link Dictionary}.
   * 
   * @param dictionaryName The name of the Dictionary
   * @return The builder instance
   */
  public static DictionaryBuilder create(String dictionaryName) {
    return create(dictionaryName, new HashMap<String, String>());
  }

  /**
   * Create a new builder for a {@link Dictionary} initialized with some
   * key/value entries.
   * 
   * @param dictionaryName The name of the Dictionary
   * @param entries The Dictionnary's entries
   * @return The builder instance
   */
  public static DictionaryBuilder create(String dictionaryName,
      Map<String, String> entries) {
    return new DictionaryBuilder(dictionaryName, entries);
  }

  private final Map<String, String> entries;

  private final String name;

  private DictionaryBuilder(String dictionaryName, Map<String, String> entries) {
    this.name = dictionaryName;
    this.entries = entries;
  }

  /**
   * Add an entry to the {@link Dictionary} this builder is in charge of.
   * 
   * @param key The key of the dictionary's entry
   * @param value The value of the dictionary's entry
   * @return The builder instance
   */
  public DictionaryBuilder addEntry(String key, String value) {
    entries.put(key, value);
    return this;
  }

  /**
   * Build the {@link Dictionary} instance with its name and entries configured
   * 
   * @return The GWT dictionary instance.
   */
  public Dictionary build() {
    Dictionary dictionary = Dictionary.getDictionary(this.name);

    DictionaryUtils.addEntries(dictionary, this.entries);

    return dictionary;
  }

}
