package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;

public class GridPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "addRows")) {
			return callMethod("addRows", "$1, $2, $3");
		}

		return null;
	}

	public static void addRows(Element table, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			table.appendChild(createRow(columns));
		}
	}

	private static TableRowElement createRow(int columns) {
		TableRowElement tr = Document.get().createTRElement();
		for (int i = 0; i < columns; i++) {
			tr.appendChild(Document.get().createTDElement());
		}

		return tr;
	}
}
