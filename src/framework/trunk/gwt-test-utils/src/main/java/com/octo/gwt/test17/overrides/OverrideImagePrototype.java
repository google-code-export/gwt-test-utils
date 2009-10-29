package com.octo.gwt.test17.overrides;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;

public class OverrideImagePrototype extends AbstractImagePrototype {

	public void applyTo(Image arg0) {
	}

	public Image createImage() {
		Image image = new Image();
		return image;
	}

	public String getHTML() {
		return "<img/>";
	}

}
