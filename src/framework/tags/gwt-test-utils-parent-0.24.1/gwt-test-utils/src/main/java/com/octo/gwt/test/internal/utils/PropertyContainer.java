package com.octo.gwt.test.internal.utils;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;

public class PropertyContainer extends HashMap<String, Object> {

  private static final long serialVersionUID = -2421991095282208998L;

  private final JavaScriptObject owner;

  public PropertyContainer(JavaScriptObject jso) {
    owner = jso;
  }

  public boolean getBoolean(String key) {
    Boolean b = (Boolean) get(key);
    return b == null ? false : b;
  }

  public double getDouble(String key) {
    Double d = (Double) get(key);
    return d == null ? 0 : d;
  }

  public int getInteger(String key) {
    Integer i = (Integer) get(key);
    return i == null ? 0 : i;
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject(String key) {
    return (T) get(key);
  }

  public JavaScriptObject getOwner() {
    return owner;
  }

  public short getShort(String key) {
    Short s = (Short) get(key);
    return s == null ? 0 : s;
  }

  public String getString(String key) {
    String s = (String) get(key);
    return s == null ? "" : s;
  }

  public void put(String key, boolean value) {
    put(key, Boolean.valueOf(value));
  }

  public void put(String key, double value) {
    put(key, Double.valueOf(value));
  }

  public void put(String key, int value) {
    put(key, Integer.valueOf(value));
  }

  public void put(String key, long value) {
    put(key, Long.valueOf(value));
  }

  public Object put(String key, short value) {
    // TODO Auto-generated method stub
    return super.put(key, Short.valueOf(value));
  }

}
