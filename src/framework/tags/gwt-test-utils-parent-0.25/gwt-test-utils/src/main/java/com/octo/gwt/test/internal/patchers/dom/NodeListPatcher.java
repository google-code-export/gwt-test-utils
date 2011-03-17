package com.octo.gwt.test.internal.patchers.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(NodeList.class)
public class NodeListPatcher extends AutomaticPatcher {

	@PatchMethod
	public static <T extends Node> T getItem(NodeList<T> nodeList, int index) {
		if (nodeList.getLength() <= index) {
			return null;
		} else {
			return ((OverrideNodeList<T>) nodeList).getList().get(index);
		}
	}

	@PatchMethod
	public static <T extends Node> int getLength(NodeList<T> nodeList) {
		return ((OverrideNodeList<T>) nodeList).getList().size();
	}

}
