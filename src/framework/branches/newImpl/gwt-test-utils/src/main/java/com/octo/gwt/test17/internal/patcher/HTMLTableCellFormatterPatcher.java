package com.octo.gwt.test17.internal.patcher;

import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test17.ElementUtils;
import com.octo.gwt.test17.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test17.internal.patcher.tools.PatchMethod;

public class HTMLTableCellFormatterPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getCellElement(HTMLTable.CellFormatter cellFormatter, Element table, int row, int col) {
		TableRowElement rowElement = (TableRowElement) table.getChildNodes().getItem(row);
		return ElementUtils.castToUserElement((TableCellElement) rowElement.getChildNodes().getItem(col));
	}

}
