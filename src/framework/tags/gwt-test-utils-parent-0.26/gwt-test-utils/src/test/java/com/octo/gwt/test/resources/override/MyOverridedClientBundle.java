package com.octo.gwt.test.resources.override;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.octo.gwt.test.resources.MyClientBundle;

public interface MyOverridedClientBundle extends MyClientBundle {

	public static final MyOverridedClientBundle INSTANCE = GWT.create(MyOverridedClientBundle.class);

	@Source("override_testImageResource.gif")
	public ImageResource testImageResource();

	public TextResource textResourceTxt();

}
