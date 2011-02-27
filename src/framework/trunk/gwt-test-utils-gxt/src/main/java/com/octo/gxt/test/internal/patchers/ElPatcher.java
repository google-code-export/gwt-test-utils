package com.octo.gxt.test.internal.patchers;

import java.util.LinkedHashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.core.El;
import com.google.gwt.dom.client.Element;
import com.octo.gwt.test.internal.utils.StyleHelper;
import com.octo.gwt.test.patchers.AutomaticPatcher;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;

@PatchClass(El.class)
public class ElPatcher extends AutomaticPatcher {

	@PatchMethod
	public static El removeStyleName(El el, String styleName) {
		Element elem = getWrappedElement(el);
		elem.removeClassName(styleName);

		return el;
	}

	@PatchMethod
	public static El applyStyles(El el, String styles) {
		Element elem = getWrappedElement(el);
		LinkedHashMap<String, String> styleProperties = StyleHelper.getStyleProperties(elem.getAttribute("style"));

		for (Map.Entry<String, String> entry : styleProperties.entrySet()) {
			elem.getStyle().setProperty(entry.getKey(), entry.getValue());
		}

		return el;
	}

	private static Element getWrappedElement(El el) {
		return GwtTestReflectionUtils.getPrivateFieldValue(el, "dom");
	}

}
