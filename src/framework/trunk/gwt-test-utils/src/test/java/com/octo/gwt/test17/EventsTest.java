package com.octo.gwt.test17;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.StackPanel;
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

		//simule the event
		click(b);

		Assert.assertTrue("onClick event was not triggered", tested);
	}

	@Test
	public void checkClickOnComplexPanel() {

		// Set up
		tested = false;
		ComplexPanel panel = new StackPanel() {

			@Override
			public void onBrowserEvent(com.google.gwt.user.client.Event event) {
				super.onBrowserEvent(event);

				if (DOM.eventGetType(event) == Event.ONCLICK) {
					tested = !tested;
				}
			};
		};

		panel.add(new Anchor());

		// Test
		click(panel, 0);

		// Assert
		Assert.assertTrue("onClick event was not triggered", tested);

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

		//simule the event
		blur(b);

		Assert.assertTrue("onBlur event was not triggered", tested);
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

		//simule the event
		focus(b);

		Assert.assertTrue("onFocus event was not triggered", tested);
	}

	@Test
	public void checkKeyDownEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent arg0) {
				tested = !tested;
			}
		});

		//simule the event
		keyDown(b);

		Assert.assertTrue("onKeyDown event was not triggered", tested);
	}

	@Test
	public void checkKeyPressEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent arg0) {
				tested = !tested;
			}
		});

		//simule the event
		keyPress(b);

		Assert.assertTrue("onKeyPress event was not triggered", tested);
	}

	@Test
	public void checkKeyUpEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyUpHandler(new KeyUpHandler() {

			public void onKeyUp(KeyUpEvent arg0) {
				tested = !tested;
			}
		});

		//simule the event
		keyUp(b);

		Assert.assertTrue("onKeyUp event was not triggered", tested);
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

		//simule the event
		mouseDown(b);

		Assert.assertTrue("onMouseDown event was not triggered", tested);
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

		//simule the event
		mouseUp(b);

		Assert.assertTrue("onMouseUp event was not triggered", tested);
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

		//simule the event
		mouseMove(b);

		Assert.assertTrue("onMouseMove event was not triggered", tested);
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

		//simule the event
		mouseWheel(b);

		Assert.assertTrue("onMouseWheel event was not triggered", tested);
	}

}
