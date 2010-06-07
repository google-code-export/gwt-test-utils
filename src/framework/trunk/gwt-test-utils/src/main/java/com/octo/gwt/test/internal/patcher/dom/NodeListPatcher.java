package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(NodeList.class)
public class NodeListPatcher extends AutomaticPatcher {

	@PatchMethod
	public static <T extends Node> T getItem(NodeList<T> nodeList, int index) {
		return ((OverrideNodeList<T>) nodeList).getList().get(index);
	}

	@PatchMethod
	public static <T extends Node> int getLength(NodeList<T> nodeList) {
		return ((OverrideNodeList<T>) nodeList).getList().size();
	}

}
