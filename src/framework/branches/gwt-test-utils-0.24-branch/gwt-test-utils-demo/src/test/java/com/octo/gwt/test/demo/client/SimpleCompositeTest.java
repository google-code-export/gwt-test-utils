package com.octo.gwt.test.demo.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class SimpleCompositeTest extends AbstractGwtTest {

	private SimpleComposite composite;

	@Before
	public void init() throws Exception {
		composite = new SimpleComposite();
	}

	@Test
	public void checkMouseMoveOnPicture() {
		// Setup
		Image img = GwtTestReflectionUtils.getPrivateFieldValue(composite, "img");
		Label label = GwtTestReflectionUtils.getPrivateFieldValue(composite, "label");

		Assert.assertEquals("", label.getText());

		// Test
		Browser.mouseMove(img);

		// Assert
		Assert.assertEquals("mouse moved on picture !", label.getText());
	}

}
