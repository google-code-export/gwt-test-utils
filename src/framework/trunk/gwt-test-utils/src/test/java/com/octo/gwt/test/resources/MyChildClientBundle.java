package com.octo.gwt.test.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;

public interface MyChildClientBundle extends MyClientBundle {

	public static final MyChildClientBundle INSTANCE = GWT.create(MyChildClientBundle.class);

	@Source("override_testImageResource.gif")
	public ImageResource testImageResource();

}
