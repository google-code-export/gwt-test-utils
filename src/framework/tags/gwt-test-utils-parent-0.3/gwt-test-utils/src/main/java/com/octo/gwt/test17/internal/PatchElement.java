package com.octo.gwt.test17.internal;

import java.lang.reflect.Field;

import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchElement {
	
	public static String getTagName(Object o) {
		try {
			Field f = o.getClass().getField("overrideTagName");
			return (String) f.get(null);
		} catch (Exception e) {
			throw new RuntimeException("No overrideTagName found in class " + o.getClass().getCanonicalName(), e);
		}
	}
	
	public static UserElement getFirstChildElement(Object o) {
		UserElement e = UserElement.overrideCast(o);
		
		//TODO : FIX properly ?
		//return e.getOverrideList().size() == 0 ? null : e.getOverrideList().get(0);
		return e.getOverrideList().size() == 0 ? new UserElement(null) : e.getOverrideList().get(0);
		
	}

}
