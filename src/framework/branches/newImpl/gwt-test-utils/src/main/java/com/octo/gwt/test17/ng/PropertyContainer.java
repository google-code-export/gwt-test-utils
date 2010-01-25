package com.octo.gwt.test17.ng;

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
	
	public int getInteger(String key) {
		Integer i = (Integer) get(key);
		return i == null ? 0 : i;
	}
	
}
