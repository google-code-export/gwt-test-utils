package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;
import com.octo.gwt.test.patcher.AutomaticSpecificTagElementPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(TableCellElement.class)
public class TableCellElementPatcher extends AutomaticSpecificTagElementPatcher {

	@PatchMethod
	public static int getCellIndex(TableCellElement element) {
		TableRowElement row = TableRowElement.as(element.getParentElement());

		for (int i = 0; i < row.getCells().getLength(); i++) {
			if (element.equals(row.getChild(i))) {
				return i;
			}
		}
		return -1;
	}

}
