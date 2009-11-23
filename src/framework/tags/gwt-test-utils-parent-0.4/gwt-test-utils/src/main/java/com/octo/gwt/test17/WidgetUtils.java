package com.octo.gwt.test17;

import java.util.List;

import org.junit.Assert;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test17.internal.overrides.OverrideEvent;

/**
 * Class which provide reflection utilities on {@link Widget} classes.
 * 
 * @author GLZ
 * 
 */
public class WidgetUtils {

	public static List<MenuItem> getMenuItems(MenuBar menuBar) {
		return ReflectionUtils.getPrivateFieldValue(menuBar, "items");
	}

	public static boolean hasStyle(UIObject object, String styleName) {
		return object.getStyleName().contains(styleName);
	}

	public static void blur(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONBLUR));
	}

	public static void change(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONCHANGE));
	}

	public static void click(Widget target) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}

		checkIsClickable(target, "click");
		target.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public static void click(MenuBar parent, MenuItem clickedItem) {
		parent.onBrowserEvent(new OverrideEvent(Event.ONCLICK, clickedItem.getElement()));
	}

	public static void click(MenuBar parent, int clickedItemIndex) {
		List<MenuItem> menuItems = ReflectionUtils.getPrivateFieldValue(parent, "items");
		MenuItem itemToClick = menuItems.get(clickedItemIndex);
		click(parent, itemToClick);
	}

	public static void click(Grid grid, int row, int column) {
		grid.onBrowserEvent(new OverrideEvent(Event.ONCLICK, grid.getWidget(row, column).getElement()));
	}

	public static void click(ComplexPanel panel, int index) {
		panel.onBrowserEvent(new OverrideEvent(Event.ONCLICK, panel.getWidget(index).getElement()));
	}

	public static void focus(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONFOCUS));
	}

	public static void keyDown(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYDOWN));
	}

	public static void keyPress(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYPRESS));
	}

	public static void keyUp(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYUP));
	}

	public static void mouseMove(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEMOVE));
	}

	public static void mouseDown(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEDOWN));
	}

	public static void mouseUp(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEUP));
	}

	public static void mouseWheel(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEWHEEL));
	}

	private static void checkIsClickable(Widget widget, String event) {
		checkWidgetVisible(widget, event);

		if (widget instanceof FocusWidget) {
			if (!((FocusWidget) widget).isEnabled()) {
				Assert.fail("Widget has to be enabled to apply the browser event \"" + event + "\"");
			}
		}
	}

	private static void checkWidgetVisible(Widget widget, String event) {
		if (!widget.isVisible()) {
			Assert.fail("Widget has to be enabled to apply the browser event \"" + event + "\"");
		}
		if (widget.getParent() != null) {
			checkWidgetVisible(widget.getParent(), event);
		}
	}

}
