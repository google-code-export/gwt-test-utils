package com.octo.gwt.test.utils.events;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.Event;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Builder for complex {@link Event}.
 * 
 * @author Gael Lazzari
 * 
 */
public class EventBuilder {

  /**
   * Create a builder to configure an {@link Event} of the specified type.
   * 
   * @param eventType The type of the browser event to build.
   * @return The builder which can be used to configure the event to build.
   */
  public static EventBuilder create(int eventType) {
    EventBuilder builder = new EventBuilder();

    JavaScriptObjects.setProperty(builder.event, JsoProperties.EVENT_TYPE,
        eventType);

    return builder;
  }

  private final Event event;

  private EventBuilder() {
    event = GwtReflectionUtils.instantiateClass(Event.class);
  }

  /**
   * Return the configured {@link Event}.
   * 
   * @return The event which has been configured by the current builder.
   */
  public Event build() {
    return event;
  }

  public EventBuilder setAltKey(boolean altKey) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_KEY_ALT, altKey);
    return this;
  }

  public EventBuilder setButton(int button) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_BUTTON, button);
    return this;
  }

  public EventBuilder setCtrlKey(boolean ctrlKey) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_KEY_CTRL, ctrlKey);
    return this;
  }

  public EventBuilder setKeyCode(int keyCode) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_KEYCODE, keyCode);
    return this;
  }

  public EventBuilder setMetaKey(boolean metaKey) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_KEY_META, metaKey);
    return this;
  }

  public EventBuilder setRelatedTarget(EventTarget target) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_RELATEDTARGET,
        target);
    return this;
  }

  public EventBuilder setShiftKey(boolean shiftKey) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_KEY_SHIFT,
        shiftKey);
    return this;
  }

  public EventBuilder setTarget(Element target) {
    JavaScriptObjects.setProperty(event, JsoProperties.EVENT_TARGET, target);
    return this;
  }

}
