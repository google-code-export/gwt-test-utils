package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;

public class PatchNodeList {
	
	public static int getLengthUserNodeList(NodeList<?> l) {
		if (l instanceof OverrideNodeList<?>) {
			OverrideNodeList<?> ll = (OverrideNodeList<?>) l;
			return ll.getOverrideList().size();
		}
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public static <T extends Node> T getItemUserNodeList(NodeList<T> l, int index) {
		if (l instanceof OverrideNodeList<?>) {
			OverrideNodeList<T> ll = (OverrideNodeList<T>) l;
			return ll.getOverrideList().get(index);
		}
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
