package com.octo.gwt.test.internal.patcher.tools;

import java.util.HashMap;

public class PropertyContainer extends HashMap<String, Object> {

	private static final long serialVersionUID = -2421991095282208998L;

	public void put(String key, boolean value) {
		put(key, new Boolean(value));
	}

	public void put(String key, int value) {
		put(key, new Integer(value));
	}

	public boolean getBoolean(String key) {
		Boolean b = (Boolean) get(key);
		return b == null ? false : b;
	}

	public short getShort(String key) {
		Short s = (Short) get(key);
		return s == null ? 0 : s;
	}

	public int getInteger(String key) {
		Integer i = (Integer) get(key);
		return i == null ? 0 : i;
	}

	public double getDouble(String key) {
		Double d = (Double) get(key);
		return d == null ? 0 : d;
	}

}
