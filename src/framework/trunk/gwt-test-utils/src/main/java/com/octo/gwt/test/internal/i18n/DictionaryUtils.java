package com.octo.gwt.test.internal.i18n;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.Dictionary;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class DictionaryUtils {

  public static void addEntries(Dictionary dictionary,
      Map<String, String> entries) {
    getEntries(dictionary).putAll(entries);
  }

  public static void attach(Dictionary dictionary, String name) {
    JavaScriptObject dict = JavaScriptObjects.newObject(JavaScriptObject.class);

    Map<String, String> entries = new HashMap<String, String>();
    JavaScriptObjects.setProperty(dict, JsoProperties.DICTIONARY_ENTRIES,
        entries);

    GwtReflectionUtils.setPrivateFieldValue(dictionary, "dict", dict);
  }

  public static JavaScriptObject getDict(Dictionary dictionary) {
    return GwtReflectionUtils.getPrivateFieldValue(dictionary, "dict");
  }

  public static Map<String, String> getEntries(Dictionary dictionary) {
    return JavaScriptObjects.getObject(getDict(dictionary),
        JsoProperties.DICTIONARY_ENTRIES);
  }

  private DictionaryUtils() {

  }

}
