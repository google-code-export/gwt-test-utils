package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.ElementWrapper;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public abstract class AbstractElementPatcher<T extends Element> extends AbstractPatcher {

	private Class<T> clazz;

	public AbstractElementPatcher(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String getNewBody(CtMethod m) {
		if ("as".equals(m.getName())) {
			return getAsCode();
		}

		return null;
	}

	private String getAsCode() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ if ($1 instanceof ");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") { return (");
		sb.append(clazz.getCanonicalName());
		sb.append(") ((");
		sb.append(ElementWrapper.class.getCanonicalName());
		sb.append(") $1).getWrappedElement(); } else { return (");
		sb.append(clazz.getCanonicalName());
		sb.append(") $1; } }");

		return sb.toString();
	}

}
