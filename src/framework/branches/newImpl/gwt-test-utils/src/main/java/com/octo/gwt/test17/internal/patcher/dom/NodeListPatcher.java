package com.octo.gwt.test17.internal.patcher.dom;

import javassist.CtMethod;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.patcher.AbstractPatcher;

public class NodeListPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getItem")) {
			return callMethod("getItem", "this, $1");
		} else if (match(m, "getLength")) {
			return callMethod("getLength", "this");
		}

		return null;
	}

	public static <T extends Node> T getItem(NodeList<T> nodeList, int index) {
		return ((OverrideNodeList<T>) nodeList).getList().get(index);
	}

	public static <T extends Node> int getLength(NodeList<T> nodeList) {
		return ((OverrideNodeList<T>) nodeList).getList().size();
	}

}
