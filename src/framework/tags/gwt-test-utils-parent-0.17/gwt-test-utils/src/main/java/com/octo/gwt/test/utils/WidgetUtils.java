package com.octo.gwt.test.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.overrides.OverrideEvent;

/**
 * Class which provide reflection utilities on {@link Widget} classes.
 * 
 * @author GLZ
 * 
 */
public class WidgetUtils {

	/**
	 * Check if the current widget and its possible parents are visible. NOTE :
	 * if the current widget is a Popup, it is the isShowing() flag which would
	 * be evaluate.
	 * 
	 * @param widget
	 *            The widget to check.
	 * @return True if the widget and its possible parents are visible, false
	 *         otherwise.
	 */
	public static boolean isWidgetVisible(Widget widget) {
		// FIXME : remove this hack which is required for octo main GWT
		// project...
		if (widget instanceof RootPanel) {
			return true;
		} else if (widget instanceof PopupPanel) {
			PopupPanel popup = (PopupPanel) widget;
			if (popup.isShowing()) {
				return true;
			}
		} else if (!widget.isVisible()) {
			return false;
		} else if (widget.getParent() != null) {
			return isWidgetVisible(widget.getParent());
		}

		return true;
	}

	public static boolean assertListBoxDataMatch(ListBox listBox, String[] content) {
		int contentSize = content.length;
		if (contentSize != listBox.getItemCount()) {
			return false;
		}
		for (int i = 0; i < contentSize; i++) {
			if (!content[i].equals(listBox.getItemText(i))) {
				return false;
			}
		}

		return true;
	}

	public static String getListBoxContentToString(ListBox listBox) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listBox.getItemCount(); i++) {
			sb.append(listBox.getItemText(i)).append(" | ");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static int getIndexInListBox(ListBox listBox, String regex) {
		int selectedIndex = -1;

		Pattern p = Pattern.compile(regex);

		int i = 0;
		String itemText;
		Matcher m;
		while (i < listBox.getItemCount() && selectedIndex == -1) {
			itemText = listBox.getItemText(i);
			m = p.matcher(itemText);
			if (m.matches() || regex.equals(itemText)) {
				selectedIndex = i;
			} else {
				i++;
			}
		}

		return selectedIndex;
	}

	public static List<MenuItem> getMenuItems(MenuBar menuBar) {
		return GwtTestReflectionUtils.getPrivateFieldValue(menuBar, "items");
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

		checkIsClickable(target, null, null);
		target.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public static void click(Widget target, String errorPrefix, String widgetName) {
		if (target instanceof CheckBox) {
			CheckBox checkBox = (CheckBox) target;
			checkBox.setValue(!checkBox.getValue());
		}

		checkIsClickable(target, errorPrefix, widgetName);
		target.onBrowserEvent(new OverrideEvent(Event.ONCLICK));
	}

	public static void click(MenuBar parent, MenuItem clickedItem) {
		checkIsClickable(parent, null, null);
		parent.onBrowserEvent(new OverrideEvent(Event.ONCLICK, clickedItem.getElement()));
	}

	public static void click(MenuBar parent, int clickedItemIndex) {
		checkIsClickable(parent, null, null);
		List<MenuItem> menuItems = getMenuItems(parent);
		MenuItem itemToClick = menuItems.get(clickedItemIndex);
		click(parent, itemToClick);
	}

	public static void click(Grid grid, int row, int column) {
		checkIsClickable(grid, null, null);
		grid.onBrowserEvent(new OverrideEvent(Event.ONCLICK, grid.getWidget(row, column).getElement()));
	}

	public static void click(ComplexPanel panel, int index) {
		checkIsClickable(panel, null, null);
		panel.onBrowserEvent(new OverrideEvent(Event.ONCLICK, panel.getWidget(index).getElement()));
	}

	public static void focus(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONFOCUS));
	}

	public static void keyDown(Widget target, int keyCode) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYDOWN).setOverrideKeyCode(keyCode));
	}

	public static void keyPress(Widget target, int keyCode) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYPRESS).setOverrideKeyCode(keyCode));
	}

	public static void keyUp(Widget target, int keyCode) {
		target.onBrowserEvent(new OverrideEvent(Event.ONKEYUP).setOverrideKeyCode(keyCode));
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

	public static void mouseOver(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEOVER));
	}

	public static void mouseOut(Widget target) {
		target.onBrowserEvent(new OverrideEvent(Event.ONMOUSEOUT));
	}

	private static void checkIsClickable(Widget widget, String errorPrefix, String widgetName) {
		String action = "click";
		checkWidgetVisible(widget, errorPrefix, widgetName, action);

		if (widget instanceof FocusWidget) {
			if (!((FocusWidget) widget).isEnabled()) {
				Assert.fail(createFailureMessage(errorPrefix, widgetName, action, "enabled"));
			}
		}
	}

	private static void checkWidgetVisible(Widget widget, String errorPrefix, String widgetName, String action) {
		String errorMessage = createFailureMessage(errorPrefix, widgetName, action, "visible");
		Assert.assertTrue(errorMessage, isWidgetVisible(widget));
	}

	private static String createFailureMessage(String errorPrefix, String widgetName, String action, String attribut) {
		StringBuilder sb = new StringBuilder();

		if (errorPrefix != null)
			sb.append(errorPrefix);
		if (widgetName != null)
			sb.append("Widget [" + widgetName + "]");
		else
			sb.append("The targeted widget");

		sb.append(" and its possible parents have to be ");
		sb.append(attribut);
		sb.append(" to apply the browser \"");
		sb.append(action);
		sb.append("\" event");

		return sb.toString();

	}

}
