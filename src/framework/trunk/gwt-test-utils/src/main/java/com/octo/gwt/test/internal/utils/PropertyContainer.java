package com.octo.gwt.test.internal.utils;

import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * An object which is added to all {@link JavaScriptObject} instance to store
 * its native properties. <strong>For internal use only.</strong>
 * 
 * @author Gael Lazzari
 * @author Bertrand Paquet
 * 
 */
public class PropertyContainer {

  private static final long serialVersionUID = -2421991095282208998L;

  public static PropertyContainer newInstance(JavaScriptObject jso,
      Map<String, Object> map) {
    return new PropertyContainer(jso, map);
  }

  private final Map<String, Object> map;

  private final JavaScriptObject owner;

  private PropertyContainer(JavaScriptObject jso, Map<String, Object> map) {
    this.owner = jso;
    this.map = map;
  }

  public void clear() {
    map.clear();
  }

  public Set<Map.Entry<String, Object>> entrySet() {
    return map.entrySet();
  }

  public boolean getBoolean(String key) {
    Boolean b = (Boolean) map.get(key);
    return b == null ? false : b;
  }

  public double getDouble(String key) {
    Double d = (Double) map.get(key);
    return d == null ? 0 : d;
  }

  public int getInteger(String key) {
    Integer i = (Integer) map.get(key);
    return i == null ? 0 : i;
  }

  @SuppressWarnings("unchecked")
  public <T> T getObject(String key) {
    return (T) map.get(key);
  }

  public JavaScriptObject getOwner() {
    return owner;
  }

  public short getShort(String key) {
    Short s = (Short) map.get(key);
    return s == null ? 0 : s;
  }

  public String getString(String key) {
    String s = (String) map.get(key);
    return s == null ? "" : s;
  }

  public Object put(String key, boolean value) {
    return map.put(key, Boolean.valueOf(value));
  }

  public Object put(String key, double value) {
    return map.put(key, Double.valueOf(value));
  }

  public Object put(String key, int value) {
    return map.put(key, Integer.valueOf(value));
  }

  public Object put(String key, long value) {
    return map.put(key, Long.valueOf(value));
  }

  public Object put(String key, Object value) {
    return map.put(key, value);
  }

  public Object put(String key, short value) {
    return map.put(key, Short.valueOf(value));
  }

  public Object remove(String key) {
    return map.remove(key);
  }

  public int size() {
    return map.size();
  }

}
