package com.octo.gwt.test;

import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.WidgetUtils;

@RunWith(GwtTestRunner.class)
public abstract class AbstractGwtTest extends AbstractGwtConfigurableTest {

	public static void blur(Widget target) {
		WidgetUtils.blur(target);
	}

	public static void change(Widget target) {
		WidgetUtils.change(target);
	}

	public static void click(Widget target) {
		WidgetUtils.click(target);
	}

	public static void click(MenuBar parent, MenuItem clickedItem) {
		WidgetUtils.click(parent, clickedItem);
	}

	public static void click(MenuBar parent, int clickedItemIndex) {
		WidgetUtils.click(parent, clickedItemIndex);
	}

	public static void click(Grid grid, int row, int column) {
		WidgetUtils.click(grid, row, column);
	}

	public static void click(ComplexPanel panel, int index) {
		WidgetUtils.click(panel, index);
	}

	public static void focus(Widget target) {
		WidgetUtils.focus(target);
	}

	public static void keyDown(Widget target, int keyCode) {
		WidgetUtils.keyDown(target, keyCode);
	}

	public static void keyPress(Widget target, int keyCode) {
		WidgetUtils.keyPress(target, keyCode);
	}

	public static void keyUp(Widget target, int keyCode) {
		WidgetUtils.keyUp(target, keyCode);
	}

	public static void mouseMove(Widget target) {
		WidgetUtils.mouseMove(target);
	}

	public static void mouseDown(Widget target) {
		WidgetUtils.mouseDown(target);
	}

	public static void mouseUp(Widget target) {
		WidgetUtils.mouseUp(target);
	}

	public static void mouseWheel(Widget target) {
		WidgetUtils.mouseWheel(target);
	}

	public static void mouseOver(Widget target) {
		WidgetUtils.mouseOver(target);
	}

	public static void mouseOut(Widget target) {
		WidgetUtils.mouseOut(target);
	}

}
