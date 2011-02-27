package com.octo.gwt.test.internal.patchers;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(RowFormatter.class)
public class HTMLTableRowFormatterPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getRow(RowFormatter rowFormatter, Element elem, int row) {
		return elem.getChildNodes().getItem(row).cast();
	}

}
