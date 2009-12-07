package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;

public class NodeListPatcher extends AbstractPatcher {

	@Override
	public boolean patchMethod(CtMethod m) throws Exception {
		if ("getItem".equals(m.getName())) {
			replaceImplementation(m, "getItem", "this, $1");
		} else if ("getLength".equals(m.getName())) {
			replaceImplementation(m, "getLength", "this");
		} else {
			return false;
		}

		return true;
	}

	public static <T extends Node> T getItem(NodeList<T> nodeList, int index) {
		return ((OverrideNodeList<T>) nodeList).getList().get(index);
	}

	public static <T extends Node> int getLength(NodeList<T> nodeList) {
		return ((OverrideNodeList<T>) nodeList).getList().size();
	}

}
