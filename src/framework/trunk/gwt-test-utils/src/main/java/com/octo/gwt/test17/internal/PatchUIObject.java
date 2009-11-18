package com.octo.gwt.test17.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideStyle;
import com.octo.gwt.test17.internal.overrides.UserElementWrapper;

public class PatchUIObject {

	public static String getPropertyOnElement(Object o, String propName) {
		UserElement e = UserElement.overrideCast(o);
		OverrideStyle s = OverrideStyle.overrideCast(e.getStyle());
		String result = s.getOverrideProperty(propName);
		return result != null ? result : "";
	}

	public static String getStylePrimaryName(Element elem) {
		String fullClassName = getStyleName(elem);

		// The primary style name is always the first token of the full CSS class
		// name. There can be no leading whitespace in the class name, so it's not
		// necessary to trim() it.
		int spaceIdx = fullClassName.indexOf(' ');
		if (spaceIdx >= 0) {
			return fullClassName.substring(0, spaceIdx);
		}
		return fullClassName;
	}

	public static void setStylePrimaryName(Element e, String styleName) {
		String style = getStyleName(e);
		String oldPrimary = getStylePrimaryName(e);

		if (oldPrimary.length() > 0) {
			setPropertyOnElement(e, "className", style.replaceAll(oldPrimary, styleName + " "));
		} else {
			setPropertyOnElement(e, "className", styleName.trim() + " ");
		}
	}

	public static String getStyleName(Element e) {
		return getPropertyOnElement(e, "className");
	}

	public static void setStyleName(Element e, String styleName) {
		String style = getStyleName(e);
		String primaryStyle = getStylePrimaryName(e);

		if (!style.contains(styleName)) {
			if (primaryStyle.length() > 0 && !style.equals(primaryStyle)) {
				setPropertyOnElement(e, "className", primaryStyle + " " + styleName);
			} else {
				setPropertyOnElement(e, "className", styleName);
			}
		}
	}

	public static void addOrRemoveStyle(Element e, String styleName, boolean add) {
		if (!add) {
			String style = getPropertyOnElement(e, "className");
			if (style.contains(styleName)) {
				int startIndex = style.indexOf(styleName);
				startIndex = (startIndex > 0) ? startIndex - 1 : startIndex;

				style = style.substring(0, startIndex) + style.substring(style.indexOf(styleName) + styleName.length());
			}
			setPropertyOnElement(e, "className", style);
		} else {

			String style = getPropertyOnElement(e, "className");
			if (!style.contains(styleName)) {
				setPropertyOnElement(e, "className", (style + " " + styleName).trim());
			}
		}
	}

	public static boolean getPropertyOnElementBoolean(Object o, String propName) {
		return Boolean.parseBoolean(getPropertyOnElement(o, propName));
	}

	public static void setPropertyOnElement(Object o, String propName, String propValue) {
		UserElement e = UserElement.overrideCast(o);
		OverrideStyle s = OverrideStyle.overrideCast(e.getStyle());
		s.setOverrideProperty(propName, propValue);
	}

	public static void setPropertyOnElement(Object o, String propName, boolean propValue) {
		setPropertyOnElement(o, propName, Boolean.toString(propValue));
	}

	public static com.google.gwt.user.client.Element cast(JavaScriptObject object) {
		if (object instanceof UserElement) {
			UserElement e = (UserElement) object;
			return e;
		}

		if (object instanceof UserElementWrapper) {
			UserElementWrapper wrapper = (UserElementWrapper) object;
			if (wrapper.getWrappedElement() != null)
				return wrapper.getWrappedElement();
		}

		if (object instanceof Element) {
			Element e = (Element) object;
			return new UserElement(e);
		}
		throw new RuntimeException("Unable to cast class " + object.getClass().getCanonicalName());
	}

}
