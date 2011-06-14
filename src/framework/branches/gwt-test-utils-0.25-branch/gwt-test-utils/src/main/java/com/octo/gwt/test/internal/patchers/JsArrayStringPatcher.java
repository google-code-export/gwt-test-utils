package com.octo.gwt.test.internal.patchers;

import java.util.List;

import com.google.gwt.core.client.JsArrayString;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JsArrayString.class)
class JsArrayStringPatcher {

  @PatchMethod
  static String get(JsArrayString array, int index) {
    List<String> wrapped = getWrappedList(array);

    if (index >= wrapped.size()) {
      return null;
    }

    return wrapped.get(index);
  }

  @PatchMethod
  static String join(JsArrayString array, String separator) {
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
  static int length(JsArrayString array) {
    return getWrappedList(array).size();
  }

  @PatchMethod
  static void push(JsArrayString array, String value) {
    getWrappedList(array).add(value);
  }

  @PatchMethod
  static void set(JsArrayString array, int index, String value) {
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
  static void setLength(JsArrayString array, int newLength) {
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
  static String shift(JsArrayString array) {
    List<String> wrapped = getWrappedList(array);
    return (wrapped.size() > 0) ? wrapped.remove(0) : null;
  }

  @PatchMethod
  static void unshift(JsArrayString array, String value) {
    getWrappedList(array).add(0, value);
  }

  @SuppressWarnings("unchecked")
  private static List<String> getWrappedList(JsArrayString array) {
    return (List<String>) JavaScriptObjects.getObject(array,
        JsoProperties.JSARRAY_WRAPPED_LIST);
  }

}
