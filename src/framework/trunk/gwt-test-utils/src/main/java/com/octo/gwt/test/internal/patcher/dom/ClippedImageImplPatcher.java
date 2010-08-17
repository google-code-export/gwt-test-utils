package com.octo.gwt.test.internal.patcher.dom;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.impl.ClippedImageImpl;
import com.octo.gwt.test.patcher.AutomaticPatcher;
import com.octo.gwt.test.patcher.PatchClass;
import com.octo.gwt.test.patcher.PatchMethod;

@PatchClass(ClippedImageImpl.class)
public class ClippedImageImplPatcher extends AutomaticPatcher {

	//TODO : remove this patch when setInnerHTML method will be correctly patched
	@PatchMethod
	public static Element createStructure(ClippedImageImpl impl, String url, int left, int top, int width, int height) {
		ImageElement element = Document.get().createImageElement();
		element.setSrc(url);
		element.setHeight(height);
		element.setWidth(width);

		return element;
	}

}
