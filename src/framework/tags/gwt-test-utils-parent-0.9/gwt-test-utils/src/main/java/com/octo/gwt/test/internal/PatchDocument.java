package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideBodyElement;
import com.octo.gwt.test.internal.overrides.OverrideDocument;
import com.octo.gwt.test.internal.overrides.OverrideImageElement;

public class PatchDocument {

	public static Document get() {
		return new OverrideDocument();
	}

	public static BodyElement getBody() {
		return new OverrideBodyElement();
	}

	public static ImageElement createImageElement() {
		return new OverrideImageElement(new UserElement(null));
	}

}
