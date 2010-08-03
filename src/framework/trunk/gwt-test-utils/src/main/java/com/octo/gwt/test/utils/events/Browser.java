package com.octo.gwt.test.utils.events;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.WidgetUtils;

public class Browser {

	public static void dispatchEvent(Widget target, Event event) {
		target.onBrowserEvent(event);
	}

	public static void blur(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONBLUR).build());
	}

	public static void change(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONCHANGE).build());
	}

	public static void click(Widget target) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}

		WidgetUtils.assertWidgetIsClickable(target, null, null);
		dispatchEvent(target, EventBuilder.create(Event.ONCLICK).build());
	}

	public static void click(Widget target, String errorPrefix, String widgetName) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}

		WidgetUtils.assertWidgetIsClickable(target, errorPrefix, widgetName);
		dispatchEvent(target, EventBuilder.create(Event.ONCLICK).build());
	}

	public static void click(MenuBar parent, MenuItem clickedItem) {
		WidgetUtils.assertWidgetIsClickable(parent, null, null);
		dispatchEvent(parent, EventBuilder.create(Event.ONCLICK).setTarget(clickedItem.getElement()).build());
	}

	public static void click(MenuBar parent, int clickedItemIndex) {
		WidgetUtils.assertWidgetIsClickable(parent, null, null);
		List<MenuItem> menuItems = WidgetUtils.getMenuItems(parent);
		MenuItem itemToClick = menuItems.get(clickedItemIndex);
		click(parent, itemToClick);
	}

	public static void click(Grid grid, int row, int column) {
		WidgetUtils.assertWidgetIsClickable(grid, null, null);
		Element target = grid.getWidget(row, column).getElement();
		dispatchEvent(grid, EventBuilder.create(Event.ONCLICK).setTarget(target).build());
	}

	public static void click(ComplexPanel panel, int index) {
		WidgetUtils.assertWidgetIsClickable(panel, null, null);
		Element target = panel.getWidget(index).getElement();
		dispatchEvent(panel, EventBuilder.create(Event.ONCLICK).setTarget(target).build());
	}

	public static void dblClick(Widget target) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}

		WidgetUtils.assertWidgetIsClickable(target, null, null);
		dispatchEvent(target, EventBuilder.create(Event.ONDBLCLICK).build());
	}

	public static void focus(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONFOCUS).build());
	}

	public static void keyDown(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYDOWN).setKeyCode(keyCode).build());
	}

	public static void keyPress(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYPRESS).setKeyCode(keyCode).build());
	}

	public static void keyUp(Widget target, int keyCode) {
		dispatchEvent(target, EventBuilder.create(Event.ONKEYUP).setKeyCode(keyCode).build());
	}

	public static void mouseMove(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEMOVE).build());
	}

	public static void mouseDown(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEDOWN).build());
	}

	public static void mouseUp(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEUP).build());
	}

	public static void mouseWheel(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEWHEEL).build());
	}

	public static void mouseOver(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOVER).build());
	}

	public static void mouseOut(Widget target) {
		dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOUT).build());
	}

}
