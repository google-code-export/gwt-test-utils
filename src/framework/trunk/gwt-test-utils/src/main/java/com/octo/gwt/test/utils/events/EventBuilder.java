package com.octo.gwt.test.utils.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.overrides.OverrideEvent;

public class EventBuilder {

  public static EventBuilder create(int eventType) {
    EventBuilder builder = new EventBuilder();
    builder.event = new OverrideEvent(eventType);
    return builder;
  }

  private OverrideEvent event;

  // not instanciable outside this class
  private EventBuilder() {

  }

  public Event build() {
    return event;
  }

  public EventBuilder setAltKey(boolean altKey) {
    event.setOverrideAltKey(altKey);
    return this;
  }

  public EventBuilder setButton(int button) {
    event.setOverrideButton(button);
    return this;
  }

  public EventBuilder setCtrlKey(boolean ctrlKey) {
    event.setOverrideCtrlKey(ctrlKey);
    return this;
  }

  public EventBuilder setKeyCode(int keyCode) {
    event.setOverrideKeyCode(keyCode);
    return this;
  }

  public EventBuilder setMetaKey(boolean metaKey) {
    event.setOverrideMetaKey(metaKey);
    return this;
  }

  public EventBuilder setShiftKey(boolean shiftKey) {
    event.setOverrideShiftKey(shiftKey);
    return this;
  }

  public EventBuilder setTarget(Element target) {
    event.setOverrideTargetElement(target);
    return this;
  }

}
