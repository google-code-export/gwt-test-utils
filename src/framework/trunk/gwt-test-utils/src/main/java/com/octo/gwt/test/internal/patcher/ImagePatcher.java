package com.octo.gwt.test.internal.patcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test.ElementUtils;
import com.octo.gwt.test.internal.patcher.tools.AutomaticPatcher;
import com.octo.gwt.test.internal.patcher.tools.PatchClass;
import com.octo.gwt.test.internal.patcher.tools.PatchMethod;

@PatchClass(Image.class)
public class ImagePatcher extends AutomaticPatcher {

	private static final Pattern PATTERN = Pattern.compile("^(\\d*).*$");

	@PatchMethod
	public static ImageElement getImageElement(Image image) {
		return ElementUtils.castToDomElement(image.getElement());
	}

	@PatchMethod
	public static int getWidth(Image image) {
		return getDim(image, "width");
	}

	@PatchMethod
	public static int getHeight(Image image) {
		return getDim(image, "height");
	}

	public static int getDim(Image image, String dim) {
		ImageElement elem = ElementUtils.castToDomElement(image.getElement());
		String width = elem.getStyle().getProperty(dim);
		if (width == null)
			return 0;
		Matcher m = PATTERN.matcher(width);
		if (m.matches()) {
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}

}
