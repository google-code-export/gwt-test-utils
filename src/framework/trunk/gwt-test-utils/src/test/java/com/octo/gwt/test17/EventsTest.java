package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.Button;
import com.octo.gwt.test17.test.AbstractGWTTest;

public class EventsTest extends AbstractGWTTest {

	private boolean tested;

	@Test
	public void checkClickEvent() {
		tested = false;
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		click(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkClickEventAndError() {
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				//nothing to do

			}

		});

		//the button is disabled
		b.setVisible(false);

		WidgetException exceptedEx = null;

		try {
			//simule the event
			click(b);
		} catch (WidgetException e) {
			exceptedEx = e;
		}

		//Assert exception has been thrown
		Assert.assertNotNull(exceptedEx);
	}

	@Test
	public void checkBlurEvent() {
		tested = false;
		Button b = new Button();
		b.addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		blur(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkFocusEvent() {
		tested = false;
		Button b = new Button();
		b.addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				tested = !tested;	
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		focus(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkMouseDownEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		mouseDown(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkMouseUpEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		mouseUp(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkMouseMoveEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseMoveHandler(new MouseMoveHandler() {

			public void onMouseMove(MouseMoveEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		mouseMove(b);

		Assert.assertEquals(true, tested);
	}

	@Test
	public void checkMouseWheelEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseWheelHandler(new MouseWheelHandler() {

			public void onMouseWheel(MouseWheelEvent event) {
				tested = !tested;
			}

		});

		Assert.assertEquals(false, tested);

		//simule the event
		mouseWheel(b);

		Assert.assertEquals(true, tested);
	}

}
