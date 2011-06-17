package com.octo.gwt.test.internal.patchers;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.patchers.PatchClass;

@PatchClass(Widget.class)
class WidgetPatcher {

  // TODO : remove this when cast() will be patched
  // @PatchMethod
  static void onBrowserEvent(Widget widget, Event event) {
    DomEvent.fireNativeEvent(event, widget, widget.getElement());
  }

}
