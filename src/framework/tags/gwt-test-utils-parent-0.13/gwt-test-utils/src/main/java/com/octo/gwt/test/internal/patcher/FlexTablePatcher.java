package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class FlexTablePatcher extends AutomaticPatcher {

	@PatchMethod
	public static void addCells(Element table, int row, int num) {
		TableRowElement trCell = getCellElement(table, row);
		for (int i = 0; i < num; i++) {
			trCell.appendChild(Document.get().createTDElement());
		}
	}

	private static TableRowElement getCellElement(Element tableElement, int row) {
		return (TableRowElement) tableElement.getChildNodes().getItem(row);
	}
}
