package com.octo.gwt.test.internal.patcher;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(RowFormatter.class)
public class HTMLTableRowFormatterPatcher extends AutomaticPatcher {

	@PatchMethod
	public static Element getRow(RowFormatter rowFormatter, Element elem, int row) {
		return ElementUtils.castToUserElement((com.google.gwt.dom.client.Element) elem.getChildNodes().getItem(row));
	}

}
