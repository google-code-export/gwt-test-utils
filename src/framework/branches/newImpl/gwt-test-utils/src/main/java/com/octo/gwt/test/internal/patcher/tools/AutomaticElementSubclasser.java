package com.octo.gwt.test.internal.patcher.tools;

import javassist.CtClass;

import com.octo.gwt.test.ElementWrapper;

public class AutomaticElementSubclasser extends AutomaticSubclasser {

	@PatchMethod(PatchType.NEW_CODE_AS_STRING)
	public static String as(CtClass clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ if ($1 instanceof ");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") { return (");
		sb.append(clazz.getName());
		sb.append(") ((");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") $1).getWrappedElement(); } else { return (");
		sb.append(clazz.getName());
		sb.append(") $1; } }");

		return sb.toString();
	}
	
}
