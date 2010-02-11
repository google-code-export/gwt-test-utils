package com.octo.gwt.test17.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideStyle;
import com.octo.gwt.test17.internal.overrides.UserElementWrapper;

public class PatchUIObject extends UIObject {

	public static String getPropertyOnElement(Object o, String propName) {
		UserElement e = UserElement.overrideCast(o);
		OverrideStyle s = OverrideStyle.overrideCast(e.getStyle());
		String result = s.getProperty(propName);
		return result != null ? result : "";
	}

	public static String getStyleName(Element e) {
		String s = DOM.getElementProperty(e.<com.google.gwt.user.client.Element> cast(), "className");
		return s == null ? "" : s;
	}

	public static void updatePrimaryAndDependentStyleNames(Element elem, String newPrimaryStyle) {

		String[] classes = getStyleName(elem).split(" ");

		if (classes.length < 1) {
			setStyleName(elem, newPrimaryStyle);
		} else {
			String oldPrimaryStyle = classes[0];
			int oldPrimaryStyleLen = oldPrimaryStyle.length();

			classes[0] = newPrimaryStyle;
			for (int i = 1; i < classes.length; i++) {
				String name = classes[i];
				if (name.length() > oldPrimaryStyleLen && name.charAt(oldPrimaryStyleLen) == '-' && name.indexOf(oldPrimaryStyle) == 0) {
					classes[i] = newPrimaryStyle + name.substring(oldPrimaryStyleLen);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (String name : classes) {
				sb.append(name + " ");
			}

			setStyleName(elem, sb.toString().trim());
		}
	}

	public static boolean getPropertyOnElementBoolean(Object o, String propName) {
		return Boolean.parseBoolean(getPropertyOnElement(o, propName));
	}

	public static void setPropertyOnElement(Object o, String propName, String propValue) {
		UserElement e = UserElement.overrideCast(o);
		OverrideStyle s = OverrideStyle.overrideCast(e.getStyle());
		s.setProperty(propName, propValue);
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
