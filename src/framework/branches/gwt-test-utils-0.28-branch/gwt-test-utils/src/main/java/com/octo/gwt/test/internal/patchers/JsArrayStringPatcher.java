package com.octo.gwt.test.internal.patchers;

import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JsArrayString.class)
public class JsArrayStringPatcher extends AutomaticPatcher {

  @PatchMethod
  public static String get(JsArrayString array, int index) {
    List<String> wrapped = getWrappedList(array);

    if (index >= wrapped.size()) {
      return null;
    }

    return wrapped.get(index);
  }

  @PatchMethod
  public static String join(JsArrayString array, String separator) {
    StringBuilder sb = new StringBuilder();

    for (String s : getWrappedList(array)) {
      if (s != null) {
        sb.append(s);
      }
      sb.append(separator);
    }

    return sb.substring(0, sb.length() - separator.length());
  }

  @PatchMethod
  public static int length(JsArrayString array) {
    return getWrappedList(array).size();
  }

  @PatchMethod
  public static void push(JsArrayString array, String value) {
    getWrappedList(array).add(value);
  }

  @PatchMethod
  public static void set(JsArrayString array, int index, String value) {
    List<String> wrapped = getWrappedList(array);
    int currentSize = wrapped.size();

    if (index >= currentSize) {
      for (int i = currentSize; i <= index; i++) {
        wrapped.add(null);
      }
    }

    wrapped.set(index, value);
  }

  @PatchMethod
  public static void setLength(JsArrayString array, int newLength) {
    List<String> wrapped = getWrappedList(array);

    int currentSize = wrapped.size();
    if (currentSize > newLength) {
      for (int i = newLength; i < currentSize; i++) {
        wrapped.remove(i - 1);
      }
    } else if (currentSize < newLength) {
      for (int i = currentSize; i <= newLength; i++) {
        wrapped.add(null);
      }
    }
  }

  @PatchMethod
  public static String shift(JsArrayString array) {
    List<String> wrapped = getWrappedList(array);
    return (wrapped.size() > 0) ? wrapped.remove(0) : null;
  }

  @PatchMethod
  public static void unshift(JsArrayString array, String value) {
    getWrappedList(array).add(0, value);
  }

  @SuppressWarnings("unchecked")
  private static List<String> getWrappedList(JsArrayString array) {
    return (List<String>) JavaScriptObjects.getObject(array,
        JsoProperties.JSARRAY_WRAPPED_LIST);
  }

}
