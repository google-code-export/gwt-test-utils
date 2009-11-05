package com.octo.gwt.test17.internal.dom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.internal.overrides.OverrideInputElement;
import com.octo.gwt.test17.internal.overrides.OverrideStyle;

public class UserElement extends Element {

	private com.google.gwt.dom.client.Element other;

	private String id;

	private HashMap<String, String> propsList;
	private HashMap<String, String> attrList;

	private List<UserElement> childList;

	private String overrideInnerHtml;

	private String overrideInnerText;

	private OverrideStyle overrideStyle;

	private UserElement parent;

	private int tabIndex;

	public UserElement(com.google.gwt.dom.client.Element other) {
		this.other = other;
		this.childList = new ArrayList<UserElement>();
		this.propsList = new HashMap<String, String>();
		this.attrList = new HashMap<String, String>();
		this.overrideStyle = new OverrideStyle(this);
		this.propsList.put("accessKey", "");
	}

	public void setOverrideAttribute(String name, String value) {
		this.attrList.put(name, value);
	}

	public String getOverrideAttribute(String name) {
		return attrList.get(name);
	}

	public OverrideStyle getOverrideStyle() {
		return overrideStyle;
	}

	public String getOverrideInnerHtml() {
		return overrideInnerHtml;
	}

	public void setOverrideInnerHtml(String overrideInnerHtml) {
		this.overrideInnerHtml = overrideInnerHtml;
	}

	public String getOverrideInnerText() {
		return overrideInnerText;
	}

	public void setOverrideInnerText(String overrideInnerText) {
		this.overrideInnerText = overrideInnerText;
	}

	public com.google.gwt.dom.client.Element getOther() {
		return other;
	}

	public void setOverrideProperty(String key, String value) {
		propsList.put(key, value);
	}

	public String getOverrideProperty(String key) {
		if ("tagName".equals(key)) {
			String tagName = getTagName(other);
			return tagName;
		}
		return propsList.get(key);
	}

	public int getOverridePropertyInt(String key) {
		if (getOverrideProperty(key) == null) {
			return 0;
		} else {
			return Integer.parseInt(getOverrideProperty(key));
		}
	}

	public List<UserElement> getOverrideList() {
		return childList;
	}

	public UserElement getOverrideParent() {
		return parent;
	}

	public void setParent(UserElement parent) {
		this.parent = parent;
	}

	public static UserElement overrideCast(Object o) {
		if (o instanceof UserElement) {
			UserElement e = (UserElement) o;
			return e;
		}

		if (o instanceof OverrideInputElement) {
			return new UserElement((OverrideInputElement) o);
		}

		if (o instanceof com.google.gwt.dom.client.Element) {
			return new UserElement((com.google.gwt.dom.client.Element) o);
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

	public String dump(int index) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < index; i++)
			b.append("  ");
		b.append("<" + other.getClass().getSimpleName() + ">\n");
		for (UserElement e : childList) {
			b.append(e.dump(index + 1));
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public UserElement overrideClone(boolean deep) {
		UserElement newUserElement = new UserElement(null);
		newUserElement.overrideInnerHtml = this.overrideInnerHtml;
		newUserElement.overrideInnerText = this.overrideInnerText;
		newUserElement.overrideStyle = this.overrideStyle.clone(newUserElement);
		newUserElement.propsList = (HashMap<String, String>) this.propsList.clone();
		try {
			newUserElement.other = other.getClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to clone " + other.getClass().getCanonicalName(), e);
		}
		if (deep) {
			for (UserElement c : childList) {
				newUserElement.appendChild(c.overrideClone(deep));
			}
		}
		return newUserElement;
	}

	public String getOverrideId() {
		return id;
	}

	public void setOverrideId(String id) {
		this.id = id;
	}

	public int getOverrideTabIndex() {
		return this.tabIndex;
	}

	public void setOverrideTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isOrHasChild(Object child) {
		UserElement c = overrideCast(child);
		return this == c || childList.contains(c);
	}

	public static String getTagName(Object o) {
		try {
			Field f = o.getClass().getField("overrideTagName");
			return (String) f.get(null);
		} catch (Exception e) {
			throw new RuntimeException("No overrideTagName found in class " + o.getClass().getCanonicalName(), e);
		}
	}

}
