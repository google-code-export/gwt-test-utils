package com.octo.gwt.test.dom;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.utils.WidgetUtils;

public class StyleTest extends AbstractGwtTest {

	@Test
	public void checkStyles() {
		// Setup
		Button b = new Button();
		b.setStylePrimaryName("toto");
		b.addStyleName("tata");
		b.addStyleName("titi");

		// Tests & Asserts
		Assert.assertEquals("toto", b.getStylePrimaryName());
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "tata"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "titi"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "toto"));
	}

	@Test
	public void checkOpacity() {
		// Setup
		Style style = new Button().getElement().getStyle();

		// Test 1
		style.setOpacity(1.0);

		// Assert 1
		Assert.assertEquals("1.0", style.getOpacity());

		// Test 2
		style.clearOpacity();

		// Assert 2
		Assert.assertEquals("", style.getOpacity());
	}
}
