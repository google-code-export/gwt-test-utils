package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchFlexTable {

	public static void addCells(Element element, int rows, int nums) {
		UserElement e = UserElement.overrideCast(element);

		Element td = new UserElement(PatchDOMImpl.createElement("td"));
		td.setInnerHTML("&nbsp;");

		Element row = e.getOverrideList().get(rows);

		for (int cellNum = 0; cellNum < nums; cellNum++) {
			Element cell = (Element) td.cloneNode(true);
			row.appendChild(cell);
		}
	}

}
