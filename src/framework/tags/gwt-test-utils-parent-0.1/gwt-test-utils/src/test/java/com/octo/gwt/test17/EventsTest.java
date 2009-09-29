package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class EventsTest extends AbstractGWTTest {

	private boolean click;
	private boolean blur;
	private boolean focus;

	@Test
	public void checkClickEvent() {
		click = false;
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				click = !click;
			}
		});

		Assert.assertEquals(false, click);

		//simule the click
		click(b);

		Assert.assertEquals(true, click);
	}

	@Test
	public void checkBlurEvent() {
		blur = false;
		Button b = new Button();
		b.addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				blur = !blur;
			}
		});

		Assert.assertEquals(false, blur);

		//simule the blur
		blur(b);

		Assert.assertEquals(true, blur);
	}

	@Test
	public void checkFocusEvent() {
		focus = false;
		Button b = new Button();
		b.addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				focus = !focus;	
			}
		});

		Assert.assertEquals(false, focus);

		//simule the focus
		focus(b);

		Assert.assertEquals(true, focus);
	}

}
