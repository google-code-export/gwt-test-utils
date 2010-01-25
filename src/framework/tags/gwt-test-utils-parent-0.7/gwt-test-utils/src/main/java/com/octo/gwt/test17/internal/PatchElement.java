package com.octo.gwt.test17.internal;

import java.lang.reflect.Field;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.UserElementWrapper;

public class PatchElement {

	public static String getTagName(Object o) {
		if (o instanceof UserElement) {
			Element e = ((UserElement) o).getOther();
			if (e != null)
				return e.getTagName();
			else
				return null;
		} else if (o instanceof UserElementWrapper) {
			return ((UserElementWrapper) o).getWrappedElement().getTagName();
		} else {
			try {
				Field f = o.getClass().getField("overrideTagName");
				return (String) f.get(null);
			} catch (Exception e) {
				throw new RuntimeException("No overrideTagName found in class " + o.getClass().getCanonicalName(), e);
			}
		}
	}

	public static UserElement getFirstChildElement(Object o) {
		UserElement e = UserElement.overrideCast(o);

		//TODO : FIX properly ?
		//return e.getOverrideList().size() == 0 ? null : e.getOverrideList().get(0);
		return e.getOverrideList().size() == 0 ? new UserElement(null) : e.getOverrideList().get(0);

	}

}
