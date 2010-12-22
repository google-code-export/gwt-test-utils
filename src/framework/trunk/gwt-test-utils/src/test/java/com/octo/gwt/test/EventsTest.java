package com.octo.gwt.test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
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
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.octo.gwt.test.utils.events.Browser;

public class EventsTest extends AbstractGwtTest {

	private boolean tested;
	private int counter;

	@Override
	public String getCurrentTestedModuleFile() {
		return "test-config.gwt.xml";
	}

	@Test
	public void checkClickEvent() {
		tested = false;
		Button b = new Button();
		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				tested = !tested;
			}

		});

		// simule the event
		Browser.click(b);

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
		Browser.click(panel, 0);

		// Assert
		Assert.assertTrue("onClick event was not triggered", tested);
	}

	Cell clickedCell;

	@Test
	public void checkClickOnGrid() {
		// Setup
		final Grid g = new Grid(2, 2);
		g.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				clickedCell = g.getCellForEvent(event);
			}
		});

		Anchor a = new Anchor();
		g.setWidget(1, 1, a);

		// Test
		Browser.click(g, 1, 1);

		// Assert
		Assert.assertEquals(1, clickedCell.getRowIndex());
		Assert.assertEquals(1, clickedCell.getCellIndex());
	}

	@Test
	public void checkClickOnSuggestBox() {
		// Setup
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		oracle.add("suggestion 1");
		oracle.add("suggestion 2");
		SuggestBox box = new SuggestBox(oracle);

		// Test
		Browser.fillText(box, "sug");
		Browser.click(box, 1);

		// Assert
		Assert.assertEquals("suggestion 2", box.getText());
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

		// simule the event
		Browser.blur(b);

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

		// simule the event
		Browser.focus(b);

		Assert.assertTrue("onFocus event was not triggered", tested);
	}

	@Test
	public void checkKeyDownEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					tested = !tested;
			}
		});

		// Test
		Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
		// Assert
		Assert.assertFalse("onKeyDown event should not be triggered", tested);

		// Test 2
		Browser.keyDown(b, KeyCodes.KEY_ENTER);
		// Assert 2
		Assert.assertTrue("onKeyDown event was not triggered", tested);
	}

	@Test
	public void checkKeyPressEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
					tested = !tested;
			}
		});

		// Test
		Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
		// Assert
		Assert.assertFalse("onKeyPress event should not be triggered", tested);

		// Test 2
		Browser.keyPress(b, KeyCodes.KEY_ENTER);
		// Assert 2
		Assert.assertTrue("onKeyPress event was not triggered", tested);
	}

	@Test
	public void checkKeyUpEvent() {
		tested = false;
		Button b = new Button();
		b.addKeyUpHandler(new KeyUpHandler() {

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					tested = !tested;
			}
		});

		// Test
		Browser.keyDown(b, KeyCodes.KEY_ESCAPE);
		// Assert
		Assert.assertFalse("onKeyUp event should not be triggered", tested);

		// Test 2
		Browser.keyUp(b, KeyCodes.KEY_ENTER);
		// Assert 2
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

		// simule the event
		Browser.mouseDown(b);

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

		// simule the event
		Browser.mouseUp(b);

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

		// simule the event
		Browser.mouseMove(b);

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

		// simule the event
		Browser.mouseWheel(b);

		Assert.assertTrue("onMouseWheel event was not triggered", tested);
	}

	@Test
	public void checkMouseOverEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent event) {
				tested = !tested;
			}

		});

		// simule the event
		Browser.mouseOver(b);

		Assert.assertTrue("onMouseOver event was not triggered", tested);
	}

	@Test
	public void checkMouseOutEvent() {
		tested = false;
		Button b = new Button();
		b.addMouseOutHandler(new MouseOutHandler() {

			public void onMouseOut(MouseOutEvent event) {
				tested = !tested;
			}

		});

		// simule the event
		Browser.mouseOut(b);

		Assert.assertTrue("onMouseOut event was not triggered", tested);
	}

	// FIXME : pass this test correctly
	// @Test
	public void checkAddNativePreviewHandler() {
		counter = 0;

		Event.addNativePreviewHandler(new NativePreviewHandler() {

			public void onPreviewNativeEvent(NativePreviewEvent event) {
				counter++;

			}
		});

		NativeEvent event = Document.get().createBlurEvent();
		DomEvent.fireNativeEvent(event, new Button());

		Assert.assertEquals(1, counter);
	}
}
