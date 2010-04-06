package com.octo.gwt.test.internal.overrides;

import com.google.gwt.dom.client.AnchorElement;
import com.octo.gwt.test.internal.dom.UserElement;

public class OverrideAnchorElement extends AnchorElement implements UserElementWrapper {

	private UserElement wrappedElement;

	public OverrideAnchorElement(UserElement element) {
		wrappedElement = element;
	}

	public String getOverrideHref() {
		return this.wrappedElement.getOverrideAttribute("href");
	}

	public void setOverrideHref(String overrideHref) {
		this.wrappedElement.setOverrideAttribute("href", overrideHref);
	}

	public String getOverrideName() {
		return this.wrappedElement.getOverrideAttribute("name");
	}

	public void setOverrideName(String overrideName) {
		this.wrappedElement.setOverrideAttribute("name", overrideName);
	}

	public char getOverrideAccessKey() {
		return wrappedElement.getOverrideAttribute("accessKey").charAt(0);
	}

	public void setOverrideAccessKey(String overrideAccessKey) {
		this.wrappedElement.setOverrideAttribute("accessKey", overrideAccessKey);
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public String getOverrideTarget() {
		return this.wrappedElement.getOverrideAttribute("target");
	}

	public void setOverrideTarget(String overrideTarget) {
		this.wrappedElement.setOverrideAttribute("target", overrideTarget);
	}

	public static OverrideAnchorElement overrideCast(Object o) {
		if (o instanceof OverrideAnchorElement) {
			OverrideAnchorElement e = (OverrideAnchorElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

}
