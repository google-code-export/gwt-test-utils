package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.StackPanel;
import com.octo.gwt.test.AbstractGWTTest;
import com.octo.gwt.test.internal.dom.UserElement;
import com.octo.gwt.test.internal.overrides.OverrideWidgetCollection;

public class OverrideWidgetCollectionTest extends AbstractGWTTest {

	@Test
	public void checkInsertion() {

		// Set up
		OverrideWidgetCollection col = new OverrideWidgetCollection(new StackPanel());
		Anchor a0 = new Anchor();
		UserElement e0 = (UserElement) a0.getElement();
		Anchor a1 = new Anchor();
		UserElement e1 = (UserElement) a1.getElement();
		Anchor a2 = new Anchor();
		UserElement e2 = (UserElement) a2.getElement();

		// Test
		col.add(a1);
		col.add(a2);
		col.insert(a0, 0);

		// Assert
		Assert.assertEquals(0, e0.getPropertyInt("__index"));
		Assert.assertEquals(1, e1.getPropertyInt("__index"));
		Assert.assertEquals(2, e2.getPropertyInt("__index"));
	}

}
