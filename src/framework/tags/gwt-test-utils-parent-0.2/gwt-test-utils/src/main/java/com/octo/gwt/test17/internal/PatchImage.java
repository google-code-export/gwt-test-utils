package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.ImageElement;
import com.octo.gwt.test17.overrides.OverrideImageElement;

public class PatchImage {
	
	public static ImageElement getImageElement() {
	    return new OverrideImageElement();
	  }

}
