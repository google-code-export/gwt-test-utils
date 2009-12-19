package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;

public class HTMLTablePatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getDOMRowCount")) {
			return callMethod("getDOMRowCount", "this");
		} else if (matchWithArgs(m, "getDOMCellCount", com.google.gwt.user.client.Element.class, Integer.TYPE)) {
			return callMethod("getDOMCellCount", "$1, $2");
		}

		return null;
	}

	public static int getDOMRowCount(HTMLTable table) {
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) table.getElement().getChildNodes();

		for (Node node : nodeList.getList()) {
			if (node instanceof Element && ((Element) node).getTagName().equals("tbody")) {
				return ((Element) node).getChildNodes().getLength();
			}
		}
		throw new UnsupportedOperationException("Unable to find <tbody> element in table");
	}

	public static int getDOMCellCount(Element element, int row) {
		return element.getChildNodes().getItem(row).getChildNodes().getLength();
	}

}
