package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class StyleTest extends AbstractGWTTest {

	@Test
	public void checkStyles() {
		Button b = new Button();
		b.setStylePrimaryName("toto");
		//		b.setStyleName("bouh");
		b.addStyleName("tata");
		b.addStyleName("titi");

		Assert.assertEquals("toto", b.getStylePrimaryName());
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "tata"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "titi"));
		//		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "bouh"));
		Assert.assertEquals(true, WidgetUtils.hasStyle(b, "toto"));
	}

}
