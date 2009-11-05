package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class CheckBoxTest extends AbstractGWTTest {

	@Test
	public void checkCheckBoxClick() {
		// Make a new check box
		final CheckBox cb = new CheckBox();
		cb.setValue(false);
		cb.setFocus(true);
		
		Assert.assertEquals(false, cb.getValue());
		
		//we must use the parent click method to simule the click without registering an handler
		click(cb);
		Assert.assertEquals(true, cb.getValue());
	}

}
