package com.googlecode.gwt.test.internal.patchers;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;

@PatchClass(JsonUtils.class)
class JsonUtilsPatcher {

   @PatchMethod
   static String escapeValue(String toEscape) {
      return "\"" + StringEscapeUtils.escapeJavaScript(toEscape) + "\"";
   }

   @PatchMethod
   static boolean hasJsonParse() {
      return true;
   }

   @PatchMethod
   static JavaScriptObject initEscapeTable() {
      return null;
   }
}
