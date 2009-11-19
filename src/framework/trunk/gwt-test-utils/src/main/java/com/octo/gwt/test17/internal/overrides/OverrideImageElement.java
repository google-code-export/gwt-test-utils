package com.octo.gwt.test17.internal.overrides;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.dom.client.ImageElement;
import com.octo.gwt.test17.internal.dom.UserElement;

public class OverrideImageElement extends ImageElement implements UserElementWrapper {

	private UserElement wrappedElement;

	private static Pattern PATTERN = Pattern.compile("^(\\d*).*$");

	public OverrideImageElement(UserElement element) {
		wrappedElement = element;
	}

	public String getOverrideSrc() {
		return wrappedElement.getOverrideAttribute("src");
	}

	public void setOverrideSrc(String overrideSrc) {
		wrappedElement.setOverrideAttribute("src", overrideSrc);
	}

	public int getOverrideHeight() {
		OverrideStyle s = wrappedElement.getOverrideStyle();
		return getDimension(s.getOverrideProperty("height"));
	}

	public int getOverrideWidth() {
		OverrideStyle s = wrappedElement.getOverrideStyle();
		return getDimension(s.getOverrideProperty("width"));
	}

	public UserElement getWrappedElement() {
		return wrappedElement;
	}

	public static OverrideImageElement overrideCast(Object o) {
		if (o instanceof OverrideImageElement) {
			OverrideImageElement e = (OverrideImageElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

	private static int getDimension(String dimension) {
		Matcher m = PATTERN.matcher(dimension);
		if (m.matches())
			return Integer.valueOf(m.group(1));
		else
			return 0;
	}

}
