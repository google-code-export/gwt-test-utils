package com.octo.gwt.test.demo.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.octo.gwt.test.AbstractGwtTest;
import com.octo.gwt.test.utils.GwtTestReflectionUtils;
import com.octo.gwt.test.utils.events.Browser;

public class SimpleCompositeTest2 extends AbstractGwtTest {

	private SimpleComposite2 composite2;

	@Before
	public void init() throws Exception {
		composite2 = new SimpleComposite2();
	}

	@Test
	public void checkDisplayClick() {
		// Setup
		TextBox textBox = GwtTestReflectionUtils.getPrivateFieldValue(composite2, "textBox");
		Button button = GwtTestReflectionUtils.getPrivateFieldValue(composite2, "button");
		Label label = GwtTestReflectionUtils.getPrivateFieldValue(composite2, "label");
		ListBox listBox = GwtTestReflectionUtils.getPrivateFieldValue(composite2, "listBox");
		listBox.setSelectedIndex(1);

		// fill the textBox
		textBox.setText("Gael");
		Browser.change(textBox);

		Assert.assertEquals("", label.getText());
		Assert.assertEquals("Good morning", listBox.getItemText(listBox.getSelectedIndex()));

		//Test
		Browser.click(button);

		// Assert
		Assert.assertEquals("Good morning Gael", label.getText());
	}

}
