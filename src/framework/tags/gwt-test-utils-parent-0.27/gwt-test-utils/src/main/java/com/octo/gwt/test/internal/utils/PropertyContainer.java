package com.octo.gwt.test.internal.utils;

import java.util.HashMap;

public class PropertyContainer extends HashMap<String, Object> {

  private static final long serialVersionUID = -2421991095282208998L;

  private PropertyContainerAware owner;

  public PropertyContainer(Object o) {
    if (!PropertyContainerAware.class.isInstance(o)) {
      throw new IllegalArgumentException("Error while creating a '"
          + PropertyContainer.class.getSimpleName()
          + "' instance : owner object is not an instance of '"
          + PropertyContainerAware.class.getSimpleName() + "' : "
          + o.getClass().getName() + "'");
    }

    owner = (PropertyContainerAware) o;
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

  public PropertyContainerAware getOwner() {
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
    put(key, new Boolean(value));
  }

  public void put(String key, int value) {
    put(key, new Integer(value));
  }

}
