package com.octo.gwt.test.internal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class WidgetChangeHandlerManager implements AfterTestCallback {

  private static final WidgetChangeHandlerManager INSTANCE = new WidgetChangeHandlerManager();

  public static WidgetChangeHandlerManager get() {
    return INSTANCE;
  }

  private final List<WidgetChangeHandler> widgetChangeHandlers;

  private WidgetChangeHandlerManager() {
    widgetChangeHandlers = new ArrayList<WidgetChangeHandler>();

    AfterTestCallbackManager.get().registerCallback(this);
  }

  public void afterTest() {
    this.widgetChangeHandlers.clear();
  }

  public void fireOnAttach(Widget widget) {
    for (WidgetChangeHandler widgetChangeHandler : widgetChangeHandlers) {
      if (widgetChangeHandler.onAttach(widget)) {
        break; // event has been handled, no more dispatching
      }
    }
  }

  public void fireOnDetach(Widget widget) {
    for (WidgetChangeHandler widgetChangeHandler : widgetChangeHandlers) {
      if (widgetChangeHandler.onDetach(widget)) {
        break; // event has been handled, no more dispatching
      }
    }
  }

  public void fireSetIdEvent(UIObject o, String newId) {
    for (WidgetChangeHandler widgetChangeHandler : widgetChangeHandlers) {
      if (widgetChangeHandler.onSetId(o, newId)) {
        break; // event has been handled, no more dispatching
      }
    }
  }

  public void registerWidgetChangedHandler(
      WidgetChangeHandler widgetChangeHandler) {
    this.widgetChangeHandlers.add(widgetChangeHandler);
  }

}
