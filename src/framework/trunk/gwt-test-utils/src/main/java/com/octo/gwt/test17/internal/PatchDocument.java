package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.octo.gwt.test17.overrides.OverrideBodyElement;
import com.octo.gwt.test17.overrides.OverrideDocument;

public class PatchDocument {

	public static Document get() {
		return new OverrideDocument();
	}
	
	public static BodyElement getBody() {
		return new OverrideBodyElement();
	}

	//	public static ImageElement createImageElement() {
	//		return new OverrideImageElement();
	//	}

}
