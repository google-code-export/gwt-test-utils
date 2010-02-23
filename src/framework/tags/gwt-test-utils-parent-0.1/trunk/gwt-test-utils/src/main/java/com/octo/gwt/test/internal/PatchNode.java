package com.octo.gwt.test.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Node;
import com.octo.gwt.test.internal.dom.UserElement;

public class PatchNode {

	public static Node appendChild(Object root, Object child) {
		UserElement r = UserElement.overrideCast(root);
		UserElement c = UserElement.overrideCast(child);
		r.getOverrideList().add(c);
		c.setParent(r);
		return r;
	}

	public static Node removeChild(Node parent, Node child) {
		UserElement p = UserElement.overrideCast(parent);
		UserElement c = UserElement.overrideCast(child);
		p.getOverrideList().remove(c);
		return p;
	}

	public static Node getFirstChild(Node parent) {
		UserElement p = UserElement.overrideCast(parent);
		if (p.getOverrideList().size() > 0)
			return p.getOverrideList().get(0);
		else
			return null;
	}

	public static boolean is(JavaScriptObject jsObject) {
		return jsObject == null;
	}

}
