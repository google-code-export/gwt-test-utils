package com.octo.gxt.test.internal.patchers;

import java.util.LinkedHashMap;
import java.util.List;

import com.extjs.gxt.ui.client.core.FastMap;
import com.extjs.gxt.ui.client.core.impl.ComputedStyleImpl;
import com.google.gwt.user.client.Element;
import com.octo.gwt.test.internal.utils.GwtStringUtils;
import com.octo.gwt.test.internal.utils.StyleHelper;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(ComputedStyleImpl.class)
public class ComputedStyleImplPatcher extends AutomaticPatcher {

	@PatchMethod
	public static FastMap<String> getComputedStyle(ComputedStyleImpl impl, Element elem, List<String> orginalNames, List<String> hyphenizedNames,
			List<String> camelizedNames, String pseudo) {

		FastMap<String> result = new FastMap<String>();

		LinkedHashMap<String, String> styleProperties = StyleHelper.getStyleProperties(elem.getAttribute("style"));

		for (String name : orginalNames) {
			String value = styleProperties.get(GwtStringUtils.hyphenize(name));
			if (value == null) {
				value = "";
			}

			result.put(name, value);
		}

		return result;
	}

}
