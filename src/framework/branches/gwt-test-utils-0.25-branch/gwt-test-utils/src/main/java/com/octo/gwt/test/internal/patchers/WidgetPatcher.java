package com.octo.gwt.test.internal.patchers;

import java.util.Set;

import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.patchers.PatchClass;
import com.octo.gwt.test.patchers.PatchMethod;

@PatchClass(Widget.class)
class WidgetPatcher {

  @PatchMethod
  static void onBrowserEvent(Widget widget, Event event) {
    switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEOVER:
        // Only fire the mouse over event if it's coming from outside this
        // widget.
      case Event.ONMOUSEOUT:
        // Only fire the mouse out event if it's leaving this
        // widget.
        Element related = event.getRelatedEventTarget().cast();
        if (related != null && widget.getElement().isOrHasChild(related)) {
          return;
        }
        break;
    }

    Set<Widget> applied = new HashSet<Widget>();
    onBrowserEventWithBubble(widget, event, applied);
  }

  private static void onBrowserEventWithBubble(Widget widget, Event event,
      Set<Widget> applied) {

    if (widget == null) {
      return;
    }

    if (applied.contains(widget)) {
      return;
    }

    boolean eventStopped = JavaScriptObjects.getBoolean(event,
        JsoProperties.EVENT_IS_STOPPED);

    if (eventStopped) {
      return;
    }

    // fire
    DomEvent.fireNativeEvent(event, widget, widget.getElement());

    applied.add(widget);

    // process bubbling
    onBrowserEventWithBubble(widget.getParent(), event, applied);
  }

}
