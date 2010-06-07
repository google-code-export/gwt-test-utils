package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonTest extends AbstractGwtTest {

	private boolean tested;

	@Test
	public void checkName() {
		RadioButton rb = new RadioButton("myRadioGroup", "foo");
		rb.setName("name");
		Assert.assertEquals("name", rb.getName());
	}

	@Test
	public void checkText() {
		RadioButton rb = new RadioButton("myRadioGroup", "foo");
		Assert.assertEquals("foo", rb.getText());
		rb.setText("text");
		Assert.assertEquals("text", rb.getText());
	}

	@Test
	public void checkTitle() {
		RadioButton rb = new RadioButton("myRadioGroup", "foo");
		rb.setTitle("title");
		Assert.assertEquals("title", rb.getTitle());
	}

	@Test
	public void checkHTML() {
		RadioButton rb = new RadioButton("myRadioGroup", "<h1>foo</h1>", true);
		Assert.assertEquals("<h1>foo</h1>", rb.getHTML());
		rb.setHTML("<h1>test</h1>");
		Assert.assertEquals("<h1>test</h1>", rb.getHTML());
	}

	@Test
	public void checkVisible() {
		RadioButton rb = new RadioButton("myRadioGroup", "foo");
		Assert.assertEquals(true, rb.isVisible());
		rb.setVisible(false);
		Assert.assertEquals(false, rb.isVisible());
	}

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

	@Test
	public void checkRadioButtonName() {
		// Make some radio buttons, all in one group.
		RadioButton rb0 = new RadioButton("myRadioGroup");

		//Assert.assertEquals("myRadioGroup", rb0.getName());

		rb0.setName("myNewRadioGroup");

		Assert.assertEquals("myNewRadioGroup", rb0.getName());
	}

	@Test
	public void checkRadioButtonClick() {
		tested = false;
		RadioButton r = new RadioButton("myRadioGroup", "foo");
		r.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		click(r);

		Assert.assertEquals(true, tested);
	}

}
