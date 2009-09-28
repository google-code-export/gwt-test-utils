package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.user.client.ui.CheckBox;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class CheckBoxTest extends AbstractGWTTest {

	//@Test
	public void checkCheckBox() {
		// Make a new check box, and select it by default.
		CheckBox cb = new CheckBox("Foo");
		cb.setValue(false);

//		// Hook up a handler to find out when it's clicked.
//		cb.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				boolean checked = ((CheckBox) event.getSource()).getValue();
//			}
//		});
		
		
		Assert.assertEquals(false, cb.getValue());
		
		click(cb);
		Assert.assertEquals(true, cb.getValue());
		
		


	}

}
