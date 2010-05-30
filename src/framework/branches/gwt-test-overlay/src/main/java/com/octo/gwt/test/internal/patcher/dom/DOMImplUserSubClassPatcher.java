package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class DOMImplUserSubClassPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void insertChild(DOMImpl domImpl, Element parent,
			Element child, int index) {
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) parent
				.getChildNodes();
		if (index >= nodeList.getLength()) {
			nodeList.getList().add(child);
		} else {
			nodeList.getList().add(index, child);
		}
	}

	@PatchMethod
	public static Element getChild(DOMImpl domImpl, Element elem, int index) {
		if (index >= elem.getChildNodes().getLength()) {
			return null;
		}

		return elem.getChildNodes().getItem(index).cast();
	}

	@PatchMethod
	public static int getChildIndex(DOMImpl domImpl, Element parent,
			Element child) {
		if (parent == null || child == null) {
			return -1;
		}

		for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
			if (child.equals(parent.getChildNodes().getItem(i))) {
				return i;
			}
		}

		return -1;
	}

	@PatchMethod
	public static void sinkEvents(DOMImpl domImpl, Element elem, int eventBits) {

	}

	@PatchMethod
	public static void initEventSystem(DOMImpl domImpl) {
	}

	@PatchMethod
	public static int getChildCount(Object domImpl, Element elem) {
		return elem.getChildCount();
	}

}
