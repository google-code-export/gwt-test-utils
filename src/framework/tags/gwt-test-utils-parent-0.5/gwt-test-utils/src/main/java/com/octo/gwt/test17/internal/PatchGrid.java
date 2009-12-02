package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchGrid {

	public static void addRows(Element table, int rows, int columns) {

		Element td = new UserElement(PatchDOMImpl.createElement("td"));
		td.setInnerHTML("&nbsp;");
		Element row = new UserElement(PatchDOMImpl.createElement("tr"));
		for (int cellNum = 0; cellNum < columns; cellNum++) {
			Element cell = (Element) td.cloneNode(true);
			row.appendChild(cell);
		}
		table.appendChild(row);
		for (int rowNum = 1; rowNum < rows; rowNum++) {
			table.appendChild(row.cloneNode(true));
		}
	}

}
