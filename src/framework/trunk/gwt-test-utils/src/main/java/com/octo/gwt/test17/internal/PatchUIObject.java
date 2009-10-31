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
	
	public static String getStyleName(Element e) {
//		String s = DOM.getElementProperty(e.<com.google.gwt.user.client.Element> cast(), "className");
//		return s == null ? "" : s;
		return getPropertyOnElement(e, "className");
	}
	
	public static void setStyleName(Object o, String styleName, boolean add) {
		if (!add) {
			setPropertyOnElement(o, "className", styleName);
		} else {
			String style = getPropertyOnElement(o, "className");
			setPropertyOnElement(o, "className", (style + " " + styleName).trim());
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
