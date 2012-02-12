package com.octo.gwt.test.internal.patchers;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.octo.gwt.test.internal.utils.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JsArray.class)
class JsArrayPatcher {

  @PatchMethod
  static JavaScriptObject get(JsArray<JavaScriptObject> array, int index) {
    List<JavaScriptObject> wrapped = getWrappedList(array);

    if (index >= wrapped.size()) {
      return null;
    }

    return wrapped.get(index);
  }

  @PatchMethod
  static String join(JsArray<JavaScriptObject> array, String separator) {
    StringBuilder sb = new StringBuilder();

    for (Object o : getWrappedList(array)) {
      if (o != null) {
        sb.append(o);
      }
      sb.append(separator);
    }

    return sb.substring(0, sb.length() - separator.length());
  }

  @PatchMethod
  static int length(JsArray<JavaScriptObject> array) {
    return getWrappedList(array).size();
  }

  @PatchMethod
  static void push(JsArray<JavaScriptObject> array, JavaScriptObject value) {
    getWrappedList(array).add(value);
  }

  @PatchMethod
  static void set(JsArray<JavaScriptObject> array, int index,
      JavaScriptObject value) {
    List<JavaScriptObject> wrapped = getWrappedList(array);
    int currentSize = wrapped.size();

    if (index >= currentSize) {
      for (int i = currentSize; i <= index; i++) {
        wrapped.add(null);
      }
    }

    wrapped.set(index, value);
  }

  @PatchMethod
  static void setLength(JsArray<JavaScriptObject> array, int newLength) {
    List<JavaScriptObject> wrapped = getWrappedList(array);

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
  static JavaScriptObject shift(JsArray<JavaScriptObject> array) {
    List<JavaScriptObject> wrapped = getWrappedList(array);
    return (wrapped.size() > 0) ? wrapped.remove(0) : null;
  }

  @PatchMethod
  static <T extends JavaScriptObject> void unshift(JsArray<T> array, T value) {
    List<T> list = getWrappedList(array);
    list.add(0, value);
  }

  @SuppressWarnings("unchecked")
  private static <T extends JavaScriptObject> List<T> getWrappedList(
      JsArray<T> array) {
    return (List<T>) JavaScriptObjects.getObject(array,
        JsoProperties.JSARRAY_WRAPPED_LIST);
  }

}
