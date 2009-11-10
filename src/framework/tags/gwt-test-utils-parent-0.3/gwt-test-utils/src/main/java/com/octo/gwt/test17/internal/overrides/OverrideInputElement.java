package com.octo.gwt.test17.internal.overrides;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideInputElement extends InputElement implements UserElementWrapper {

	private UserElement wrappedElement;
	
	public OverrideInputElement(Document document) {
	}

	public OverrideInputElement(UserElement element) {
		wrappedElement = element;
	}
	
	public String getOverrideValue() {
		return wrappedElement.getAttribute("value");
	}

	public void setOverrideValue(String overrideValue) {
		wrappedElement.setOverrideAttribute("value", overrideValue);
	}
	
	public String getOverrideAccessKey() {
		String temp = wrappedElement.getAttribute("accessKey");
		
		return (temp == null)? "" : temp;
	}

	public void setOverrideAccessKey(String overrideAccessKey) {
		wrappedElement.setOverrideAttribute("accessKey", overrideAccessKey);
	}
	
	public String getOverrideName() {
		return wrappedElement.getAttribute("name");
	}

	public void setOverrideName(String overrideName) {
		wrappedElement.setOverrideAttribute("name", overrideName);
	}

	public int getOverrideTabIndex() {
		return Integer.valueOf(this.wrappedElement.getOverrideAttribute("tabIndex"));
	}

	public void setOverrideTabIndex(int overrideTabIndex) {
		this.wrappedElement.setOverrideAttribute("tabIndex", String.valueOf(overrideTabIndex));
	}

	public boolean isOverrideDefaultChecked() {
		String dc = wrappedElement.getAttribute("defaultChecked");
		return dc == null ? false : Boolean.valueOf(dc);
	}

	public void setOverrideDefaultChecked(boolean overrideDefaultChecked) {
		this.wrappedElement.setOverrideAttribute("defaultChecked", String.valueOf(overrideDefaultChecked));
	}

	public boolean isOverrideChecked() {
		String c = wrappedElement.getAttribute("checked");
		return c == null ? false : Boolean.valueOf(c);
	}

	public void setOverrideChecked(boolean overrideChecked) {
		this.wrappedElement.setOverrideAttribute("checked", String.valueOf(overrideChecked));
	}

	public boolean isOverrideDisabled() {
		String d = wrappedElement.getAttribute("disabled");
		return d == null ? false : Boolean.valueOf(d);
	}

	public void setOverrideDisabled(boolean overrideDisabled) {
		this.wrappedElement.setOverrideAttribute("disabled", String.valueOf(overrideDisabled));
	}

	public int getOverrideMaxLength() {
		String ml = wrappedElement.getAttribute("maxLength");
		return ml == null ? 0 : Integer.valueOf(ml);
	}

	public void setOverrideMaxLength(int overrideMaxLength) {
		this.wrappedElement.setOverrideAttribute("maxLength", String.valueOf(overrideMaxLength));
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public static OverrideInputElement overrideCast(Object o) {
		if (o instanceof OverrideInputElement) {
			OverrideInputElement e = (OverrideInputElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
