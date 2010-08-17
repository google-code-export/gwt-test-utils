package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Grid;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(Grid.class)
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
