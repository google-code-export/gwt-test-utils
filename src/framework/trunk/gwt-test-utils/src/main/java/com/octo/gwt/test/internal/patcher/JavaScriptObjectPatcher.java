package com.octo.gwt.test.internal.patcher;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Node;
import com.octo.gwt.test.internal.overrides.OverrideJsArrayString;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;
import com.octo.gwt.test.internal.utils.ElementUtils;

@PatchClass(JavaScriptObject.class)
public class JavaScriptObjectPatcher extends AutomaticPatcher {

	@PatchMethod
	public static JavaScriptObject createArray() {
		return new OverrideJsArrayString();
	}

	@PatchMethod
	public static boolean equals(JavaScriptObject jso, Object o) {
		if (Node.class.isInstance(jso)) {
			jso = ElementUtils.castToDomElement((Node) jso);
		}

		if (Node.class.isInstance(o)) {
			o = ElementUtils.castToDomElement((Node) o);
		}

		return jso == o;
	}

}
