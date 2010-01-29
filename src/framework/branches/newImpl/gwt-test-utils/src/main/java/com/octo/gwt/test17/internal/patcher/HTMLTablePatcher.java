package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test17.internal.overrides.OverrideNodeList;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class HTMLTablePatcher extends AutomaticPatcher {

	@PatchMethod(args={Element.class})
	public static int getDOMRowCount(HTMLTable table, Element element) {
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) element.getChildNodes();
		return nodeList.getLength();
	}

	@PatchMethod(args={Element.class, Integer.class})
	public static int getDOMCellCount(HTMLTable table, Element element, int row) {
		return element.getChildNodes().getItem(row).getChildNodes().getLength();
	}

}
