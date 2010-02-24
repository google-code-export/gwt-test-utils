package com.octo.gwt.test.internal.overrides;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.SelectElement;
import com.octo.gwt.test.internal.dom.UserElement;

public class OverrideSelectElement extends SelectElement {

	private List<OverrideOptionElement> list;
	private UserElement wrapped;

	public OverrideSelectElement(UserElement wrapped) {
		this.wrapped = wrapped;
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
		return -1;
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
		return Integer.valueOf(wrapped.getOverrideAttribute("size"));
	}

	public void setOverrideSize(int overrideSize) {
		wrapped.setOverrideAttribute("size", String.valueOf(overrideSize));
	}

	public String getOverrideName() {
		return wrapped.getOverrideAttribute("name");
	}

	public void setOverrideName(String overrideName) {
		wrapped.setOverrideAttribute("name", overrideName);
	}

	public boolean isOverrideMultiple() {
		return Boolean.valueOf(wrapped.getOverrideAttribute("multiple"));
	}

	public void setOverrideMultiple(boolean overrideMultiple) {
		wrapped.setOverrideAttribute("multiple", String.valueOf(overrideMultiple));
	}

}
