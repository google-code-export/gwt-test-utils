package com.octo.gwt.test17.internal.patcher.dom;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.impl.DOMImpl;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.ng.AutomaticPatcher;
import com.octo.gwt.test17.ng.PatchMethod;

public class DOMImplUserSubClassPatcher extends AutomaticPatcher {

	@PatchMethod
	public static void insertChild(DOMImpl domImpl, Element userParent, Element userChild, int index) {
		com.google.gwt.dom.client.Element parent = ElementUtils.castToDomElement(userParent);
		com.google.gwt.dom.client.Element child = ElementUtils.castToDomElement(userChild);
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) parent.getChildNodes();
		if (index >= nodeList.getLength()) {
			nodeList.getList().add(child);
		} else {
			nodeList.getList().add(index, child);
		}
	}

	@PatchMethod
	public static Element getChild(DOMImpl domImpl, Element userElem, int index) {
		com.google.gwt.dom.client.Element elem = ElementUtils.castToDomElement(userElem);
		if (index >= elem.getChildNodes().getLength()) {
			return null;
		}

		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(index));
	}

	@PatchMethod
	public static int getChildIndex(DOMImpl domImpl, Element parent, Element child) {
		if (parent == null || child == null) {
			return -1;
		}

		parent = ElementUtils.castToDomElement(parent);
		com.google.gwt.dom.client.Element domElem = ElementUtils.castToDomElement(child);

		for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
			Element current = ElementUtils.castToDomElement(parent.getChildNodes().getItem(i));
			if (domElem.equals(current)) {
				return i;
			}
		}

		return -1;
	}

	@PatchMethod
	public static void sinkEvents(DOMImpl domImpl, Element elem, int eventBits) {
		
	}
	
}
