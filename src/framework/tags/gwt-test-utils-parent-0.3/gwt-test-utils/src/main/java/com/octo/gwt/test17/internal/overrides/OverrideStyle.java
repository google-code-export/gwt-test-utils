package com.octo.gwt.test17.internal.overrides;

import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Style;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideStyle extends Style {

	private Hashtable<String, String> stringPropList;

	private UserElement parent;

	public OverrideStyle(UserElement parent) {
		this.parent = parent;
		this.stringPropList = new Hashtable<String, String>();
		this.stringPropList.put("visible", "true");
	}

	public UserElement getParent() {
		return parent;
	}

	public void setParent(UserElement parent) {
		this.parent = parent;
	}

	public void setOverrideProperty(String propName, String propValue) {
		stringPropList.put(propName, propValue);
		if (propName.equals("styleName") && propValue.equals("gwt-DialogBox")) {
			stringPropList.put("visible", Boolean.toString(false));
		}
		if (propName.equals("overflow")) {
			stringPropList.put("visible", Boolean.toString(propValue.equals("visible")));
		}
	}
	
	public void setOverridePropertyPx(String propName, int propValue) {
		setOverrideProperty(propName, propValue + "px");
	}

	public String getOverrideProperty(String propName) {
		return stringPropList.get(propName);
	}

	public String dump() {
		StringBuilder b = new StringBuilder();
		for (Entry<String, String> entry : stringPropList.entrySet()) {
			b.append(entry.getKey() + " : " + entry.getValue() + "\n");
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public OverrideStyle clone(UserElement e) {
		OverrideStyle s = new OverrideStyle(e);
		s.stringPropList = (Hashtable<String, String>) this.stringPropList.clone();
		return s;

	}

	public static OverrideStyle overrideCast(Object o) {
		if (o instanceof OverrideStyle) {
			OverrideStyle s = (OverrideStyle) o;
			return s;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
