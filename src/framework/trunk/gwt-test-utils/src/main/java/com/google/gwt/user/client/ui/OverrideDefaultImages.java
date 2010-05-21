package com.google.gwt.user.client.ui;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.DisclosurePanel.DefaultImages;

public class OverrideDefaultImages implements DisclosurePanel.DefaultImages {

	private static OverrideDefaultImages INSTANCE;

	public static OverrideDefaultImages getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new OverrideDefaultImages();
		}
		return INSTANCE;
	}

	public static Class<DefaultImages> getDefaultImagesClass() {
		return DefaultImages.class;
	}

	private OverrideDefaultImages() {

	}

	public ImageResource disclosurePanelClosed() {
		return null;
	}

	public ImageResource disclosurePanelOpen() {
		return null;
	}

}
