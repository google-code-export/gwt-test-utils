package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;

public class FlexTablePatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "addCells")) {
			return callMethod("addCells", "$1, $2, $3");
		}

		return null;
	}

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
