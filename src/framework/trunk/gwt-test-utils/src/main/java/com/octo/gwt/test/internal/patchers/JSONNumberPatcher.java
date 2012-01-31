package com.octo.gwt.test.internal.patchers;

import com.google.gwt.json.client.JSONNumber;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JSONNumber.class)
public class JSONNumberPatcher {

  @PatchMethod
  static String toString(JSONNumber jsonNumber) {
    return String.valueOf(jsonNumber.doubleValue());
  }

}
