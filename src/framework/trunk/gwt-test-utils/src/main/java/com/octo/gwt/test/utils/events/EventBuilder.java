package com.octo.gwt.test.utils.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.patchers.dom.DOMProperties;
import com.octo.gwt.test.internal.utils.PropertyContainerUtils;
import com.octo.gwt.test.utils.GwtReflectionUtils;

public class EventBuilder {

  public static EventBuilder create(int eventType) {
    EventBuilder builder = new EventBuilder();

    // instanciate the event to build
    builder.event = GwtReflectionUtils.instantiateClass(Event.class);
    PropertyContainerUtils.setProperty(builder.event, DOMProperties.EVENT_TYPE,
        eventType);

    return builder;
  }

  private Event event;

  // not instanciable outside this class
  private EventBuilder() {

  }

  public Event build() {
    return event;
  }

  public EventBuilder setAltKey(boolean altKey) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_KEY_ALT,
        altKey);
    return this;
  }

  public EventBuilder setButton(int button) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_BUTTON,
        button);
    return this;
  }

  public EventBuilder setCtrlKey(boolean ctrlKey) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_KEY_CTRL,
        ctrlKey);
    return this;
  }

  public EventBuilder setKeyCode(int keyCode) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_KEYCODE,
        keyCode);
    return this;
  }

  public EventBuilder setMetaKey(boolean metaKey) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_KEY_META,
        metaKey);
    return this;
  }

  public EventBuilder setShiftKey(boolean shiftKey) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_KEY_SHIFT,
        shiftKey);
    return this;
  }

  public EventBuilder setTarget(Element target) {
    PropertyContainerUtils.setProperty(event, DOMProperties.EVENT_TARGET,
        target);
    return this;
  }

}
