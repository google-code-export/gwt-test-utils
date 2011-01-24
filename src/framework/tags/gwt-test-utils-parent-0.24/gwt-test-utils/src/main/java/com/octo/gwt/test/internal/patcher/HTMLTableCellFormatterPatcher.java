package com.octo.gwt.test.internal.patcher;

import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(CellFormatter.class)
public class HTMLTableCellFormatterPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getCellElement(CellFormatter cellFormatter, Element table, int row, int col) {
		TableRowElement rowElement = (TableRowElement) table.getChildNodes().getItem(row);
		return rowElement.getChildNodes().getItem(col).cast();
	}

}
