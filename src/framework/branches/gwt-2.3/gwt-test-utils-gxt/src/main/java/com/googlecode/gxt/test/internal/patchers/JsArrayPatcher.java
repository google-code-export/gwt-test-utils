package com.googlecode.gxt.test.internal.patchers;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.js.JsArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.gwt.test.patchers.PatchClass;
import com.googlecode.gwt.test.patchers.PatchMethod;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

@PatchClass(JsArray.class)
class JsArrayPatcher {

  private static class JsArrayJSO extends JavaScriptObject {

    private final List<Object> list;

    JsArrayJSO() {
      list = new ArrayList<Object>();
    }

    /**
     * Adds a boolean value to the array.
     * 
     * @param value the value to add
     */
    void add(boolean value) {
      list.add(value);
    }

    /**
     * Adds a byte value to the array.
     * 
     * @param value the value to add
     */
    void add(byte value) {
      list.add(value);
    }

    /**
     * Adds a char value to the array.
     * 
     * @param value the value to add
     */
    void add(char value) {
      list.add(value);
    }

    /**
     * Adds a double value to the array.
     * 
     * @param value the value to add
     */
    void add(double value) {
      list.add(value);
    }

    /**
     * Adds a float value to the array.
     * 
     * @param value the value to add
     */
    void add(float value) {
      list.add(value);
    }

    /**
     * Adds a int value to the array.
     * 
     * @param value the value to add
     */
    void add(int value) {
      list.add(value);
    }

    /**
     * Adds a native javascript object to the array.
     * 
     * @param object the object to add
     */
    void add(JavaScriptObject object) {
      list.add(object);
    }

    /**
     * Adds a short value to the array.
     * 
     * @param value the value to add
     */
    void add(short value) {
      list.add(value);
    }

    /**
     * Adds a string value to the array.
     * 
     * @param value the value to add
     */
    void add(String value) {
      list.add(value);
    }

    void addObject(Object object) {
      list.add(object);
    }

    /**
     * Returns a property value.
     * 
     * @param index the index
     * @return the value
     */
    Object get(int index) {
      if (list.size() <= index) {
        return null;
      }
      return list.get(index);
    }

    boolean getBoolean(int index) {
      if (list.size() <= index) {
        return false;
      }

      Object o = list.get(index);
      if (Boolean.TYPE.isInstance(o)) {
        return (Boolean) o;
      } else {
        return false;
      }
    }

    byte getByte(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Byte.TYPE.isInstance(o)) {
        return (Byte) o;
      } else {
        return 0;
      }
    }

    char getChar(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Character.TYPE.isInstance(o)) {
        return (Character) o;
      } else {
        return 0;
      }
    }

    double getDouble(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Double.TYPE.isInstance(o)) {
        return (Double) o;
      } else {
        return 0;
      }
    }

    float getFloat(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Float.TYPE.isInstance(o)) {
        return (Float) o;
      } else {
        return 0;
      }
    }

    /**
     * Returns a property value.
     * 
     * @param index the index
     * @return the value
     */
    int getInt(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Integer.TYPE.isInstance(o)) {
        return (Integer) o;
      } else {
        return 0;
      }
    }

    short getShort(int index) {
      if (list.size() <= index) {
        return 0;
      }

      Object o = list.get(index);
      if (Short.TYPE.isInstance(o)) {
        return (Short) o;
      } else {
        return 0;
      }
    }

    /**
     * Returns a property value.
     * 
     * @param index the index
     * @return the value
     */
    String getString(int index) {
      if (list.size() <= index) {
        return "";
      }

      Object o = list.get(index);
      if (String.class.isInstance(o)) {
        return (String) o;
      } else {
        return "";
      }
    }

    /**
     * Returns the size of the array.
     * 
     * @return the size
     */
    int size() {
      return list.size();
    }
  }

  @PatchMethod
  static void add(JsArray jsArray, boolean value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, byte value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, char value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, double value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, float value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, int value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, JavaScriptObject value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, short value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void add(JsArray jsArray, String value) {
    getJsArrayJSO(jsArray).add(value);
  }

  @PatchMethod
  static void addObjectInternal(JsArray jsArray, Object value) {
    getJsArrayJSO(jsArray).addObject(value);
  }

  @PatchMethod
  static JavaScriptObject create(JsArray jsArray) {
    return new JsArrayJSO();
  }

  @PatchMethod
  static Object get(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).get(index);
  }

  @PatchMethod
  static boolean getBoolean(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getBoolean(index);
  }

  @PatchMethod
  static byte getByte(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getByte(index);
  }

  @PatchMethod
  static char getChar(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getChar(index);
  }

  @PatchMethod
  static double getDouble(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getDouble(index);
  }

  @PatchMethod
  static float getFloat(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getFloat(index);
  }

  @PatchMethod
  static int getInt(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getInt(index);
  }

  @PatchMethod
  static short getShort(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getShort(index);
  }

  @PatchMethod
  static String getString(JsArray jsArray, int index) {
    return getJsArrayJSO(jsArray).getString(index);
  }

  @PatchMethod
  static int size(JsArray jsArray) {
    return getJsArrayJSO(jsArray).size();
  }

  private static JsArrayJSO getJsArrayJSO(JsArray jsArray) {
    return GwtReflectionUtils.getPrivateFieldValue(jsArray, "jsArray");
  }
}
