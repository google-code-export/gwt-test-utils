package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLTable;
import com.octo.gwt.test17.internal.dom.UserElement;

public class PatchHTMLTable {

	public static int getDOMRowCount(HTMLTable table) {
		UserElement e = UserElement.overrideCast(table.getElement());
		for (UserElement child : e.getOverrideList()) {
			if (child.getOther().getClass().getCanonicalName()
					.endsWith("tbody")) {
				return child.getOverrideList().size();
			}
		}
		throw new UnsupportedOperationException("Unable to find tbody in table");
	}

	public static int getDOMCellCount(Element element, int row) {
		UserElement e = UserElement.overrideCast(element);
		return e.getOverrideList().get(row).getOverrideList().size();
	}
	
	public static com.google.gwt.user.client.Element getCellElement(com.google.gwt.user.client.Element e, int row, int column) {
		UserElement ee = UserElement.overrideCast(e);
		UserElement r = ee.getOverrideList().get(row);
		UserElement c = r.getOverrideList().get(column);
		return c;
	}
	
	public static com.google.gwt.user.client.Element getRowRowFormatter(com.google.gwt.user.client.Element element, int row) {
		UserElement e = UserElement.overrideCast(element);
		return e.getOverrideList().get(row);
	}

}
