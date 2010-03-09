package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideImageElement;

public class PatchImage {

	public static ImageElement getImageElement(Image image) {
		UserElement element = (UserElement) image.getElement();
		if (element.getOther() != null && element.getOther() instanceof ImageElement) {
			return (ImageElement) element.getOther();
		} else {
			return new OverrideImageElement((UserElement) image.getElement());
		}
	}

	public static void setWidth(Image image, int width) {
		ImageElement e = (ImageElement) getImageElement(image);
		e.setWidth(width);
	}

}
