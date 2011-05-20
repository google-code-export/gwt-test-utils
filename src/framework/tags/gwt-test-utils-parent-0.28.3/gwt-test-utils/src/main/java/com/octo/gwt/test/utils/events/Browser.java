package com.octo.gwt.test.utils.events;

import org.junit.Assert;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.WidgetUtils;

/**
 * Provides several methods to simulate the occurring of browser events
 * (onClick, onKeyDown, onChange, etc.) caused by the interaction with a widget.
 */
public class Browser {

	/**
	 * Simulates a onblur event.
	 */
	public static void blur(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONBLUR).build());
	}

	/**
	 * Simulates a onchange event.
	 */
	public static void change(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONCHANGE).build());
	}

	/**
	 * Simulates a click event on the widget with the given index inside a
	 * ComplexPanel.
	 */
	public static void click(ComplexPanel panel, int index) {
		Widget target = panel.getWidget(index);
		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();
		assertCanApplyEvent(target, clickEvent);
		dispatchEventInternal(panel, clickEvent);
	}

	/**
	 * Simulates a click event on the Grid cell with the given indexes.
	 */
	public static void click(Grid grid, int row, int column) {
		Widget target = grid.getWidget(row, column);
		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(target.getElement()).build();
		assertCanApplyEvent(target, clickEvent);
		dispatchEventInternal(grid, clickEvent);
	}

	/**
	 * Simulates a click event on the item of a MenuBar with the given index.
	 */
	public static void click(MenuBar parent, int clickedItemIndex) {
		click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
	}

	/**
	 * Simulates a onchange event on a particular MenuItem of a MenuBar.
	 */
	public static void click(MenuBar parent, MenuItem clickedItem) {
		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(clickedItem.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(clickedItem.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(clickedItem.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(clickedItem.getElement()).build();

		assertCanApplyEvent(clickedItem, onMouseOver, onMouseDown, onMouseUp, onClick);
		dispatchEventInternal(parent, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	/**
	 * Simulates a click event on the item of a SuggestBox with the given index.
	 */
	public static void click(SuggestBox parent, int clickedItemIndex) {
		click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
	}

	/**
	 * Simulates a click event on a particular MenuItem of a SuggestBox.
	 */
	public static void click(SuggestBox parent, MenuItem clickedItem) {
		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(clickedItem.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(clickedItem.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(clickedItem.getElement()).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(clickedItem.getElement()).build();

		assertCanApplyEvent(clickedItem, onMouseOver, onMouseDown, onMouseUp, onClick);
		clickedItem.getCommand().execute();
	}

	/**
	 * Simulates a click event.
	 */
	public static void click(Widget target) {
		Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(target.getElement()).build();
		Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setButton(Event.BUTTON_LEFT).build();
		Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setButton(Event.BUTTON_LEFT).build();
		Event onClick = EventBuilder.create(Event.ONCLICK).build();

		dispatchEvent(target, onMouseOver, onMouseDown, onMouseUp, onClick);
	}

	/**
	 * Simulates a dblclick event.
	 */
	public static void dblClick(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONDBLCLICK).build());
	}

	/**
	 * Simulates an occurring of the given event due to an interaction with the
	 * target widget.
	 */
	public static void dispatchEvent(Widget target, Event... events) {
		assertCanApplyEvent(target, events);
		dispatchEventInternal(target, events);
	}

	/**
	 * <p>
	 * Remove the text within a widget which implements HasText interface by
	 * simulating a long backspace key press.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>For each character in the text value of the widget, a
	 * {@link KeyDownEvent} is triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE} . It can be prevented with normal effect.</li>
	 * <li><strong>Only one</strong> {@link KeyUpEvent} is triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE}.</li>
	 * <li>Than, a {@link BlurEvent} is triggered.</li>
	 * <li>Finally, if at least one on the KeyDown events has not been
	 * prevented, a {@link ChangeEvent} is triggered.</li>
	 * </p>
	 * <p>
	 * Note that no {@link KeyPressEvent} would be triggered.
	 * </p>
	 * 
	 * @param hasTextWidget
	 *            The widget to fill. If this implementation actually isn't a
	 *            {@link Widget} instance, nothing would be done.
	 */
	public static void emptyText(HasText hasTextWidget) {
		boolean changed = false;

		int baseLength = hasTextWidget.getText().length();
		for (int i = 0; i < baseLength; i++) {
			Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(KeyCodes.KEY_BACKSPACE).build();
			dispatchEvent((Widget) hasTextWidget, keyDownEvent);

			boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);

			if (!keyDownEventPreventDefault) {
				hasTextWidget.setText(hasTextWidget.getText().substring(0, hasTextWidget.getText().length() - 1));
				changed = true;
			}

		}

		// don't have to check if the event can be dispatch since it's check
		// before
		Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_BACKSPACE).build();
		dispatchEventInternal((Widget) hasTextWidget, keyUpEvent);

		dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONBLUR).build());

		if (changed) {
			dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONCHANGE).build());
		}

	}

	/**
	 * <p>
	 * Remove the text within a widget which implements HasText interface
	 * </p>
	 * <p>
	 * <ul>
	 * <li>For each character in the text value of the widget, a
	 * {@link KeyDownEvent} is triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE} . It can be prevented with normal effect.</li>
	 * <li>Either one or x {@link KeyUpEvent} are triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE}, according to the chosen empty text
	 * simulation (with x the number of character in the text value).</li>
	 * <li>Than, a {@link BlurEvent} is triggered.</li>
	 * <li>Finally, if at least one on the KeyDown events has not been
	 * prevented, a {@link ChangeEvent} is triggered.</li>
	 * </p>
	 * <p>
	 * Note that no {@link KeyPressEvent} would be triggered.
	 * </p>
	 * 
	 * @param hasTextWidget
	 *            The widget to fill. If this implementation actually isn't a
	 *            {@link Widget} instance, nothing would be done.
	 * @param longBackPress
	 *            True if it should simulate a long backspace press or not.
	 */
	public static void emptyText(HasText hasTextWidget, boolean longBackPress) {
		if (longBackPress) {
			emptyText(hasTextWidget);
		} else {
			removeText(hasTextWidget, hasTextWidget.getText().length());
		}
	}

	/**
	 * <p>
	 * Fill a widget which implements HasText interface.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>For each character in the value to fill, {@link KeyDownEvent},
	 * {@link KeyPressEvent} and {@link KeyUpEvent} are triggered. They can be
	 * prevented with normal effect.</li>
	 * <li>After typing, a {@link BlurEvent} is triggered.</li>
	 * <li>Than, if at least one on the KeyDown or KeyPress events has not been
	 * prevented, a {@link ChangeEvent} is triggered.</li>
	 * </ul>
	 * </p>
	 * 
	 * <p>
	 * <strong>Do not use this method to remove text by calling it with an empty
	 * string. Use {@link Browser#emptyText(HasText, boolean)} instead.</strong>
	 * </p>
	 * 
	 * @param hasTextWidget
	 *            The widget to fill. If this implementation actually isn't a
	 *            {@link Widget} instance, nothing would be done.
	 * @param value
	 *            The value to fill. Cannot be null or empty.
	 * @throws IllegalArgumentException
	 *             if the value to fill is null or empty.
	 */
	public static void fillText(HasText hasTextWidget, String value) throws IllegalArgumentException {
		if (value == null || "".equals(value)) {
			throw new IllegalArgumentException("Cannot fill a null or empty text. If you intent to remove some text, use '"
					+ Browser.class.getSimpleName() + ".emptyText(..)' instead");
		}
		if (!Widget.class.isInstance(hasTextWidget)) {
			return;
		}

		boolean changed = false;

		for (int i = 0; i < value.length(); i++) {

			int keyCode = value.charAt(i);

			// trigger keyDown and keyPress
			Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(keyCode).build();
			Event keyPressEvent = EventBuilder.create(Event.ONKEYPRESS).setKeyCode(keyCode).build();
			dispatchEvent((Widget) hasTextWidget, keyDownEvent, keyPressEvent);

			// check if one on the events has been prevented
			boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);
			boolean keyPressEventPreventDefault = JavaScriptObjects.getBoolean(keyPressEvent, JsoProperties.EVENT_PREVENTDEFAULT);

			if (!keyDownEventPreventDefault && !keyPressEventPreventDefault) {
				hasTextWidget.setText(value.substring(0, i + 1));
				changed = true;
			}

			// trigger keyUp
			Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(keyCode).build();
			dispatchEventInternal((Widget) hasTextWidget, keyUpEvent);
		}

		// don't have to check if the event can be dispatch since it's check before
		dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONBLUR).build());

		if (changed) {
			dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONCHANGE).build());
		}
	}

	/**
	 * Simulates a focus event.
	 */
	public static void focus(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONFOCUS).build());
	}

	/**
	 * Simulates a keydown event.
	 */
	public static void keyDown(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYDOWN).setKeyCode(keyCode).build());
	}

	/**
	 * Simulates a keypress event.
	 */
	public static void keyPress(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYPRESS).setKeyCode(keyCode).build());
	}

	/**
	 * Simulates a keyup event.
	 */
	public static void keyUp(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYUP).setKeyCode(keyCode).build());
	}

	/**
	 * Simulates a mousedown event.
	 */
	public static void mouseDown(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEDOWN).build());
	}

	/**
	 * Simulates a mousemove event.
	 */
	public static void mouseMove(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEMOVE).build());
	}

	/**
	 * Simulates a mouseout event.
	 */
	public static void mouseOut(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOUT).build());
	}

	/**
	 * Simulates a mouseover event.
	 */
	public static void mouseOver(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOVER).build());
	}

	/**
	 * Simulates a mouseup event.
	 */
	public static void mouseUp(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEUP).build());
	}

	/**
	 * Simulates a mousewheel event.
	 */
	public static void mouseWheel(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEWHEEL).build());
	}

	/**
	 * <p>
	 * Remove a fixed number of character from the text within a widget which
	 * implements HasText interface by simulating a backspace key press.
	 * </p>
	 * <p>
	 * <ul>
	 * <li>x {@link KeyDownEvent} are triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE}, with x the number of backspace press
	 * passed as parameter. It can be prevented with normal effect.</li>
	 * <li>Than, x {@link KeyUpEvent} are triggered with value
	 * {@link KeyCodes#KEY_BACKSPACE}, with x the number of backspace press
	 * passed as parameter.</li>
	 * <li>Than, a {@link BlurEvent} is triggered.</li>
	 * <li>Finally, if at least one on the KeyDown events has not been
	 * prevented, a {@link ChangeEvent} is triggered.</li>
	 * </p>
	 * <p>
	 * Note that no {@link KeyPressEvent} would be triggered.
	 * </p>
	 * 
	 * @param hasTextWidget
	 *            The targeted widget. If this implementation actually isn't a
	 *            {@link Widget} instance, nothing would be done.
	 * @param backspacePressNumber
	 *            The number of backspace key press to simulate.
	 */
	public static void removeText(HasText hasTextWidget, int backspacePressNumber) {
		boolean changed = false;

		for (int i = 0; i < backspacePressNumber; i++) {
			Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(KeyCodes.KEY_BACKSPACE).build();
			Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(KeyCodes.KEY_BACKSPACE).build();
			dispatchEvent((Widget) hasTextWidget, keyDownEvent, keyUpEvent);

			boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);

			if (!keyDownEventPreventDefault) {
				hasTextWidget.setText(hasTextWidget.getText().substring(0, hasTextWidget.getText().length() - 1));
				changed = true;
			}

		}

		// don't have to check if the event can be dispatch since it's check
		// before
		dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONBLUR).build());

		if (changed) {
			dispatchEventInternal((Widget) hasTextWidget, EventBuilder.create(Event.ONCHANGE).build());
		}

	}

	private static void assertCanApplyEvent(UIObject target, Event... events) {
		for (Event event : events) {
			if (!WidgetUtils.isWidgetVisible(target)) {
				Assert.fail(createFailureMessage(target, event, "visible"));
			}

			if (target instanceof FocusWidget && !((FocusWidget) target).isEnabled()) {
				Assert.fail(createFailureMessage(target, event, "enabled"));
			}
		}
	}

	private static String createFailureMessage(UIObject target, Event event, String attribut) {
		StringBuilder sb = new StringBuilder();

		String className = target.getClass().isAnonymousClass() ? target.getClass().getName() : target.getClass().getSimpleName();
		sb.append("The targeted widget (").append(className);
		sb.append(") and its possible parents have to be ").append(attribut);
		sb.append(" to apply a browser '").append(event.getType()).append("\' event");

		return sb.toString();
	}

  private static void dispatchEventInternal(Widget target, Event... events) {
    try {
      for (Event event : events) {

        // set the target if it's not
        Element targetElement = JavaScriptObjects.getObject(event,
            JsoProperties.EVENT_TARGET);
        if (targetElement == null) {
          JavaScriptObjects.setProperty(event, JsoProperties.EVENT_TARGET,
              target.getElement());
        }

        // set the related target
        Element relatedTargetElement = JavaScriptObjects.getObject(event,
            JsoProperties.EVENT_RELATEDTARGET);

        if (relatedTargetElement == null) {
          switch (event.getTypeInt()) {
            case Event.ONMOUSEOVER:
            case Event.ONMOUSEOUT:
              Widget parent = target.getParent();
              if (parent != null) {
                relatedTargetElement = parent.getElement();
              } else {
                relatedTargetElement = Document.get().getDocumentElement();
              }
              break;
          }

          JavaScriptObjects.setProperty(event,
              JsoProperties.EVENT_RELATEDTARGET, relatedTargetElement);
        }

        if (CheckBox.class.isInstance(target)
            && event.getTypeInt() == Event.ONCLICK) {
          CheckBox checkBox = (CheckBox) target;
          if (RadioButton.class.isInstance(target)) {
            checkBox.setValue(true);
          } else {
            checkBox.setValue(!checkBox.getValue());
          }
        }
        target.onBrowserEvent(event);
      }
    } catch (UmbrellaException e) {
      if (AssertionError.class.isInstance(e.getCause())) {
        throw (AssertionError) e.getCause();
      } else {
        throw e;
      }
    }
  }

	private Browser() {

	}

}
