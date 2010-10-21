package com.octo.gwt.test.utils.events;

import org.junit.Assert;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.WidgetUtils;

/**
 * Provides several methods to simulate the occurring of browser events
 * (onClick, onKeyDown, onChange, etc.) caused by the interaction with a widget.
 */
public class Browser {

	/**
	 * Simulates an occurring of the given event due to an interaction with the
	 * target widget.
	 */
	public static void dispatchEvent(Widget target, Event event) {
		assertCanApplyEvent(target, event);
		dispatchEventInternal(target, event);
	}

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
	 * Simulates a click event.
	 */
	public static void click(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONCLICK).build());
	}

	/**
	 * Simulates a onchange event on a particular MenuItem of a MenuBar.
	 */
	public static void click(MenuBar parent, MenuItem clickedItem) {
		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(clickedItem.getElement()).build();
		assertCanApplyEvent(clickedItem, clickEvent);
		dispatchEventInternal(parent, clickEvent);
	}

	/**
	 * Simulates a click event on the item of a MenuBar with the given index.
	 */
	public static void click(MenuBar parent, int clickedItemIndex) {
		click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
	}

	/**
	 * Simulates a click event on a particular MenuItem of a SuggestBox.
	 */
	public static void click(SuggestBox parent, MenuItem clickedItem) {
		Event clickEvent = EventBuilder.create(Event.ONCLICK).setTarget(clickedItem.getElement()).build();
		assertCanApplyEvent(clickedItem, clickEvent);
		dispatchEventInternal(parent, clickEvent);
	}

	/**
	 * Simulates a click event on the item of a SuggestBox with the given index.
	 */
	public static void click(SuggestBox parent, int clickedItemIndex) {
		click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
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
	 * Simulates a dblclick event.
	 */
	public static void dblClick(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONDBLCLICK).build());
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
	 * Simulates a mousemove event.
	 */
	public static void mouseMove(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEMOVE).build());
	}

	/**
	 * Simulates a mousedown event.
	 */
	public static void mouseDown(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEDOWN).build());
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
	 * Simulates a mouseover event.
	 */
	public static void mouseOver(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOVER).build());
	}

	/**
	 * Simulates a mouseout event.
	 */
	public static void mouseOut(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOUT).build());
	}

	private static void dispatchEventInternal(Widget target, Event event) {
		if (CheckBox.class.isInstance(target) && event.getTypeInt() == Event.ONCLICK) {
			CheckBox checkBox = (CheckBox) target;
			if (RadioButton.class.isInstance(target)) {
				checkBox.setValue(true);
			} else {
				checkBox.setValue(!checkBox.getValue());
			}
		}
		target.onBrowserEvent(event);
	}

	private static void assertCanApplyEvent(UIObject target, Event event) {
		if (!WidgetUtils.isWidgetVisible(target)) {
			Assert.fail(createFailureMessage(target, event, "visible"));
		}

		if (target instanceof FocusWidget && !((FocusWidget) target).isEnabled()) {
			Assert.fail(createFailureMessage(target, event, "enabled"));
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

}
