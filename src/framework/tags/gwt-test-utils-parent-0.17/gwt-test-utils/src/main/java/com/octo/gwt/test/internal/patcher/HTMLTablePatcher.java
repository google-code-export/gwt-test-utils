package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test.ElementWrapper;
import com.octo.gwt.test.internal.overrides.OverrideNodeList;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(HTMLTable.class)
public class HTMLTablePatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getEventTargetCell(HTMLTable table, Event event) {
		Object bodyElem = GwtTestReflectionUtils.getPrivateFieldValue(table, "bodyElem");

		if (bodyElem instanceof ElementWrapper) {
			bodyElem = ((ElementWrapper) bodyElem).getWrappedElement();
		}

		Element td = DOM.eventGetTarget(event);
		for (; td != null; td = DOM.getParent(td)) {
			// If it's a TD, it might be the one we're looking for.
			if (DOM.getElementProperty(td, "tagName").equalsIgnoreCase("td")) {
				// Make sure it's directly a part of this table before returning
				// it.
				Element tr = DOM.getParent(td);
				Object body = DOM.getParent(tr);

				if (body instanceof ElementWrapper) {
					body = ((ElementWrapper) body).getWrappedElement();
				}
				if (body == bodyElem) {
					return td;
				}
			}
			// If we run into this table's body, we're out of options.
			if (td == bodyElem) {
				return null;
			}
		}
		return null;
	}

	@PatchMethod
	public static int getDOMRowCount(HTMLTable table, Element element) {
		OverrideNodeList<Node> nodeList = (OverrideNodeList<Node>) element.getChildNodes();
		return nodeList.getLength();
	}

	@PatchMethod
	public static int getDOMCellCount(HTMLTable table, Element element, int row) {
		return element.getChildNodes().getItem(row).getChildNodes().getLength();
	}

}
