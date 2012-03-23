package com.octo.gwt.test.internal.patchers;

import com.google.gwt.json.client.JSONNumber;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JSONNumber.class)
class JSONNumberPatcher {

  @PatchMethod
  static String toString(JSONNumber jsonNumber) {
    double doubleValue = jsonNumber.doubleValue();
    if (Math.floor(doubleValue) == doubleValue) {
      // the number is an integer
      return String.valueOf((int) doubleValue);
    } else {
      return String.valueOf(doubleValue);
    }
  }

}
