package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class GridPatcher extends AutomaticPatcher {

	@PatchMethod
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
