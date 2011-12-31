package com.octo.gwt.test.internal.patchers;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JSONArray.class)
class JSONArrayPatcher {

  private static final String JSONARRAY_LIST = "JSONARRAY_LIST";

  @PatchMethod
  static JSONValue get(JSONArray jsonArray, int index) {
    List<JSONValue> list = getInnerList(jsonArray);

    if (index < 0 || index >= list.size()) {
      return null;
    }

    return list.get(index);
  }

  @PatchMethod
  static void set0(JSONArray jsonArray, int index, JSONValue value) {
    List<JSONValue> list = getInnerList(jsonArray);

    for (int i = list.size(); i <= index; i++) {
      list.add(null);
    }

    list.set(index, value);
  }

  @PatchMethod
  static int size(JSONArray jsonArray) {
    return getInnerList(jsonArray).size();
  }

  private static List<JSONValue> getInnerList(JSONArray jsonArray) {
    JavaScriptObject jsArray = jsonArray.getJavaScriptObject();

    List<JSONValue> list = JavaScriptObjects.getObject(jsArray, JSONARRAY_LIST);
    if (list == null) {
      list = new ArrayList<JSONValue>();
      JavaScriptObjects.setProperty(jsArray, JSONARRAY_LIST, list);
    }

    return list;
  }

}
