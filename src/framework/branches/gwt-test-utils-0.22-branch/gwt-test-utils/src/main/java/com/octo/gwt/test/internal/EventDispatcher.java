package com.octo.gwt.test.internal;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.FinallyCommandTrigger;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.WidgetUtils;
import com.octo.gwt.test.utils.events.Browser;
import com.octo.gwt.test.utils.events.EventBuilder;

/**
 * 
 * Class responsible of dispatching {@link Event} object to {@link Widget}.
 * 
 * @author Gael Lazzari
 * 
 */
public class EventDispatcher {

  /**
   * An callback interface to handle error when dispatching a browser
   * {@link Event}.
   * 
   * @author Gael Lazzari
   * 
   */
  public static interface BrowserErrorHandler {

    /**
     * The callback method called when an error occurs.
     * 
     * @param errorMessage The error's message.
     */
    void onError(String errorMessage);
  }

  public static EventDispatcher newInstance(
      BrowserErrorHandler browserErrorHandler) {
    return new EventDispatcher(browserErrorHandler);
  }

  private final BrowserErrorHandler browserErrorHandler;

  private EventDispatcher(BrowserErrorHandler browserErrorHandler) {
    this.browserErrorHandler = browserErrorHandler;
  }

  public void blur(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONBLUR).build());
  }

  public void change(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONCHANGE).build());
  }

  public void click(ComplexPanel panel, int index) {
    Widget target = panel.getWidget(index);
    clickInternal(panel, target);
  }

  public void click(Grid grid, int row, int column) {
    Widget target = grid.getWidget(row, column);
    clickInternal(grid, target);
  }

  public void click(MenuBar parent, int clickedItemIndex) {
    click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
  }

  public void click(MenuBar parent, MenuItem clickedItem) {
    clickInternal(parent, clickedItem);
  }

  public void click(SimplePanel panel) {
    clickInternal(panel, panel.getWidget());
  }

  public void click(SuggestBox parent, int clickedItemIndex) {
    click(parent, WidgetUtils.getMenuItems(parent).get(clickedItemIndex));
  }

  public void click(SuggestBox parent, MenuItem clickedItem) {
    Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(clickedItem).build();

    assertCanApplyEvent(onClick);
    clickedItem.getCommand().execute();
  }

  public void click(Widget target) {
    clickInternal(target, target);
  }

  public void dblClick(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONDBLCLICK).build());
  }

  public void dispatchEvent(Widget target, boolean check, Event... events) {

    // run finally scheduled commands first because they may modify the DOM
    // structure
    FinallyCommandTrigger.triggerCommands();

    if (events.length == 0) {
      return;
    }

    prepareEvents(target, events);
    if (check) {
      assertCanApplyEvent(events[0]);
    }
    dispatchEventInternal(target, events);

    // run finally scheduled commands because some could have been scheduled
    // when the event was dispatched.
    FinallyCommandTrigger.triggerCommands();
  }

  public void dispatchEvent(Widget target, Event... events) {
    dispatchEvent(target, true, events);
  }

  public void emptyText(HasText hasTextWidget) {
    boolean changed = false;

    int baseLength = hasTextWidget.getText().length();
    for (int i = 0; i < baseLength; i++) {
      Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(
          KeyCodes.KEY_BACKSPACE).build();
      dispatchEvent((Widget) hasTextWidget, keyDownEvent);

      boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(
          keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);

      if (!keyDownEventPreventDefault) {
        hasTextWidget.setText(hasTextWidget.getText().substring(0,
            hasTextWidget.getText().length() - 1));
        changed = true;
      }

    }

    // don't have to check if the event can be dispatch since it's check
    // before
    Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(
        KeyCodes.KEY_BACKSPACE).build();
    dispatchEvent((Widget) hasTextWidget, keyUpEvent);

    dispatchEvent((Widget) hasTextWidget,
        EventBuilder.create(Event.ONBLUR).build());

    if (changed) {
      dispatchEvent((Widget) hasTextWidget,
          EventBuilder.create(Event.ONCHANGE).build());
    }

  }

  public void emptyText(HasText hasTextWidget, boolean longBackPress) {
    if (longBackPress) {
      emptyText(hasTextWidget);
    } else {
      removeText(hasTextWidget, hasTextWidget.getText().length());
    }
  }

  public void fillText(HasText hasTextWidget, boolean check, String value)
      throws IllegalArgumentException {
    if (value == null || "".equals(value)) {
      throw new IllegalArgumentException(
          "Cannot fill a null or empty text. If you intent to remove some text, use '"
              + Browser.class.getSimpleName() + ".emptyText(..)' instead");
    }
    if (!Widget.class.isInstance(hasTextWidget)) {
      return;
    }

    boolean changed = false;

    for (int i = 0; i < value.length(); i++) {

      int keyCode = value.charAt(i);

      // trigger keyDown and keyPress
      Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(
          keyCode).build();
      Event keyPressEvent = EventBuilder.create(Event.ONKEYPRESS).setKeyCode(
          keyCode).build();
      dispatchEvent((Widget) hasTextWidget, check, keyDownEvent, keyPressEvent);

      // check if one on the events has been prevented
      boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(
          keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);
      boolean keyPressEventPreventDefault = JavaScriptObjects.getBoolean(
          keyPressEvent, JsoProperties.EVENT_PREVENTDEFAULT);

      if (!keyDownEventPreventDefault && !keyPressEventPreventDefault) {
        hasTextWidget.setText(value.substring(0, i + 1));
        changed = true;
      }

      // trigger keyUp
      Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(keyCode).build();
      dispatchEvent((Widget) hasTextWidget, check, keyUpEvent);
    }

    // no need to check event anymore
    dispatchEvent((Widget) hasTextWidget, false,
        EventBuilder.create(Event.ONBLUR).build());

    if (changed) {
      dispatchEvent((Widget) hasTextWidget, false,
          EventBuilder.create(Event.ONCHANGE).build());
    }
  }

  public void fillText(HasText hasTextWidget, String value)
      throws IllegalArgumentException {
    fillText(hasTextWidget, true, value);
  }

  public void focus(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONFOCUS).build());
  }

  public void keyDown(Widget target, int keyCode) {
    dispatchEvent(target,
        EventBuilder.create(Event.ONKEYDOWN).setKeyCode(keyCode).build());
  }

  public void keyPress(Widget target, int keyCode) {
    dispatchEvent(target,
        EventBuilder.create(Event.ONKEYPRESS).setKeyCode(keyCode).build());
  }

  public void keyUp(Widget target, int keyCode) {
    dispatchEvent(target,
        EventBuilder.create(Event.ONKEYUP).setKeyCode(keyCode).build());
  }

  public void mouseDown(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEDOWN).build());
  }

  public void mouseMove(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEMOVE).build());
  }

  public void mouseOut(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOUT).build());
  }

  public void mouseOver(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEOVER).build());
  }

  public void mouseUp(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEUP).build());
  }

  public void mouseWheel(Widget target) {
    dispatchEvent(target, EventBuilder.create(Event.ONMOUSEWHEEL).build());
  }

  public void removeText(HasText hasTextWidget, int backspacePressNumber) {
    boolean changed = false;

    for (int i = 0; i < backspacePressNumber; i++) {
      Event keyDownEvent = EventBuilder.create(Event.ONKEYDOWN).setKeyCode(
          KeyCodes.KEY_BACKSPACE).build();
      Event keyUpEvent = EventBuilder.create(Event.ONKEYUP).setKeyCode(
          KeyCodes.KEY_BACKSPACE).build();
      dispatchEvent((Widget) hasTextWidget, keyDownEvent, keyUpEvent);

      boolean keyDownEventPreventDefault = JavaScriptObjects.getBoolean(
          keyDownEvent, JsoProperties.EVENT_PREVENTDEFAULT);

      if (!keyDownEventPreventDefault) {
        hasTextWidget.setText(hasTextWidget.getText().substring(0,
            hasTextWidget.getText().length() - 1));
        changed = true;
      }

    }

    dispatchEvent((Widget) hasTextWidget,
        EventBuilder.create(Event.ONBLUR).build());

    if (changed) {
      dispatchEvent((Widget) hasTextWidget,
          EventBuilder.create(Event.ONCHANGE).build());
    }

  }

  private void assertCanApplyEvent(Event event) {
    Element targetElement = event.getEventTarget().cast();

    if (!isVisible(targetElement)) {
      browserErrorHandler.onError("Cannot dispatch '"
          + event.getType()
          + "' event : the targeted widget and its possible parents have to be visible");
    } else if (isDisabled(targetElement)) {
      browserErrorHandler.onError("Cannot dispatch '" + event.getType()
          + "' event : the targeted widget has to be enabled : "
          + targetElement.toString());
    }
  }

  private void clickInternal(Widget parent, UIObject target) {
    Event onMouseOver = EventBuilder.create(Event.ONMOUSEOVER).setTarget(target).build();
    Event onMouseDown = EventBuilder.create(Event.ONMOUSEDOWN).setTarget(target).setButton(
        Event.BUTTON_LEFT).build();
    Event onMouseUp = EventBuilder.create(Event.ONMOUSEUP).setTarget(target).setButton(
        Event.BUTTON_LEFT).build();
    Event onClick = EventBuilder.create(Event.ONCLICK).setTarget(target).build();

    dispatchEvent(parent, onMouseOver, onMouseDown, onMouseUp, onClick);
  }

  private void dispatchEventInternal(Widget target, Event... events) {
    for (Event event : events) {
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

            JavaScriptObjects.setProperty(event,
                JsoProperties.EVENT_RELATEDTARGET, relatedTargetElement);

            break;
        }
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
  }

  private boolean isDisabled(Element element) {
    return element.getPropertyBoolean("disabled");
  }

  private boolean isVisible(Element element) {
    if (element == null) {
      return true;
    }

    return UIObject.isVisible(element) ? isVisible(element.getParentElement())
        : false;
  }

  private void prepareEvents(Widget target, Event... events) {
    for (Event event : events) {
      Element effectiveTarget = JavaScriptObjects.getObject(event,
          JsoProperties.EVENT_TARGET);

      if (effectiveTarget == null) {
        effectiveTarget = target.getElement();
        JavaScriptObjects.setProperty(event, JsoProperties.EVENT_TARGET,
            effectiveTarget);
      }
    }
  }

}
