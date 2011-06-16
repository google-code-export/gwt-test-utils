package com.octo.gwt.test.utils.events;

import org.junit.Assert;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.utils.events.EventDispatcher.BrowserErrorHandler;

/**
 * Provides several methods to simulate the occurring of browser events
 * (onClick, onKeyDown, onChange, etc.) caused by the interaction with a widget.
 * 
 * <p>
 * It relies on an internal instance of {@link EventDispatcher}.
 * </p>
 * 
 * @author Gael Lazzari
 * 
 * @see EventDispatcher
 */
public class Browser {

  private static final EventDispatcher DISPATCHER = EventDispatcher.newInstance(new BrowserErrorHandler() {

    public void onError(String errorMessage) {
      Assert.fail(errorMessage);
    }
  });

  /**
   * Simulates a blur event.
   * 
   * @param target The targeted widget.
   */
  public static void blur(Widget target) {
    DISPATCHER.blur(target);
  }

  /**
   * Simulates a change event.
   * 
   * @param target The targeted widget.
   */
  public static void change(Widget target) {
    DISPATCHER.change(target);
  }

  /**
   * Simulates a click event on the widget with the given index inside a
   * ComplexPanel.
   * 
   * @param panel The targeted panel.
   * @param index The index of the child widget to click inside the panel.
   */
  public static void click(ComplexPanel panel, int index) {
    DISPATCHER.click(panel, index);
  }

  /**
   * Simulates a click event on the Grid cell with the given indexes.
   * 
   * @param grid The targeted grid.
   * @param row The row index of the cell to click.
   * @param column The column index of the cell to click.
   */
  public static void click(Grid grid, int row, int column) {
    DISPATCHER.click(grid, row, column);
  }

  /**
   * Simulates a click event on the item of a MenuBar with the given index.
   * 
   * @param parent The targeted menu bar.
   * @param clickedItemIndex The index of the child widget to click inside the
   *          menu bar.
   */
  public static void click(MenuBar parent, int clickedItemIndex) {
    DISPATCHER.click(parent, clickedItemIndex);
  }

  /**
   * Simulates a click event on a particular MenuItem of a MenuBar.
   * 
   * @param parent The targeted menu bar.
   * @param clickedItem The widget to click inside the menu bar.
   */
  public static void click(MenuBar parent, MenuItem clickedItem) {
    DISPATCHER.click(parent, clickedItem);
  }

  /**
   * Simulates a click event on the item of a SuggestBox with the given index.
   * 
   * @param parent The targeted suggest box.
   * @param clickedItemIndex The index of the child widget to click inside the
   *          suggest box.
   */
  public static void click(SuggestBox parent, int clickedItemIndex) {
    DISPATCHER.click(parent, clickedItemIndex);
  }

  /**
   * Simulates a click event on a particular MenuItem of a SuggestBox.
   * 
   * @param parent The targeted suggest box.
   * @param clickedItem The child widget to click inside the suggest box.
   * 
   */
  public static void click(SuggestBox parent, MenuItem clickedItem) {
    DISPATCHER.click(parent, clickedItem);
  }

  /**
   * Simulates a click event.
   * 
   * @param The targeted widget.
   */
  public static void click(Widget target) {
    DISPATCHER.click(target);
  }

