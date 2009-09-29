package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.RadioButton;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class RadioButtonTest extends AbstractGWTTest {
	
	@Test
	public void checkRadioButton() {
		  // Make some radio buttons, all in one group.
	    RadioButton rb0 = new RadioButton("myRadioGroup", "foo");
	    RadioButton rb1 = new RadioButton("myRadioGroup", "bar");
	    RadioButton rb2 = new RadioButton("myRadioGroup", "baz");

	    // Check 'baz' by default.
	    rb1.setValue(true);
	    
	    Assert.assertEquals(false, rb0.getValue());
	    Assert.assertEquals(true, rb1.getValue());
	    Assert.assertEquals(false, rb2.getValue());
	}

}
