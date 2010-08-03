package com.octo.gwt.test.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

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
import com.octo.gwt.test.utils.events.Browser;

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

	public static void assertWidgetIsClickable(Widget widget, String errorPrefix, String widgetName) {
		String action = "click";
		assertWidgetIsVisible(widget, errorPrefix, widgetName, action);

		if (widget instanceof FocusWidget) {
			if (!((FocusWidget) widget).isEnabled()) {
				Assert.fail(createFailureMessage(errorPrefix, widgetName, action, "enabled"));
			}
		}
	}

	public static void assertWidgetIsVisible(Widget widget, String errorPrefix, String widgetName, String action) {
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

	/**
	 * @param target
	 * @deprecated use {@link Browser#blur(Widget)} instead
	 */
	public static void blur(Widget target) {
		Browser.blur(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#change(Widget)} instead
	 */
	public static void change(Widget target) {
		Browser.change(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#click(Widget)} instead
	 */
	public static void click(Widget target) {
		Browser.click(target);
	}

	/**
	 * 
	 * @param target
	 * @param errorPrefix
	 * @param widgetName
	 * @deprecated use {@link Browser#click(Widget, String, String)} instead
	 */
	public static void click(Widget target, String errorPrefix, String widgetName) {
		Browser.click(target, errorPrefix, widgetName);
	}

	/**
	 * 
	 * @param parent
	 * @param clickedItem
	 * @deprecated use {@link Browser#click(MenuBar, MenuItem)} instead
	 */
	public static void click(MenuBar parent, MenuItem clickedItem) {
		Browser.click(parent, clickedItem);
	}

	/**
	 * 
	 * @param parent
	 * @param clickedItemIndex
	 * @deprecated use {@link Browser#click(MenuBar, int)} instead
	 */
	public static void click(MenuBar parent, int clickedItemIndex) {
		Browser.click(parent, clickedItemIndex);
	}

	/**
	 * 
	 * @param grid
	 * @param row
	 * @param column
	 * @deprecated use {@link Browser#click(Grid, int, int)} instead
	 */
	public static void click(Grid grid, int row, int column) {
		Browser.click(grid, row, column);
	}

	/**
	 * 
	 * @param panel
	 * @param index
	 * @deprecated use {@link Browser#click(ComplexPanel, int)} instead
	 */
	public static void click(ComplexPanel panel, int index) {
		Browser.click(panel, index);
	}

	/**
	 * 
	 * @param target
	 * @deprecated use {@link Browser#focus(Widget)} instead
	 */
	public static void focus(Widget target) {
		Browser.focus(target);
	}

	/**
	 * 
	 * @param target
	 * @param keyCode
	 * @deprecated use {@link Browser#keyDown(Widget, int)} instead
	 */
	public static void keyDown(Widget target, int keyCode) {
		Browser.keyDown(target, keyCode);
	}

	/**
	 * 
	 * @param target
	 * @param keyCode
	 * @deprecated use {@link Browser#keyPress(Widget, int)} instead
	 */
	public static void keyPress(Widget target, int keyCode) {
		Browser.keyPress(target, keyCode);
	}

	/**
	 * 
	 * @param target
	 * @param keyCode
	 * @deprecated use {@link Browser#keyUp(Widget, int)} instead
	 */
	public static void keyUp(Widget target, int keyCode) {
		Browser.keyUp(target, keyCode);
	}

	/**
	 * 
	 * @param target
	 * @deprecated use {@link Browser#mouseMove(Widget)} instead
	 */
	public static void mouseMove(Widget target) {
		Browser.mouseMove(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#mouseDown(Widget)} instead
	 */
	public static void mouseDown(Widget target) {
		Browser.mouseDown(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#mouseUp(Widget)} instead
	 */
	public static void mouseUp(Widget target) {
		Browser.mouseUp(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#mouseWheel(Widget)} instead
	 */
	public static void mouseWheel(Widget target) {
		Browser.mouseWheel(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#mouseOver(Widget)} instead
	 */
	public static void mouseOver(Widget target) {
		Browser.mouseOver(target);
	}

	/**
	 * @param target
	 * @deprecated use {@link Browser#mouseOut(Widget)} instead
	 */
	@Deprecated
	public static void mouseOut(Widget target) {
		Browser.mouseOut(target);
	}

}
