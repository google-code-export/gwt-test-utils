package com.octo.gwt.test17.internal.patcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.CtMethod;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.octo.gwt.test17.ElementUtils;

public class ImagePatcher extends AbstractPatcher {

	private static final Pattern PATTERN = Pattern.compile("^(\\d*).*$");

	@Override
	public String getNewBody(CtMethod m) {

		if (match(m, "getImageElement")) {
			return callMethod("getImageElement", "this");
		} else if (match(m, "getWidth")) {
			return callMethod("getDim", "this, \"width\"");
		} else if (match(m, "getHeight")) {
			return callMethod("getDim", "this, \"height\"");
		}

		return null;
	}

	public static ImageElement getImageElement(Image image) {
		return ElementUtils.castToDomElement(image.getElement());
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
