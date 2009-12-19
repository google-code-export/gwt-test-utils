package com.octo.gwt.test17.internal.patcher;

import javassist.CtMethod;

import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test17.ElementUtils;

public class HTMLTableCellFormatterPatcher extends AbstractPatcher {

	@Override
	public String getNewBody(CtMethod m) {
		if (match(m, "getCellElement")) {
			return callMethod("getCellElement", "$1, $2, $3");
		}

		return null;
	}

	public static Element getCellElement(Element table, int row, int col) {
		TableRowElement rowElement = (TableRowElement) table.getChildNodes().getItem(row);
		return ElementUtils.castToUserElement((TableCellElement) rowElement.getChildNodes().getItem(col));
	}

}
