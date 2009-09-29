package com.octo.gwt.test17.overrides;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.SelectElement;

public class OverrideSelectElement extends SelectElement {

	private List<OverrideOptionElement> list;
	
	private int overrideSize;

	public OverrideSelectElement() {
		list = new ArrayList<OverrideOptionElement>();
	}

	public List<OverrideOptionElement> getOverrideOptionList() {
		return list;
	}

	public int getOverrideSelectedIndex() {
		int i = 0;
		for (OverrideOptionElement o : list) {
			if (o.isOverrideSelected()) {
				return i;
			}
			i++;
		}
		return 0;
	}

	public void setOverrideSelectedIndex(int index) {
		if (list.size() == 0 && index == 0) {
			return;
		}
		for (OverrideOptionElement o : list) {
			o.setOverrideSelected(false);
		}
		if (index != -1) {
			list.get(index).setOverrideSelected(true);
		}
	}

	public static OverrideSelectElement overrideCast(Object o) {
		if (o instanceof OverrideSelectElement) {
			OverrideSelectElement e = (OverrideSelectElement) o;
			return e;
		}
		throw new RuntimeException("Unable to cast class " + o.getClass().getCanonicalName());
	}

	public void overrideClear() {
		list.clear();
	}

	public int getOverrideSize() {
		return overrideSize;
	}

	public void setOverrideSize(int overrideSize) {
		this.overrideSize = overrideSize;
	}

}
