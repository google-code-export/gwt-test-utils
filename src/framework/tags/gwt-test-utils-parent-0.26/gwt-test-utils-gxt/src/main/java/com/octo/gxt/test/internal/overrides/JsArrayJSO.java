package com.octo.gxt.test.internal.overrides;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

public class JsArrayJSO extends JavaScriptObject {

	private List<Object> list;

	public JsArrayJSO() {
		list = new ArrayList<Object>();
	}

	/**
	 * Adds a boolean value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(boolean value) {
		list.add(value);
	}

	/**
	 * Adds a byte value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(byte value) {
		list.add(value);
	}

	/**
	 * Adds a char value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(char value) {
		list.add(value);
	}

	/**
	 * Adds a double value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(double value) {
		list.add(value);
	}

	/**
	 * Adds a float value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(float value) {
		list.add(value);
	}

	/**
	 * Adds a int value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(int value) {
		list.add(value);
	}

	/**
	 * Adds a native javascript object to the array.
	 * 
	 * @param object
	 *            the object to add
	 */
	public void add(JavaScriptObject object) {
		list.add(object);
	}

	/**
	 * Adds a short value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(short value) {
		list.add(value);
	}

	/**
	 * Adds a string value to the array.
	 * 
	 * @param value
	 *            the value to add
	 */
	public void add(String value) {
		list.add(value);
	}

	public void addObject(Object object) {
		list.add(object);
	}

	/**
	 * Returns a property value.
	 * 
	 * @param index
	 *            the index
	 * @return the value
	 */
	public Object get(int index) {
		if (list.size() <= index) {
			return null;
		}
		return list.get(index);
	}

	public boolean getBoolean(int index) {
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

	public byte getByte(int index) {
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

	public char getChar(int index) {
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

	public double getDouble(int index) {
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

	public float getFloat(int index) {
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
	 * @param index
	 *            the index
	 * @return the value
	 */
	public int getInt(int index) {
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

	public short getShort(int index) {
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
	 * @param index
	 *            the index
	 * @return the value
	 */
	public String getString(int index) {
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
	public int size() {
		return list.size();
	}

}