  /**
   * Simulates a dblclick event.
   * 
   * @param The targeted widget.
   */
  public static void dblClick(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONDBLCLICK).build());
  }

  /**
   * Simulates an occurring of the given event due to an interaction with the
   * target widget.
   * 
   * @param target The targeted widget.
   * @param browserErrorHandler The errorHandler to notify if a browser error
   *          occurs.
   * @param events Some events to dispatch.
   */
  public static void dispatchEvent(Widget target, Event... events) {
    DISPATCHER.dispatchEvent(target, events);
  }

  /**
   * <p>
   * Remove the text within a widget which implements HasText interface by
   * simulating a long backspace key press.
   * </p>
   * <p>
   * <ul>
   * <li>For each character in the text value of the widget, a
   * {@link KeyDownEvent} is triggered with value {@link KeyCodes#KEY_BACKSPACE}
   * . It can be prevented with normal effect.</li>
   * <li><strong>Only one</strong> {@link KeyUpEvent} is triggered with value
   * {@link KeyCodes#KEY_BACKSPACE}.</li>
   * <li>Than, a {@link BlurEvent} is triggered.</li>
   * <li>Finally, if at least one on the KeyDown events has not been prevented,
   * a {@link ChangeEvent} is triggered.</li>
   * </p>
   * <p>
   * Note that no {@link KeyPressEvent} would be triggered.
   * </p>
   * 
   * @param hasTextWidget The widget to fill. If this implementation actually
   *          isn't a {@link Widget} instance, nothing would be done.
   */
  public static void emptyText(HasText hasTextWidget) {
    DISPATCHER.emptyText(hasTextWidget);
  }

  /**
   * <p>
   * Remove the text within a widget which implements HasText interface
   * </p>
   * <p>
   * <ul>
   * <li>For each character in the text value of the widget, a
   * {@link KeyDownEvent} is triggered with value {@link KeyCodes#KEY_BACKSPACE}
   * . It can be prevented with normal effect.</li>
   * <li>Either one or x {@link KeyUpEvent} are triggered with value
   * {@link KeyCodes#KEY_BACKSPACE}, according to the chosen empty text
   * simulation (with x the number of character in the text value).</li>
   * <li>Than, a {@link BlurEvent} is triggered.</li>
   * <li>Finally, if at least one on the KeyDown events has not been prevented,
   * a {@link ChangeEvent} is triggered.</li>
   * </p>
   * <p>
   * Note that no {@link KeyPressEvent} would be triggered.
   * </p>
   * 
   * @param hasTextWidget The widget to fill. If this implementation actually
   *          isn't a {@link Widget} instance, nothing would be done.
   * @param longBackPress True if it should simulate a long backspace press or
   *          not.
   */
  public static void emptyText(HasText hasTextWidget, boolean longBackPress) {
    DISPATCHER.emptyText(hasTextWidget, longBackPress);
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
   * @param hasTextWidget The widget to fill. If this implementation actually
   *          isn't a {@link Widget} instance, nothing would be done.
   * @param value The value to fill. Cannot be null or empty.
   * @throws IllegalArgumentException if the value to fill is null or empty.
   */
  public static void fillText(HasText hasTextWidget, String value)
      throws IllegalArgumentException {
    DISPATCHER.fillText(hasTextWidget, value);
  }

  /**
   * Simulates a focus event.
   * 
   * @param target The targeted widget.
   */
  public static void focus(Widget target) {
    DISPATCHER.focus(target);
  }

  /**
   * Simulates a keydown event.
   * 
   * @param target The targeted widget.
   */
  public static void keyDown(Widget target, int keyCode) {
    DISPATCHER.keyDown(target, keyCode);
  }

  /**
   * Simulates a keypress event.
   * 
   * @param target The targeted widget.
   */
  public static void keyPress(Widget target, int keyCode) {
    DISPATCHER.keyPress(target, keyCode);
  }

  /**
   * Simulates a keyup event.
   * 
   * @param target The targeted widget.
   */
  public static void keyUp(Widget target, int keyCode) {
    DISPATCHER.keyUp(target, keyCode);
  }

  /**
   * Simulates a mousedown event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseDown(Widget target) {
    DISPATCHER.mouseDown(target);
  }

  /**
   * Simulates a mousemove event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseMove(Widget target) {
    DISPATCHER.mouseMove(target);
  }

  /**
   * Simulates a mouseout event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseOut(Widget target) {
    DISPATCHER.mouseOut(target);
  }

  /**
   * Simulates a mouseover event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseOver(Widget target) {
    DISPATCHER.mouseOver(target);
  }

  /**
   * Simulates a mouseup event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseUp(Widget target) {
    DISPATCHER.mouseUp(target);
  }

  /**
   * Simulates a mousewheel event.
   * 
   * @param target The targeted widget.
   */
  public static void mouseWheel(Widget target) {
    DISPATCHER.mouseWheel(target);
  }

  /**
   * <p>
   * Remove a fixed number of character from the text within a widget which
   * implements HasText interface by simulating a backspace key press.
   * </p>
   * <p>
   * <ul>
   * <li>x {@link KeyDownEvent} are triggered with value
   * {@link KeyCodes#KEY_BACKSPACE}, with x the number of backspace press passed
   * as parameter. It can be prevented with normal effect.</li>
   * <li>Than, x {@link KeyUpEvent} are triggered with value
   * {@link KeyCodes#KEY_BACKSPACE}, with x the number of backspace press passed
   * as parameter.</li>
   * <li>Than, a {@link BlurEvent} is triggered.</li>
   * <li>Finally, if at least one on the KeyDown events has not been prevented,
   * a {@link ChangeEvent} is triggered.</li>
   * </p>
   * <p>
   * Note that no {@link KeyPressEvent} would be triggered.
   * </p>
   * 
   * @param hasTextWidget The targeted widget. If this implementation actually
   *          isn't a {@link Widget} instance, nothing would be done.
   * @param backspacePressNumber The number of backspace key press to simulate.
   */
  public static void removeText(HasText hasTextWidget, int backspacePressNumber) {
    DISPATCHER.removeText(hasTextWidget, backspacePressNumber);
  }

  private Browser() {

  }

}
