package com.octo.gwt.test.internal.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.octo.gwt.test.internal.patcher.dom.ElementPatcher;

public class PropertyContainer extends HashMap<String, Object> {

	private static final long serialVersionUID = -2421991095282208998L;

	private Set<String> history = new LinkedHashSet<String>();
	private Object owner;

	public PropertyContainer(Object owner) {
		this.owner = owner;
	}

	public Object getOwner() {
		return owner;
	}

	void putNotRecordedObject(String key, Object value) {
		super.put(key, value);
	}

	@Override
	public Object put(String key, Object value) {
		if (value == null || (String.class.isInstance(value) && ((String) value).trim().length() == 0)) {
			history.remove(key);
		} else if (!history.contains(key)) {
			history.add(key);

			if (Style.class.isInstance(owner)) {
				Style style = (Style) owner;
				Element targetElement = StyleHelper.getTargetElement(style);
				PropertyContainer elemPc = PropertyContainerHelper.cast(targetElement).getProperties();
				if (!elemPc.history.contains(ElementPatcher.STYLE_FIELD)) {
					elemPc.history.add(ElementPatcher.STYLE_FIELD);
				}
			}
		}

		return super.put(key, value);

	}

	@Override
	public void clear() {
		super.clear();
		history.clear();
	}

	public Iterator<String> orderedIterator() {
		return history.iterator();
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

}
