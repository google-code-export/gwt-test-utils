package com.octo.gwt.test;

import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.events.Browser;

/**
 * <p>
 * Base class for test classes which need to manipulate (directly or indirectly)
 * GWT components.
 * </p>
 * 
 * <p>
 * AbstractGwtTest provides the mechanism which allows the instantiation of GWT
 * components in the Java Virtual Machine.
 * </p>
 * 
 * <p>
 * Class loading parameters used to instantiate classes referenced in tests can
 * be configured using the META-INF\gwt-test-utils.properties file of your
 * application.
 * </p>
 * 
 */
@RunWith(GwtTestRunner.class)
public abstract class AbstractGwtTest extends AbstractGwtConfigurableTest {

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
