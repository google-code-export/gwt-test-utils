package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Node;
import com.octo.gwt.test17.internal.dom.UserElement;

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

}
