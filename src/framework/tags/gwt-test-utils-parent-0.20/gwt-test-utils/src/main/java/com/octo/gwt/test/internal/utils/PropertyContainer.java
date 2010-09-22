package com.octo.gwt.test.internal.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PropertyContainer extends HashMap<String, Object> {

	private static final long serialVersionUID = -2421991095282208998L;

	private PropertyContainerAware owner;
	private List<PropertyContainerObserver> observers = new ArrayList<PropertyContainerObserver>();

	public PropertyContainer(Object o) {
		if (!PropertyContainerAware.class.isInstance(o)) {
			throw new IllegalArgumentException("Error while creating a '" + PropertyContainer.class.getSimpleName()
					+ "' instance : owner object is not an instance of '" + PropertyContainerAware.class.getSimpleName() + "' : "
					+ o.getClass().getName() + "'");
		}

		owner = (PropertyContainerAware) o;
	}

	@Override
	public Object put(String propertyName, Object propertyValue) {
		for (PropertyContainerObserver observer : observers) {
			observer.fireSetProperty(owner, propertyName, propertyValue);
		}
		return super.put(propertyName, propertyValue);
	}

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
	
	public PropertyContainerAware getOwner() {
		return owner;
	}

	public void addObserver(PropertyContainerObserver observer) {
		observers.add(observer);
	}

	public boolean removeObserver(PropertyContainerObserver observer) {
		return observers.remove(observer);
	}

	public static interface PropertyContainerObserver {

		public void fireSetProperty(PropertyContainerAware owner, String propertyName, Object propertyValue);
	}

}
