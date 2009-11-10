package com.octo.gwt.test17.internal;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test17.internal.dom.UserElement;
import com.octo.gwt.test17.internal.overrides.OverrideImageElement;

public class PatchImage {

	public static ImageElement getImageElement(Image image) {
		UserElement element = (UserElement) image.getElement();
		if (element.getOther() != null && element.getOther() instanceof ImageElement) {
			return (ImageElement) element.getOther();
		} else {
			return new OverrideImageElement((UserElement) image.getElement());
		}
	}

}
