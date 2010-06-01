package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

public class HTMLTableRowFormatterPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getRow(HTMLTable.RowFormatter rowFormatter, Element elem, int row) {
		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(row));
	}

}
