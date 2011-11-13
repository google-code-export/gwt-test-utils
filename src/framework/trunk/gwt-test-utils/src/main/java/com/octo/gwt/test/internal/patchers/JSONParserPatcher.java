package com.octo.gwt.test.internal.patchers;

import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestJSONException;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(JSONParser.class)
class JSONParserPatcher {

  @PatchMethod
  static JSONValue evaluate(String json, boolean strict) {

    JsonParser jp = null;
    try {
      JsonFactory f = new JsonFactory();
      jp = f.createJsonParser(json);
      jp.nextToken(); // will return JsonToken.START_OBJECT (verify?)
      JSONObject jsonObject = extractJSONObject(json, jp);
      return jsonObject;
    } catch (Exception e) {
      if (e instanceof GwtTestException) {
        throw (GwtTestException) e;
      }
      throw new GwtTestJSONException("Error while parsing JSON string '" + json
          + "'", e);
    } finally {
      if (jp != null) {
        try {
          // ensure resources get cleaned up timely and properly
          jp.close();
        } catch (IOException e) {
          // should never happen
        }
      }
    }
  }

  @PatchMethod
  static JavaScriptObject initTypeMap() {
    return null;
  }

  private static JSONArray extractJSONArray(String json,
      JsonToken currentToken, JsonParser jp) throws GwtTestJSONException,
      IOException {
    JSONArray jsonArray = new JSONArray();
    int i = 0;

    while ((currentToken = jp.nextToken()) != JsonToken.END_ARRAY) {
      JSONValue value = extractNextValue(json, currentToken, jp);
      jsonArray.set(i++, value);
    }

    return jsonArray;
  }

  private static JSONObject extractJSONObject(String json, JsonParser jp)
      throws IOException, JsonParseException {
    JSONObject jsonObject = new JSONObject();
    while (jp.nextToken() != JsonToken.END_OBJECT) {
      String fieldName = jp.getCurrentName();
      JSONValue value = extractNextValue(json, jp.nextToken(), jp);
      jsonObject.put(fieldName, value);
    }
    return jsonObject;
  }

  private static JSONValue extractNextValue(String json,
      JsonToken currentToken, JsonParser jp) throws GwtTestJSONException,
      IOException {
    JSONValue fieldValue;
    switch (currentToken) {
      case VALUE_NULL:
        fieldValue = JSONNull.getInstance();
        break;
      case VALUE_STRING:
        fieldValue = new JSONString(jp.getText());
        break;
      case VALUE_NUMBER_INT:
        fieldValue = new JSONNumber(jp.getIntValue());
        break;
      case VALUE_NUMBER_FLOAT:
        fieldValue = new JSONNumber(jp.getDoubleValue());
        break;
      case VALUE_TRUE:
        fieldValue = JSONBoolean.getInstance(true);
        break;
      case VALUE_FALSE:
        fieldValue = JSONBoolean.getInstance(false);
        break;
      case START_ARRAY:
        fieldValue = extractJSONArray(json, currentToken, jp);
        break;
      case START_OBJECT:
        fieldValue = extractJSONObject(json, jp);
        break;
      default:
        throw new GwtTestJSONException("Error while parsing JSON string '"
            + json + "' : gwt-test-utils does not handle token '"
            + jp.getText() + "', line " + jp.getTokenLocation().getLineNr()
            + " column " + jp.getTokenLocation().getColumnNr());
    }

    return fieldValue;
  }

}
